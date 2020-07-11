package hu.gdulai.gitpublish;

import org.gradle.internal.impldep.com.google.common.io.Files;
import org.gradle.internal.impldep.org.junit.Rule;
import org.gradle.internal.impldep.org.junit.rules.TemporaryFolder;
import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author gdulai
 */
public class GitPublishTest {

    private static final String BUILD_GRADLE_CONTENT = "buildscript {\n" +
            "    repositories {\n" +
            "        mavenLocal()\n" +
            "        mavenCentral()\n" +
            "    }\n" +
            "    dependencies {\n" +
            "        classpath 'hu.gdulai:gitpublish:1.0.1'\n" +
            "    }\n" +
            "}\n" +
            "apply plugin: 'hu.gdulai.gitpublish'\n" +
            "apply plugin: 'java'\n" +
            "\n" +
            "group 'org.example'\n" +
            "version '1.0-SNAPSHOT'\n" +
            "\n" +
            "sourceCompatibility = '11'\n" +
            "\n" +
            "repositories {\n" +
            "    mavenLocal()\n" +
            "    //mavenCentral()\n" +
            "}\n" +
            "\n" +
            "gitPublish {\n" +
            "    shouldKeep = false\n" +
            "    repositories = [\n" +
            "            //[\"jmdb\", \"git@gitlab.com:gdulai/jmdb.git\"],\n" +
            "            [\"junit\", \"https://github.com/junit-team/junit4.git\"],\n" +
            "            [\"assertj-core\", \"https://github.com/joel-costigliola/assertj-core.git\"],\n" +
            "            [\"example-gradle-project\", \"https://github.com/gdulai/example-gradle-project.git\"]\n" +
            "    ]\n" +
            "}\n" +
            "\n" +
            "dependencies {\n" +
            "    //implementation group: 'hu.gdulai', name: 'jmdb', version: '1.0'\n" +
            "    implementation group: 'hu.gdulai', name: 'example-gradle-project', version: '1.0-SNAPSHOT'\n" +
            "\n" +
            "    testImplementation group: 'junit', name: 'junit', version: '4.12'\n" +
            "    testImplementation group: 'org.assertj', name: 'assertj-core', version: '3.15.1-SNAPSHOT'\n" +
            "}";

    private TemporaryFolder temporaryFolder;
    private File buildGradle;
    private File homeFolder;
    private File m2Folder;
    private File gradleProps;

    @BeforeEach
    public void beforeEach() throws IOException {
        temporaryFolder = new TemporaryFolder();
        temporaryFolder.create();

        buildGradle = temporaryFolder.newFile("build.gradle");
        homeFolder = temporaryFolder.newFolder("home");
        m2Folder = temporaryFolder.newFolder("m2");
        gradleProps = new File(homeFolder.getPath() + "/gradle.properties");

        Files.write(BUILD_GRADLE_CONTENT.getBytes(), buildGradle);
        Files.write(("mavenLocalRepo=" + m2Folder + "").getBytes(), gradleProps);
    }

    @AfterEach
    public void afterEach() {
        temporaryFolder.delete();
    }

    private BuildResult gradle(String... args) {
        return gradle(true, args);
    }

    private BuildResult gradle(boolean isSuccessExpected, String... args) {
        GradleRunner runner = GradleRunner.create()
                .withArguments(args)
                .withProjectDir(temporaryFolder.getRoot())
                .withPluginClasspath()
                .withDebug(true);

        return isSuccessExpected ? runner.build() : runner.buildAndFail();
    }

    @Test
    public void test() throws IOException {
        BuildResult buildResult = gradle("-Dgradle.user.home=" + homeFolder.getPath(), "gitPublish");
        buildResult.getOutput().contains("gitPublish");

        File[] rootFiles = temporaryFolder.getRoot().listFiles();

        for (File file : rootFiles) {

        }
    }
}