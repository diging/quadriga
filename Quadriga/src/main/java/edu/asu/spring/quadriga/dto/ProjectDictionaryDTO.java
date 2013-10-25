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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Karthik
 */
@Entity
@Table(name = "tbl_project_dictionary")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProjectDictionaryDTO.findAll", query = "SELECT p FROM ProjectDictionaryDTO p"),
    @NamedQuery(name = "ProjectDictionaryDTO.findByProjectid", query = "SELECT p FROM ProjectDictionaryDTO p WHERE p.projectDictionaryDTOPK.projectid = :projectid"),
    @NamedQuery(name = "ProjectDictionaryDTO.findByDictionaryid", query = "SELECT p FROM ProjectDictionaryDTO p WHERE p.projectDictionaryDTOPK.dictionaryid = :dictionaryid"),
    @NamedQuery(name = "ProjectDictionaryDTO.findByUpdatedby", query = "SELECT p FROM ProjectDictionaryDTO p WHERE p.updatedby = :updatedby"),
    @NamedQuery(name = "ProjectDictionaryDTO.findByUpdateddate", query = "SELECT p FROM ProjectDictionaryDTO p WHERE p.updateddate = :updateddate"),
    @NamedQuery(name = "ProjectDictionaryDTO.findByCreatedby", query = "SELECT p FROM ProjectDictionaryDTO p WHERE p.createdby = :createdby"),
    @NamedQuery(name = "ProjectDictionaryDTO.findByCreateddate", query = "SELECT p FROM ProjectDictionaryDTO p WHERE p.createddate = :createddate")})
public class ProjectDictionaryDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProjectDictionaryDTOPK projectDictionaryDTOPK;
    @Basic(optional = false)
    @Column(name = "updatedby")
    private String updatedby;
    @Basic(optional = false)
    @Column(name = "updateddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateddate;
    @Basic(optional = false)
    @Column(name = "createdby")
    private String createdby;
    @Basic(optional = false)
    @Column(name = "createddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createddate;

    public ProjectDictionaryDTO() {
    }

    public ProjectDictionaryDTO(ProjectDictionaryDTOPK projectDictionaryDTOPK) {
        this.projectDictionaryDTOPK = projectDictionaryDTOPK;
    }

    public ProjectDictionaryDTO(ProjectDictionaryDTOPK projectDictionaryDTOPK, String updatedby, Date updateddate, String createdby, Date createddate) {
        this.projectDictionaryDTOPK = projectDictionaryDTOPK;
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
    }

    public ProjectDictionaryDTO(String projectid, String dictionaryid) {
        this.projectDictionaryDTOPK = new ProjectDictionaryDTOPK(projectid, dictionaryid);
    }

    public ProjectDictionaryDTOPK getProjectDictionaryDTOPK() {
        return projectDictionaryDTOPK;
    }

    public void setProjectDictionaryDTOPK(ProjectDictionaryDTOPK projectDictionaryDTOPK) {
        this.projectDictionaryDTOPK = projectDictionaryDTOPK;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    public Date getUpdateddate() {
        return updateddate;
    }

    public void setUpdateddate(Date updateddate) {
        this.updateddate = updateddate;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (projectDictionaryDTOPK != null ? projectDictionaryDTOPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProjectDictionaryDTO)) {
            return false;
        }
        ProjectDictionaryDTO other = (ProjectDictionaryDTO) object;
        if ((this.projectDictionaryDTOPK == null && other.projectDictionaryDTOPK != null) || (this.projectDictionaryDTOPK != null && !this.projectDictionaryDTOPK.equals(other.projectDictionaryDTOPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hpsdtogeneration.ProjectDictionaryDTO[ projectDictionaryDTOPK=" + projectDictionaryDTOPK + " ]";
    }
    
}
