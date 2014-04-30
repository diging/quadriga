package edu.asu.spring.quadriga.service.dictionary.mapper;

import java.util.List;

import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.dictionary.IDictionaryItems;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDictionaryDeepMapper {

	public abstract IDictionary getDictionaryDetails(String dictionaryId)
			throws QuadrigaStorageException;
	

	IDictionary getDictionaryDetails(String dictionaryId, String userName)
			throws QuadrigaStorageException;

}