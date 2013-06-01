package edu.asu.spring.quadriga.domain.implementation;

import java.util.List;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
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
	private List<ICollaboratorRole> collaboratorRoles;
	
		
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
	public List<ICollaboratorRole> getCollaboratorRoles() {
		return collaboratorRoles;
	}
	@Override
	public void setCollaboratorRoles(List<ICollaboratorRole> collaboratorRoles) {
		this.collaboratorRoles = collaboratorRoles;
	}
	

}
