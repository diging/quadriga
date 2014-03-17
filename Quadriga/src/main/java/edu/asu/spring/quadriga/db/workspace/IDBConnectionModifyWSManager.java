package edu.asu.spring.quadriga.db.workspace;

import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionModifyWSManager
{

	/**
	 * Delete the given workspace.
	 * @param workspaceIdList
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract String deleteWorkspaceRequest(String workspaceIdList)
			throws QuadrigaStorageException;

	/**
	 * Add a workspace and associate it to the given project.
	 * @param workSpace
	 * @param projectId
	 * @throws QuadrigaStorageException
	 */
	public abstract void addWorkSpaceRequest(IWorkSpace workSpace, String projectId)
			throws QuadrigaStorageException;

	/**
	 * This methods modifies the workspace details.
	 * @param workspace
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract String updateWorkspaceRequest(IWorkSpace workspace)
			throws QuadrigaStorageException;

	/**
	 * This method transfers ownership of workspace to given new user
	 * @param projectId
	 * @param oldOwner
	 * @param newOwner
	 * @param collabRole
	 * @throws QuadrigaStorageException
	 */
	public abstract void transferWSOwnerRequest(String projectId, String oldOwner,
			String newOwner, String collabRole) throws QuadrigaStorageException;

	/**
	 * This methods assigns editor role to given user for the associated workspace.
	 * @param workspaceId
	 * @param owner
	 * @throws QuadrigaStorageException
	 */
	public abstract void assignWorkspaceOwnerEditor(String workspaceId, String owner)
			throws QuadrigaStorageException;

	/**
	 * This method deletes editor role to given user for the associated workspace.
	 * @param workspaceId
	 * @param owner
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract String deleteWorkspaceOwnerEditor(String workspaceId, String owner)
			throws QuadrigaStorageException;
	/**
	 * Delete the given workspace.
	 * @param workspaceIdList
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract String deleteProjectWorkspace(String workspaceIdList)
			throws QuadrigaStorageException;
	/**
	 * Delete the project workspace for given workspace.
	 * @param workspaceIdList
	 * @return
	 * @throws QuadrigaStorageException
	 */
	
	public abstract String deleteWorkspaceDictionary(String workspaceIdList)
			throws QuadrigaStorageException;
	/**
	 * Delete the workspace dictionary for given workspace.
	 * @param workspaceIdList
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract String deleteWorkspaceConceptcollection(String workspaceIdList)
			throws QuadrigaStorageException;
	/**
	 * Delete the workspace COncept Collection for given workspace.
	 * @param workspaceIdList
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract String deleteWorkspaceDspace(String workspaceIdList)
			throws QuadrigaStorageException;
	/**
	 * Delete the workspace editor for given workspace.
	 * @param workspaceIdList
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract String deleteWorkspaceEditor(String workspaceIdList)
			throws QuadrigaStorageException;
	
	/**
	 * Delete the workspace netowrk for given workspace.
	 * @param workspaceIdList
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract String deleteWorkspaceNetwork(String workspaceIdList)
			throws QuadrigaStorageException;
	
	public abstract int setupTestEnvironment(String sQuery) throws QuadrigaStorageException;
}
