package edu.asu.spring.quadriga.service.impl.dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.db.dictionary.IDBConnectionRetrieveDictionaryManager;
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.dictionary.IRetrieveDictionaryManager;

@Service
public class RetrieveDictionaryManager implements IRetrieveDictionaryManager
{
	@Autowired
	@Qualifier("retrieveDictionaryManagerDAO")
	private IDBConnectionRetrieveDictionaryManager dbConnect;
	
	
	@Override
	@Transactional
	public IDictionary getDictionaryDetails(String dictionaryId) throws QuadrigaStorageException
	{
		IDictionary dictionary;
		dictionary = dbConnect.getDictionaryDetails(dictionaryId);
		return dictionary;
	}

}
