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
@Table(name = "tbl_project_conceptcollection")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProjectConceptcollectionDTO.findAll", query = "SELECT p FROM ProjectConceptcollectionDTO p"),
    @NamedQuery(name = "ProjectConceptcollectionDTO.findByProjectid", query = "SELECT p FROM ProjectConceptcollectionDTO p WHERE p.projectConceptcollectionDTOPK.projectid = :projectid"),
    @NamedQuery(name = "ProjectConceptcollectionDTO.findByConceptcollectionid", query = "SELECT p FROM ProjectConceptcollectionDTO p WHERE p.projectConceptcollectionDTOPK.conceptcollectionid = :conceptcollectionid"),
    @NamedQuery(name = "ProjectConceptcollectionDTO.findByUpdatedby", query = "SELECT p FROM ProjectConceptcollectionDTO p WHERE p.updatedby = :updatedby"),
    @NamedQuery(name = "ProjectConceptcollectionDTO.findByUpdateddate", query = "SELECT p FROM ProjectConceptcollectionDTO p WHERE p.updateddate = :updateddate"),
    @NamedQuery(name = "ProjectConceptcollectionDTO.findByCreatedby", query = "SELECT p FROM ProjectConceptcollectionDTO p WHERE p.createdby = :createdby"),
    @NamedQuery(name = "ProjectConceptcollectionDTO.findByCreateddate", query = "SELECT p FROM ProjectConceptcollectionDTO p WHERE p.createddate = :createddate")})
public class ProjectConceptcollectionDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProjectConceptcollectionDTOPK projectConceptcollectionDTOPK;
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

    public ProjectConceptcollectionDTO() {
    }

    public ProjectConceptcollectionDTO(ProjectConceptcollectionDTOPK projectConceptcollectionDTOPK) {
        this.projectConceptcollectionDTOPK = projectConceptcollectionDTOPK;
    }

    public ProjectConceptcollectionDTO(ProjectConceptcollectionDTOPK projectConceptcollectionDTOPK, String updatedby, Date updateddate, String createdby, Date createddate) {
        this.projectConceptcollectionDTOPK = projectConceptcollectionDTOPK;
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
    }

    public ProjectConceptcollectionDTO(String projectid, String conceptcollectionid) {
        this.projectConceptcollectionDTOPK = new ProjectConceptcollectionDTOPK(projectid, conceptcollectionid);
    }

    public ProjectConceptcollectionDTOPK getProjectConceptcollectionDTOPK() {
        return projectConceptcollectionDTOPK;
    }

    public void setProjectConceptcollectionDTOPK(ProjectConceptcollectionDTOPK projectConceptcollectionDTOPK) {
        this.projectConceptcollectionDTOPK = projectConceptcollectionDTOPK;
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
        hash += (projectConceptcollectionDTOPK != null ? projectConceptcollectionDTOPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProjectConceptcollectionDTO)) {
            return false;
        }
        ProjectConceptcollectionDTO other = (ProjectConceptcollectionDTO) object;
        if ((this.projectConceptcollectionDTOPK == null && other.projectConceptcollectionDTOPK != null) || (this.projectConceptcollectionDTOPK != null && !this.projectConceptcollectionDTOPK.equals(other.projectConceptcollectionDTOPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hpsdtogeneration.ProjectConceptcollectionDTO[ projectConceptcollectionDTOPK=" + projectConceptcollectionDTOPK + " ]";
    }
    
}
