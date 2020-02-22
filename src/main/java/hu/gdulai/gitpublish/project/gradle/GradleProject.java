package hu.gdulai.gitpublish.project.gradle;

import hu.gdulai.gitpublish.project.BuildSystemProject;
import org.apache.commons.io.FileUtils;
import org.gradle.tooling.BuildLauncher;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;

import java.io.File;
import java.io.IOException;

/**
 * @author gdulai
 */
public class GradleProject implements BuildSystemProject {
    private final File projectDir;

    public GradleProject(File projectDir) {
        this.projectDir = projectDir;
    }

    @Override
    public void build(boolean shouldKeep) {
        GradleConnector connector = GradleConnector.newConnector();

        System.out.println("PROJECT DIR: " + projectDir);

        ProjectConnection projectConnection = connector.forProjectDirectory(projectDir).connect();

        BuildLauncher build = projectConnection.newBuild();
        build.forTasks("clean", "build", "publishToMavenLocal").run();

        projectConnection.close();

        try {
            if (!shouldKeep) {
                deleteProject();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteProject() throws IOException {
        FileUtils.deleteDirectory(projectDir);
    }
}
