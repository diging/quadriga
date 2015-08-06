package edu.asu.spring.quadriga.service.impl.dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.dictionary.IDBConnectionDictionaryCollaboratorManager;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryCollaboratorManager;

@Service
public class DictionaryCollaboratorManager implements
		IDictionaryCollaboratorManager 
{
	
	@Autowired
	private IDBConnectionDictionaryCollaboratorManager dbConnect;
	
	/**
	 * This method updates the collaborator roles for dictionary collaborators
	 * @param dictionaryId - dictionary id
	 * @param collabUser - collaborator user name
	 * @param collaboratorRole - collaborator role
	 * @param username - logged in user name
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public void updateCollaboratorRoles(String dictionaryId,String collabUser,String collaboratorRole,String username) throws QuadrigaStorageException
	{
		dbConnect.updateCollaboratorRoles(dictionaryId, collabUser, collaboratorRole, username);
	}
	
	

}
