package edu.asu.spring.quadriga.service;

import java.util.List;

import edu.asu.spring.quadriga.domain.ICollaboratorRole;

public interface ICollaboratorRoleManager {
	
	public abstract ICollaboratorRole getProjectCollaboratorRoleById(String collaboratorRoleId);
	
	public abstract ICollaboratorRole getCCCollaboratorRoleById(String collaboratorRoleId);
	
	public abstract ICollaboratorRole getDictCollaboratorRoleById(String collaboratorRoleId);
	
	public abstract String getProjectCollaboratorRoleByDBId(String collaboratorRoleDBId);
	
	public abstract String getDictCollaboratorRoleByDBId(String collaboratorRoleDBId);

	public abstract String getCollectionCollabRoleByDBId(String collaboratorRoleDBId);

	public abstract void setProjectCollaboratorRole(List<ICollaboratorRole> collaboratorRoles);
	
	public abstract List<ICollaboratorRole> getProjectCollaboratorRoles();
	
	public abstract void setDictCollaboratorRole(List<ICollaboratorRole> collaboratorRoles);
	
	public abstract List<ICollaboratorRole> getDictCollaboratorRoles();
	
	public abstract void setCollectionCollaboratorRole(List<ICollaboratorRole> collaboratorRoles);
	
	public abstract List<ICollaboratorRole> getCollectionCollaboratorRoles();
	
	public abstract void fillProjectCollaboratorRole(ICollaboratorRole collaboratorRole);

	public abstract void fillCollectionCollaboratorRole(ICollaboratorRole collaboratorRole);
	
	public abstract void fillDictCollaboratorRole(ICollaboratorRole collaboratorRole);

	public abstract void setWsCollabRoles(List<ICollaboratorRole> wsCollabRoles);

	public abstract List<ICollaboratorRole> getWsCollabRoles();

	public abstract ICollaboratorRole getWSCollaboratorRoleByDBId(String collaboratorRoleDBId);

	public abstract String getDictCollaboratorRoleIdByDBId(String collaboratorRoleDBId);

	public abstract ICollaboratorRole getWSCollaboratorRoleById(String collaboratorRoleId);
}
