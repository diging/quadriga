package edu.asu.spring.quadriga.db.dictionary;

import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionRetrieveDictionaryManager
{

	/**
	 * This method retrieves the dictionary details for the specified dictionaryid.
	 * @param dictionaryId
	 * @return IDictinary object
	 * @throws QuadrigaStorageException
	 */
	public abstract DictionaryDTO getDictionaryDetails(String dictionaryId)
			throws QuadrigaStorageException;

	public abstract DictionaryDTO getDictionaryDTO(String dictionaryId)
			throws QuadrigaStorageException;

	DictionaryDTO getDictionaryDTO(String dictionaryId, String userName)
			throws QuadrigaStorageException;
	


	

}