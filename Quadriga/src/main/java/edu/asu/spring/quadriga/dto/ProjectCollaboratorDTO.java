/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.spring.quadriga.dto;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 *This class represents the column mappings for project collaborator 
 *table.
 * @author Karthik
 */
@Entity
@Table(name = "tbl_project_collaborator")
@NamedQueries({
    @NamedQuery(name = "ProjectCollaboratorDTO.findAll", query = "SELECT p FROM ProjectCollaboratorDTO p"),
    @NamedQuery(name = "ProjectCollaboratorDTO.findByProjectid", query = "SELECT p FROM ProjectCollaboratorDTO p WHERE p.collaboratorDTOPK.projectid = :projectid"),
    @NamedQuery(name = "ProjectCollaboratorDTO.findByCollaboratoruser", query = "SELECT p FROM ProjectCollaboratorDTO p WHERE p.collaboratorDTOPK.collaboratoruser = :collaboratoruser"),
    @NamedQuery(name = "ProjectCollaboratorDTO.findByCollaboratorrole", query = "SELECT p FROM ProjectCollaboratorDTO p WHERE p.collaboratorDTOPK.collaboratorrole = :collaboratorrole"),
    })
public class ProjectCollaboratorDTO extends CollaboratorDTO<ProjectCollaboratorDTOPK, ProjectCollaboratorDTO> {
    private static final long serialVersionUID = 1L;
    
    @JoinColumn(name = "projectid", referencedColumnName = "projectid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ProjectDTO projectDTO;

    public ProjectCollaboratorDTO() { }

    public ProjectCollaboratorDTO(ProjectCollaboratorDTOPK projectCollaboratorDTOPK, String updatedby, Date updateddate, String createdby, Date createddate) {
        this.collaboratorDTOPK = projectCollaboratorDTOPK;
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
    }

    public ProjectCollaboratorDTO(String projectid, String collaboratoruser, String collaboratorrole, String updatedby, Date updateddate, String createdby, Date createddate) {
        this.collaboratorDTOPK = new ProjectCollaboratorDTOPK(projectid, collaboratoruser, collaboratorrole);
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
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
        hash += (collaboratorDTOPK != null ? collaboratorDTOPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ProjectCollaboratorDTO)) {
            return false;
        }
        ProjectCollaboratorDTO other = (ProjectCollaboratorDTO) object;
        if ((this.collaboratorDTOPK == null && other.collaboratorDTOPK != null) || (this.collaboratorDTOPK != null && !this.collaboratorDTOPK.equals(other.collaboratorDTOPK))) {
            return false;
        }
        return true;
    }

    @Override
    public void setRelatedDTO(
            CollaboratingDTO<ProjectCollaboratorDTOPK, ProjectCollaboratorDTO> relatedDto) {
        projectDTO = (ProjectDTO) relatedDto;
        
    }
}
