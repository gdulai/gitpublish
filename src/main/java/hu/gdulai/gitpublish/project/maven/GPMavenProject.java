package hu.gdulai.gitpublish.project.maven;

import hu.gdulai.gitpublish.project.BuildSystemProject;
import org.gradle.internal.impldep.org.apache.maven.project.*;

import java.io.File;

/**
 * @author gdulai
 */
public class GPMavenProject implements BuildSystemProject {

    private final String projectName;
    private final File projectDir;

    public GPMavenProject(String projectName, File projectDir) {
        this.projectName = projectName;
        this.projectDir = projectDir;
    }

    @Override
    public void build() {
        ProjectBuildingRequest request = new DefaultProjectBuildingRequest();

        ProjectBuilder builder = new DefaultProjectBuilder();
    }
}
