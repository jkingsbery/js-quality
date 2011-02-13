package net.kingsbery.js;

import java.util.ArrayList;
import java.util.List;

import net.kingsbery.js.complexity.CyclomaticComplexity.FunctionTreeNode;


public class Result {
    
    private String filePath;
    private int linesOfCode;
    private int violationCount;
    public List<Result> children = new ArrayList<Result>();
    private FunctionTreeNode functionTreeNode;
    private boolean parent=false;
    public Result(String filePath, int linesOfCode, int violationCount, FunctionTreeNode functionTreeNode) {
        this.filePath=filePath;
        this.linesOfCode = linesOfCode;
        this.violationCount = violationCount;
        this.functionTreeNode = functionTreeNode;
    }

    public Result(String path, List<Result> childResults) {
        this.filePath=path;
        this.children=childResults;
        this.parent=true;
    }

    public int getTotalLinesOfCode() {
        int count = linesOfCode;
        for (Result child : children) {
            count += child.getTotalLinesOfCode();
        }
        return count;
    }

    public int getTotalViolations() {
        int count = violationCount;
        for (Result child : children) {
            count += child.getTotalViolations();
        }
        return count;
    }

    public double getCompliance() {
        return 1 - ((double) getTotalViolations() / (double) getTotalLinesOfCode());
    }

    public int getComplexity() {
        if(parent){
            int complexity=0;
            for (Result child : children) {
                complexity += child.getComplexity();
            }
            return complexity;
        }else{
            return this.functionTreeNode.getComplexity();
        }
    }

    public long getFunctions() {
        if(parent){

            int functionCount=0;
            for (Result child : children) {
                functionCount += child.getFunctions();
            }
            return functionCount;
        }else{
            return this.functionTreeNode.getFunctionCount();
        }
    }
}
