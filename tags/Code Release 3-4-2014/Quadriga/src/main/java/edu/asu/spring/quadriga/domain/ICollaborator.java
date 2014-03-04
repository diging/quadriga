package edu.asu.spring.quadriga.domain;

import java.util.List;

/**
 * @description  : interface to implement Collaborator class.
 * 
 * @author       : Kiran Kumar Batna
 *
 */
public interface ICollaborator 
{

	public abstract void setCollaboratorRoles(List<ICollaboratorRole> collaboratorRoles);

	public abstract List<ICollaboratorRole> getCollaboratorRoles();

	public abstract void setDescription(String description);

	public abstract String getDescription();

	public abstract void setUserObj(IUser userObj);

	public abstract IUser getUserObj();
	
	public abstract void setCollaboratorName(String collaborator);

}
