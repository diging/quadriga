package edu.asu.spring.quadriga.service;

import java.util.List;

import edu.asu.spring.quadriga.domain.ICollaboratorRole;

public interface ICollaboratorRoleMapper {
	
	public abstract void setCollaboratorRole(List<ICollaboratorRole> collaboratorroles);
	
	public abstract List<ICollaboratorRole> getCollaboratorRole();
	
	public abstract ICollaboratorRole getCollaboratorRoles(String CollaboratorRoleDBid);

	
}
