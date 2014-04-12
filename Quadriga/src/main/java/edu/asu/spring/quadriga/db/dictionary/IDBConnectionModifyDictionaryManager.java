package edu.asu.spring.quadriga.db.dictionary;

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

	/**
	 * Transfer dictionary owner
	 * @param dictionaryId, old owner, new owner and collaborator role
	 * @return void
	 * @throws QuadrigaStorageException
	 */
	public abstract void transferDictionaryOwner(String dictionaryId, String oldOwner,
			String newOwner, String collabRole) throws QuadrigaStorageException;

}
