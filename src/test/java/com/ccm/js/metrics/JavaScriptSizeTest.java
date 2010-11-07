package com.ccm.js.metrics;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class JavaScriptSizeTest {

	private static final String TEST_DIR = "src/test/resources/com/ccm/js/complexity";

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { "and1", 8, 1 },
				{ "for1", 7, 1 }, { "if1", 12, 4 }, { "if2", 11, 0 },
				{ "if3", 13, 0 }, { "or1", 6, 0 }, { "return1", 11, 0 },
				{ "simple1", 3, 0 }, { "ternary1", 1, 0 }, });
	}

	private String fileName;
	private int physicalLines;
	private int commentLines;

	public JavaScriptSizeTest(String fileName, int physicalLines,
			int commentLines) {
		this.fileName = fileName;
		this.physicalLines = physicalLines;
		this.commentLines = commentLines;
	}

	@Test
	public void physicalLinesShouldMatch() throws IOException {
		assertEquals("Line count did not match for " + fileName, physicalLines,
				new JavaScriptSize().process(getFile()).physicalLines);
	}

	@Test
	public void commentLinesShouldMatch() throws IOException {
		assertEquals("Comment line count did not match for " + fileName,
				commentLines,
				new JavaScriptSize().process(getFile()).commentLines);
	}

	private File getFile() {
		return new File(TEST_DIR + "/" + fileName + ".js");
	}
}
