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
 *project dictionary table.
 * @author Karthik
 */
@Embeddable
public class ProjectDictionaryDTOPK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    @Basic(optional = false)
    @Column(name = "projectid")
    private String projectid;
    @Basic(optional = false)
    @Column(name = "dictionaryid")
    private String dictionaryid;

    public ProjectDictionaryDTOPK() {
    }

    public ProjectDictionaryDTOPK(String projectid, String dictionaryid) {
        this.projectid = projectid;
        this.dictionaryid = dictionaryid;
    }
    
    public ProjectDictionaryDTOPK(String dictionaryid){
    	this.dictionaryid = dictionaryid;
    }
    

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid;
    }

    public String getDictionaryid() {
        return dictionaryid;
    }

    public void setDictionaryid(String dictionaryid) {
        this.dictionaryid = dictionaryid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (projectid != null ? projectid.hashCode() : 0);
        hash += (dictionaryid != null ? dictionaryid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ProjectDictionaryDTOPK)) {
            return false;
        }
        ProjectDictionaryDTOPK other = (ProjectDictionaryDTOPK) object;
        if ((this.projectid == null && other.projectid != null) || (this.projectid != null && !this.projectid.equals(other.projectid))) {
            return false;
        }
        if ((this.dictionaryid == null && other.dictionaryid != null) || (this.dictionaryid != null && !this.dictionaryid.equals(other.dictionaryid))) {
            return false;
        }
        return true;
    }
}
