/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.spring.quadriga.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *This class represents column mappings for project workspace table.
 * @author Karthik
 */
@Entity
@Table(name = "tbl_project_workspace")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProjectWorkspaceDTO.findAll", query = "SELECT p FROM ProjectWorkspaceDTO p"),
    @NamedQuery(name = "ProjectWorkspaceDTO.findByProjectid", query = "SELECT p FROM ProjectWorkspaceDTO p WHERE p.projectWorkspaceDTOPK.projectid = :projectid"),
    @NamedQuery(name = "ProjectWorkspaceDTO.findByWorkspaceid", query = "SELECT p FROM ProjectWorkspaceDTO p WHERE p.projectWorkspaceDTOPK.workspaceid = :workspaceid"),
    })

public class ProjectWorkspaceDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProjectWorkspaceDTOPK projectWorkspaceDTOPK;
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
    @JoinColumn(name = "workspaceid", referencedColumnName = "workspaceid",insertable = false, updatable = false)
    @ManyToOne(optional = false,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private WorkspaceDTO workspaceDTO;
    @JoinColumn(name = "projectid", referencedColumnName = "projectid",insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ProjectDTO projectDTO;

    public ProjectWorkspaceDTO() {
    }

    public ProjectWorkspaceDTO(ProjectWorkspaceDTOPK projectWorkspaceDTOPK, String updatedby, Date updateddate, String createdby, Date createddate) {
        this.projectWorkspaceDTOPK = projectWorkspaceDTOPK;
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
    }

    public ProjectWorkspaceDTO(String projectid, String workspaceid,String updatedby, Date updateddate, String createdby, Date createddate) {
        this.projectWorkspaceDTOPK = new ProjectWorkspaceDTOPK(projectid, workspaceid);
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
    }

    public ProjectWorkspaceDTOPK getProjectWorkspaceDTOPK() {
        return projectWorkspaceDTOPK;
    }

    public void setProjectWorkspaceDTOPK(ProjectWorkspaceDTOPK projectWorkspaceDTOPK) {
        this.projectWorkspaceDTOPK = projectWorkspaceDTOPK;
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

    public WorkspaceDTO getWorkspaceDTO() {
        return workspaceDTO;
    }

    public void setWorkspaceDTO(WorkspaceDTO workspaceDTO) {
        this.workspaceDTO = workspaceDTO;
    }

    public ProjectDTO getProjectDTO() {
        return projectDTO;
    }

    public void setProjectDTO(ProjectDTO projectDTO) {
        this.projectDTO = projectDTO;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (projectWorkspaceDTOPK != null ? projectWorkspaceDTOPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ProjectWorkspaceDTO)) {
            return false;
        }
        ProjectWorkspaceDTO other = (ProjectWorkspaceDTO) object;
        if ((this.projectWorkspaceDTOPK == null && other.projectWorkspaceDTOPK != null) || (this.projectWorkspaceDTOPK != null && !this.projectWorkspaceDTOPK.equals(other.projectWorkspaceDTOPK))) {
            return false;
        }
        return true;
    }
}
