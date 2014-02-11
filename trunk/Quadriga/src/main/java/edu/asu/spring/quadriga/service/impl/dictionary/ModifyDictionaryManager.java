package edu.asu.spring.quadriga.service.impl.dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.db.dictionary.IDBConnectionModifyDictionaryManager;
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.dictionary.IModifyDictionaryManager;

@Service
public class ModifyDictionaryManager implements IModifyDictionaryManager 
{
	@Autowired
	@Qualifier("modifyDictionaryManagerDAO")
	private IDBConnectionModifyDictionaryManager dbConnect;
	
	/**
	 * This method updates the dictionary details
	 * @param dictionary - IDictionary object containing dictionary details.
	 * @param userName - logged in user
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public void updateDictionaryDetailsRequest(IDictionary dictionary,String userName) throws QuadrigaStorageException
	{
		dbConnect.updateDictionaryRequest(dictionary, userName);
	}
	
	/**
	 * This method transfers the current dictionary owner to its collaborator and assigns the
	 * selected collaborator as the new owner for the dictionary.
	 * @param dictionaryId - dictionary id.
	 * @param oldOwner - current owner of the dictionary.
	 * @param newOwner - one of its collaborator who is assigned the ownership.
	 * @param collabRole - collaborator role for which the current owner is associated.
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public void transferDictionaryOwner(String dictionaryId,String oldOwner,String newOwner,String collabRole) throws QuadrigaStorageException
	{
		dbConnect.transferDictionaryOwner(dictionaryId, oldOwner, newOwner, collabRole);
	}

}
