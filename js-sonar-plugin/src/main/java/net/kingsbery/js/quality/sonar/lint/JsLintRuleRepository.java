package net.kingsbery.js.quality.sonar.lint;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.kingsbery.js.quality.sonar.JavaScriptLanguage;

import org.sonar.api.rules.Rule;
import org.sonar.api.rules.RuleRepository;
import org.sonar.api.rules.XMLRuleParser;

public class JsLintRuleRepository extends RuleRepository {

    public static final String JSLINT_RULES_KEY = "jslint_rules";
    public static final String JSLINT_RULES_NAME = "JsLint Rules";
//    private ServerFileSystem fileSystem;
    private XMLRuleParser parser;

    public JsLintRuleRepository() {
        super(JSLINT_RULES_KEY, JavaScriptLanguage.INSTANCE.getKey());
        this.parser = new XMLRuleParser();
    }

    @Override
    public List<Rule> createRules() {
        assert getRuleInputStream() != null;
        assert getParser() != null;
        
        List<Rule> rules = new ArrayList<Rule>();
        rules.addAll(getParser().parse(getRuleInputStream()));
        for (File userExtensionXml : getExtensions()) {
            rules.addAll(getParser().parse(userExtensionXml));
        }
        return rules;
    }

    protected XMLRuleParser getParser() {
        return parser;
    }

    protected InputStream getRuleInputStream() {
        return getClass().getResourceAsStream(
                "/net/kingsbery/js/quality/sonar/lint/rules.xml");
    }
    
    protected List<File> getExtensions(){
        return Collections.emptyList();
//        return  serverFileSystem.getExtensions(
//                JSLINT_RULES_KEY, "xml");
    }
}
