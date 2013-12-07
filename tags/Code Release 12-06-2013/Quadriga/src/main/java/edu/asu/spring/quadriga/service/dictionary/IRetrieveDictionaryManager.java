package edu.asu.spring.quadriga.service.dictionary;

import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IRetrieveDictionaryManager {

	public abstract IDictionary getDictionaryDetails(String dictionaryId)
			throws QuadrigaStorageException;

}
