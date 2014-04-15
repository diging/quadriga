package edu.asu.spring.quadriga.domain.factory.workspace.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factory.workspace.IWorkspaceConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.impl.workspace.WorkspaceConceptCollection;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceConceptCollection;

@Service
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
