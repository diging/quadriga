/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.spring.quadriga.dto;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represents the column mappings for statistics settings table.
 * 
 * @author Ajay Modi
 */
@Entity
@Table(name = "tbl_project_statistics_settings")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "StatisticsSettingsDTO.findAll", query = "SELECT p FROM StatisticsSettingsDTO p"),
        @NamedQuery(name = "StatisticsSettingsDTO.findByProjectid", query = "SELECT p FROM StatisticsSettingsDTO p WHERE p.statisticsSettingsDTOPK.projectid = :projectid"),
        @NamedQuery(name = "StatisticsSettingsDTO.findByName", query = "SELECT p FROM StatisticsSettingsDTO p WHERE p.statisticsSettingsDTOPK.projectid = :projectid and p.statisticsSettingsDTOPK.name = :name"), })
public class StatisticsSettingsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected StatisticsSettingsDTOPK statisticsSettingsDTOPK;

    @Basic(optional = false)
    @Column(name = "ischecked")
    private Boolean ischecked;

    public String getProjectId() {
        return this.statisticsSettingsDTOPK.getProjectid();
    }

    public String getName() {
        return this.statisticsSettingsDTOPK.getName();
    }

    public StatisticsSettingsDTO() {
    }

    public StatisticsSettingsDTO(
            StatisticsSettingsDTOPK projectStatisticsDTOPK, Boolean isChecked) {
        this.statisticsSettingsDTOPK = projectStatisticsDTOPK;
        this.ischecked = isChecked;
    }

    public StatisticsSettingsDTO(String projectid, String name,
            Boolean isChecked) {
        this.statisticsSettingsDTOPK = new StatisticsSettingsDTOPK(projectid,
                name);
        this.ischecked = isChecked;
    }

    public StatisticsSettingsDTOPK getProjectStatisticsDTOPK() {
        return statisticsSettingsDTOPK;
    }

    public void setProjectStatisticsDTOPK(
            StatisticsSettingsDTOPK projectStatisticsDTOPK) {
        this.statisticsSettingsDTOPK = projectStatisticsDTOPK;
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
        hash += (statisticsSettingsDTOPK != null ? statisticsSettingsDTOPK
                .hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof StatisticsSettingsDTO)) {
            return false;
        }
        StatisticsSettingsDTO other = (StatisticsSettingsDTO) object;
        if ((this.statisticsSettingsDTOPK == null && other.statisticsSettingsDTOPK != null)
                || (this.statisticsSettingsDTOPK != null && !this.statisticsSettingsDTOPK
                        .equals(other.statisticsSettingsDTOPK))) {
            return false;
        }
        return true;
    }
}
