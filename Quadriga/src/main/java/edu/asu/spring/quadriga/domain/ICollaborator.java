package edu.asu.spring.quadriga.domain;

import java.util.List;

public interface ICollaborator 
{

	public abstract void setCollaboratorRoles(List<ICollaboratorRoles> collaboratorRoles);

	public abstract List<ICollaboratorRoles> getCollaboratorRoles();

	public abstract void setDescription(String description);

	public abstract String getDescription();

	public abstract void setUserObj(IUser userObj);

	public abstract IUser getUserObj();

}
