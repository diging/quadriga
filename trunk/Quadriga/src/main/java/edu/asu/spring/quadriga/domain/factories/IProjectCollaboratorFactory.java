package edu.asu.spring.quadriga.domain.factories;

/**
 * 
 * @Description : factory interface to produce collaborator objects
 * 
 * @author rohit sukleshwar pendbhaje
 * 
 */

import edu.asu.spring.quadriga.domain.ICollaborator;

public interface IProjectCollaboratorFactory {
	
	public abstract ICollaborator createProjectCollaboratorObject();

}
