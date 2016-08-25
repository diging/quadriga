package edu.asu.spring.quadriga.web.profile.impl;


/**
 * this class is used as a backing bean for the search results retrieved 
 * through various services like viaf, conceptpower in the user profile page
 * 
 * @author rohit pendbhaje
 *
 */

public class SearchResultBackBean {
	
	private String word;
	private String id;
	private String description;
	private String serviceId;
	private boolean isChecked = false;
	
	public String getServiceId() {
		return serviceId;
	}
	
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	
	public boolean getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
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
