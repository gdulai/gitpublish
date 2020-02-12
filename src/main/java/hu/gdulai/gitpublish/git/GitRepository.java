package hu.gdulai.gitpublish.git;

import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.Serializable;
import java.net.MalformedURLException;

/**
 * @author gdulai
 */
public class GitRepository implements Serializable {

    private String projectName;
    private String url;
    private String username;
    private String password;

    public GitRepository(String projectName, String url) {
        this.projectName = projectName;
        this.url = url;
    }

    public GitRepository(String projectName, String url, String username, String password) {
        this.projectName = projectName;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public GitRepositoryManager createGitRepository(String tempDirectoryPath) throws MalformedURLException {
        GitRepositoryManager repositoryManager = new GitRepositoryManager(projectName, url, tempDirectoryPath);

        if (username == null || password == null)
            return repositoryManager;

        UsernamePasswordCredentialsProvider credentials = new UsernamePasswordCredentialsProvider(username, password);

        return repositoryManager
                .setCredentials(credentials);
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
