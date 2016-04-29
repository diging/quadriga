package edu.asu.spring.quadriga.domain.factory.impl.dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.dictionary.IDictionaryCollaborator;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factory.dictionary.IDictionaryCollaboratorFactory;
import edu.asu.spring.quadriga.domain.factory.dictionary.IDictionaryFactory;
import edu.asu.spring.quadriga.domain.impl.dictionary.DictionaryCollaborator;

@Service
public class DictionaryCollaboratorFactory implements IDictionaryCollaboratorFactory {
	
	@Autowired
	IDictionaryFactory dictionaryFactory;

	@Autowired
	ICollaboratorFactory collaboratorFactory;
	
	/**
	 * {@inheritDoc}
	*/
	@Override
	public IDictionaryCollaborator createDictionaryCollaboratorObject() {
		IDictionaryCollaborator dictionaryCollaborator = new DictionaryCollaborator();
		dictionaryCollaborator.setDictionary(dictionaryFactory.createDictionaryObject());
		dictionaryCollaborator.setCollaborator(collaboratorFactory.createCollaborator());
		return dictionaryCollaborator;
		
	}

	@Override
	public IDictionaryCollaborator cloneDictionaryCollaboratorObject(IDictionaryCollaborator dictionaryCollaborator) {
        IDictionaryCollaborator clone = createDictionaryCollaboratorObject();
        clone.setCollaborator(dictionaryCollaborator.getCollaborator());
        clone.setDictionary(dictionaryCollaborator.getDictionary());
        clone.setCreatedBy(dictionaryCollaborator.getCreatedBy());
        clone.setCreatedDate(dictionaryCollaborator.getCreatedDate());
        clone.setUpdatedBy(dictionaryCollaborator.getUpdatedBy());
        clone.setUpdatedDate(dictionaryCollaborator.getUpdatedDate());
		return clone;
	}
	
}
