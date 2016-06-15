package edu.asu.spring.quadriga.service.dictionary.mapper;

import java.util.List;

import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDictionaryShallowMapper {

    public abstract List<IDictionary> getDictionaryList(List<DictionaryDTO> dictionaryDTOList)
            throws QuadrigaStorageException;

    public abstract IDictionary getDictionaryDetails(DictionaryDTO dictionaryDTO) throws QuadrigaStorageException;

    public abstract List<IDictionary> getDictionaryListOfCollaborator(List<DictionaryDTO> dictionaryDTOList)
            throws QuadrigaStorageException;

    public abstract List<IDictionary> getNonAssociatedProjectDictionaries(List<DictionaryDTO> dictionaryDTOList)
            throws QuadrigaStorageException;

}
