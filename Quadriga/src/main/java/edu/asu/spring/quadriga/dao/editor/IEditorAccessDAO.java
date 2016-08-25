package edu.asu.spring.quadriga.dao.editor;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IEditorAccessDAO 
{

	/**
	 * This method checks if the logged in user has an editor role
	 * @param : userName - logged in user
	 * @throws : QuadrigaStorageException
	 * @return : has editor role - true
	 *           no editor role - false
	 */
	public abstract boolean chkIsEditor(String userName)
			throws QuadrigaStorageException;

	/**
	 * This method checks if the logged in user has an editor role for
	 * the given network
	 * @param : networkId - network id
	 * @param : userName - logged in user
	 * @throws : QuadrigaStorageException
	 * @return : has network editor role - true
	 *           no network editor role - false
	 */
	public abstract boolean chkIsNetworkEditor(String networkId, String userName)
			throws QuadrigaStorageException;

}
