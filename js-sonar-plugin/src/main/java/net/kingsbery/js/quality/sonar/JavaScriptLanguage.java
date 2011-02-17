package net.kingsbery.js.quality.sonar;

import org.sonar.api.resources.AbstractLanguage;
import org.sonar.api.resources.Language;

public class JavaScriptLanguage extends AbstractLanguage {

    public static final Language INSTANCE = new JavaScriptLanguage();

    public JavaScriptLanguage(){
        super("js","JavaScript");
    }
    
    public String[] getFileSuffixes() {
        return new String[] { "js" };
    }

    public String getKey() {
        return "js";
    }

    public String getName() {
        return "JavaScript";
    }

}
