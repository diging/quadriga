/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.spring.quadriga.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represents the column mappings for project statistics settings
 * table.
 * 
 * @author Ajay Modi
 */
@Entity
@Table(name = "tbl_project_statistics_settings")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "ProjectStatisticsDTO.findAll", query = "SELECT p FROM ProjectStatisticsDTO p"),
        @NamedQuery(name = "ProjectStatisticsDTO.findByProjectid", query = "SELECT p FROM ProjectStatisticsDTO p WHERE p.projectStatisticsDTOPK.projectid = :projectid"),
        @NamedQuery(name = "ProjectStatisticsDTO.findByName", query = "SELECT p FROM ProjectStatisticsDTO p WHERE p.projectStatisticsDTOPK.projectid = :projectid and p.projectStatisticsDTOPK.name = :name"), })
public class ProjectStatisticsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected ProjectStatisticsDTOPK projectStatisticsDTOPK;

    @Basic(optional = false)
    @Column(name = "ischecked")
    private Boolean ischecked;

    public ProjectStatisticsDTO() {
    }

    public ProjectStatisticsDTO(ProjectStatisticsDTOPK projectStatisticsDTOPK,
            Boolean isChecked) {
        this.projectStatisticsDTOPK = projectStatisticsDTOPK;
        this.ischecked = isChecked;
    }

    public ProjectStatisticsDTO(String projectid, String name, Boolean isChecked) {
        this.projectStatisticsDTOPK = new ProjectStatisticsDTOPK(projectid,
                name);
        this.ischecked = isChecked;
    }

    public ProjectStatisticsDTOPK getProjectStatisticsDTOPK() {
        return projectStatisticsDTOPK;
    }

    public void setProjectStatisticsDTOPK(
            ProjectStatisticsDTOPK projectStatisticsDTOPK) {
        this.projectStatisticsDTOPK = projectStatisticsDTOPK;
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
        hash += (projectStatisticsDTOPK != null ? projectStatisticsDTOPK
                .hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ProjectStatisticsDTO)) {
            return false;
        }
        ProjectStatisticsDTO other = (ProjectStatisticsDTO) object;
        if ((this.projectStatisticsDTOPK == null && other.projectStatisticsDTOPK != null)
                || (this.projectStatisticsDTOPK != null && !this.projectStatisticsDTOPK
                        .equals(other.projectStatisticsDTOPK))) {
            return false;
        }
        return true;
    }
}
