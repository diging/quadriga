package edu.asu.spring.quadriga.domain.factory.impl.dictionary;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factory.dictionary.IDictionaryItemFactory;
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
