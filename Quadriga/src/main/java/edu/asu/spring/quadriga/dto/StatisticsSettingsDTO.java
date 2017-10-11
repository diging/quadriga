/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.spring.quadriga.dto;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * This class represents the column mappings for statistics settings table.
 * 
 * @author Ajay Modi
 */
@Entity
@Table(name = "tbl_project_statistics_settings")
@NamedQueries({
        @NamedQuery(name = "StatisticsSettingsDTO.findAll", query = "SELECT p FROM StatisticsSettingsDTO p"),
        @NamedQuery(name = "StatisticsSettingsDTO.findByProjectid", query = "SELECT p FROM StatisticsSettingsDTO p WHERE p.projectid = :projectid"),
        @NamedQuery(name = "StatisticsSettingsDTO.findByName", query = "SELECT p FROM StatisticsSettingsDTO p WHERE p.projectid = :projectid and p.name = :name"), })
public class StatisticsSettingsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    // @EmbeddedId
    // protected StatisticsSettingsDTOPK statisticsSettingsDTOPK;

    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String id;

    @Basic(optional = false)
    @Column(name = "projectid")
    private String projectid;

    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @Column(name = "ischecked")
    private Boolean ischecked;

    public String getProjectid() {
        return projectid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public StatisticsSettingsDTO() {
    }

    public StatisticsSettingsDTO(String id, String projectid, String name,
            Boolean isChecked) {
        this.id = id;
        this.projectid = projectid;
        this.name = name;
        this.ischecked = isChecked;
    }

    public Boolean getIschecked() {
        return ischecked;
    }

    public void setIschecked(Boolean ischecked) {
        this.ischecked = ischecked;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof StatisticsSettingsDTO)) {
            return false;
        }
        StatisticsSettingsDTO other = (StatisticsSettingsDTO) object;
        if ((this.id == null && other.id != null)
                || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
}
