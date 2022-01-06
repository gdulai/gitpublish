package hu.gdulai.gitpublish;

import hu.gdulai.gitpublish.git.GitRepository;

/** @author gdulai */
public class GitPublishExtension {
  private boolean shouldKeep;
  private String tempDirectoryPath;
  private String privateKeyPath;
  private GitRepository[] repositories;

  public boolean isShouldKeep() {
    return shouldKeep;
  }

  public void setShouldKeep(boolean shouldKeep) {
    this.shouldKeep = shouldKeep;
  }

  public String getTempDirectoryPath() {
    return tempDirectoryPath;
  }

  public void setTempDirectoryPath(String tempDirectoryPath) {
    this.tempDirectoryPath = tempDirectoryPath;
  }

  public String getPrivateKeyPath() {
    return privateKeyPath;
  }

  public void setPrivateKeyPath(String privateKeyPath) {
    this.privateKeyPath = privateKeyPath;
  }

  public GitRepository[] getRepositories() {
    return repositories;
  }

  public void setRepositories(GitRepository[] repositories) {
    this.repositories = repositories;
  }
}
