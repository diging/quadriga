package edu.asu.spring.quadriga.profile.factory.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.profile.IServiceFormFactory;
import edu.asu.spring.quadriga.profile.impl.ServiceForm;

@Service
public class ServiceFormFactory implements IServiceFormFactory {

	@Override
	public ServiceForm getServiceFormObject() {

		return new ServiceForm();
	}

	
	
}
