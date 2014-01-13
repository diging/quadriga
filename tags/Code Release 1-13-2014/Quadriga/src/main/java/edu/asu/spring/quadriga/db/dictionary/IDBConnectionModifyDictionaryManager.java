package edu.asu.spring.quadriga.db.dictionary;

import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionModifyDictionaryManager {

	public abstract void updateDictionaryRequest(IDictionary dictionary, String userName)
			throws QuadrigaStorageException;

	public abstract void transferDictionaryOwner(String dictionaryId, String oldOwner,
			String newOwner, String collabRole) throws QuadrigaStorageException;

}
