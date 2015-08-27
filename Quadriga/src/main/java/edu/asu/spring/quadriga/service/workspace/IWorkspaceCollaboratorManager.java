package edu.asu.spring.quadriga.service.workspace;

import java.util.ArrayList;
import java.util.List;

import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceCollaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorManager;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;

public interface IWorkspaceCollaboratorManager extends ICollaboratorManager {

	public abstract List<IWorkspaceCollaborator> getWorkspaceCollaborators(String workspaceId)
			throws QuadrigaStorageException;

}
