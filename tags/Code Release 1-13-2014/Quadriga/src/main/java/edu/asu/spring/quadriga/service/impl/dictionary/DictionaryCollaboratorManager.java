package edu.asu.spring.quadriga.service.impl.dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.db.dictionary.IDBConnectionDictionaryCollaboratorManager;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryCollaboratorManager;

@Service
public class DictionaryCollaboratorManager implements
		IDictionaryCollaboratorManager 
{
	
	@Autowired
	@Qualifier("dictionaryCollaboratorManagerDAO")
	private IDBConnectionDictionaryCollaboratorManager dbConnect;
	
	@Override
	@Transactional
	public void updateCollaboratorRoles(String dictionaryId,String collabUser,String collaboratorRole,String username) throws QuadrigaStorageException
	{
		dbConnect.updateCollaboratorRoles(dictionaryId, collabUser, collaboratorRole, username);
	}
	
	

}
