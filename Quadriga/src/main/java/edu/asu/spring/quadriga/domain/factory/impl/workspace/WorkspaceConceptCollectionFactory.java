package edu.asu.spring.quadriga.domain.factory.impl.workspace;

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

	@Override
	public IWorkspaceConceptCollection cloneWorkspaceConceptCollectionObject(IWorkspaceConceptCollection workspaceConceptCollection) 
	{
		IWorkspaceConceptCollection clone = new WorkspaceConceptCollection();
		clone.setConceptCollection(workspaceConceptCollection.getConceptCollection());
		clone.setWorkspace(workspaceConceptCollection.getWorkspace());
		clone.setCreatedBy(workspaceConceptCollection.getCreatedBy());
		clone.setCreatedDate(workspaceConceptCollection.getCreatedDate());
		clone.setUpdatedBy(workspaceConceptCollection.getUpdatedBy());
		clone.setUpdatedDate(workspaceConceptCollection.getUpdatedDate());
		return clone;
	}

}
