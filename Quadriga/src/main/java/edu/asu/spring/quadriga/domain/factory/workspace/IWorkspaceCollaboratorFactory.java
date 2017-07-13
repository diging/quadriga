package edu.asu.spring.quadriga.domain.factory.workspace;

import edu.asu.spring.quadriga.domain.workspace.IWorkspaceCollaborator;
import edu.asu.spring.quadriga.domain.workspace.impl.WorkspaceCollaborator;

public interface IWorkspaceCollaboratorFactory {

	/**
	 * This class should returns a {@link IWorkspaceCollaborator} object of domain object type {@link WorkspaceCollaborator}
	 * @return							Returns {@link IWorkspaceCollaborator} object of domain type {@link WorkspaceCollaborator}
	 */
	public abstract IWorkspaceCollaborator createWorkspaceCollaboratorObject();
	
	public abstract IWorkspaceCollaborator cloneWorkspaceCollaboratorObject(IWorkspaceCollaborator workspaceCollaborator);

}