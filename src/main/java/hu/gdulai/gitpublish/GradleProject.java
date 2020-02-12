package hu.gdulai.gitpublish;

import org.gradle.tooling.BuildLauncher;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;
import org.gradle.tooling.model.GradleTask;

import java.io.File;

/**
 * @author gdulai
 */
public class GradleProject {

    private final String projectName;
    private final File projectDir;

    GradleProject(String projectName, File projectDir) {
        this.projectName = projectName;
        this.projectDir = projectDir;
    }

    public final void publish() {
        GradleConnector connector = GradleConnector.newConnector();

        System.out.println("PROJECT DIR: " + projectDir);

        ProjectConnection projectConnection = connector.forProjectDirectory(projectDir).connect();

        BuildLauncher build = projectConnection.newBuild();
        build.forTasks("clean", "build", "publishToMavenLocal").run();

        projectConnection.close();
    }
}
