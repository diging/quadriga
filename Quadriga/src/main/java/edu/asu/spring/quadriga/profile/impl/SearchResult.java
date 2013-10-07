package edu.asu.spring.quadriga.profile.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.profile.ISearchResult;

@Service
public class SearchResult implements ISearchResult {

	
	String description;
	String word;
	
	
	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setWord(String word) {
		this.word = word;
	}

	@Override
	public String getWord() {
		return word;
	}

}
