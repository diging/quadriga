package edu.asu.spring.quadriga.domain.factories.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.factories.IDictionaryFactory;
import edu.asu.spring.quadriga.domain.implementation.Dictionary;


@Service
public class DictionaryFactory implements IDictionaryFactory {
	@Override
	public IDictionary createDictionaryObject() {
		
		return new Dictionary();
	}

}
