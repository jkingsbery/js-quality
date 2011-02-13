package net.kingsbery.js.metrics;

import static org.junit.Assert.assertTrue;
import net.kingsbery.js.Result;
import net.kingsbery.js.complexity.CyclomaticComplexity;
import net.kingsbery.js.lint.JSLint;

import org.junit.Test;

public class JavaScriptMetricsTest {

    String fileName = "src/test/resources/jquery.form.js";

    @Test
    public void metricsResulttShouldReturnComplexity(){
        JavaScriptMetrics metrics = new JavaScriptMetrics("src/test/resources");
        metrics.addMeasure(new CyclomaticComplexity());
        metrics.addMeasure(new JSLint());
        Result result = metrics.run();
        assertTrue(result.getComplexity() > 0);
    }
    
    @Test
    public void metricsResultShouldReturnViolations(){
        JavaScriptMetrics metrics = new JavaScriptMetrics("src/test/resources");
        metrics.addMeasure(new CyclomaticComplexity());
        metrics.addMeasure(new JSLint());
        Result result = metrics.run();
        assertTrue(result.getTotalLinesOfCode() > 0);
    }

    
    @Test
    public void metricsResultShouldReturnCompliance(){
        JavaScriptMetrics metrics = new JavaScriptMetrics("src/test/resources");
        metrics.addMeasure(new CyclomaticComplexity());
        metrics.addMeasure(new JSLint());
        Result result = metrics.run();
        double compliance = result.getCompliance();
        assertTrue("Compliance is expected to be between 0 and 1, exclusive, but was " + compliance,0< compliance && compliance < 1);
    }
}
