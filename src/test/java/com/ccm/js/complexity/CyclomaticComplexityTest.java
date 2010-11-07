package com.ccm.js.complexity;

import static org.junit.Assert.assertEquals;

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

	@Parameters
    public static Collection<Object[]> data(){
    	return Arrays.asList(new Object[][]{
    			{"simple1",0,1,1,1},
    			{"ternary1",1,1,1,2},
    			{"if1",1,1,1,2},
    			{"if2",2,1,1,3},
    			{"for1",1,1,1,2},
    			{"return1",3+0,4+1,2,2},
    	});
    }

	private String script;
	private int decisionPoints;
	private int exitPoints;
	private int functions;
	private int complexity;
	
    public CyclomaticComplexityTest(String script, int decisionPoints, int exitPoints,int functions,int complexity){
    	this.script=script;
    	this.decisionPoints=decisionPoints;
    	this.exitPoints=exitPoints;
    	this.functions = functions;
    	this.complexity=complexity;
    }
    
    @Test
    public void testComplexity() throws IOException{
    	assertEquals(complexity,
    			new CyclomaticComplexity().run(new FileReader(TEST_DIR + "/" + script + ".js")));
    }
    
    @Test
    public void testDecisionPoints() throws IOException{
		assertEquals(decisionPoints,new CyclomaticComplexity().process(new FileReader(TEST_DIR + "/" + script+ ".js")).getTotalDecisions());
    }
    
    @Test
    public void testExitPoints() throws IOException{
		assertEquals(exitPoints,new CyclomaticComplexity().process(new FileReader(TEST_DIR + "/" + script+ ".js")).getTotalExits());
    }
}
