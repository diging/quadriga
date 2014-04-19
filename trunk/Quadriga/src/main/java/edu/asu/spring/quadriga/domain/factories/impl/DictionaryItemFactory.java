package edu.asu.spring.quadriga.domain.factories.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factories.IDictionaryItemFactory;
import edu.asu.spring.quadriga.domain.impl.dictionary.Item;

/**
 * Factory class for creating {@link Item}.
 * 
 * @author Lohith Dwaraka
 *
 */

@Service
public class DictionaryItemFactory implements IDictionaryItemFactory {
	@Override
	public Item createDictionaryItemObject() {
		
		return new Item();
	}

}
