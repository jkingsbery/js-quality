package net.kingsbery.js.mojo;

import java.io.File;
import java.io.IOException;

import net.kingsbery.js.metrics.RulesCompliance;
import net.kingsbery.js.metrics.RulesCompliance.RulesComplianceResult;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;


/**
 * Compiles stats on the project's JavaScript code.
 * 
 * @goal stat
 */
public class JsMojo extends AbstractMojo
{
    
    /**
     * The directory that contains the main JavaScript code.
     * @parameter
     */
    private File mainJs;
    
    /**
     * @parameter
     */
    private File testJs;
    
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException
    {
        RulesComplianceResult result;
        try {
            result = new RulesCompliance().process(mainJs);
            System.out.println(result.getTotalLinesOfCode());
            System.out.println(result.getTotalViolations());
            System.out.println(result.getCompliance());
        } catch (IOException e) {
            getLog().error(e);
            throw new MojoFailureException("There was a problem getting JavaScript stats");
        }
    }
    
    public void setMainCode(File file){
        this.mainJs=file;
    }
    
    public static void main(String args[]) throws MojoExecutionException, MojoFailureException{
        JsMojo mojo = new JsMojo();
        mojo.setMainCode(new File("../js-quality-core/src/test/resources"));
        mojo.execute();
    }
}
