package net.kingsbery.js.quality.sonar;

import org.sonar.api.resources.AbstractLanguage;

public class JavaScriptLanguage extends AbstractLanguage {

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
