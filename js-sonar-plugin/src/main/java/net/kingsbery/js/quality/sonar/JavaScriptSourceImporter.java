package net.kingsbery.js.quality.sonar;

import java.io.File;
import java.util.List;

import org.sonar.api.batch.AbstractSourceImporter;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.resources.Language;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.ProjectFileSystem;

public class JavaScriptSourceImporter extends AbstractSourceImporter {

    public JavaScriptSourceImporter() {
        super(JavaScriptLanguage.INSTANCE);
    }

    @Override
    public void analyse(Project project, SensorContext context) {
        Language[] language = new Language[] { JavaScriptLanguage.INSTANCE };
        ProjectFileSystem fileSystem = project.getFileSystem();

        List<File> sourceDirs = fileSystem.getSourceDirs();
        System.out.println("Using " + sourceDirs + " as the source directories");
        List<File> sourceFiles = fileSystem.getSourceFiles(language);
        parseDirs(context, sourceFiles, sourceDirs, false,
                fileSystem.getSourceCharset());

        List<File> testDirs = fileSystem.getTestDirs();
        System.out.println("Using " + testDirs + " as the test directories");
        List<File> testFiles = fileSystem.getTestFiles(language);
        parseDirs(context, testFiles, testDirs, true,
                fileSystem.getSourceCharset());

        for (File directory : sourceDirs) {
            System.out.println(directory.getName());
        }
        for (File directory : testDirs) {
            System.out.println(directory.getName());
        }

    }

}
