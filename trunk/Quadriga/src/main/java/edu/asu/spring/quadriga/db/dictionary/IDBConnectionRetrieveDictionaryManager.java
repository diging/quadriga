package edu.asu.spring.quadriga.db.dictionary;

import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionRetrieveDictionaryManager
{

	public abstract IDictionary getDictionaryDetails(String dictionaryId)
			throws QuadrigaStorageException;

}
