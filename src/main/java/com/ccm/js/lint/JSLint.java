package com.ccm.js.lint;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.Scriptable;

import com.ccm.js.lint.Issue.IssueBuilder;



public class JSLint {

	static String readerToString(Reader reader) throws IOException {
        StringBuffer sb = new StringBuffer();
        int c;
        while ((c = reader.read()) != -1) {
            sb.append((char) c);
        }
        return sb.toString();
    }
	
	public void run(Reader script) throws ScriptException, NoSuchMethodException, IOException{
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByExtension("js");
		Reader jslint = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("jslint.js"));
		assert engine !=null;
		assert jslint !=null;
		
		engine.eval(jslint);
		Invocable invocableEngine = (Invocable) engine;
		invocableEngine.invokeFunction("JSLINT", readerToString(script));
		System.out.println(engine.get("JSLINT"));
		Scriptable x = (Scriptable) engine.get("JSLINT");
		Scriptable errors = (Scriptable) x.get("errors", x);
		double errorCount = (Double) errors.get("length", errors);
		System.out.println("Found " + errorCount + " errors.");
		for(int i=0; i< errorCount; i++){
			System.out.println(IssueBuilder.fromJavaScript((Scriptable)errors.get(i, errors)));
		}
		System.out.println(errors);

		
	}
	
	
}
