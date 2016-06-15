package edu.asu.spring.quadriga.service.editor;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IEditorAccessManager {

	public abstract boolean checkIsEditor(String userName)
			throws QuadrigaStorageException;

	public abstract boolean checkIsNetworkEditor(String networkId, String userName)
			throws QuadrigaStorageException;

}
