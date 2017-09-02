package edu.asu.spring.quadriga.mapper.dictionary;

import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDictionaryDeepMapper {

    IDictionary getDictionary(DictionaryDTO dictionaryDTO) throws QuadrigaStorageException;

}