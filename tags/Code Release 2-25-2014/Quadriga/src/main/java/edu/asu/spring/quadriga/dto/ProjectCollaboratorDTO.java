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
 *This class represents the column mappings for project collaborator 
 *table.
 * @author Karthik
 */
@Entity
@Table(name = "tbl_project_collaborator")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProjectCollaboratorDTO.findAll", query = "SELECT p FROM ProjectCollaboratorDTO p"),
    @NamedQuery(name = "ProjectCollaboratorDTO.findByProjectid", query = "SELECT p FROM ProjectCollaboratorDTO p WHERE p.projectCollaboratorDTOPK.projectid = :projectid"),
    @NamedQuery(name = "ProjectCollaboratorDTO.findByCollaboratoruser", query = "SELECT p FROM ProjectCollaboratorDTO p WHERE p.projectCollaboratorDTOPK.collaboratoruser = :collaboratoruser"),
    @NamedQuery(name = "ProjectCollaboratorDTO.findByCollaboratorrole", query = "SELECT p FROM ProjectCollaboratorDTO p WHERE p.projectCollaboratorDTOPK.collaboratorrole = :collaboratorrole"),
    })
public class ProjectCollaboratorDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProjectCollaboratorDTOPK projectCollaboratorDTOPK;
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
    @JoinColumn(name = "collaboratoruser", referencedColumnName = "username", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private QuadrigaUserDTO quadrigaUserDTO;
    @JoinColumn(name = "projectid", referencedColumnName = "projectid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ProjectDTO projectDTO;

    public ProjectCollaboratorDTO() {
    }

    public ProjectCollaboratorDTO(ProjectCollaboratorDTOPK projectCollaboratorDTOPK, String updatedby, Date updateddate, String createdby, Date createddate) {
        this.projectCollaboratorDTOPK = projectCollaboratorDTOPK;
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
    }

    public ProjectCollaboratorDTO(String projectid, String collaboratoruser, String collaboratorrole, String updatedby, Date updateddate, String createdby, Date createddate) {
        this.projectCollaboratorDTOPK = new ProjectCollaboratorDTOPK(projectid, collaboratoruser, collaboratorrole);
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
    }

    public ProjectCollaboratorDTOPK getProjectCollaboratorDTOPK() {
        return projectCollaboratorDTOPK;
    }

    public void setProjectCollaboratorDTOPK(ProjectCollaboratorDTOPK projectCollaboratorDTOPK) {
        this.projectCollaboratorDTOPK = projectCollaboratorDTOPK;
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

    public QuadrigaUserDTO getQuadrigaUserDTO() {
        return quadrigaUserDTO;
    }

    public void setQuadrigaUserDTO(QuadrigaUserDTO quadrigaUserDTO) {
        this.quadrigaUserDTO = quadrigaUserDTO;
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
        hash += (projectCollaboratorDTOPK != null ? projectCollaboratorDTOPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ProjectCollaboratorDTO)) {
            return false;
        }
        ProjectCollaboratorDTO other = (ProjectCollaboratorDTO) object;
        if ((this.projectCollaboratorDTOPK == null && other.projectCollaboratorDTOPK != null) || (this.projectCollaboratorDTOPK != null && !this.projectCollaboratorDTOPK.equals(other.projectCollaboratorDTOPK))) {
            return false;
        }
        return true;
    }
}
