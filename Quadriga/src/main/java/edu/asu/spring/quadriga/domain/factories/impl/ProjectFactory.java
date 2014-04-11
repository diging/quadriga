package edu.asu.spring.quadriga.domain.factories.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factories.IProjectFactory;
import edu.asu.spring.quadriga.domain.implementation.Project;
import edu.asu.spring.quadriga.domain.workbench.IProject;

/**
 * Factory class to create Project object
 * @author kiran batna
 * 
 */
@Service
public class ProjectFactory implements IProjectFactory {

	@Override
	public IProject createProjectObject() {
		
		return new Project();
	}
	
}
