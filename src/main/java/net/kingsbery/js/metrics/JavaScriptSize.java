package net.kingsbery.js.metrics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JavaScriptSize {

	public class JavaScriptSizeResult{
		int physicalLines;
		public int commentLines;
		int blankLines;
		
		public int getLinesOfCode(){
			return physicalLines - blankLines - commentLines;
		}
	}
	
	public JavaScriptSizeResult process(File file) throws IOException{
		JavaScriptSizeResult result = new JavaScriptSizeResult();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;
		boolean commentMode=false;
		while( (line = reader.readLine())!=null){
			result.physicalLines++;
			if(line.trim().startsWith("/*")){
				commentMode=true;
			}
			if(line.replace("//", "").replace("/*","").replace("/**", "").replace("*/", "").trim().isEmpty()){
				result.blankLines++;
			}
			else if(line.trim().startsWith("//") || commentMode){
				result.commentLines++;
			}
			if(line.trim().endsWith("*/")){
				commentMode=false;
			}
		}
		return result;
	}
}
