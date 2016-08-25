package edu.asu.spring.quadriga.service.workspace;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IArchiveWSManager {

	public abstract void activateWorkspace(String workspaceIdList, String wsUser)
			throws QuadrigaStorageException;

	public abstract void deactivateWorkspace(String workspaceIdList, String wsUser)
			throws QuadrigaStorageException;

	public abstract void unArchiveWorkspace(String workspaceIdList, String wsUser)
			throws QuadrigaStorageException;

	public abstract void archiveWorkspace(String workspaceIdList, String wsUser)
			throws QuadrigaStorageException;

}
