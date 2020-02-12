package hu.gdulai.gitpublish;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author gdulai
 */
public class GitPublishPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        File projectDir = project.getProjectDir();

        GitPublishPluginExtension extension = project
                .getExtensions()
                .create("gitPublish", GitPublishPluginExtension.class);

        project.task("gitPublishToMavenLocal").doLast(action -> {
                    try {
                        Arrays.stream(extension.repositories).forEach(gitRepository -> {
                            try {
                                GradleProject gradleProject = gitRepository.createGitRepository().acquire();

                                gradleProject.publish();

                            } catch (GitAPIException | IOException e) {
                                e.printStackTrace();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
        );
    }

    public static class GitPublishPluginExtension {
        private GitRepository[] repositories;

        public GitRepository[] getRepositories() {
            return repositories;
        }

        public void setRepositories(GitRepository[] repositories) {
            this.repositories = repositories;
        }
    }
}