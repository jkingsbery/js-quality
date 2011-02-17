package net.kingsbery.js.quality.sonar.lint;

import java.util.ArrayList;
import java.util.List;

import net.kingsbery.js.lint.Issue;
import net.kingsbery.js.lint.JSLint;
import net.kingsbery.js.quality.sonar.JavaScriptLanguage;

import org.sonar.api.resources.File;
import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;
import org.sonar.api.rules.Rule;
import org.sonar.api.rules.RuleFinder;
import org.sonar.api.rules.Violation;

public class JsLintSensor implements Sensor {

    public boolean shouldExecuteOnProject(Project arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    private RuleFinder ruleFinder;
    private RulesProfile profile;

    public JsLintSensor(RulesProfile profile, RuleFinder ruleFinder) {
        super();
        this.ruleFinder = ruleFinder;
        this.profile = profile;
      }

    
    public void analyse(Project project, SensorContext context) {
        JSLint lint = new JSLint();
        List<Issue> issues =  lint.getList(project.getFileSystem().getBasedir());
        
        List<Violation> contextViolations = new ArrayList<Violation>();
        for (Issue issue : issues) {
            Rule rule = null;
//            Rule rule = ruleFinder.findByKey(
//                    PhpmdRuleRepository.PHPMD_REPOSITORY_KEY,
//                    issue.getRuleKey());
            if (true) {//rule != null) {
                Resource resource = context.getResource(new File(JavaScriptLanguage.INSTANCE,issue.getFileName()));
                if (context.getResource(resource) != null) {
                    Violation v = Violation.create(rule, resource)
                            .setLineId(issue.getLine())
                            .setMessage(issue.getReason());
                    contextViolations.add(v);
                }
            }
        }
        context.saveViolations(contextViolations);

    }

}
