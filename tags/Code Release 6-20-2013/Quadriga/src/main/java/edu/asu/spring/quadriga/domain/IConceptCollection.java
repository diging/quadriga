package edu.asu.spring.quadriga.domain;

import java.util.List;

/**
 * @description   : interface to implement ConceptCollection.
 * 
 * @author        : Kiran Kumar Batna
 *
 */
public interface IConceptCollection 
{

	public abstract void setCollaborators(List<ICollaborator> collaborators);

	public abstract List<ICollaborator> getCollaborators();

	public abstract void setOwner(IUser owner);

	public abstract IUser getOwner();

	

	public abstract void setDescription(String description);

	public abstract String getDescription();

	public abstract void setName(String name);

	public abstract String getName();
	
	
	
	public abstract void addItem(IConcept concept);

	public abstract List<IConcept> getItems();

	public abstract void setId(int id);

	public abstract int getId();
	

}
