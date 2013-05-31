package edu.asu.spring.quadriga.domain;

import java.util.List;

public interface ICollection {

	public abstract String getTitle();
	
	public abstract void setTitle(String title);
	
	public abstract String getDescription();
	
	public abstract void setDescription(String description);
	
	public abstract List<IItem> getItems();
	
	public abstract void setItems(List<IItem> items);
	
	public abstract void addItem(IItem item);
}
