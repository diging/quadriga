package edu.asu.spring.quadriga.domain.factory.dictionary.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.dictionary.IDictionaryCollaborator;
import edu.asu.spring.quadriga.domain.factory.dictionary.IDictionaryCollaboratorFactory;
import edu.asu.spring.quadriga.domain.impl.dictionary.DictionaryCollaborator;

@Service
public class DictionaryCollaboratorFactory implements IDictionaryCollaboratorFactory {

	/**
	 * {@inheritDoc}
	*/
	@Override
	public IDictionaryCollaborator createDictionaryCollaboratorObject() {
		
		return new DictionaryCollaborator();
	}
	
}
