package edu.asu.spring.quadriga.domain.factory.impl.workspace;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factory.workspace.IWorkspaceCollaboratorFactory;
import edu.asu.spring.quadriga.domain.impl.workspace.WorkspaceCollaborator;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceCollaborator;

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
}
