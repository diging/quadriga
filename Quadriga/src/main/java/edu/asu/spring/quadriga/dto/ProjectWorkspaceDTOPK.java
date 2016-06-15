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
 *project workspace table.
 * @author Karthik
 */
@Embeddable
public class ProjectWorkspaceDTOPK implements Serializable {
	private static final long serialVersionUID = 1L;
	
    @Basic(optional = false)
    @Column(name = "projectid")
    private String projectid;
    @Basic(optional = false)
    @Column(name = "workspaceid")
    private String workspaceid;

    public ProjectWorkspaceDTOPK() {
    }

    public ProjectWorkspaceDTOPK(String projectid, String workspaceid) {
        this.projectid = projectid;
        this.workspaceid = workspaceid;
    }

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid;
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
        hash += (projectid != null ? projectid.hashCode() : 0);
        hash += (workspaceid != null ? workspaceid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ProjectWorkspaceDTOPK)) {
            return false;
        }
        ProjectWorkspaceDTOPK other = (ProjectWorkspaceDTOPK) object;
        if ((this.projectid == null && other.projectid != null) || (this.projectid != null && !this.projectid.equals(other.projectid))) {
            return false;
        }
        if ((this.workspaceid == null && other.workspaceid != null) || (this.workspaceid != null && !this.workspaceid.equals(other.workspaceid))) {
            return false;
        }
        return true;
    }
}
