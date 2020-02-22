package hu.gdulai.gitpublish.project.maven;

import hu.gdulai.gitpublish.project.BuildSystemProject;
import org.apache.commons.io.FileUtils;
import org.apache.groovy.json.internal.Exceptions;
import org.apache.maven.shared.invoker.*;
import org.gradle.internal.impldep.org.apache.maven.DefaultMaven;
import org.gradle.internal.impldep.org.apache.maven.execution.DefaultMavenExecutionRequest;
import org.gradle.internal.impldep.org.apache.maven.project.*;
import org.gradle.tooling.GradleConnector;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

/**
 * @author gdulai
 */
public class MavenProject implements BuildSystemProject {

    private final File projectDir;

    public MavenProject(File projectDir) {
        this.projectDir = projectDir;
    }

    @Override
    public void build(boolean shouldKeep) throws Exception {
        File pomXml = new File(projectDir + "/pom.xml");

        System.out.println("MAVEN pom: " + pomXml.getPath());

        File javaHome = getJdk().orElseThrow(() -> new Exception("Could not find java home!"));

        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile(pomXml);
        request.setJavaHome(javaHome);
        request.setGoals(List.of("clean", "install -DskipTests"));

        File mavenHome = getMavenHome().orElseThrow(() -> new Exception("Could not find maven home!"));

        Invoker invoker = new DefaultInvoker();
        invoker.setMavenHome(mavenHome);

        try {
            InvocationResult result = invoker.execute(request);
            System.out.println("Result code: " + result.getExitCode());

            if (!shouldKeep) {
                deleteProject();
            }
        } catch (MavenInvocationException | IOException e) {
            e.printStackTrace();
        }
    }

    private Optional<File> getJdk() {
        String javaHomePath = System.getenv("JAVA_HOME");

        System.out.println("JAVA_HOME:  " + javaHomePath);

        if (javaHomePath != null && !javaHomePath.isEmpty()) {
            return Optional.of(new File(javaHomePath));
        }

        return Optional.empty();
    }

    private Optional<File> getMavenHome() {
        String mavenHomePath = System.getenv("MAVEN_HOME");
        String m2HomePath = System.getenv("M2_HOME");

        System.out.println("MAVEN_HOME: " + mavenHomePath);
        System.out.println("M2_HOME: " + m2HomePath);

        if (mavenHomePath != null && !mavenHomePath.isEmpty()) {
            return Optional.of(new File(mavenHomePath));

        } else if (m2HomePath != null && !m2HomePath.isEmpty()) {
            return Optional.of(new File(m2HomePath));
        }

        return Optional.empty();
    }

    private void deleteProject() throws IOException {
        FileUtils.deleteDirectory(projectDir);
    }
}
