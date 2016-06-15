package edu.asu.spring.quadriga.domain.factory.workspace;

import edu.asu.spring.quadriga.domain.impl.workspace.WorkspaceConceptCollection;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceConceptCollection;

public interface IWorkspaceConceptCollectionFactory {
	/**
	 * Factory method for creating {@link WorkspaceConceptCollection} object.
	 * @return
	 */
	public abstract IWorkspaceConceptCollection  createWorkspaceConceptCollectionObject();
	
	
	public abstract IWorkspaceConceptCollection cloneWorkspaceConceptCollectionObject(IWorkspaceConceptCollection workspaceConceptCollection);

}
