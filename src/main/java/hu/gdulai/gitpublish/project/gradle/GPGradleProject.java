package hu.gdulai.gitpublish.project.gradle;

import hu.gdulai.gitpublish.git.GitRepositoryManager;
import hu.gdulai.gitpublish.project.BuildSystemProject;
import org.gradle.tooling.BuildLauncher;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;

import java.io.File;

/**
 * @author gdulai
 */
public class GPGradleProject implements BuildSystemProject {
    private final File projectDir;
    private final GitRepositoryManager repositoryManager;

    public GPGradleProject(File projectDir, GitRepositoryManager repositoryManager) {
        this.projectDir = projectDir;
        this.repositoryManager = repositoryManager;
    }

    @Override
    public void build() {
        GradleConnector connector = GradleConnector.newConnector();

        System.out.println("PROJECT DIR: " + projectDir);

        ProjectConnection projectConnection = connector.forProjectDirectory(projectDir).connect();

        BuildLauncher build = projectConnection.newBuild();
        build.forTasks("clean", "build", "publishToMavenLocal").run();

        projectConnection.close();

        projectDir.delete();
    }
}
