package edu.asu.spring.quadriga.domain.factory.workspace;

import edu.asu.spring.quadriga.domain.workspace.IWorkspace;
import edu.asu.spring.quadriga.domain.workspace.impl.Workspace;

public interface IWorkspaceFactory {

	/**
	 * Method for cloning a {@link Workspace} object. 
	 * Note that this will produce a shallow clone, meaning that the Collaborators
	 * will simply be put into a new list for the clone, but the Collaborators objects themselves will be the same.
	 * @param workspace the workspace object to be cloned.
	 * @return a clone of the given user object that contains the exact same information as the original object.
	 */
	public abstract IWorkspace cloneWorkspaceObject(IWorkspace workspace);

	/**
	 * Factory method for creating {@link Workspace} objects.
	 * @return
	 */
	public abstract IWorkspace createWorkspaceObject();

}
