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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *This class represents the column mappings for workspace collaborator table.
 * @author Karthik
 */
@Entity
@Table(name = "tbl_workspace_collaborator")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WorkspaceCollaboratorDTO.findAll", query = "SELECT w FROM WorkspaceCollaboratorDTO w"),
    @NamedQuery(name = "WorkspaceCollaboratorDTO.findByWorkspaceid", query = "SELECT w FROM WorkspaceCollaboratorDTO w WHERE w.collaboratorDTOPK.workspaceid = :workspaceid"),
    @NamedQuery(name = "WorkspaceCollaboratorDTO.findByUsername", query = "SELECT w FROM WorkspaceCollaboratorDTO w WHERE w.collaboratorDTOPK.collaboratoruser = :collaboratoruser"),
    @NamedQuery(name = "WorkspaceCollaboratorDTO.findByCollaboratorrole", query = "SELECT w FROM WorkspaceCollaboratorDTO w WHERE w.collaboratorDTOPK.collaboratorrole = :collaboratorrole"),
    })
public class WorkspaceCollaboratorDTO extends CollaboratorDTO<WorkspaceCollaboratorDTOPK, WorkspaceCollaboratorDTO> {
    
    private static final long serialVersionUID = 1L;
    @JoinColumn(name = "workspaceid", referencedColumnName = "workspaceid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private WorkspaceDTO workspaceDTO;
   
    public WorkspaceCollaboratorDTO() {
    }

    public WorkspaceCollaboratorDTO(WorkspaceCollaboratorDTOPK workspaceCollaboratorDTOPK, String updatedby, Date updateddate, String createdby, Date createddate) {
        this.collaboratorDTOPK = workspaceCollaboratorDTOPK;
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
    }

    public WorkspaceCollaboratorDTO(String workspaceid, String username, String collaboratorrole,String updatedby, Date updateddate, String createdby, Date createddate) {
        this.collaboratorDTOPK = new WorkspaceCollaboratorDTOPK(workspaceid, username, collaboratorrole);
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
    }

    public WorkspaceDTO getWorkspaceDTO() {
        return workspaceDTO;
    }

    public void setWorkspaceDTO(WorkspaceDTO workspaceDTO) {
        this.workspaceDTO = workspaceDTO;
    }

    public QuadrigaUserDTO getQuadrigaUserDTO() {
        return quadrigaUserDTO;
    }

    public void setQuadrigaUserDTO(QuadrigaUserDTO quadrigaUserDTO) {
        this.quadrigaUserDTO = quadrigaUserDTO;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (collaboratorDTOPK != null ? collaboratorDTOPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof WorkspaceCollaboratorDTO)) {
            return false;
        }
        WorkspaceCollaboratorDTO other = (WorkspaceCollaboratorDTO) object;
        if ((this.collaboratorDTOPK == null && other.collaboratorDTOPK != null) || (this.collaboratorDTOPK != null && !this.collaboratorDTOPK.equals(other.collaboratorDTOPK))) {
            return false;
        }
        return true;
    }

    @Override
    public void setRelatedDTO(
            CollaboratingDTO<WorkspaceCollaboratorDTOPK, WorkspaceCollaboratorDTO> relatedDto) {
        workspaceDTO = (WorkspaceDTO) relatedDto;
    }
}
