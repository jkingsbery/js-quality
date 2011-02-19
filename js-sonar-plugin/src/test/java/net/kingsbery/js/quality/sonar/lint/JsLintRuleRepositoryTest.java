package net.kingsbery.js.quality.sonar.lint;

import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.Test;
import org.sonar.api.rules.Rule;
public class JsLintRuleRepositoryTest {

    @Test
    public void ruleRepositoryShouldHaveRules(){
        Rule.create();
        List<Rule> rules = new JsLintRuleRepository().createRules();
        System.out.println(rules);
        assertFalse(rules.isEmpty());
    }
}
