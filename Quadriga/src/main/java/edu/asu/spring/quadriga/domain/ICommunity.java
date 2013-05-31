package edu.asu.spring.quadriga.domain;

import java.util.List;

public interface ICommunity {

	public abstract String getTitle();
	
	public abstract void setTitle(String title);
	
	public abstract String getDescription();
	
	public abstract void setDescription(String description);
	
	public abstract List<ICollection> getCollections();
	
	public abstract void setCollections(List<ICollection> collections);
	
	public abstract void addCollection(ICollection collection);
}
