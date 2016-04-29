package edu.asu.spring.quadriga.domain.impl.dictionary;

import java.util.Date;
import java.util.List;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.dictionary.IDictionaryCollaborator;
import edu.asu.spring.quadriga.domain.dictionary.IDictionaryItems;
import edu.asu.spring.quadriga.domain.workbench.IProjectDictionary;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceDictionary;

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
	private List<IDictionaryCollaborator> dictionaryCollaborators;
	private List<IDictionaryItems> dictionaryItems;
	private List<IProjectDictionary> dictionaryProjects;
	private List<IWorkspaceDictionary> dictionaryWorkspaces;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;

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

	@Override
	public List<IDictionaryCollaborator> getDictionaryCollaborators() {
		return dictionaryCollaborators;
	}

	@Override
	public void setDictionaryCollaborators(
			List<IDictionaryCollaborator> dictionaryCollaborators) {
        this.dictionaryCollaborators = dictionaryCollaborators;		
	}

	@Override
	public List<IDictionaryItems> getDictionaryItems() {
		return dictionaryItems;
	}

	@Override
	public void setDictionaryItems(List<IDictionaryItems> dictionaryItems) {
       this.dictionaryItems = dictionaryItems;		
	}

	@Override
	public List<IProjectDictionary> getDictionaryProjects() {
		return dictionaryProjects;
	}

	@Override
	public void setDictionaryProjects(
			List<IProjectDictionary> dictionaryProjects) {
        this.dictionaryProjects = dictionaryProjects;		
	}

	@Override
	public List<IWorkspaceDictionary> getDictionaryWorkspaces() {
		return dictionaryWorkspaces;
	}

	@Override
	public void setDictionaryWorkspaces(
			List<IWorkspaceDictionary> dictionaryWorkspaces) {
       this.dictionaryWorkspaces = dictionaryWorkspaces;		
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
		result = prime
				* result
				+ ((dictionaryCollaborators == null) ? 0
						: dictionaryCollaborators.hashCode());
		result = prime * result
				+ ((dictionaryId == null) ? 0 : dictionaryId.hashCode());
		result = prime * result
				+ ((dictionaryItems == null) ? 0 : dictionaryItems.hashCode());
		result = prime * result
				+ ((dictionaryName == null) ? 0 : dictionaryName.hashCode());
		result = prime
				* result
				+ ((dictionaryProjects == null) ? 0 : dictionaryProjects
						.hashCode());
		result = prime
				* result
				+ ((dictionaryWorkspaces == null) ? 0 : dictionaryWorkspaces
						.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result
				+ ((updatedBy == null) ? 0 : updatedBy.hashCode());
		result = prime * result
				+ ((updatedDate == null) ? 0 : updatedDate.hashCode());
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
		if (dictionaryCollaborators == null) {
			if (other.dictionaryCollaborators != null)
				return false;
		} else if (!dictionaryCollaborators
				.equals(other.dictionaryCollaborators))
			return false;
		if (dictionaryId == null) {
			if (other.dictionaryId != null)
				return false;
		} else if (!dictionaryId.equals(other.dictionaryId))
			return false;
		if (dictionaryItems == null) {
			if (other.dictionaryItems != null)
				return false;
		} else if (!dictionaryItems.equals(other.dictionaryItems))
			return false;
		if (dictionaryName == null) {
			if (other.dictionaryName != null)
				return false;
		} else if (!dictionaryName.equals(other.dictionaryName))
			return false;
		if (dictionaryProjects == null) {
			if (other.dictionaryProjects != null)
				return false;
		} else if (!dictionaryProjects.equals(other.dictionaryProjects))
			return false;
		if (dictionaryWorkspaces == null) {
			if (other.dictionaryWorkspaces != null)
				return false;
		} else if (!dictionaryWorkspaces.equals(other.dictionaryWorkspaces))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
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
		return true;
	}
}
