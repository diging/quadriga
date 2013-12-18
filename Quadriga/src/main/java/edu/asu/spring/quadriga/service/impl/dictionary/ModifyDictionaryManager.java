package edu.asu.spring.quadriga.service.impl.dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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
	
	@Override
	public void updateDictionaryDetailsRequest(IDictionary dictionary,String userName) throws QuadrigaStorageException
	{
		dbConnect.updateDictionaryRequest(dictionary, userName);
	}
	
	@Override
	public void transferDictionaryOwner(String dictionaryId,String oldOwner,String newOwner,String collabRole) throws QuadrigaStorageException
	{
		dbConnect.transferDictionaryOwner(dictionaryId, oldOwner, newOwner, collabRole);
	}

}
