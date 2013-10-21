package edu.asu.spring.quadriga.profile.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.profile.IServiceFormFactory;

@Service
public class ServiceFormFactory implements IServiceFormFactory {

	@Override
	public ServiceForm getServiceFormObject() {

		return new ServiceForm();
	}

	
	
}
