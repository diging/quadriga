package edu.asu.spring.quadriga.profile.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.profile.ISearchResult;

@Service
public class SearchResult implements ISearchResult {

	
	private String description;
	private String id;

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}

	
}
