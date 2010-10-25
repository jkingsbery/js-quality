package com.ccm.js.complexity;

import static org.junit.Assert.assertEquals;

import java.io.FileReader;

import org.junit.Test;

public class CyclomaticComplexityTest {

	private static final String FOR1 = "src/test/resources/com/ccm/js/complexity/for1.js";
	private static final String IF1 = "src/test/resources/com/ccm/js/complexity/if1.js";
	private static final String IF2 = "src/test/resources/com/ccm/js/complexity/if2.js";
	private static final String IF2_A = "src/test/resources/com/ccm/js/complexity/if2a.js";
	private static final String SIMPLE1 = "src/test/resources/com/ccm/js/complexity/simple1.js";
	private static final String ACCOUNTS_CREATE = "../mudpuppy/crm-web/src/main/webapp/js/ccm/accounts/create.js";
	
//	@Test
//	public void getComplexity() throws Exception{
//		new CyclomaticComplexity().run(new FileReader(ACCOUNTS_CREATE));
//	
//}
	@Test
	public void getComplexitySimple1() throws Exception{
		System.out.println("============================");
		assertEquals(1,new CyclomaticComplexity().run(new FileReader(SIMPLE1)));
	}
	
	@Test
	public void getComplexityIf1() throws Exception{
		System.out.println("============================");
		assertEquals(2,new CyclomaticComplexity().run(new FileReader(IF1)));
	}
	
	@Test
	public void getComplexityIf2() throws Exception{
		System.out.println("============================");
		assertEquals(2,new CyclomaticComplexity().process(new FileReader(IF2)).getDecisions());
		assertEquals(1,new CyclomaticComplexity().process(new FileReader(IF2)).getExitPoints());
	}
	
	@Test
	public void getComplexityIf2a() throws Exception{
		System.out.println("============================");
		assertEquals(3,new CyclomaticComplexity().process(new FileReader(IF2)).getComplexity());
	}
	
	@Test
	public void getComplexityFor1() throws Exception{
		System.out.println("============================");
		assertEquals(2,new CyclomaticComplexity().run(new FileReader(FOR1)));
	}
	
	@Test
	public void getComplexityReturn1() throws Exception{
		System.out.println("============================");
		new CyclomaticComplexity().run(new FileReader("src/test/resources/com/ccm/js/complexity/return1.js"));
	}

}
