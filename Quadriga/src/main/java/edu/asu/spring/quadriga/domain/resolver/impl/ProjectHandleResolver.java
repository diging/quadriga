package edu.asu.spring.quadriga.domain.resolver.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.asu.spring.quadriga.domain.resolver.IProjectHandleResolver;
import edu.asu.spring.quadriga.domain.resolver.Status;

/**
 * A ProjectHandleResolver describes how original handles referenced in creation
 * events can be resolved to the actual text in a repository or website. E.g. if
 * the original handle points to a XHTML version in a repository that has a
 * representation on a website and there is a direct link between handle and
 * presentation that can be expressed using RegEx, this class can be used.
 * 
 * For example:
 * 
 * Handle is: http://my.handle.org/handle/123/456 Presentation is:
 * http://my.website.org/123/456
 * 
 * Handle pattern could now be: <code>(/[0-9]+/[0-9]+$)</code> Resolved handle
 * pattern: <code>http://my.website.org/{0}</code>
 * 
 * <code>{0}</code> refers to the 1. group defined in the pattern and will be
 * replace with the content of the group.
 * 
 * 
 * @author jdamerow
 *
 */
public class ProjectHandleResolver implements IProjectHandleResolver {

    private String id;
    private String projectName;
    private String description;
    private String projectUrl;
    private String username;

    /**
     * The resolved handle pattern is the pattern that specifies how the
     * resolved handle should be build. It should use <code>{n}</code> to refer
     * to groups in the pattern specified in handlePattern.
     */
    private String resolvedHandlePattern;

    /**
     * The pattern that describes what parts of the original handle should be
     * used in the resolved handle.
     */
    private String handlePattern;
    private String handleExample;
    private String resolvedHandleExample;
    private Status validation = Status.NOT_RUN;

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.asu.spring.quadriga.domain.resolver.impl.IProjectHandleResolver#getId
     * ()
     */
    @Override
    public String getId() {
        return id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.asu.spring.quadriga.domain.resolver.impl.IProjectHandleResolver#setId
     * (java.lang.String)
     */
    @Override
    public void setId(String id) {
        this.id = id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.spring.quadriga.domain.resolver.impl.IProjectHandleResolver#
     * getProjectName()
     */
    @Override
    public String getProjectName() {
        return projectName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.spring.quadriga.domain.resolver.impl.IProjectHandleResolver#
     * setProjectName(java.lang.String)
     */
    @Override
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.spring.quadriga.domain.resolver.impl.IProjectHandleResolver#
     * getDescription()
     */
    @Override
    public String getDescription() {
        return description;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.spring.quadriga.domain.resolver.impl.IProjectHandleResolver#
     * setDescription(java.lang.String)
     */
    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.spring.quadriga.domain.resolver.impl.IProjectHandleResolver#
     * getProjectUrl()
     */
    @Override
    public String getProjectUrl() {
        return projectUrl;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.spring.quadriga.domain.resolver.impl.IProjectHandleResolver#
     * setProjectUrl(java.lang.String)
     */
    @Override
    public void setProjectUrl(String projectUrl) {
        this.projectUrl = projectUrl;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.spring.quadriga.domain.resolver.impl.IProjectHandleResolver#
     * getResolvedHandlePattern()
     */
    @Override
    public String getResolvedHandlePattern() {
        return resolvedHandlePattern;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.spring.quadriga.domain.resolver.impl.IProjectHandleResolver#
     * setResolvedHandlePattern(java.lang.String)
     */
    @Override
    public void setResolvedHandlePattern(String resolvedHandlePattern) {
        this.resolvedHandlePattern = resolvedHandlePattern;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.spring.quadriga.domain.resolver.impl.IProjectHandleResolver#
     * getHandlePattern()
     */
    @Override
    public String getHandlePattern() {
        return handlePattern;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.spring.quadriga.domain.resolver.impl.IProjectHandleResolver#
     * setHandlePattern(java.lang.String)
     */
    @Override
    public void setHandlePattern(String handlePattern) {
        this.handlePattern = handlePattern;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.spring.quadriga.domain.resolver.impl.IProjectHandleResolver#
     * getHandleExample()
     */
    @Override
    public String getHandleExample() {
        return handleExample;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.spring.quadriga.domain.resolver.impl.IProjectHandleResolver#
     * setHandleExample(java.lang.String)
     */
    @Override
    public void setHandleExample(String handleExample) {
        this.handleExample = handleExample;
    }

    @Override
    public String getResolvedHandleExample() {
        return resolvedHandleExample;
    }

    @Override
    public void setResolvedHandleExample(String resolvedHandleExample) {
        this.resolvedHandleExample = resolvedHandleExample;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.spring.quadriga.domain.resolver.impl.IProjectHandleResolver#
     * buildResolvedHandle(java.lang.String)
     */
    @Override
    public String buildResolvedHandle(String handle) {
        Pattern pattern = Pattern.compile(handlePattern);
        Matcher matcher = pattern.matcher(handle);

        List<String> groups = new ArrayList<String>();
        while (matcher.find()) {
            for (int i = 0; i < matcher.groupCount(); i++) {
                groups.add(matcher.group(i + 1));
            }
        }

        String resolvedHandle = resolvedHandlePattern;
        for (int i = 0; i < groups.size(); i++) {
            resolvedHandle = resolvedHandle.replace("{" + i + "}", groups.get(i));
        }

        return resolvedHandle;
    }

    @Override
    public Status getValidation() {
        return validation;
    }

    @Override
    public void setValidation(Status validation) {
        this.validation = validation;
    }
}
