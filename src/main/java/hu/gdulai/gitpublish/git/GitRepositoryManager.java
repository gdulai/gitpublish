package hu.gdulai.gitpublish.git;

import java.io.File;
import java.io.IOException;

import hu.gdulai.gitpublish.project.BuildSystemProject;
import hu.gdulai.gitpublish.project.gradle.GradleProject;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.gradle.internal.impldep.org.apache.http.auth.UsernamePasswordCredentials;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author gdulai
 */
public class GitRepositoryManager {

    private final String projectName;
    private final File local;
    private final String url;

    @Nullable
    private UsernamePasswordCredentialsProvider credentials;

    GitRepositoryManager(
            @Nonnull String projectName,
            @Nonnull String url,
            @Nonnull String localPath) {
        this.projectName = projectName;
        this.local = new File(localPath + projectName);
        this.url = url;
    }

    public final BuildSystemProject acquire() throws GitAPIException, IOException {
        File projectDir = local.exists() ?
                pullRepository() :
                cloneRepository();

        return new GradleProject(projectName, projectDir);
    }

    private File cloneRepository() throws InvalidRemoteException, TransportException, GitAPIException {
        final CloneCommand cloneCmd = credentials != null ? Git.cloneRepository()
                .setCredentialsProvider(credentials) : Git.cloneRepository();

        cloneCmd.setURI(url)
                .setDirectory(local)
                .setBranch("master")
                .call();

        return local;
    }

    private File pullRepository() throws IOException, GitAPIException {
        PullCommand pullCmd = credentials != null ?
                Git.open(local).pull().setCredentialsProvider(credentials) :
                Git.open(local).pull();

        pullCmd.call();

        return local;
    }

    GitRepositoryManager setCredentials(UsernamePasswordCredentialsProvider credentials) {
        this.credentials = credentials;

        return this;
    }
}
