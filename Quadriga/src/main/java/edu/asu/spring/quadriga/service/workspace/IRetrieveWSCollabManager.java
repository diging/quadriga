package edu.asu.spring.quadriga.service.workspace;

import java.util.List;

import edu.asu.spring.quadriga.domain.workspace.IWorkspaceCollaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;


public interface IRetrieveWSCollabManager {

	public abstract List<IWorkspaceCollaborator> getWorkspaceCollaborators(String workspaceId)
			throws QuadrigaStorageException;
}
