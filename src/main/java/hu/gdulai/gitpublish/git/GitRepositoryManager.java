package hu.gdulai.gitpublish.git;

import hu.gdulai.gitpublish.project.BuildSystemProject;
import hu.gdulai.gitpublish.project.gradle.GradleProject;
import hu.gdulai.gitpublish.project.maven.MavenProject;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.TransportConfigCallback;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;

/**
 * @author gdulai
 */
public class GitRepositoryManager {

    private final String projectName;
    private final File localCopy;
    private final String url;

    @Nullable
    private UsernamePasswordCredentialsProvider credentials;

    GitRepositoryManager(
            @Nonnull String projectName,
            @Nonnull String url,
            @Nonnull String localPath) {
        this.projectName = projectName;
        this.localCopy = new File(localPath + projectName);
        this.url = url;
    }

    public final void removeLocalCopy() {
        localCopy.delete();
    }

    public final BuildSystemProject acquire() throws Exception {
        File projectDir = localCopy.exists() ?
                pullRepository() :
                cloneRepository();

        File pomXml = new File(projectDir.getPath() + "/pom.xml");
        File buildGradle = new File(projectDir.getPath() + "/build.gradle");

        if (pomXml.exists()) {
            return new MavenProject(projectDir);
        } else if (buildGradle.exists()) {
            return new GradleProject(projectDir);
        }

        throw new Exception("Could not determine project type!");
    }

    private File cloneRepository() throws InvalidRemoteException, TransportException, GitAPIException {
        final CloneCommand cloneCmd = credentials != null ? Git.cloneRepository()
                .setCredentialsProvider(credentials) : Git.cloneRepository();

        cloneCmd.setURI(url)
                .setDirectory(localCopy)
                .setBranch("master")
                .call();

        return localCopy;
    }

    private File pullRepository() throws IOException, GitAPIException {
        PullCommand pullCmd = credentials != null ?
                Git.open(localCopy).pull().setCredentialsProvider(credentials) :
                Git.open(localCopy).pull();

        pullCmd.call();

        return localCopy;
    }

    GitRepositoryManager setCredentials(UsernamePasswordCredentialsProvider credentials) {
        this.credentials = credentials;

        return this;
    }
}
