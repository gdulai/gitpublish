package hu.gdulai.gitpublish;

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

    public GitRepositoryManager createGitRepository() throws MalformedURLException {
        if (username == null || password == null)
            return new GitRepositoryManager(projectName, url);

        GitCredentialsBuilder credentialsBuilder = new GitCredentialsBuilder(username, password);

        return new GitRepositoryManager(projectName, url, credentialsBuilder);
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
