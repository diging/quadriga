package edu.asu.spring.quadriga.service.workspace;

import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IModifyWSManager {

	public abstract String deleteWorkspaceRequest(String workspaceIdList)
			throws QuadrigaStorageException;

	public abstract String addWorkSpaceRequest(IWorkSpace workspace, String projectId)
			throws QuadrigaStorageException;

	public abstract String updateWorkspaceRequest(IWorkSpace workspace)
			throws QuadrigaStorageException;

	public abstract void transferWSOwnerRequest(String projectId, String oldOwner,
			String newOwner, String collabRole) throws QuadrigaStorageException;

	public abstract String assignEditorRoleToOwner(String workspaceId, String userName)
			throws QuadrigaStorageException;

	public abstract String deleteEditorRoleToOwner(String workspaceId, String userName)
			throws QuadrigaStorageException;

}
