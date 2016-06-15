package edu.asu.spring.quadriga.domain.factory.workspace;

import edu.asu.spring.quadriga.domain.impl.workspace.WorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;

public interface IWorkspaceFactory {

	/**
	 * Method for cloning a {@link WorkSpace} object. 
	 * Note that this will produce a shallow clone, meaning that the Collaborators
	 * will simply be put into a new list for the clone, but the Collaborators objects themselves will be the same.
	 * @param workspace the workspace object to be cloned.
	 * @return a clone of the given user object that contains the exact same information as the original object.
	 */
	public abstract IWorkSpace cloneWorkspaceObject(IWorkSpace workspace);

	/**
	 * Factory method for creating {@link WorkSpace} objects.
	 * @return
	 */
	public abstract IWorkSpace createWorkspaceObject();

}
