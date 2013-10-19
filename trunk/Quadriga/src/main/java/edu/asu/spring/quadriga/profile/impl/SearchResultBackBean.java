package edu.asu.spring.quadriga.profile.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.profile.ISearchResult;

@Service
public class SearchResultBackBean {
	
	private String id;
	private String description;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	

}
