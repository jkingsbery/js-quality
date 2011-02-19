package net.kingsbery.js.quality.sonar.lint;

import org.sonar.api.profiles.ProfileDefinition;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.profiles.XMLProfileParser;
import org.sonar.api.utils.ValidationMessages;

public class JsLintProfileDefinition extends ProfileDefinition {

    XMLProfileParser parser;

    public JsLintProfileDefinition(XMLProfileParser parser) {
        this.parser = parser;
    }

    @Override
    public RulesProfile createProfile(ValidationMessages messages) {
        return parser.parseResource(getClass().getClassLoader(),
                "net/kingsbery/js/quality/sonar/lint/js-lint-profile.xml", messages);

    }

}
