package edu.asu.spring.quadriga.domain;

import java.util.List;

import edu.asu.spring.quadriga.domain.implementation.Collection;

/**
 * The interface that provides access to the class representation of the community got from Dspace repostiory.
 * 
 * @author Ram Kumar Kumaresan
 * 
 */
public interface ICommunity {

	public abstract String getTitle();
	
	public abstract void setTitle(String title);
	
	public abstract String getDescription();
	
	public abstract void setDescription(String description);
	
//	public abstract List<Collection> getCollections();
//	
//	public abstract void setCollections(List<Collection> collections);
//	
//	public abstract void addCollection(Collection collection);
}
