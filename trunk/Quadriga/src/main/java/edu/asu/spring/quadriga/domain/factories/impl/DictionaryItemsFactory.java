package edu.asu.spring.quadriga.domain.factories.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factories.IDictionaryItemsFactory;
import edu.asu.spring.quadriga.domain.impl.dictionary.Item;

/**
 * Factory class for creating {@link Item}.
 * 
 * @author Lohith Dwaraka
 *
 */

@Service
public class DictionaryItemsFactory implements IDictionaryItemsFactory {
	@Override
	public Item createDictionaryItemsObject() {
		
		return new Item();
	}

}
