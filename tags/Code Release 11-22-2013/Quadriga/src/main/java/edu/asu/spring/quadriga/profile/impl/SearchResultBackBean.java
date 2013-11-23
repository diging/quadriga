package edu.asu.spring.quadriga.profile.impl;

import org.springframework.stereotype.Service;

@Service
public class SearchResultBackBean {
	
	private String word;
	private String id;
	private String description;
	
	
	
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
