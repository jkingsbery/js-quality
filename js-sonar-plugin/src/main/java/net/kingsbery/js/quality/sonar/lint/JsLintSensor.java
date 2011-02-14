package net.kingsbery.js.quality.sonar.lint;

import java.io.File;

import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.resources.Project;

public class JsLintSensor implements Sensor {

    public boolean shouldExecuteOnProject(Project arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    public void analyse(Project project, SensorContext context) {
     
    }

}
