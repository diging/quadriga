package edu.asu.spring.quadriga.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import edu.asu.spring.quadriga.domain.settings.IAboutText;

/**
 * DTO for about project text of project's public website.
 * 
 * @author Rajat Aggarwal
 */

@Entity
@Table(name = "tbl_about_text")
@NamedQueries({
        @NamedQuery(name = "AboutTextDTO.findByProjectId", query = "SELECT a FROM AboutTextDTO a where a.projectId= :projectId") })

public class AboutTextDTO implements Serializable, IAboutText {

    private static final long serialVersionUID = 1103019135158917211L;

    @Id
    private String id;

    @Column(name = "ProjectId")
    private String projectId;

    @Column(name = "title", columnDefinition = "text")
    private String title;

    @Column(name = "description", columnDefinition = "text")
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
