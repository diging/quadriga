package edu.asu.spring.quadriga.service.dictionary;

import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IModifyDictionaryManager {

	public abstract void updateDictionaryDetailsRequest(IDictionary dictionary,
			String userName) throws QuadrigaStorageException;

	public abstract void transferDictionaryOwner(String dictionaryId, String oldOwner,
			String newOwner, String collabRole) throws QuadrigaStorageException;

}
