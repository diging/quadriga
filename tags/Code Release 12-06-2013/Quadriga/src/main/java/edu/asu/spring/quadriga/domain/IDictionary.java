package edu.asu.spring.quadriga.domain;

import java.util.List;
/**
 * Interface to implement Dictionary.
 * 
 * @author        : Kiran Kumar Batna
 *
 */
public interface IDictionary 
{

	public abstract void setCollaborators(List<ICollaborator> collaborators);

	public abstract List<ICollaborator> getCollaborators();

	public abstract void setOwner(IUser owner);

	public abstract IUser getOwner();

	public abstract void setId(String id);

	public abstract String getId();

	public abstract void setDescription(String description);

	public abstract String getDescription();

	public abstract void setName(String name);

	public abstract String getName();

}
