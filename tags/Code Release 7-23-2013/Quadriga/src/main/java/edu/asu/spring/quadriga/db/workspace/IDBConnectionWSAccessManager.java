package edu.asu.spring.quadriga.db.workspace;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionWSAccessManager 
{

	public abstract boolean chkWorkspaceOwner(String userName, String workspaceId)
			throws QuadrigaStorageException;
	

}
