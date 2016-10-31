package edu.asu.spring.quadriga.domain.settings.impl;

import edu.asu.spring.quadriga.domain.settings.IAboutText;

/**
 * @author Nischal Samji Implementation class for {@link IAboutText}
 *
 */
public class AboutText implements IAboutText {

    private String id;
    private String projectId;
    private String title;
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
