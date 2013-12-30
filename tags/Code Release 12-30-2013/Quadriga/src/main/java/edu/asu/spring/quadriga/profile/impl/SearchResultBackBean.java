package edu.asu.spring.quadriga.profile.impl;

import org.springframework.stereotype.Service;

@Service
public class SearchResultBackBean {
	
	private String word;
	private String id;
	private String description;
	private boolean isChecked = false;
	
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
