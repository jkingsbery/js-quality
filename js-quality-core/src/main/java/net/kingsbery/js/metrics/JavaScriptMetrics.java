package net.kingsbery.js.metrics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.kingsbery.js.Result;
import net.kingsbery.js.complexity.CyclomaticComplexity;
import net.kingsbery.js.lint.JSLint;

public class JavaScriptMetrics {

    /**
     * Which file/directory to measure
     */
    private File directory;

    public JavaScriptMetrics(String string) {
        directory = new File(string);
    }

    public void addMeasure(JavaScriptProcessor jsLint) {
        // TODO Auto-generated method stub

    }

    public Result run() {
        return runHelper(directory);
    }

    private Result runHelper(File file) {
        List<Result> childResults = new ArrayList<Result>();
        // Map<String, Result> results = new HashMap<String, Result>();
        if (!file.isDirectory() && file.getName().endsWith("js")) {
            childResults.add(processFile(file));
        } else {
            for (File sub : file.listFiles()) {
                childResults.add(runHelper(sub));
            }
        }
        return new Result(file.getPath(), childResults);
    }

    private Result processFile(File file) {
        try {
            int linesOfCode = new JavaScriptSize().process(file)
                    .getLinesOfCode();
            int violations = new JSLint().getList(file.getPath(),
                    new FileReader(file)).size();
            return new Result(file.getPath(), linesOfCode, violations,
                    new CyclomaticComplexity().process(new FileReader(file)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
