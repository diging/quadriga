/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.spring.quadriga.dto;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *This class represents the primary key column mappings for
 *project editor table.
 * @author Karthik
 */
@Embeddable
public class ProjectEditorDTOPK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    @Basic(optional = false)
    @Column(name = "projectid")
    private String projectid;
    
    @Basic(optional = false)
    @Column(name = "editor")
    private String editor;

	public ProjectEditorDTOPK() {
    }

    public ProjectEditorDTOPK(String projectid, String editor) {
        this.projectid = projectid;
        this.editor = editor;
    }

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid;
    }
    
    public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (projectid != null ? projectid.hashCode() : 0);
        hash += (editor != null ? editor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ProjectEditorDTOPK)) {
            return false;
        }
        ProjectEditorDTOPK other = (ProjectEditorDTOPK) object;
        if ((this.projectid == null && other.projectid != null) || (this.projectid != null && !this.projectid.equals(other.projectid))) {
            return false;
        }
        if ((this.editor == null && other.editor != null) || (this.editor != null && !this.editor.equals(other.editor))) {
            return false;
        }
        return true;
    }
}
