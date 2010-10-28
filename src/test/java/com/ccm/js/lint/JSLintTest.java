package com.ccm.js.lint;

import java.io.File;
import java.util.Iterator;

import org.junit.Test;

public class JSLintTest {

	private static final String ACCOUNTS_CREATE = "../mudpuppy/crm-web/src/main/webapp/js/ccm/accounts/create.js";

	@Test
	public void runLint() throws Exception{
		Iterator<Issue> issues = new JSLint().run(new File(ACCOUNTS_CREATE));
			Issue next = issues.next();
			System.out.println(next);
	}

}
