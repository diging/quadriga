package edu.asu.spring.quadriga.domain.implementation;

import java.util.List;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRoles;
import edu.asu.spring.quadriga.domain.IUser;

public class Collaborator implements ICollaborator 
{
	private IUser userObj;
	private String description;
	private List<ICollaboratorRoles> collaboratorRoles;

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
	public List<ICollaboratorRoles> getCollaboratorRoles() {
		return collaboratorRoles;
	}
	@Override
	public void setCollaboratorRoles(List<ICollaboratorRoles> collaboratorRoles) {
		this.collaboratorRoles = collaboratorRoles;
	}
	

}
