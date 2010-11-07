package com.ccm.js.complexity;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.mozilla.javascript.CompilerEnvirons;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.FunctionNode;
import org.mozilla.javascript.Node;
import org.mozilla.javascript.Parser;
import org.mozilla.javascript.ScriptOrFnNode;
import org.mozilla.javascript.Token;

public class CyclomaticComplexity {

	public static class FunctionTreeNode{
		ComplexityTally tally=new ComplexityTally(0,0);
		FunctionTreeNode(){
		}
		List<FunctionTreeNode> children=new ArrayList<FunctionTreeNode>();
		
		int getTotalDecisions() {
			int decisions = tally.decisions;
			for (FunctionTreeNode child : children) {
				decisions += child.getTotalDecisions();
			}
			return decisions;

		}

		int getTotalExits() {
			int exits = tally.exitPoints;
			for (FunctionTreeNode child : children) {
				exits += child.getTotalExits();
			}
			return exits;
		}

		int getComplexity(){
			int complexity = tally.decisions-tally.exitPoints+2;
			for(FunctionTreeNode child : children){
				complexity+=child.getComplexity();
			}
			return complexity;
			
		}
	}
	
	public static class ComplexityTally{
		private final int decisions;
		private final int exitPoints;
		
		public ComplexityTally(int decisions, int exitPoints){
			this.decisions=decisions;
			this.exitPoints = (decisions+1<=exitPoints) ? decisions+1 : exitPoints;
		}
		
		public int getDecisions(){
			return decisions;
		}
		
		public int getExitPoints(){
			return exitPoints;
		}
		
		public int getComplexity(){
			return decisions-exitPoints+2;
		}

		public ComplexityTally add(ComplexityTally other) {
			
			return new ComplexityTally(this.decisions+other.decisions,
					this.exitPoints+other.exitPoints);
		}
	}
	
	
	public int run(Reader script) throws IOException {

		CompilerEnvirons compilerEnvirons = new CompilerEnvirons();
		ErrorReporter errorReporter = new DefaultErrorReporter();
		Parser parser = new Parser(compilerEnvirons, errorReporter);
		ScriptOrFnNode parse = parser.parse(script, "some.js", 0);
		return process(parse).getComplexity();
	}
	
	public FunctionTreeNode process(Reader script) throws IOException {

		CompilerEnvirons compilerEnvirons = new CompilerEnvirons();
		ErrorReporter errorReporter = new DefaultErrorReporter();
		Parser parser = new Parser(compilerEnvirons, errorReporter);
		ScriptOrFnNode parse = parser.parse(script, "some.js", 0);
		return process(parse);
	}

	public FunctionTreeNode process(ScriptOrFnNode node) {
		FunctionTreeNode result = process(node, 0);
		//FIXME move this into a getter for exitPoints
		if(result.tally.exitPoints==0){
			result.tally = result.tally.add(new ComplexityTally(0,1));
		}
		return result;
	}

	public FunctionTreeNode process(ScriptOrFnNode node, int depth) {
		FunctionTreeNode result = new FunctionTreeNode();
		System.out.println("Function count: " + node.getFunctionCount());

		Node child = node.getFirstChild();

		// Handle non-function children.
		while (child != null) {
			result.tally=result.tally.add(computeNodeComplexity(child, depth));
			child = child.getNext();
		}

		for (int i = 0; i < node.getFunctionCount(); i++) {
			FunctionNode function = node.getFunctionNode(i);
			String name = ((FunctionNode) function).getFunctionName();
			name = name.trim().isEmpty() ? "<anonymous>" : name;
			System.out.println("Function name: " + name);
			result.children.add(process(function));
		}
		return result;
	}

	private ComplexityTally computeNodeComplexity(Node next, int depth) {
		ComplexityTally result = new ComplexityTally(0,0);
		
		System.out.println(indent(depth) + TokenType.fromVal(next.getType()));
		Node child = next.getFirstChild();
		while (child != null) {
			result = result.add(computeNodeComplexity(child, depth + 1));
			child = child.getNext();
		}
		if(isDecisionPoint(next)){
			return new ComplexityTally(1,0);
		}else if(isExitPoint(next)){
			return new ComplexityTally(0,1);
		} else{
			return result;
		}
	}

	private boolean isExitPoint(Node next) {
		return next.getType()==TokenType.RETURN.val || next.getType()==TokenType.RETURN_RESULT.val;
	}

	public boolean isDecisionPoint(Node next) {
		return next.getType()==TokenType.IFNE.val || next.getType()==Token.IFEQ
		 || next.getType()==TokenType.HOOK.val;
	}

	private String indent(int depth) {
		String str = "";
		for (int i = 0; i < depth; i++) {
			str += "\t";
		}
		return str;
	}

	public static class DefaultErrorReporter implements ErrorReporter {

		@Override
		public void error(String arg0, String arg1, int arg2, String arg3,
				int arg4) {
			// TODO Auto-generated method stub

		}

		@Override
		public EvaluatorException runtimeError(String arg0, String arg1,
				int arg2, String arg3, int arg4) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void warning(String arg0, String arg1, int arg2, String arg3,
				int arg4) {
			// TODO Auto-generated method stub

		}

	}
}
