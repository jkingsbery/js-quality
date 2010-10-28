package com.ccm.js.lint;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.mozilla.javascript.Scriptable;

import com.ccm.js.lint.Issue.IssueBuilder;

public class JSLint {

	public static class JsLintException extends RuntimeException {

		public JsLintException(Throwable cause) {
			super(cause);
		}

	}

	static String readerToString(Reader reader) throws IOException {
		StringBuffer sb = new StringBuffer();
		int c;
		while ((c = reader.read()) != -1) {
			sb.append((char) c);
		}
		return sb.toString();
	}

	public Iterator<Issue> run(String fileName, Reader script) {
		try {
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByExtension("js");
			Reader jslint = new InputStreamReader(this.getClass()
					.getClassLoader().getResourceAsStream("jslint.js"));
			assert engine != null;
			assert jslint != null;

			engine.eval(jslint);
			Invocable invocableEngine = (Invocable) engine;
			invocableEngine.invokeFunction("JSLINT", readerToString(script));
			System.out.println(engine.get("JSLINT"));
			Scriptable x = (Scriptable) engine.get("JSLINT");
			Scriptable errors = (Scriptable) x.get("errors", x);
			return new IssueIterator(fileName, errors);
		} catch (ScriptException e) {
			throw new JsLintException(e);
		} catch (NoSuchMethodException e) {
			throw new JsLintException(e);
		} catch (IOException e) {
			throw new JsLintException(e);
		}
	}

	public Iterator<Issue> run(File js) {
		try {
			return this.run(js.getAbsolutePath(),new FileReader(js));
		} catch (FileNotFoundException e) {
			throw new JsLintException(e);
		}

	}

	private static final class IssueIterator implements Iterator<Issue> {

		private int marker = 0;
		private Scriptable errors;
		private Double errorCount;
		private String fileName;

		public IssueIterator(String fileName, Scriptable errors) {
			this.errors = errors;
			this.errorCount = (Double) errors.get("length", errors);
			this.fileName=fileName;
		}

		@Override
		public boolean hasNext() {
			return marker < errorCount;
		}

		@Override
		public Issue next() {
			Issue issue = IssueBuilder.fromJavaScript(fileName,(Scriptable) errors.get(marker,
					errors));
			marker++;
			return issue;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

}
