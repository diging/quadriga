package edu.asu.spring.quadriga.domain.implementation;


import java.util.List;

import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.ICommunity;

/**
 * Javadoc missing
 * 
 */
public class Community implements ICommunity{

	private String title;
	private String description;
	private List<ICollection> collections;
	
	
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
	public List<ICollection> getCollections() {
		return this.collections;
	}

	@Override
	public void setCollections(List<ICollection> collections) {
		this.collections= collections;		
	}

	@Override
	public void addCollection(ICollection collection) {
		this.collections.add(collection);
	}

}
