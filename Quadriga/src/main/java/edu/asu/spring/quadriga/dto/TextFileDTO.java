package edu.asu.spring.quadriga.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DTO for Text File Operations
 * 
 * @author Nischal Samji
 *
 */
@Entity
@Table(name = "tbl_textfiles")
public class TextFileDTO implements Serializable {

    private static final long serialVersionUID = -1798070786993154676L;

    @Id
    @Column(name = "textid", unique = true)
    private String textId;
    @Column(name = "refid")
    private String refId;
    @Column(name = "projectid")
    private String projectId;
    @Column(name = "filename")
    private String filename;
    @Column(name = "wsid")
    private String workspaceId;
    @Column(name = "accessibility")
    private String accessibility;
    
    private String title;
    private String author;
    private String creationDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getTextId() {
        return textId;
    }

    public void setTextId(String textId) {
        this.textId = textId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String project) {
        this.projectId = project;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(String accessibility) {
        this.accessibility = accessibility;
    }

}
