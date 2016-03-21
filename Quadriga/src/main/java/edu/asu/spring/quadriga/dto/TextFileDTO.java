package edu.asu.spring.quadriga.dto;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author Nischal Samji
 * 
 *         DTO for Text File Operations
 *
 */
@Entity
@Table(name = "tbl_textfiles")
@XmlRootElement
public class TextFileDTO implements Serializable {

    private static final long serialVersionUID = -1798070786993154676L;

    @Id
    @Basic(optional = false)
    @Column(name = "textid", unique = true, nullable = false)
    private String textId;
    @Basic(optional = false)
    @Column(name = "refid")
    private String refId;
    @Basic(optional = false)
    @Column(name = "projectid")
    private String projectId;
    @Basic(optional = false)
    @Column(name = "filename")
    private String filename;
    @Basic(optional = false)
    @Column(name = "wsid")
    private String workspaceId;

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

}
