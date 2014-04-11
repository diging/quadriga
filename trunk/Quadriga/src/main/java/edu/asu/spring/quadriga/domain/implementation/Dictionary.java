package edu.asu.spring.quadriga.domain.implementation;

import java.util.List;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.IDictionaryItem;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;

/**
 * Dictionary class describing the properties 
 *                of a Dictionary object
 * 
 * @author      : Kiran Kumar Batna
 *
 */
public class Dictionary implements IDictionary 
{
	private String dictionaryId;
	private String dictionaryName;
	private String description;
	private IUser owner;
	private List<ICollaborator> collaborators;
	private List<IDictionaryItem> items;
	private List<IProject> projects;
	private List<IWorkSpace> workspaces;

	/**
	 * getter for variable id 
	 * 
	 * @return 	Return id
	 */
	
	@Override
	public String getDictionaryId() {
		return dictionaryId;
	}
	
	/**
	 * setter for variable id 
	 * @param id
	 * @return 	void
	 */
	@Override
	public void setDictionaryId(String id) {
		this.dictionaryId = id;
	}
	
	/**
	 * getter for variable name 
	 * 
	 * @return 	Return name
	 */
	
	@Override
	public String getDictionaryName() {
		return dictionaryName;
	}
	/**
	 * setter for variable name 
	 * @param name
	 * @return 	void
	 */
	@Override
	public void setDictionaryName(String name) {
		this.dictionaryName = name;
	}
	/**
	 * getter for variable description 
	 * 
	 * @return 	Return description
	 */
	@Override
	public String getDescription() {
		return description;
	}
	/**
	 * setter for variable description 
	 * @param description
	 * @return 	void
	 */
	@Override
	public void setDescription(String description) {
		this.description = description;
	}
	

	
	/**
	 * getter for variable owner 
	 * 
	 * @return 	Return owner
	 */
	
	@Override
	public IUser getOwner() {
		return owner;
	}
	/**
	 * setter for variable owner 
	 * @param IUser
	 * @return 	void
	 */
	@Override
	public void setOwner(IUser owner) {
		this.owner = owner;
	}
	/**
	 * getter for variable collaborator 
	 * 
	 * @return 	Return collaborator
	 */
	@Override
	public List<ICollaborator> getCollaborators() {
		return collaborators;
	}
	
	/**
	 * setter for variable collaborator 
	 * @param ICollaborator
	 * @return 	void
	 */
	
	@Override
	public void setCollaborators(List<ICollaborator> ICollaborator) {
		this.collaborators = ICollaborator;
	}
	@Override
	public List<IDictionaryItem> getDictionaryItems() {
		return items;
	}
	@Override
	public void setDictionaryItems(List<IDictionaryItem> dictionaryItems) {
		this.items = dictionaryItems;
	}
	@Override
	public List<IProject> getProjects() {
		return projects;
	}
	
	@Override
	public void setProjects(List<IProject> projects) {
        this.projects = projects;		
	}

	@Override
	public List<IWorkSpace> getWorkspaces() {
		return workspaces;
	}

	@Override
	public void setWorkspaces(List<IWorkSpace> workspaces) {
		this.workspaces = workspaces;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((collaborators == null) ? 0 : collaborators.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((dictionaryId == null) ? 0 : dictionaryId.hashCode());
		result = prime * result
				+ ((dictionaryName == null) ? 0 : dictionaryName.hashCode());
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result
				+ ((projects == null) ? 0 : projects.hashCode());
		result = prime * result
				+ ((workspaces == null) ? 0 : workspaces.hashCode());
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
		Dictionary other = (Dictionary) obj;
		if (collaborators == null) {
			if (other.collaborators != null)
				return false;
		} else if (!collaborators.equals(other.collaborators))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (dictionaryId == null) {
			if (other.dictionaryId != null)
				return false;
		} else if (!dictionaryId.equals(other.dictionaryId))
			return false;
		if (dictionaryName == null) {
			if (other.dictionaryName != null)
				return false;
		} else if (!dictionaryName.equals(other.dictionaryName))
			return false;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (projects == null) {
			if (other.projects != null)
				return false;
		} else if (!projects.equals(other.projects))
			return false;
		if (workspaces == null) {
			if (other.workspaces != null)
				return false;
		} else if (!workspaces.equals(other.workspaces))
			return false;
		return true;
	}
	
	
}
