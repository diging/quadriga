package edu.asu.spring.quadriga.domain.factories;

import edu.asu.spring.quadriga.domain.implementation.DictionaryItem;

/**
 * Factory interface for DictionaryItems factories.
 * 
 */
public interface IDictionaryItemsFactory {
	/**
	 * 
	 * @return DictionaryItems
	 */
	public abstract DictionaryItem createDictionaryItemsObject();

}
