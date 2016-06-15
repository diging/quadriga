package edu.asu.spring.quadriga.service.dictionary.mapper;

import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDictionaryDeepMapper {

    IDictionary getDictionaryDetails(DictionaryDTO dictionaryDTO) throws QuadrigaStorageException;

}