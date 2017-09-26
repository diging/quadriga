package edu.asu.spring.quadriga.domain.factory.dictionary.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.dictionary.impl.Dictionary;
import edu.asu.spring.quadriga.domain.factory.dictionary.IDictionaryFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * Factory class for creating {@link Dictionary}.
 * 
 * @author Lohith Dwaraka
 *
 */
@Service
public class DictionaryFactory implements IDictionaryFactory {
    
    @Override
    public IDictionary createDictionaryObject() {
        return new Dictionary();
    }

    @Override
    public IDictionary cloneDictionaryObject(IDictionary dictionary) throws QuadrigaStorageException {
        IDictionary clone = createDictionaryObject();
        clone.setDictionaryId(dictionary.getDictionaryId());
        clone.setDictionaryName(dictionary.getDictionaryName());
        clone.setDescription(dictionary.getDescription());
        clone.setOwner(dictionary.getOwner());
        clone.setDictionaryCollaborators(dictionary.getDictionaryCollaborators());
        clone.setDictionaryItems(dictionary.getDictionaryItems());
        clone.setProjects(dictionary.getProjects());
        clone.setWorkspaces(dictionary.getWorkspaces());

        clone.setCreatedBy(dictionary.getCreatedBy());
        clone.setCreatedDate(dictionary.getCreatedDate());
        clone.setUpdatedBy(dictionary.getUpdatedBy());
        clone.setUpdatedDate(dictionary.getUpdatedDate());
        return clone;
    }

}
