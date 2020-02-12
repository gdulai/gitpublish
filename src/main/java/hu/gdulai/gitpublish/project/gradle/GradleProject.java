package hu.gdulai.gitpublish.project.gradle;

import hu.gdulai.gitpublish.project.BuildSystemProject;
import org.gradle.tooling.BuildLauncher;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;

import java.io.File;

/**
 * @author gdulai
 */
public class GradleProject implements BuildSystemProject {

    private final String projectName;
    private final File projectDir;

    public GradleProject(String projectName, File projectDir) {
        this.projectName = projectName;
        this.projectDir = projectDir;
    }

    @Override
    public void build() {
        GradleConnector connector = GradleConnector.newConnector();

        System.out.println("PROJECT DIR: " + projectDir);

        ProjectConnection projectConnection = connector.forProjectDirectory(projectDir).connect();

        BuildLauncher build = projectConnection.newBuild();
        build.forTasks("clean", "build", "publishToMavenLocal").run();

        projectConnection.close();
    }
}
