package edu.asu.spring.quadriga.service.impl.dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.dictionary.IDBConnectionRetrieveDictionaryManager;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.dictionary.IRetrieveDictionaryManager;
import edu.asu.spring.quadriga.service.dictionary.mapper.IDictionaryDeepMapper;

@Service
public class RetrieveDictionaryManager implements IRetrieveDictionaryManager
{
	@Autowired
	private IDBConnectionRetrieveDictionaryManager dbConnect;
	
	@Autowired
	private IDictionaryDeepMapper dictionaryDeepMapper;
	
	/**
	 * This method retrieves the dictionary details.
	 * @param dictionaryId - dictionary id
	 * @throws QuadrigaStorageException
	 * @return IDictionary - dictionary object contating the details
	 */
	@Override
	@Transactional
	public IDictionary getDictionaryDetails(String dictionaryId) throws QuadrigaStorageException
	{
		IDictionary dictionary =  null;
		//dictionaryDTO = dbConnect.getDictionaryDetails(dictionaryId);
		dictionary = dictionaryDeepMapper.getDictionaryDetails(dictionaryId);
		return dictionary;
	}


	
}
