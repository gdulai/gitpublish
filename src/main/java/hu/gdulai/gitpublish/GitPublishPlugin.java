package hu.gdulai.gitpublish;

import hu.gdulai.gitpublish.project.BuildSystemProject;
import java.io.File;
import java.util.Arrays;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

/** @author gdulai */
public class GitPublishPlugin implements Plugin<Project> {

  @Override
  public void apply(Project project) {
    File projectDir = project.getProjectDir();

    GitPublishExtension extension =
        project.getExtensions().create("gitPublish", GitPublishExtension.class);

    project
        .task("gitPublishToMavenLocal")
        .doLast(
            action -> {
              Arrays.stream(extension.getRepositories())
                  .forEach(
                      gitRepository -> {
                        try {
                          String tempDirectoryPath = extension.getTempDirectoryPath();

                          String buildDirPath =
                              tempDirectoryPath != null
                                  ? tempDirectoryPath
                                  : projectDir.toPath().toString() + "/.gitPublishTemp/";

                          BuildSystemProject projectFromRepo =
                              gitRepository.createGitRepository(buildDirPath).acquire();

                          projectFromRepo.build(extension.isShouldKeep());

                        } catch (Exception e) {
                          e.printStackTrace();
                        }
                      });
            });
  }
}
