package edu.asu.spring.quadriga.domain;

import java.util.List;

/**
 * The interface that provides access to the class representation of the Collection got from Dspace repostiory.
 * 
 * @author Ram Kumar Kumaresan
 */
public interface ICollection {

	public abstract String getTitle();
	
	public abstract void setTitle(String title);
	
	public abstract String getDescription();
	
	public abstract void setDescription(String description);
	
	public abstract List<IItem> getItems();
	
	public abstract void setItems(List<IItem> items);
	
	public abstract void addItem(IItem item);
}
