package edu.asu.spring.quadriga.profile.impl;

import org.springframework.stereotype.Service;

@Service
public class SearchResultBackBean {
	
	private String profileName;
	private String id;
	private String description;
	
	
	public String getProfileName() {
		return profileName;
	}
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	
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
