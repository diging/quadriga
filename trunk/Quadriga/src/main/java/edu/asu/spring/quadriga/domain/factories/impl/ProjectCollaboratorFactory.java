package edu.asu.spring.quadriga.domain.factories.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.ICollaborator ;
import edu.asu.spring.quadriga.domain.factories.IProjectCollaboratorFactory;
import edu.asu.spring.quadriga.domain.implementation.Collaborator;

/**
 * @description factory method to produce collaborator objects
 * 
 * @author rohit pendbhaje
 *
 */
//@Service
public class ProjectCollaboratorFactory implements IProjectCollaboratorFactory{

	@Override
	public ICollaborator createProjectCollaboratorObject() {
		
		return new Collaborator();
	}

}
