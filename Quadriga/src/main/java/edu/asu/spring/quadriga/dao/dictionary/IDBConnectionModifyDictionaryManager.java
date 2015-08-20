package edu.asu.spring.quadriga.dao.dictionary;

import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionModifyDictionaryManager {

	/**
	 * Update the dictionary details
	 * @param dictionary object and userName
	 * @return void
	 * @throws QuadrigaStorageException
	 */
	public abstract void updateDictionaryRequest(IDictionary dictionary, String userName)
			throws QuadrigaStorageException;

}
