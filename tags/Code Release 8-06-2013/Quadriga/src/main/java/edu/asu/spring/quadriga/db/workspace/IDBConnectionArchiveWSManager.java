package edu.asu.spring.quadriga.db.workspace;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionArchiveWSManager {

	public abstract String deactivateWorkspace(String workspaceIdList, int deactivate,
			String wsUser) throws QuadrigaStorageException;

	public abstract String archiveWorkspace(String workspaceIdList, int archive,
			String wsUser) throws QuadrigaStorageException;
}
