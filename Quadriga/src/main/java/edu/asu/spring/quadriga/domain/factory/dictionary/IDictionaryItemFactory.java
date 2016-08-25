package edu.asu.spring.quadriga.domain.factory.dictionary;

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
	
	public abstract Item cloneDictionaryItemObject(Item dictionaryItem);

}
