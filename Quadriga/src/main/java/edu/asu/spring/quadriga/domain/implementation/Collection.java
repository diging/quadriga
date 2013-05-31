package edu.asu.spring.quadriga.domain.implementation;

import java.util.List;

import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.IItem;

public class Collection implements ICollection{

	private String title;
	private String description;
	private List<IItem> items;
	
	
	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public List<IItem> getItems() {
		return this.items;
	}

	@Override
	public void setItems(List<IItem> items) {
		this.items = items;		
	}

	@Override
	public void addItem(IItem item) {
		this.items.add(item);
	}

}
