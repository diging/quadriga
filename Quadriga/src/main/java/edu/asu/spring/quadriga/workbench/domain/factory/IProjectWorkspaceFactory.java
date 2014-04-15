package edu.asu.spring.quadriga.workbench.domain.factory;

import edu.asu.spring.quadriga.domain.workbench.IProjectWorkspace;

/**
 * Factory interface to create ProjectWorkspace object
 * @author Sowjanya Ambati
 * 
 */
public interface IProjectWorkspaceFactory {
	public abstract IProjectWorkspace  createProjectWorkspaceObject();

}