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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

//import org.hibernate.annotations.OnDelete;
//import org.hibernate.annotations.OnDeleteAction;

/**
 *This class represents the column mappings for workspace table.
 * @author Karthik
 */
@Entity
@Table(name = "tbl_workspace")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WorkspaceDTO.findAll", query = "SELECT w FROM WorkspaceDTO w"),
    @NamedQuery(name = "WorkspaceDTO.findByWorkspacename", query = "SELECT w FROM WorkspaceDTO w WHERE w.workspacename = :workspacename"),
    @NamedQuery(name = "WorkspaceDTO.findByWorkspaceid", query = "SELECT w FROM WorkspaceDTO w WHERE w.workspaceid = :workspaceid"),
    @NamedQuery(name = "WorkspaceDTO.findByIsarchived", query = "SELECT w FROM WorkspaceDTO w WHERE w.isarchived = :isarchived"),
    @NamedQuery(name = "WorkspaceDTO.findByIsdeactivated", query = "SELECT w FROM WorkspaceDTO w WHERE w.isdeactivated = :isdeactivated"),
    })
public class WorkspaceDTO implements Serializable {
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
	@JoinColumn(name = "workspaceowner", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private QuadrigaUserDTO workspaceowner;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workspaceDTO", orphanRemoval=true)
    private List<WorkspaceCollaboratorDTO> workspaceCollaboratorDTOList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workspaceDTO")
    private List<WorkspaceConceptcollectionDTO> workspaceConceptCollectionDTOList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workspaceDTO")
    private List<WorkspaceDictionaryDTO> workspaceDictionaryDTOList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workspaceDTO")
    private List<WorkspaceDspaceDTO> workspaceDspaceDTOList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workspaceDTO")
    private List<NetworksDTO> workspaceNetworksDTOList;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "workspaceDTO")
    private List<WorkspaceEditorDTO> workspaceEditorDTOList;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "workspaceDTO")
//    @OnDelete(action=OnDeleteAction.NO_ACTION)
    private List<ProjectWorkspaceDTO> projectWorkspaceDTOList;


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
    
    @XmlTransient
    public List<WorkspaceDictionaryDTO> getWorkspaceDictionaryDTOList() {
		return workspaceDictionaryDTOList;
	}
    
    @XmlTransient
    public List<NetworksDTO> getWorkspaceNetworksDTOList() {
		return workspaceNetworksDTOList;
	}

	public void setWorkspaceNetworksDTOList(
			List<NetworksDTO> workspaceNetworksDTOList) {
		this.workspaceNetworksDTOList = workspaceNetworksDTOList;
	}

	public void setWorkspaceDictionaryDTOList(
			List<WorkspaceDictionaryDTO> workspaceDictionaryDTOList) {
		this.workspaceDictionaryDTOList = workspaceDictionaryDTOList;
	}

	@XmlTransient
	public List<WorkspaceEditorDTO> getWorkspaceEditorDTOList() {
		return workspaceEditorDTOList;
	}

	public void setWorkspaceEditorDTOList(
			List<WorkspaceEditorDTO> workspaceEditorDTOList) {
		this.workspaceEditorDTOList = workspaceEditorDTOList;
	}

	@XmlTransient
    public List<WorkspaceConceptcollectionDTO> getWorkspaceConceptCollectionDTOList() {
  		return workspaceConceptCollectionDTOList;
  	}

  	public void setWorkspaceConceptCollectionDTOList(
  			List<WorkspaceConceptcollectionDTO> workspaceConceptCollectionDTOList) {
  		this.workspaceConceptCollectionDTOList = workspaceConceptCollectionDTOList;
  	}

    @XmlTransient
    public List<WorkspaceDspaceDTO> getWorkspaceDspaceDTOList() {
        return workspaceDspaceDTOList;
    }

    public void setWorkspaceDspaceDTOList(List<WorkspaceDspaceDTO> workspaceDspaceDTOList) {
        this.workspaceDspaceDTOList = workspaceDspaceDTOList;
    }

    @XmlTransient
    public List<WorkspaceCollaboratorDTO> getWorkspaceCollaboratorDTOList() {
        return workspaceCollaboratorDTOList;
    }

    public void setWorkspaceCollaboratorDTOList(List<WorkspaceCollaboratorDTO> workspaceCollaboratorDTOList) {
        this.workspaceCollaboratorDTOList = workspaceCollaboratorDTOList;
    }

    @XmlTransient
    public List<ProjectWorkspaceDTO> getProjectWorkspaceDTOList() {
        return projectWorkspaceDTOList;
    }

    public void setProjectWorkspaceDTOList(List<ProjectWorkspaceDTO> projectWorkspaceDTOList) {
        this.projectWorkspaceDTOList = projectWorkspaceDTOList;
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
}
