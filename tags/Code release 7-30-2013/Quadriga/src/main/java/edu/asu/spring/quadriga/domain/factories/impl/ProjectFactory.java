package edu.asu.spring.quadriga.domain.factories.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.factories.IProjectFactory;
import edu.asu.spring.quadriga.domain.implementation.Project;

/**
 * Javadoc missing
 * 
 */
@Service
public class ProjectFactory implements IProjectFactory {

	@Override
	public IProject createProjectObject() {
		
		return new Project();
	}
	
}
