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
public class ProjectDictionaryDTOPK implements Serializable {
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
        // TODO: Warning - this method won't work in the case the id fields are not set
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

    @Override
    public String toString() {
        return "hpsdtogeneration.ProjectDictionaryDTOPK[ projectid=" + projectid + ", dictionaryid=" + dictionaryid + " ]";
    }
    
}