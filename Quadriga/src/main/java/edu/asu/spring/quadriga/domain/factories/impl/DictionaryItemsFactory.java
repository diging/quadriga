package edu.asu.spring.quadriga.domain.factories.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factories.IDictionaryItemsFactory;
import edu.asu.spring.quadriga.domain.implementation.DictionaryItems;

/**
 * Factory class for creating {@link DictionaryItems}.
 * 
 * @author Lohith Dwaraka
 *
 */

@Service
public class DictionaryItemsFactory implements IDictionaryItemsFactory {
	@Override
	public DictionaryItems createDictionaryItemsObject() {
		
		return new DictionaryItems();
	}

}
