package edu.asu.spring.quadriga.profile.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.profile.ISearchResult;
import edu.asu.spring.quadriga.profile.IService;

@Service
public class ServiceB implements IService {

	String name;
	String serviceId;
	
	
	@Override
	public void setServiceId(String id) {

	}

	@Override
	public String getServiceId() {

		return "edu.asu.serviceB";
	}

	@Override
	public void setName(String name) {
		
	}

	@Override
	public String getName() {

		return "ServiceB";
	}

	@Override
	public List<ISearchResult> search(String word) {

		return null;
	}

	
}
