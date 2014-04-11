package edu.asu.spring.quadriga.domain.factories;

import edu.asu.spring.quadriga.domain.workbench.IProject;

/**
 * Factory interface to create project object
 * @author kiran batna
 * 
 */
public interface IProjectFactory {
	
	public abstract IProject  createProjectObject();

}
