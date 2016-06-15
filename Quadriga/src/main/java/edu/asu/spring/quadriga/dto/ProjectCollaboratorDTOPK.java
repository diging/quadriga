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
 *for project collaborator table
 * @author Karthik
 */
@Embeddable
public class ProjectCollaboratorDTOPK extends CollaboratorDTOPK {
	
	/**
     * 
     */
    private static final long serialVersionUID = -5312712348021749534L;
    
    @Basic(optional = false)
    @Column(name = "projectid")
    private String projectid;
    
	public ProjectCollaboratorDTOPK() { }

    public ProjectCollaboratorDTOPK(String projectid, String collaboratoruser, String collaboratorrole) {
        this.projectid = projectid;
        this.collaboratoruser = collaboratoruser;
        this.collaboratorrole = collaboratorrole;
    }

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid;
    }

   
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (projectid != null ? projectid.hashCode() : 0);
        hash += (collaboratoruser != null ? collaboratoruser.hashCode() : 0);
        hash += (collaboratorrole != null ? collaboratorrole.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ProjectCollaboratorDTOPK)) {
            return false;
        }
        ProjectCollaboratorDTOPK other = (ProjectCollaboratorDTOPK) object;
        if ((this.projectid == null && other.projectid != null) || (this.projectid != null && !this.projectid.equals(other.projectid))) {
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
