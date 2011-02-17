package net.kingsbery.js.quality.sonar.lint;

import java.util.List;

import net.kingsbery.js.quality.sonar.JavaScriptLanguage;

import org.sonar.api.rules.Rule;
import org.sonar.api.rules.RuleRepository;

public class JsLintRuleRepository extends RuleRepository {

    protected JsLintRuleRepository(String key, String language) {
        super("jslint_rules", JavaScriptLanguage.INSTANCE.getKey());
    }

    @Override
    public List<Rule> createRules() {
        // TODO Auto-generated method stub
        return null;
    }

}
