package edu.asu.spring.quadriga.profile.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.profile.ISearchResult;
import edu.asu.spring.quadriga.profile.IService;

@Service
public class ServiceB implements IService {

	String name;
	String id;
	
	@Override
	public void setId(String id) {
		
	}

	@Override
	public String getId() {

		return "edu.asu.ServiceB";
	}

	@Override
	public void setName(String name) {
		
	}

	@Override
	public String getName() {

		return "serviceB";
	}

	@Override
	public ISearchResult search(String word) {

		return null;
	}

}
