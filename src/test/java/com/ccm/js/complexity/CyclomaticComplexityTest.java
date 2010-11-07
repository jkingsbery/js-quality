package com.ccm.js.complexity;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class CyclomaticComplexityTest {

	private static final String TEST_DIR = "src/test/resources/com/ccm/js/complexity";
	private static final String FOR1 = "src/test/resources/com/ccm/js/complexity/for1.js";
	private static final String IF1 = "src/test/resources/com/ccm/js/complexity/if1.js";
	private static final String IF2 = "src/test/resources/com/ccm/js/complexity/if2.js";
	private static final String IF2_A = "src/test/resources/com/ccm/js/complexity/if2a.js";
	private static final String SIMPLE1 = "src/test/resources/com/ccm/js/complexity/simple1.js";
	private static final String TERNARY1= "src/test/resources/com/ccm/js/complexity/ternary1.js";
	
	@Parameters
    public static Collection<Object[]> data(){
    	return Arrays.asList(new Object[][]{
    			{"simple1",0,1},
    			{"ternary1",1,1},
    			{"if1",1,1},
    			{"if2",2,1},
    			{"for1",1,1},
    	});
    }

	private String script;
	private int decisionPoints;
	private int exitPoints;
	
    public CyclomaticComplexityTest(String script, int decisionPoints, int exitPoints){
    	this.script=script;
    	this.decisionPoints=decisionPoints;
    	this.exitPoints=exitPoints;
    }
    
    @Test
    public void testComplexity() throws IOException{
    	assertEquals(this.decisionPoints-this.exitPoints+2,
    			new CyclomaticComplexity().run(new FileReader(TEST_DIR + "/" + script + ".js")));
    }
    
    @Test
    public void testDecisionPoints() throws IOException{
		assertEquals(decisionPoints,new CyclomaticComplexity().process(new FileReader(TEST_DIR + "/" + script+ ".js")).getDecisions());
    }
    
    @Test
    public void testExitPoints() throws IOException{
		assertEquals(exitPoints,new CyclomaticComplexity().process(new FileReader(TEST_DIR + "/" + script+ ".js")).getExitPoints());
    }
}
