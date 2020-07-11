package hu.gdulai.gitpublish;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

/** @author gdulai */
public class GitPublishTest {

  private TestSetup setup;

  @AfterEach
  private void afterEach() {
    if (setup != null) setup.tempFolder().delete();

    setup = null;
  }

  @Test
  public void testHttpJunitAssertJExampleproj() throws IOException {
    // GIVEN
    setup = new TestSetup("test_junit_assertj_exampleproj.gradle");

    // WHEN
    setup.gradle(true, "-Dgradle.user.home=" + setup.homeFolder().getPath(), "gitPublish");

    // THEN
    Optional<File> folderO = setup.gitPublishFolder();
    assertThat(folderO).isNotEmpty();

    File folder = folderO.get();
    assertThat(folder.isDirectory()).isTrue();

    File[] files = folder.listFiles();
    assertThat(files).isNotNull();
    assertThat(files).isNotEmpty();
    assertThat(files).hasSize(3);

    String assertJName = files[0].getName();
    String junitName = files[1].getName();
    String exampleGradleProjectName = files[2].getName();
    assertThat(assertJName).isEqualTo("assertj-core");
    assertThat(junitName).isEqualTo("junit");
    assertThat(exampleGradleProjectName).isEqualTo("example-gradle-project");
  }
}
