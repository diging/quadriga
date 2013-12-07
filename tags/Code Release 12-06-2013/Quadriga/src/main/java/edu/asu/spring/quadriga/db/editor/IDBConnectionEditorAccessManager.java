package edu.asu.spring.quadriga.db.editor;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionEditorAccessManager 
{

	public abstract boolean chkIsEditor(String userName)
			throws QuadrigaStorageException;

	public abstract boolean chkIsNetworkEditor(String networkId, String userName)
			throws QuadrigaStorageException;

}
