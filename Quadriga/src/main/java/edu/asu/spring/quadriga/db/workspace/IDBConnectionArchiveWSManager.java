package edu.asu.spring.quadriga.db.workspace;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionArchiveWSManager 
{

	/**
	 * This deactivate/activate the list of workspaces for a given user.
	 * @param workspaceIdList
	 * @param deactivate
	 * @param wsUser
	 * @throws QuadrigaStorageException
	 */
	public abstract void deactivateWorkspace(String workspaceIdList, boolean deactivate,
			String wsUser) throws QuadrigaStorageException;

	/**
	 * Archive/Un archive the list of workspaces for the given user
	 * @param workspaceIdList
	 * @param archive
	 * @param wsUser
	 * @throws QuadrigaStorageException
	 */
	public abstract void archiveWorkspace(String workspaceIdList, boolean archive,
			String wsUser) throws QuadrigaStorageException;
	
	public abstract int setupTestEnvironment(String sQuery) throws QuadrigaStorageException;
}
