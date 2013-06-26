package edu.asu.spring.quadriga.service;

import java.util.List;

import edu.asu.spring.quadriga.domain.ICollaboratorRole;

public interface ICollaboratorRoleManager {
	
	//public abstract void setCollaboratorRole(List<ICollaboratorRole> collaboratorroles);
	
	//public abstract List<ICollaboratorRole> getCollaboratorRoles();
	
	//public abstract void fillCollaboratorRole(ICollaboratorRole collaboratorRole);
	
	public abstract ICollaboratorRole getCollaboratorRoleById(String collaboratorRoleId);

	public abstract void setProjectCollaboratorRole(List<ICollaboratorRole> collaboratorRoles);
	
	public abstract List<ICollaboratorRole> getProjectCollaboratorRoles();
	
	public abstract void setCollectionCollaboratorRole(List<ICollaboratorRole> collaboratorRoles);
	
	public abstract List<ICollaboratorRole> getCollectionCollaboratorRoles();
	
	public abstract void fillProjectCollaboratorRole(ICollaboratorRole collaboratorRole);

	public abstract void fillCollectionCollaboratorRole(ICollaboratorRole collaboratorRole);

	
}
