package edu.asu.spring.quadriga.dao.dictionary;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionRetrieveDictionaryManager extends IBaseDAO<DictionaryDTO> {

    public DictionaryDTO getDictionaryDTO(String dictionaryId, String userName) throws QuadrigaStorageException ;
}
