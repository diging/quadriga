package edu.asu.spring.quadriga.db.dictionary;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionDictionaryCollaboratorManager {

	public abstract void updateCollaboratorRoles(String dictionaryId, String collabUser,
			String collaboratorRole, String username) throws QuadrigaStorageException;

}
