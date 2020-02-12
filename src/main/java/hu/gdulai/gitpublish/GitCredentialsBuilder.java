package hu.gdulai.gitpublish;

import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

/**
 * @author gdulai
 */
class GitCredentialsBuilder {

    private final String username;
    private final String password;

    GitCredentialsBuilder(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    final UsernamePasswordCredentialsProvider buildCredProvider() {
        return new UsernamePasswordCredentialsProvider(this.username, this.password);

    }

}
