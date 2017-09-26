package edu.asu.spring.quadriga.domain.factory.workspace.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factory.workspace.IWorkspaceCollaboratorFactory;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceCollaborator;
import edu.asu.spring.quadriga.domain.workspace.impl.WorkspaceCollaborator;

/**
 * Factory class to create {@link IWorkspaceCollaborator} object of domain class type {@link WorkspaceCollaborator}
 * @author Lohith Dwaraka
 *
 */
@Service
public class WorkspaceCollaboratorFactory implements IWorkspaceCollaboratorFactory {

	/**
	 * {@inheritDoc}
	*/
	@Override
	public IWorkspaceCollaborator createWorkspaceCollaboratorObject() {
		return new WorkspaceCollaborator();
	}

	@Override
	public IWorkspaceCollaborator cloneWorkspaceCollaboratorObject(IWorkspaceCollaborator workspaceCollaborator) {
		IWorkspaceCollaborator clone = new WorkspaceCollaborator();
		clone.setWorkspace(workspaceCollaborator.getWorkspace());
		clone.setCollaborator(workspaceCollaborator.getCollaborator());
		clone.setCreatedBy(workspaceCollaborator.getCreatedBy());
		clone.setCreatedDate(workspaceCollaborator.getCreatedDate());
		clone.setUpdatedBy(workspaceCollaborator.getUpdatedBy());
		clone.setUpdatedDate(workspaceCollaborator.getUpdatedDate());
		return clone;
	}
}
