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
 * This class represents the primary key column mappings for project and name of
 * statistics settings
 * 
 * @author Ajay Modi
 */
@Embeddable
public class StatisticsSettingsDTOPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "projectid")
    private String projectid;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    public StatisticsSettingsDTOPK() {
    }

    public StatisticsSettingsDTOPK(String projectid, String name) {
        this.projectid = projectid;
        this.name = name;
    }

    public StatisticsSettingsDTOPK(String name) {
        this.name = name;
    }

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (projectid != null ? projectid.hashCode() : 0);
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof StatisticsSettingsDTOPK)) {
            return false;
        }
        StatisticsSettingsDTOPK other = (StatisticsSettingsDTOPK) object;
        if ((this.projectid == null && other.projectid != null)
                || (this.projectid != null && !this.projectid
                        .equals(other.projectid))) {
            return false;
        }
        if ((this.name == null && other.name != null)
                || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }
}
