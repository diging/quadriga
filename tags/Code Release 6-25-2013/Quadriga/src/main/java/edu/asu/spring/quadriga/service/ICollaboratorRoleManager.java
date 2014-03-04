package edu.asu.spring.quadriga.service;

import java.util.List;

import edu.asu.spring.quadriga.domain.ICollaboratorRole;

public interface ICollaboratorRoleManager {
	
	public abstract void setCollaboratorRole(List<ICollaboratorRole> collaboratorroles);
	
	public abstract List<ICollaboratorRole> getCollaboratorRoles();
	
	public abstract void fillCollaboratorRole(ICollaboratorRole collaboratorRole);
	
	public abstract ICollaboratorRole getCollaboratorRoleById(String collaboratorRoleId);

	

	
}