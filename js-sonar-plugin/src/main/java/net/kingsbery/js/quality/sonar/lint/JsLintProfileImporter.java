package net.kingsbery.js.quality.sonar.lint;

import java.io.Reader;

import org.sonar.api.profiles.ProfileImporter;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.utils.ValidationMessages;

public class JsLintProfileImporter extends ProfileImporter {

    public JsLintProfileImporter(){
        super(JsLintRuleRepository.JSLINT_RULES_KEY,JsLintRuleRepository.JSLINT_RULES_NAME);
    }
    
    @Override
    public RulesProfile importProfile(Reader reader, ValidationMessages messages) {
        // TODO Auto-generated method stub
        return null;
    }

}
