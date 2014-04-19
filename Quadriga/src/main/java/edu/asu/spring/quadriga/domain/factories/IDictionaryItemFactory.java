package edu.asu.spring.quadriga.domain.factories;

import edu.asu.spring.quadriga.domain.impl.dictionary.Item;

/**
 * Factory interface for DictionaryItems factories.
 * 
 */
public interface IDictionaryItemFactory {
	/**
	 * 
	 * @return DictionaryItems
	 */
	public abstract Item createDictionaryItemObject();

}
