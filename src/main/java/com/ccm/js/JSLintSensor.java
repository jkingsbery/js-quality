package com.ccm.js;

import java.io.File;
import java.util.Iterator;

import org.sonar.api.batch.GeneratesViolations;
import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.resources.Language;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;
import org.sonar.api.rules.Rule;
import org.sonar.api.rules.RulesManager;
import org.sonar.api.rules.Violation;

import com.ccm.js.lint.Issue;
import com.ccm.js.lint.JSLint;

public class JSLintSensor implements Sensor, GeneratesViolations {
	
	private RulesProfile profile;
	protected RulesManager rulesManager;
	protected SensorContext context;

	protected JSLintSensor(SensorContext context, RulesManager rulesManager) {
		this.rulesManager = rulesManager;
		this.context = context;
	}

	protected String keyForPlugin() {
		return "javascript";
	}

	@Override
	public boolean shouldExecuteOnProject(Project project) {
		return !project.getFileSystem().getSourceFiles(JavaScriptLanguage.javascript).isEmpty() &&
        (!profile.getActiveRulesByPlugin(this.keyForPlugin()).isEmpty() || project.getReuseExistingRulesConfig());

	}

	@Override
	public void analyse(Project project, SensorContext ctx) {

		File javascripts[] = project.getFileSystem()
				.getSourceFiles(JavaScriptLanguage.javascript)
				.toArray(new File[] {});
		for(File js : javascripts){
			Iterator<Issue> issues = new JSLint().run(js);
			while(issues.hasNext()){
				Issue issue = issues.next();
				toViolation(issue);
			}
		}
	}

	private Violation toViolation(Issue issue) {
		Resource resource = new JavaScriptFile(issue.getFileName());
		Rule rule=rulesManager.getPluginRule(keyForPlugin(), issue.getRaw());
		Violation violation = new Violation(rule, resource).setLineId(issue.getLine())
			.setMessage(issue.getReason());
		return violation;
		
	}

	public static class JavaScriptLanguage implements Language {
		public static final JavaScriptLanguage javascript = new JavaScriptLanguage();

		private JavaScriptLanguage() {

		}

		@Override
		public String[] getFileSuffixes() {
			return new String[] { "js" };
		}

		@Override
		public String getKey() {
			return "js";
		}

		@Override
		public String getName() {
			return "JavaScript";
		}
	}
	
	public static class JavaScriptFile extends Resource{

		private String directory;
		private String fileName;
		private boolean unitTest;

		public JavaScriptFile(String fileName){
			File temp = new File(fileName);
			this.directory=temp.getParent();
			this.fileName=temp.getName();
			this.unitTest=false;
		}
		
		public JavaScriptFile(String directory, String fileName, boolean unitTest){
			this.directory=directory;
			this.fileName=fileName;
			this.unitTest=unitTest;
		}
		
		@Override
		public String getDescription() {
			return null;
		}

		@Override
		public Language getLanguage() {
			return JavaScriptLanguage.javascript;
		}

		@Override
		public String getLongName() {
			return directory+"/"+fileName;
		}

		@Override
		public String getName() {
			return fileName;
		}

		@Override
		public Resource getParent() {
			return null;
		}

		@Override
		public String getQualifier() {
			return null;
		}

		@Override
		public String getScope() {
			return Resource.SCOPE_ENTITY;
		}

		@Override
		public boolean matchFilePattern(String arg0) {
			return false;
		}
		
	}
}
