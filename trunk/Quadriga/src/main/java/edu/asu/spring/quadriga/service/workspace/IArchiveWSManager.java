package edu.asu.spring.quadriga.service.workspace;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IArchiveWSManager {

	public abstract String activateWorkspace(String workspaceIdList, String wsUser)
			throws QuadrigaStorageException;

	public abstract String deactivateWorkspace(String workspaceIdList, String wsUser)
			throws QuadrigaStorageException;

	public abstract String unArchiveWorkspace(String workspaceIdList, String wsUser)
			throws QuadrigaStorageException;

	public abstract String archiveWorkspace(String workspaceIdList, String wsUser)
			throws QuadrigaStorageException;

}
