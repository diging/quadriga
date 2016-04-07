package edu.asu.spring.quadriga.profile.impl;

import org.springframework.stereotype.Service;

/**
 * this is a backing bean for the service bean on the profile page
 * 
 * @author rohit pendbhaje
 */
@Service
public class ServiceBackBean {

	private String term;
	private String id;
	
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}
