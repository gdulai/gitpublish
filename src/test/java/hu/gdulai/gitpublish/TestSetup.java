package hu.gdulai.gitpublish;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;
import org.gradle.internal.impldep.com.google.common.io.Files;
import org.gradle.internal.impldep.org.junit.rules.TemporaryFolder;
import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;

/** @author gdulai */
public class TestSetup {
  private final TemporaryFolder tempFolder; 
  private final File buildGradle;
  private final File homeFolder;
  private final File m2Folder;
  private final File gradleProps;

  public TestSetup(final String buildGradleFileName) throws IOException {
    tempFolder = new TemporaryFolder();
    tempFolder.create();

    buildGradle = new File(getClass().getClassLoader().getResource(
    homeFolder = tempFolder.newFolder("home");
    m2Folder = tempFolder.newFolder("m2");
    gradleProps = new File(homeFolder.getPath() + "/gradle.properties");

    Files.copy(buildGradle, tempFolder.newFile("build.gradle"));
    Files.write(("mavenLocalRepo=" + m2Folder + "").getBytes(), gradleProps);
  }

  public final BuildResult gradle(boolean isSuccessExpected, String... args) {
    GradleRunner runner =
        GradleRunner.create()
            .withArguments(args)
            .withProjectDir(tempFolder.getRoot())
            .withPluginClasspath()
            .withDebug(true);

    return isSuccessExpected ? runner.build() : runner.buildAndFail();
  }

  public final boolean removeFolder() {
    try {
      tempFolder.delete();
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  public final Optional<File> gitPublishFolder() {
    return folders().filter(file -> ".gitPublishTemp".equals(file.getName())).findAny();
  }

  public final Stream<File> folders() {
    File[] rootFiles = tempFolder.getRoot().listFiles();

    return Stream.of(rootFiles);
  }

  public final TemporaryFolder tempFolder() {
    return tempFolder;
  }

  public final File buildGradle() {
    return buildGradle;
  }

  public final File homeFolder() {
    return homeFolder;
  }

  public final File m2Fodler() {
    return m2Folder;
  }

  public final File gradleProps() {
    return gradleProps;
  }
}
