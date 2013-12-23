package edu.asu.spring.quadriga.db.workspace;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionArchiveWSManager {

	public abstract void deactivateWorkspace(String workspaceIdList, boolean deactivate,
			String wsUser) throws QuadrigaStorageException;

	public abstract void archiveWorkspace(String workspaceIdList, boolean archive,
			String wsUser) throws QuadrigaStorageException;
}
