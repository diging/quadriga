package edu.asu.spring.quadriga.service.impl.dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.dictionary.IDBConnectionModifyDictionaryManager;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.dictionary.IModifyDictionaryManager;

@Service
public class ModifyDictionaryManager implements IModifyDictionaryManager 
{
	@Autowired
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
	
}
