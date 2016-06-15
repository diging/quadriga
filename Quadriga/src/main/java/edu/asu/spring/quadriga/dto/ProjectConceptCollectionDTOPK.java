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
 *project concept collection.
 * @author Karthik
 */
@Embeddable
public class ProjectConceptCollectionDTOPK implements Serializable {
	
	private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "projectid")
    private String projectid;
    @Basic(optional = false)
    @Column(name = "conceptcollectionid")
    private String conceptcollectionid;

    public ProjectConceptCollectionDTOPK() {
    }

    public ProjectConceptCollectionDTOPK(String projectid, String conceptcollectionid) {
        this.projectid = projectid;
        this.conceptcollectionid = conceptcollectionid;
    }

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid;
    }

    public String getConceptcollectionid() {
        return conceptcollectionid;
    }

    public void setConceptcollectionid(String conceptcollectionid) {
        this.conceptcollectionid = conceptcollectionid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (projectid != null ? projectid.hashCode() : 0);
        hash += (conceptcollectionid != null ? conceptcollectionid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ProjectConceptCollectionDTOPK)) {
            return false;
        }
        ProjectConceptCollectionDTOPK other = (ProjectConceptCollectionDTOPK) object;
        if ((this.projectid == null && other.projectid != null) || (this.projectid != null && !this.projectid.equals(other.projectid))) {
            return false;
        }
        if ((this.conceptcollectionid == null && other.conceptcollectionid != null) || (this.conceptcollectionid != null && !this.conceptcollectionid.equals(other.conceptcollectionid))) {
            return false;
        }
        return true;
    }
}
