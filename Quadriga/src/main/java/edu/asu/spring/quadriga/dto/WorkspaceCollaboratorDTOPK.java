/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.spring.quadriga.dto;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *This class represents the primary key column mappings 
 *for workspace collaborator table.
 * @author Karthik
 */
@Embeddable
public class WorkspaceCollaboratorDTOPK extends CollaboratorDTOPK
{
	private static final long serialVersionUID = 1L;
	
    @Basic(optional = false)
    @Column(name = "workspaceid")
    private String workspaceid;

    public WorkspaceCollaboratorDTOPK() {
    }

    public WorkspaceCollaboratorDTOPK(String workspaceid, String collaboratoruser, String collaboratorrole) {
        this.workspaceid = workspaceid;
        this.collaboratoruser = collaboratoruser;
        this.collaboratorrole = collaboratorrole;
    }

    public String getWorkspaceid() {
        return workspaceid;
    }

    public void setWorkspaceid(String workspaceid) {
        this.workspaceid = workspaceid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (workspaceid != null ? workspaceid.hashCode() : 0);
        hash += (collaboratoruser != null ? collaboratoruser.hashCode() : 0);
        hash += (collaboratorrole != null ? collaboratorrole.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof WorkspaceCollaboratorDTOPK)) {
            return false;
        }
        WorkspaceCollaboratorDTOPK other = (WorkspaceCollaboratorDTOPK) object;
        if ((this.workspaceid == null && other.workspaceid != null) || (this.workspaceid != null && !this.workspaceid.equals(other.workspaceid))) {
            return false;
        }
        if ((this.collaboratoruser == null && other.collaboratoruser != null) || (this.collaboratoruser != null && !this.collaboratoruser.equals(other.collaboratoruser))) {
            return false;
        }
        if ((this.collaboratorrole == null && other.collaboratorrole != null) || (this.collaboratorrole != null && !this.collaboratorrole.equals(other.collaboratorrole))) {
            return false;
        }
        return true;
    }
}
