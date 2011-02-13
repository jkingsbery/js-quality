package net.kingsbery.js.mojo;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import net.kingsbery.js.Result;
import net.kingsbery.js.metrics.JavaScriptMetrics;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Compiles stats on the project's JavaScript code. In the current state, this Mojo only
 * will record raw stats; it does not record derived stats. For example, it will return the
 * number of violations and the number of lines of code, but it does not record rules compliance
 * 
 * @author James Kingsbery (06jameskingsbery@gmail.com)
 * @goal stat
 * @phase site
 */
public class JsMojo extends AbstractMojo {

    /**
     * The directory that contains the main JavaScript code.
     * 
     * @parameter
     */
    private File mainJs;

    /**
     * @parameter
     */
    private File testJs;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        long start = System.currentTimeMillis();
        try {
            PrintWriter jsStatsFile = new PrintWriter(new FileWriter(
                    "jsStats.csv", true));
            Result result;
            result = new JavaScriptMetrics(mainJs).run();
            getLog().info("Total Lines of Code: " + result.getTotalLinesOfCode());
            getLog().info("Total Violations: " + result.getTotalViolations());
            getLog().info("Cyclomatic Complexity: " + result.getComplexity());
            getLog().info("Compliance: " + result.getCompliance());
            jsStatsFile.println(System.currentTimeMillis() + ", " + result.getTotalLinesOfCode() + ", " + result.getFunctions() + ", " +
                    + result.getTotalViolations() + ", "
                    + result.getComplexity());
            jsStatsFile.close();
            getLog().info("Completed JavaScript measurements in " + (System.currentTimeMillis() - start) + " milliseconds");
        } catch (Exception e) {
            getLog().error(e);
            throw new MojoFailureException(
                    "There was a problem getting JavaScript stats");
        }
    }

    public void setMainCode(File file) {
        this.mainJs = file;
    }

    public static void main(String args[]) throws MojoExecutionException,
            MojoFailureException {
        JsMojo mojo = new JsMojo();
        mojo.setMainCode(new File("../js-quality-core/src/test/resources"));
        mojo.execute();
    }
}
