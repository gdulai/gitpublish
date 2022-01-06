package hu.gdulai.gitpublish.config;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.OpenSshConfig.Host;
import org.eclipse.jgit.util.FS;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** @author gdulai */
public class JschConfigSessionFactoryImpl extends JschConfigSessionFactory {

  @Nonnull private String path;
  @Nullable private String passPharse;

  private JschConfigSessionFactoryImpl(@Nonnull String path, @Nullable String passPharse) {
    this.path = path;
    this.passPharse = passPharse;
  }

  @Override
  protected void configure(Host arg0, Session arg1) {}

  @Override
  protected JSch createDefaultJSch(FS fs) throws JSchException {
    JSch jSch = super.createDefaultJSch(fs);
    if (passPharse != null) jSch.addIdentity(path, passPharse);
    else jSch.addIdentity(path);
    return jSch;
  }

  public static JschConfigSessionFactoryImpl of(@Nonnull String path) {
    return of(path, null);
  }

  public static JschConfigSessionFactoryImpl of(@Nonnull String path, @Nullable String passPharse) {
    return new JschConfigSessionFactoryImpl(path, passPharse);
  }
}
