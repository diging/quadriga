package edu.asu.spring.quadriga.domain.implementation;

import java.util.Date;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceBitStream;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceCollaborator;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceConceptCollection;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceDictionary;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceNetwork;

/**
 * @description : WorkSpace class describing the properties 
 *                of a WorkSpace object
 * 
 * @author      : Kiran Kumar Batna
 *
 */
public class WorkSpace implements IWorkSpace 
{
	private String workspaceId;
	private String workspaceName;
	private String description;
	private IUser owner;
	private IWorkspaceCollaborator workspaceCollaborator;
	private IProject project;
	private IWorkspaceBitStream workspaceBitStream;
	private IWorkspaceConceptCollection workspaceConceptCollection;
	private IWorkspaceDictionary workspaceDictionary;
	private IWorkspaceNetwork workspaceNetwork;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;
	
	@Override
	public String getWorkspaceId() {
		return workspaceId;
	}
	@Override
	public void setWorkspaceId(String id) {
		this.workspaceId = id;
	}
	
	@Override
	public String getWorkspaceName() {
		return workspaceName;
	}
	@Override
	public void setWorkspaceName(String name) {
		this.workspaceName = name;
	}
	@Override
	public String getDescription() {
		return description;
	}
	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public IUser getOwner() {
		return owner;
	}
	@Override
	public void setOwner(IUser owner) {
		this.owner = owner;
	}
	
	@Override
	public IWorkspaceCollaborator getWorkspaceCollaborators() {
		return workspaceCollaborator;
	}
	@Override
	public void setWorkspaceCollaborators(
			IWorkspaceCollaborator workspaceCollaborator) {
       this.workspaceCollaborator = workspaceCollaborator;		
	}
	
	@Override
	public IProject getProject() {
		return project;
	}
	@Override
	public void setProject(IProject project) {
		this.project = project;
	}
	
	@Override
	public IWorkspaceBitStream getWorkspaceBitStreams() {
		return workspaceBitStream;
	}
	@Override
	public void setWorkspaceBitStreams(IWorkspaceBitStream workspaceBitStream) {
        this.workspaceBitStream = workspaceBitStream;		
	}
	
	@Override
	public IWorkspaceConceptCollection getWorkspaceConceptCollections() {
		return workspaceConceptCollection;
	}
	
	@Override
	public void setWorkspaceConceptCollections(
			IWorkspaceConceptCollection workspaceConceptCollection) {
        this.workspaceConceptCollection = workspaceConceptCollection;		
	}
	
	@Override
	public IWorkspaceDictionary getWorkspaceDictinaries() {
		return workspaceDictionary;
	}
	@Override
	public void setWorkspaceDictionaries(
			IWorkspaceDictionary workspaceDictionary) {
       this.workspaceDictionary = workspaceDictionary;		
	}
	
	@Override
	public IWorkspaceNetwork getWorkspaceNetworks() {
		return workspaceNetwork;
	}
	@Override
	public void setWorkspaceNetworks(IWorkspaceNetwork workspaceNetwork) {
        this.workspaceNetwork = workspaceNetwork;		
	}
	
	@Override
	public String getCreatedBy() {
		return createdBy;
	}
	@Override
	public void setCreatedBy(String createdBy) {
         this.createdBy = createdBy;		
	}
	@Override
	public Date getCreatedDate() {
		return createdDate;
	}
	@Override
	public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;		
	}
	@Override
	public String getUpdatedBy() {
		return updatedBy;
	}
	@Override
	public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;		
	}
	@Override
	public Date getUpdatedDate() {
		return updatedDate;
	}
	@Override
	public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;		
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + ((project == null) ? 0 : project.hashCode());
		result = prime * result
				+ ((updatedBy == null) ? 0 : updatedBy.hashCode());
		result = prime * result
				+ ((updatedDate == null) ? 0 : updatedDate.hashCode());
		result = prime
				* result
				+ ((workspaceBitStream == null) ? 0 : workspaceBitStream
						.hashCode());
		result = prime
				* result
				+ ((workspaceCollaborator == null) ? 0 : workspaceCollaborator
						.hashCode());
		result = prime
				* result
				+ ((workspaceConceptCollection == null) ? 0
						: workspaceConceptCollection.hashCode());
		result = prime
				* result
				+ ((workspaceDictionary == null) ? 0 : workspaceDictionary
						.hashCode());
		result = prime * result
				+ ((workspaceId == null) ? 0 : workspaceId.hashCode());
		result = prime * result
				+ ((workspaceName == null) ? 0 : workspaceName.hashCode());
		result = prime
				* result
				+ ((workspaceNetwork == null) ? 0 : workspaceNetwork.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorkSpace other = (WorkSpace) obj;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (project == null) {
			if (other.project != null)
				return false;
		} else if (!project.equals(other.project))
			return false;
		if (updatedBy == null) {
			if (other.updatedBy != null)
				return false;
		} else if (!updatedBy.equals(other.updatedBy))
			return false;
		if (updatedDate == null) {
			if (other.updatedDate != null)
				return false;
		} else if (!updatedDate.equals(other.updatedDate))
			return false;
		if (workspaceBitStream == null) {
			if (other.workspaceBitStream != null)
				return false;
		} else if (!workspaceBitStream.equals(other.workspaceBitStream))
			return false;
		if (workspaceCollaborator == null) {
			if (other.workspaceCollaborator != null)
				return false;
		} else if (!workspaceCollaborator.equals(other.workspaceCollaborator))
			return false;
		if (workspaceConceptCollection == null) {
			if (other.workspaceConceptCollection != null)
				return false;
		} else if (!workspaceConceptCollection
				.equals(other.workspaceConceptCollection))
			return false;
		if (workspaceDictionary == null) {
			if (other.workspaceDictionary != null)
				return false;
		} else if (!workspaceDictionary.equals(other.workspaceDictionary))
			return false;
		if (workspaceId == null) {
			if (other.workspaceId != null)
				return false;
		} else if (!workspaceId.equals(other.workspaceId))
			return false;
		if (workspaceName == null) {
			if (other.workspaceName != null)
				return false;
		} else if (!workspaceName.equals(other.workspaceName))
			return false;
		if (workspaceNetwork == null) {
			if (other.workspaceNetwork != null)
				return false;
		} else if (!workspaceNetwork.equals(other.workspaceNetwork))
			return false;
		return true;
	}
	
	
	
}
