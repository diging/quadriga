package edu.asu.spring.quadriga.domain.factories.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IProjectOwnerFactory;
import edu.asu.spring.quadriga.domain.implementation.User;

@Service
public class ProjectOwnerFactory implements IProjectOwnerFactory {
	
	
	@Override
	public IUser createProjectOwnerObject() {
		
		return new User(); 
	}

}
