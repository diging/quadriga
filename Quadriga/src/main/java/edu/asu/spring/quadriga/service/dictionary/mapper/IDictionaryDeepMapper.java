package edu.asu.spring.quadriga.service.dictionary.mapper;

import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDictionaryDeepMapper {

	public abstract IDictionary getDictionaryDetails(String dictionaryId)
			throws QuadrigaStorageException;

	IDictionary getDictionaryDetails(String dictionaryId, String userName)
			throws QuadrigaStorageException;

}