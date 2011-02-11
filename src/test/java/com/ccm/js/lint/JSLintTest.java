package com.ccm.js.lint;

import java.io.File;
import java.util.Iterator;

import org.junit.Test;

public class JSLintTest {

    ClassLoader loader = JSLintTest.class.getClassLoader();

    @Test
    public void runLint() throws Exception {
        Iterator<Issue> issues = new JSLint().run("jquery.dataTables.js",
                loader.getResourceAsStream("jquery.dataTables.js"));
        Issue next = issues.next();
        System.out.println(next);
    }

    @Test
    public void descriptify() throws Exception {
        Iterator<Issue> issues = new JSLint().run("descriptify.js",
                loader.getResourceAsStream("descriptify.js"));
        Issue next = issues.next();
        System.out.println(next);
    }

    @Test
    public void jqueryForm() throws Exception {

        Iterator<Issue> issues = new JSLint().run("jquery.form.js",
                loader.getResourceAsStream("jquery.form.js"));
        Issue next = issues.next();
        System.out.println(next);
    }

}
