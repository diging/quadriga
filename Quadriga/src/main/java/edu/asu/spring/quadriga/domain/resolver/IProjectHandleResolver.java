package edu.asu.spring.quadriga.domain.resolver;

public interface IProjectHandleResolver {

    String getId();

    void setId(String id);

    String getProjectName();

    void setProjectName(String projectName);

    String getDescription();

    void setDescription(String description);

    String getProjectUrl();

    void setProjectUrl(String projectUrl);

    String getResolvedHandlePattern();

    void setResolvedHandlePattern(String resolvedHandlePattern);

    String getHandlePattern();

    void setHandlePattern(String handlePattern);

    String getHandleExample();

    void setHandleExample(String handleExample);

    String buildResolvedHandle(String handle);

    void setUsername(String username);

    String getUsername();

    void setResolvedHandleExample(String resolvedHandleExample);

    String getResolvedHandleExample();

}