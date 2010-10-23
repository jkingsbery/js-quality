package com.ccm.js.lint;

import java.io.FileReader;

import org.junit.Test;

public class JSLintTest {

	private static final String ACCOUNTS_CREATE = "../mudpuppy/crm-web/src/main/webapp/js/ccm/accounts/create.js";

	@Test
	public void runLint() throws Exception{
		new JSLint().run(new FileReader(ACCOUNTS_CREATE));
	}

}
