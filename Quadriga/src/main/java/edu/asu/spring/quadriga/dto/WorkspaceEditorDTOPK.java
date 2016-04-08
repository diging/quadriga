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
 * This class represents the primary key column mapping
 * for workspace editor table.
 * @author Karthik
 */
@Embeddable
public class WorkspaceEditorDTOPK implements Serializable 
{
	private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "workspaceid")
    private String workspaceid;
    @Basic(optional = false)
    @Column(name = "editor")
    private String editor;

	public WorkspaceEditorDTOPK() {
    }

    public WorkspaceEditorDTOPK(String workspaceid, String editor) {
        this.workspaceid = workspaceid;
        this.editor = editor;
    }

    public String getWorkspaceid() {
        return workspaceid;
    }

    public void setWorkspaceid(String workspaceid) {
        this.workspaceid = workspaceid;
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
        hash += (workspaceid != null ? workspaceid.hashCode() : 0);
        hash += (editor != null ? editor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof WorkspaceEditorDTOPK)) {
            return false;
        }
        WorkspaceEditorDTOPK other = (WorkspaceEditorDTOPK) object;
        if ((this.workspaceid == null && other.workspaceid != null) || (this.workspaceid != null && !this.workspaceid.equals(other.workspaceid))) {
            return false;
        }
        if ((this.editor == null && other.editor != null) || (this.editor != null && !this.editor.equals(other.editor))) {
            return false;
        }
        return true;
    }
}
