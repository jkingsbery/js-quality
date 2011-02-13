package net.kingsbery.js.metrics;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import net.kingsbery.js.Result;
import net.kingsbery.js.complexity.CyclomaticComplexity;
import net.kingsbery.js.lint.Issue;
import net.kingsbery.js.lint.JSLint;
import net.kingsbery.js.metrics.JavaScriptSize.JavaScriptSizeResult;

public class RulesCompliance {

    public static void main(String args[]) throws IOException {
        String fileName = args[0];
        JavaScriptMetrics metrics = new JavaScriptMetrics(fileName);
        metrics.addMeasure(new CyclomaticComplexity());
        metrics.addMeasure(new JSLint());
        Result result = metrics.run();
        System.out.println(result.getTotalLinesOfCode());
        System.out.println(result.getTotalViolations());
        System.out.println(result.getCompliance());

    }

}
