package net.kingsbery.js.lint;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;

import net.kingsbery.js.lint.Issue.IssueBuilder;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

public class JSLint {

	public static class JsLintException extends RuntimeException {

		public JsLintException(Throwable cause) {
			super(cause);
		}

		public JsLintException(String string) {
			// TODO Auto-generated constructor stub
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
			@SuppressWarnings("deprecation")
			Context cx = Context.enter();
			try {
				Scriptable scope = cx.initStandardObjects();
				Reader jslint = new InputStreamReader(this.getClass()
						.getClassLoader().getResourceAsStream("jslint.js"));

				assert jslint != null;

				cx.evaluateReader(scope, jslint, "jslint.js", 0, null);

				Object fObj = scope.get("JSLINT", scope);
				if (!(fObj instanceof Function)) {
					throw new JsLintException(
							"jslint.js does not seem valid - cannot fetch JSLINT object.");
				} else {
					Function f = (Function) fObj;
					f.call(cx, scope, scope,
							new Object[] { readerToString(script) });
					scope.get("JSLINT", scope);
					Scriptable x = (Scriptable) scope.get("JSLINT", scope);
					Scriptable errors = (Scriptable) x.get("errors", x);
					return new IssueIterator(fileName, errors);
				}
			} finally {
				Context.exit();
			}
		} catch (IOException e) {
			throw new JsLintException(e);
		}
	}

	public Iterator<Issue> run(String fileName, InputStream stream){
		assert stream != null;
		return this.run(fileName, new InputStreamReader(stream));
	}
	
	public Iterator<Issue> run(File js) {
		try {
			return run(js.getAbsolutePath(), new FileInputStream(js));
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
			this.fileName = fileName;
		}

		@Override
		public boolean hasNext() {
			return marker < errorCount;
		}

		@Override
		public Issue next() {
			Object error = errors.get(marker, errors);
			if (error instanceof Scriptable) {

				Issue issue = IssueBuilder.fromJavaScript(fileName,
						(Scriptable) error);
				marker++;
				return issue;
			}else{
				System.out.println("Unexpected: " + error);
				return null;
			}
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

}