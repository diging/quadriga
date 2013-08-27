package edu.asu.spring.quadriga.db.workbench;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionProjectAccessManager {

	public abstract boolean chkProjectOwner(String userName,String projectId)
			throws QuadrigaStorageException;

	public abstract boolean chkDuplicateProjUnixName(String unixName,String projectId)
			throws QuadrigaStorageException;

	public abstract boolean chkProjectOwnerEditorRole(String userName, String projectId)
			throws QuadrigaStorageException;

}
