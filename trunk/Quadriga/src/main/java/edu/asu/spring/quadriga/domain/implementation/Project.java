package edu.asu.spring.quadriga.domain.implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;

/**
 * @description : Project class describing the properties of a Project object
 * 
 * @author : Kiran Kumar Batna
 * 
 */
@Service
public class Project implements IProject {
	private String projectId;
	private String projectName;
	private String description;
	private String unixName;
	private EProjectAccessibility projectAccess;
	private IUser owner;
	private List<ICollaborator> collaborators;
	private List<IWorkSpace> workspaces;
	private List<IConceptCollection> conceptCollections;
	private List<IDictionary> dictionaries;
	
	/**
	 * retrieves the internal id of the project
	 */
	@Override
	public String getProjectId() {
		return projectId;
	}

	/**
	 * assigns the internal id of the project
	 */
	@Override
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	
	/**
	 * retrieves the name of the project
	 */
	@Override
	public String getName() {
		return projectName;
	}

	/**
	 * assigns the name of the project to the supplied variable.
	 */
	@Override
	public void setName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * retrieves the description of the project
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/**
	 * assigns the description of the project
	 */
	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * retrieves the Unix ID of the project
	 */
	@Override
	public String getUnixName() {
		return unixName;
	}

	/**
	 * assigns the Unix ID of the project
	 */
	@Override
	public void setUnixName(String unixName) {
		this.unixName = unixName;
	}

	/**
	 * retrieves the accessibility of the project
	 */
	@Override
	public EProjectAccessibility getProjectAccess() {
		return projectAccess;
	}

	/**
	 * assigns the accessibility of the project
	 */
	@Override
	public void setProjectAccess(EProjectAccessibility projectAccess) {
		this.projectAccess = projectAccess;
	}

	/**
	 * retrieves the owner of the project
	 */
	@Override
	public IUser getOwner() {
		return owner;
	}

	/**
	 * assigns the owner of the project
	 */
	@Override
	public void setOwner(IUser owner) {
		this.owner = owner;
	}

	/**
	 * retrieves the collaborators of the project
	 */
	@Override
	public List<ICollaborator> getCollaborators() {
		return collaborators;
	}

	/**
	 * assigns the collaborators of the project
	 */
	@Override
	public void setCollaborators(List<ICollaborator> collaborators) {
		this.collaborators = collaborators;
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
	public List<IConceptCollection> getConceptCollections() {
		return conceptCollections;
	}

	@Override
	public void setConceptCollections(
			List<IConceptCollection> conceptCollections) {
		this.conceptCollections = conceptCollections;
	}

	@Override
	public List<IDictionary> getDictionaries() {
		return dictionaries;
	}

	@Override
	public void setDictionaries(List<IDictionary> dictionaries) {
		 this.dictionaries = dictionaries;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((collaborators == null) ? 0 : collaborators.hashCode());
		result = prime
				* result
				+ ((conceptCollections == null) ? 0 : conceptCollections
						.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((dictionaries == null) ? 0 : dictionaries.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result
				+ ((projectAccess == null) ? 0 : projectAccess.hashCode());
		result = prime * result
				+ ((projectId == null) ? 0 : projectId.hashCode());
		result = prime * result
				+ ((projectName == null) ? 0 : projectName.hashCode());
		result = prime * result
				+ ((unixName == null) ? 0 : unixName.hashCode());
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
		Project other = (Project) obj;
		if (collaborators == null) {
			if (other.collaborators != null)
				return false;
		} else if (!collaborators.equals(other.collaborators))
			return false;
		if (conceptCollections == null) {
			if (other.conceptCollections != null)
				return false;
		} else if (!conceptCollections.equals(other.conceptCollections))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (dictionaries == null) {
			if (other.dictionaries != null)
				return false;
		} else if (!dictionaries.equals(other.dictionaries))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (projectAccess != other.projectAccess)
			return false;
		if (projectId == null) {
			if (other.projectId != null)
				return false;
		} else if (!projectId.equals(other.projectId))
			return false;
		if (projectName == null) {
			if (other.projectName != null)
				return false;
		} else if (!projectName.equals(other.projectName))
			return false;
		if (unixName == null) {
			if (other.unixName != null)
				return false;
		} else if (!unixName.equals(other.unixName))
			return false;
		if (workspaces == null) {
			if (other.workspaces != null)
				return false;
		} else if (!workspaces.equals(other.workspaces))
			return false;
		return true;
	}
}
