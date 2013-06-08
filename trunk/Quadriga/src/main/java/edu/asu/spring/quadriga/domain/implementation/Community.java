package edu.asu.spring.quadriga.domain.implementation;


import java.util.List;

import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.ICommunity;

/**
 * The class representation of the community got from Dspace repostiory.
 * 
 * @author Ram Kumar Kumaresan
 * 
 */
public class Community implements ICommunity{

	private String title;
	private String description;

	
	
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
}
