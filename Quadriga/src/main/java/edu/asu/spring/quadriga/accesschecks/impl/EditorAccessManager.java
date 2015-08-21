package edu.asu.spring.quadriga.accesschecks.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.editor.IEditorAccessDAO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.editor.IEditorAccessManager;

@Service
public class EditorAccessManager implements IEditorAccessManager 
{
	@Autowired
	private IEditorAccessDAO dbConnect;
	
	/**
	 * This method checks if the given user has an editor role
	 * @param userName - logged in user
	 * @return boolean - true - if he has an editor role.
	 *                   false - if he doesn't have an editor role.
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public boolean checkIsEditor(String userName) throws QuadrigaStorageException
	{
		return dbConnect.chkIsEditor(userName);
	}
	
	/**
	 * This method checks if the user has an editor role for given network.
	 * @param userName - logged in user.
	 * @param networkId - network id.
	 * @throws QuadrigaStorageException
	 * @return boolean - true if the user has editor role for the network.
	 *                   false if the user has no editor role for the network.
	 */
	@Override
	@Transactional
	public boolean checkIsNetworkEditor(String networkId,String userName) throws QuadrigaStorageException
	{
		return dbConnect.chkIsNetworkEditor(networkId, userName);
	}

}
