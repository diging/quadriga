package edu.asu.spring.quadriga.domain;

import java.util.List;

/**
 * @description   : interface to implement WorkSpace class.
 * 
 * @author        : Kiran Kumar Batna
 *
 */
public interface IWorkSpace 
{

	public abstract void setCollaborators(List<ICollaborator> collaborators);

	public abstract List<ICollaborator> getCollaborators();

	public abstract void setOwner(IUser owner);

	public abstract IUser getOwner();

	public abstract void setDescription(String description);

	public abstract String getDescription();

	public abstract void setName(String name);

	public abstract String getName();

	public abstract void setId(String id);

	public abstract String getId();

}
