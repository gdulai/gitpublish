package hu.gdulai.gitpublish;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;

/**
 * @author gdulai
 */
public class GitRepositoryManager {

    private final String projectName;
    private final File local;
    private final String url;
    private GitCredentialsBuilder credentials;

    GitRepositoryManager(final String projectName, final String url, String localPath) {
        this.projectName = projectName;
        this.local = new File(localPath + projectName);
        this.url = url;
    }

    public final GradleProject acquire() throws GitAPIException, IOException {
        File projectDir = local.exists() ? pullRepository() : cloneRepository();
        return new GradleProject(projectName, projectDir);
    }

    private File cloneRepository() throws InvalidRemoteException, TransportException, GitAPIException {
        final CloneCommand cloneCmd = credentials != null ? Git.cloneRepository()
                .setCredentialsProvider(this.credentials.buildCredProvider()) : Git.cloneRepository();

        System.out.println(local);
        cloneCmd.setURI(url).setDirectory(local).setBranch("master").call();

        return local;
    }

    private File pullRepository() throws IOException, GitAPIException {
        PullCommand pullCmd = credentials != null ? Git.open(local).pull().setCredentialsProvider(this.credentials.buildCredProvider())
                : Git.open(local).pull();

        pullCmd.call();

        return local;
    }

    GitRepositoryManager credentials(GitCredentialsBuilder credentials) {
        this.credentials = credentials;

        return this;
    }
}
