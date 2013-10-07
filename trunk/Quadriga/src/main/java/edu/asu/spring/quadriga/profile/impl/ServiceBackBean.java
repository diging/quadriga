package edu.asu.spring.quadriga.profile.impl;

import org.springframework.stereotype.Service;

@Service
public class ServiceBackBean {
	
	String name;
	String word;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}

}
