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
@Table(name = "tbl_project_editor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProjectEditorDTO.findAll", query = "SELECT p FROM ProjectEditorDTO p"),
    @NamedQuery(name = "ProjectEditorDTO.findByProjectid", query = "SELECT p FROM ProjectEditorDTO p WHERE p.projectEditorDTOPK.projectid = :projectid"),
    @NamedQuery(name = "ProjectEditorDTO.findByOwner", query = "SELECT p FROM ProjectEditorDTO p WHERE p.projectEditorDTOPK.owner = :owner"),
    @NamedQuery(name = "ProjectEditorDTO.findByUpdatedby", query = "SELECT p FROM ProjectEditorDTO p WHERE p.updatedby = :updatedby"),
    @NamedQuery(name = "ProjectEditorDTO.findByUpdateddate", query = "SELECT p FROM ProjectEditorDTO p WHERE p.updateddate = :updateddate"),
    @NamedQuery(name = "ProjectEditorDTO.findByCreatedby", query = "SELECT p FROM ProjectEditorDTO p WHERE p.createdby = :createdby"),
    @NamedQuery(name = "ProjectEditorDTO.findByCreateddate", query = "SELECT p FROM ProjectEditorDTO p WHERE p.createddate = :createddate")})
public class ProjectEditorDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProjectEditorDTOPK projectEditorDTOPK;
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

    public ProjectEditorDTO() {
    }

    public ProjectEditorDTO(ProjectEditorDTOPK projectEditorDTOPK) {
        this.projectEditorDTOPK = projectEditorDTOPK;
    }

    public ProjectEditorDTO(ProjectEditorDTOPK projectEditorDTOPK, String updatedby, Date updateddate, String createdby, Date createddate) {
        this.projectEditorDTOPK = projectEditorDTOPK;
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
    }

    public ProjectEditorDTO(String projectid, String owner) {
        this.projectEditorDTOPK = new ProjectEditorDTOPK(projectid, owner);
    }

    public ProjectEditorDTOPK getProjectEditorDTOPK() {
        return projectEditorDTOPK;
    }

    public void setProjectEditorDTOPK(ProjectEditorDTOPK projectEditorDTOPK) {
        this.projectEditorDTOPK = projectEditorDTOPK;
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
        hash += (projectEditorDTOPK != null ? projectEditorDTOPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProjectEditorDTO)) {
            return false;
        }
        ProjectEditorDTO other = (ProjectEditorDTO) object;
        if ((this.projectEditorDTOPK == null && other.projectEditorDTOPK != null) || (this.projectEditorDTOPK != null && !this.projectEditorDTOPK.equals(other.projectEditorDTOPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hpsdtogeneration.ProjectEditorDTO[ projectEditorDTOPK=" + projectEditorDTOPK + " ]";
    }
    
}
