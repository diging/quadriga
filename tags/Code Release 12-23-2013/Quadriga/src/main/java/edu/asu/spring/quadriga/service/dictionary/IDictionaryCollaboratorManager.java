package edu.asu.spring.quadriga.service.dictionary;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDictionaryCollaboratorManager {

	public abstract void updateCollaboratorRoles(String dictionaryId, String collabUser,
			String collaboratorRole, String username) throws QuadrigaStorageException;

}
