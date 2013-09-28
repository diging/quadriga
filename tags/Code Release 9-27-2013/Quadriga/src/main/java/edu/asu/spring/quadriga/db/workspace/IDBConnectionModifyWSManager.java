package edu.asu.spring.quadriga.db.workspace;

import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionModifyWSManager {

	public abstract String deleteWorkspaceRequest(String workspaceIdList)
			throws QuadrigaStorageException;

	public abstract String addWorkSpaceRequest(IWorkSpace workSpace, String projectId)
			throws QuadrigaStorageException;

	public abstract String updateWorkspaceRequest(IWorkSpace workspace)
			throws QuadrigaStorageException;

	public abstract void transferWSOwnerRequest(String projectId, String oldOwner,
			String newOwner, String collabRole) throws QuadrigaStorageException;

	public abstract String assignWorkspaceOwnerEditor(String workspaceId, String owner)
			throws QuadrigaStorageException;

	public abstract String deleteWorkspaceOwnerEditor(String workspaceId, String owner)
			throws QuadrigaStorageException;
}
