package com.ccm.js.metrics;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ccm.js.lint.Issue;
import com.ccm.js.lint.JSLint;
import com.ccm.js.metrics.JavaScriptSize.JavaScriptSizeResult;

public class RulesCompliance {

	public static class RulesComplianceResult{
		int linesOfCode;
		int violationCount;
		public List<RulesComplianceResult> children =new ArrayList<RulesComplianceResult>();
		public RulesComplianceResult(int linesOfCode, int count) {
			this.linesOfCode=linesOfCode;
			this.violationCount=count;
		}
		
		public int getTotalLinesOfCode(){
			int count = linesOfCode;
			for(RulesComplianceResult child : children){
				count+=child.getTotalLinesOfCode();
			}
			return count;
		}
		
		public int getTotalViolations(){
			int count = violationCount;
			for(RulesComplianceResult child : children){
				count+=child.getTotalViolations();
			}
			return count;
		}

		public double getCompliance(){
			return 100 - ((double)getTotalViolations() / (double)getTotalLinesOfCode()) * 100;	
		}
		
		public String toString(){
			return Double.toString(getCompliance());
		}
	}
	
	public RulesComplianceResult process(File file) throws IOException{
		if(file.isDirectory()){
			RulesComplianceResult result = new RulesComplianceResult(0,0);
			for(File child : file.listFiles()){
				if(child.isDirectory() || child.getPath().endsWith(".js")){
					result.children.add(process(child));
				}
			}
			return result;
		}
		else{
		JavaScriptSizeResult size = new JavaScriptSize().process(file);
		Iterator<Issue> issues = new JSLint().run(file);
		int count=0;
		while(issues.hasNext()){
			Issue issue = issues.next();
			System.out.println(issue);
			count++;
		}
		System.out.println(count);
		return new RulesComplianceResult(size.getLinesOfCode(),count);
		}
	}
	
	public static void main(String args[]) throws IOException{
		System.out.println(new RulesCompliance().process(new File(args[0])));
	}
	
}
