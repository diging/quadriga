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
 *
 * @author Karthik
 */
@Embeddable
public class WorkspaceCollaboratorDTOPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "workspaceid")
    private String workspaceid;
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @Column(name = "collaboratorrole")
    private String collaboratorrole;

    public WorkspaceCollaboratorDTOPK() {
    }

    public WorkspaceCollaboratorDTOPK(String workspaceid, String username, String collaboratorrole) {
        this.workspaceid = workspaceid;
        this.username = username;
        this.collaboratorrole = collaboratorrole;
    }

    public String getWorkspaceid() {
        return workspaceid;
    }

    public void setWorkspaceid(String workspaceid) {
        this.workspaceid = workspaceid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCollaboratorrole() {
        return collaboratorrole;
    }

    public void setCollaboratorrole(String collaboratorrole) {
        this.collaboratorrole = collaboratorrole;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (workspaceid != null ? workspaceid.hashCode() : 0);
        hash += (username != null ? username.hashCode() : 0);
        hash += (collaboratorrole != null ? collaboratorrole.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WorkspaceCollaboratorDTOPK)) {
            return false;
        }
        WorkspaceCollaboratorDTOPK other = (WorkspaceCollaboratorDTOPK) object;
        if ((this.workspaceid == null && other.workspaceid != null) || (this.workspaceid != null && !this.workspaceid.equals(other.workspaceid))) {
            return false;
        }
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        if ((this.collaboratorrole == null && other.collaboratorrole != null) || (this.collaboratorrole != null && !this.collaboratorrole.equals(other.collaboratorrole))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hpsdtogeneration.WorkspaceCollaboratorDTOPK[ workspaceid=" + workspaceid + ", username=" + username + ", collaboratorrole=" + collaboratorrole + " ]";
    }
    
}
