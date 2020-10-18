package hu.gdulai.gitpublish;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import org.junit.After;
import org.junit.Test;

/** @author gdulai */
public class GitPublishTest {

  private TestSetup setup;

  @After
  public void afterEach() {
    if (setup != null) setup.tempFolder().delete();

    setup = null;
  }

  @Test
  public void testSshJmdb() throws IOException {
    // GIVEN
    setup = new TestSetup("test_jmdb.gradle");

    // WHEN
    setup.gradle(true, "-Dgradle.user.home=" + setup.homeFolder().getPath(), "build");

    // THEN
    Optional<File> folderO = setup.gitPublishFolder();
    assertThat(folderO).isNotEmpty();

    File folder = folderO.get();
    assertThat(folder).isDirectory();

    File[] files = folder.listFiles();
    assertThat(files).isNotNull().isNotEmpty().hasSize(1);

    String jdmb = files[0].getName();
    assertThat(jdmb).isEqualTo("jmdb");
  }
}
