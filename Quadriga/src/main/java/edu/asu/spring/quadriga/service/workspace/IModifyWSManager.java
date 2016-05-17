package edu.asu.spring.quadriga.service.workspace;

import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IModifyWSManager {

	public boolean deleteWorkspace(String workspaceIdList)
			throws QuadrigaStorageException;

	public abstract void addWorkspaceToProject(IWorkSpace workspace, String projectId, String username)
			throws QuadrigaStorageException;

	public void updateWorkspace(IWorkSpace workspace)
			throws QuadrigaStorageException;

	public abstract void assignEditorRole(String workspaceId, String userName)
			throws QuadrigaStorageException;

	public boolean deleteEditorRole(String workspaceId, String userName);

}
