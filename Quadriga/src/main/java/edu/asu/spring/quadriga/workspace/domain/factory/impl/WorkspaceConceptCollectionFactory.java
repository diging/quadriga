package edu.asu.spring.quadriga.workspace.domain.factory.impl;

import edu.asu.spring.quadriga.domain.impl.workspace.WorkspaceConceptCollection;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceConceptCollection;
import edu.asu.spring.quadriga.workspace.domain.factory.IWorkspaceConceptCollectionFactory;

public class WorkspaceConceptCollectionFactory implements
		IWorkspaceConceptCollectionFactory {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IWorkspaceConceptCollection createWorkspaceConceptCollectionObject() {
		return new WorkspaceConceptCollection();
	}

}
