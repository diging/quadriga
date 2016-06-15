package edu.asu.spring.quadriga.domain.impl;

import java.util.List;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;

/**
 * @description : Collaborator class describing the properties 
 *                of a Collaborator object
 * 
 * @author      : Kiran Kumar Batna
 *
 */
public class Collaborator implements ICollaborator 
{
	private IUser userObj;
	private String description;
	private List<IQuadrigaRole> collaboratorRoles;
	
	public IUser getUserObj() {
		return userObj;
	}
	public void setUserObj(IUser userObj) {
		this.userObj = userObj;
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
	public List<IQuadrigaRole> getCollaboratorRoles() {
		return collaboratorRoles;
	}
	@Override
	public void setCollaboratorRoles(List<IQuadrigaRole> collaboratorRoles) {
		this.collaboratorRoles = collaboratorRoles;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((collaboratorRoles == null) ? 0 : collaboratorRoles
						.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((userObj == null) ? 0 : userObj.hashCode());
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
		Collaborator other = (Collaborator) obj;
		if (collaboratorRoles == null) {
			if (other.collaboratorRoles != null)
				return false;
		} else if (!collaboratorRoles.equals(other.collaboratorRoles))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (userObj == null) {
			if (other.userObj != null)
				return false;
		} else if (!userObj.equals(other.userObj))
			return false;
		return true;
	}
}
