/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.spring.quadriga.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *This class represents the column mappings for workspace table.
 * @author Karthik
 */
@Entity
@Table(name = "tbl_workspace")
@NamedQueries({
    @NamedQuery(name = "WorkspaceDTO.findAll", query = "SELECT w FROM WorkspaceDTO w"),
    @NamedQuery(name = "WorkspaceDTO.findByWorkspacename", query = "SELECT w FROM WorkspaceDTO w WHERE w.workspacename = :workspacename"),
    @NamedQuery(name = "WorkspaceDTO.findByWorkspaceid", query = "SELECT w FROM WorkspaceDTO w WHERE w.workspaceid = :workspaceid"),
    @NamedQuery(name = "WorkspaceDTO.findByIsarchived", query = "SELECT w FROM WorkspaceDTO w WHERE w.isarchived = :isarchived"),
    @NamedQuery(name = "WorkspaceDTO.findByIsdeactivated", query = "SELECT w FROM WorkspaceDTO w WHERE w.isdeactivated = :isdeactivated"),
    })
public class WorkspaceDTO extends CollaboratingDTO<WorkspaceCollaboratorDTOPK, WorkspaceCollaboratorDTO> implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "workspacename")
    private String workspacename;
   
    @Lob
    @Column(name = "description")
    private String description;
   
    @Id
    @Basic(optional = false)
    @Column(name = "workspaceid")
    private String workspaceid;
   
    @Basic(optional = false)
    @Column(name = "isarchived")
    private Boolean isarchived;
    
    @Basic(optional = false)
    @Column(name = "isdeactivated")
    private Boolean isdeactivated;
	
    @JoinColumn(name = "workspaceowner", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private QuadrigaUserDTO workspaceowner;
   
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workspaceDTO", orphanRemoval=true)
    private List<WorkspaceCollaboratorDTO> workspaceCollaboratorDTOList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workspaceDTO",orphanRemoval=true)
    private List<WorkspaceConceptcollectionDTO> workspaceConceptCollectionDTOList;
   
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workspaceDTO",orphanRemoval=true)
    private List<WorkspaceDictionaryDTO> workspaceDictionaryDTOList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workspaceDTO",orphanRemoval=true)
    private List<WorkspaceEditorDTO> workspaceEditorDTOList;
   
    @OneToOne(cascade = CascadeType.ALL,mappedBy = "workspaceDTO",orphanRemoval=true)
    private ProjectWorkspaceDTO projectWorkspaceDTO;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workspaceDTO",orphanRemoval=true)
    private List<NetworkWorkspaceDTO> workspaceNetworkDTOList;
    
    public WorkspaceDTO() {
    }

    public WorkspaceDTO(String workspaceid, String workspacename, Boolean isarchived, Boolean isdeactivated, String updatedby, Date updateddate, String createdby, Date createddate) {
        this.workspaceid = workspaceid;
        this.workspacename = workspacename;
        this.isarchived = isarchived;
        this.isdeactivated = isdeactivated;
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
    }
    
    public String getWorkspacename() {
        return workspacename;
    }

    public void setWorkspacename(String workspacename) {
        this.workspacename = workspacename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWorkspaceid() {
        return workspaceid;
    }

    public void setWorkspaceid(String workspaceid) {
        this.workspaceid = workspaceid;
    }

    public Boolean getIsarchived() {
        return isarchived;
    }

    public void setIsarchived(Boolean isarchived) {
        this.isarchived = isarchived;
    }

    public Boolean getIsdeactivated() {
        return isdeactivated;
    }

    public void setIsdeactivated(Boolean isdeactivated) {
        this.isdeactivated = isdeactivated;
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
    
    public QuadrigaUserDTO getWorkspaceowner() {
        return workspaceowner;
    }

    public void setWorkspaceowner(QuadrigaUserDTO workspaceowner) {
        this.workspaceowner = workspaceowner;
    }
    
    public List<WorkspaceDictionaryDTO> getWorkspaceDictionaryDTOList() {
		return workspaceDictionaryDTOList;
	}
    
	public void setWorkspaceDictionaryDTOList(
			List<WorkspaceDictionaryDTO> workspaceDictionaryDTOList) {
		this.workspaceDictionaryDTOList = workspaceDictionaryDTOList;
	}

	public List<WorkspaceEditorDTO> getWorkspaceEditorDTOList() {
		return workspaceEditorDTOList;
	}

	public void setWorkspaceEditorDTOList(
			List<WorkspaceEditorDTO> workspaceEditorDTOList) {
		this.workspaceEditorDTOList = workspaceEditorDTOList;
	}

    public List<WorkspaceConceptcollectionDTO> getWorkspaceConceptCollectionDTOList() {
  		return workspaceConceptCollectionDTOList;
  	}

  	public void setWorkspaceConceptCollectionDTOList(
  			List<WorkspaceConceptcollectionDTO> workspaceConceptCollectionDTOList) {
  		this.workspaceConceptCollectionDTOList = workspaceConceptCollectionDTOList;
  	}

    public List<WorkspaceCollaboratorDTO> getWorkspaceCollaboratorDTOList() {
        return workspaceCollaboratorDTOList;
    }

    public void setWorkspaceCollaboratorDTOList(List<WorkspaceCollaboratorDTO> workspaceCollaboratorDTOList) {
        this.workspaceCollaboratorDTOList = workspaceCollaboratorDTOList;
    }

    public ProjectWorkspaceDTO getProjectWorkspaceDTO() {
        return projectWorkspaceDTO;
    }

    public void setProjectWorkspaceDTO(ProjectWorkspaceDTO projectWorkspaceDTO) {
        this.projectWorkspaceDTO = projectWorkspaceDTO;
    }
    
	public List<NetworkWorkspaceDTO> getWorkspaceNetworkDTOList() {
		return workspaceNetworkDTOList;
	}

	public void setWorkspaceNetworkDTOList(
			List<NetworkWorkspaceDTO> workspaceNetworkDTOList) {
		this.workspaceNetworkDTOList = workspaceNetworkDTOList;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (workspaceid != null ? workspaceid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof WorkspaceDTO)) {
            return false;
        }
        WorkspaceDTO other = (WorkspaceDTO) object;
        if ((this.workspaceid == null && other.workspaceid != null) || (this.workspaceid != null && !this.workspaceid.equals(other.workspaceid))) {
            return false;
        }
        return true;
    }

    @Override
    public List<WorkspaceCollaboratorDTO> getCollaboratorList() {
        return workspaceCollaboratorDTOList;
    }

    @Override
    public void setCollaboratorList(List<WorkspaceCollaboratorDTO> list) {
        workspaceCollaboratorDTOList = list;
    }

    @Override
    public String getId() {
        return workspaceid;
    }

    @Override
    public QuadrigaUserDTO getOwner() {
        return workspaceowner;
    }

    @Override
    public void setOwner(QuadrigaUserDTO owner) {
       this.workspaceowner = owner; 
    }
}
