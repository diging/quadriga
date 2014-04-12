package edu.asu.spring.quadriga.db.dictionary;

import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionRetrieveDictionaryManager
{

	/**
	 * This method retrieves the dictionary details for the specified dictionaryid.
	 * @param dictionaryId
	 * @return IDictinary object
	 * @throws QuadrigaStorageException
	 */
	public abstract IDictionary getDictionaryDetails(String dictionaryId)
			throws QuadrigaStorageException;

}
