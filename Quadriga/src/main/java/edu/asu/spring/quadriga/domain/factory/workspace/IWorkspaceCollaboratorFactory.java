package edu.asu.spring.quadriga.domain.factory.workspace;

import edu.asu.spring.quadriga.domain.impl.workspace.WorkspaceCollaborator;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceCollaborator;

public interface IWorkspaceCollaboratorFactory {

	/**
	 * This class should returns a {@link IWorkspaceCollaborator} object of domain object type {@link WorkspaceCollaborator}
	 * @return							Returns {@link IWorkspaceCollaborator} object of domain type {@link WorkspaceCollaborator}
	 */
	public abstract IWorkspaceCollaborator createWorkspaceCollaboratorObject();
	
	public abstract IWorkspaceCollaborator cloneWorkspaceCollaboratorObject(IWorkspaceCollaborator workspaceCollaborator);

}