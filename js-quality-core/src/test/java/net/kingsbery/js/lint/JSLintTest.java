package net.kingsbery.js.lint;

import static org.junit.Assert.assertFalse;

import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class JSLintTest {

    ClassLoader loader = JSLintTest.class.getClassLoader();

    private String file;
    
    public JSLintTest(String filename){
        this.file=filename;
    }
    
    
    @Parameters
    public static Collection<Object[]> data() {
      Object[][] data = new Object[][] { { "jquery.dataTables.js" }, { "jquery.form.js" }};
      return Arrays.asList(data);
    }

    @Test
    public void runLint() throws Exception {
        List<Issue> issues = new JSLint().getList(file,
                new InputStreamReader(loader.getResourceAsStream(file)));
        assertFalse(issues.isEmpty());
    }
}
