package net.kingsbery.js.quality.sonar;

import java.util.ArrayList;
import java.util.List;

import net.kingsbery.js.quality.sonar.lint.JsLintProfileDefinition;
import net.kingsbery.js.quality.sonar.lint.JsLintRuleRepository;
import net.kingsbery.js.quality.sonar.lint.JsLintSensor;

import org.sonar.api.Plugin;

/**
 * This class is the entry point for all extensions
 */
public class JavaScriptPlugin implements Plugin {

    /**
     * @deprecated this is not used anymore
     */
    public String getKey() {
        return "sample";
    }

    /**
     * @deprecated this is not used anymore
     */
    public String getName() {
        return "My Sonar plugin";
    }

    /**
     * @deprecated this is not used anymore
     */
    public String getDescription() {
        return "You shouldn't expect too much from this plugin except displaying the Hello World message.";
    }

    public List<?> getExtensions() {
        List<Class<?>> extensions = new ArrayList<Class<?>>();
        extensions.add(JavaScriptLanguage.class);
        extensions.add(JavaScriptSourceImporter.class);
        
        
        extensions.add(JsLintSensor.class);
        extensions.add(JsLintRuleRepository.class);
        extensions.add(JsLintProfileDefinition.class);
        return extensions;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
