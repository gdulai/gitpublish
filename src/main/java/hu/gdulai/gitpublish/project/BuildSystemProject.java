package hu.gdulai.gitpublish.project;

/**
 * @author gdulai
 */
public interface BuildSystemProject {

    void build(boolean shouldKeep) throws Exception;
}