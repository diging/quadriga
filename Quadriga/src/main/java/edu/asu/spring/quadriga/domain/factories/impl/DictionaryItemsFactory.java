package edu.asu.spring.quadriga.domain.factories.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factories.IDictionaryItemsFactory;
import edu.asu.spring.quadriga.domain.implementation.DictionaryItem;

/**
 * Factory class for creating {@link DictionaryItem}.
 * 
 * @author Lohith Dwaraka
 *
 */

@Service
public class DictionaryItemsFactory implements IDictionaryItemsFactory {
	@Override
	public DictionaryItem createDictionaryItemsObject() {
		
		return new DictionaryItem();
	}

}
