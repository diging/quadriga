package edu.asu.spring.quadriga.domain.implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.enums.ENetworkAccessibility;
import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;

/**
 * @description : Project class describing the properties of a Project object
 * 
 * @author : Kiran Kumar Batna
 * 
 */
@Service
public class Project implements IProject {
	private String name;
	private String description;
	private String unixName;
	private String internalid;
	private List<ICollaborator> collaborators;
	private EProjectAccessibility projectAccess;
	private ENetworkAccessibility networksDefaultAccess;
	private IUser owner;

	/**
	 * retrieves the name of the project
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * assigns the name of the project to the supplied variable.
	 */
	@Override
	public void setName(String name) {
		this.name = name;
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
	public void setUnixName(String unixname) {
		this.unixName = unixname;
	}

	/**
	 * retrieves the internal id of the project
	 */
	@Override
	public String getInternalid() {
		return internalid;
	}

	/**
	 * assigns the internal id of the project
	 */
	@Override
	public void setInternalid(String internalid) {
		this.internalid = internalid;
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
	 * retrieves the networks access of the project
	 */
	@Override
	public ENetworkAccessibility getNetworksDefaultAccess() {
		return networksDefaultAccess;
	}

	/**
	 * assigns the network access of the project
	 */
	@Override
	public void setNetworksDefaultAccess(
			ENetworkAccessibility networksDefaultAccess) {
		this.networksDefaultAccess = networksDefaultAccess;
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
				+ ((internalid == null) ? 0 : internalid.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime
				* result
				+ ((networksDefaultAccess == null) ? 0 : networksDefaultAccess
						.hashCode());
		result = prime * result
				+ ((projectAccess == null) ? 0 : projectAccess.hashCode());
		result = prime * result
				+ ((unixName == null) ? 0 : unixName.hashCode());
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
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (internalid == null) {
			if (other.internalid != null)
				return false;
		} else if (!internalid.equals(other.internalid))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (networksDefaultAccess != other.networksDefaultAccess)
			return false;
		if (projectAccess != other.projectAccess)
			return false;
		if (unixName == null) {
			if (other.unixName != null)
				return false;
		} else if (!unixName.equals(other.unixName))
			return false;
		return true;
	}

}