package edu.asu.spring.quadriga.domain.factory.dictionary;

import edu.asu.spring.quadriga.domain.dictionary.IDictionaryItems;

public interface IDictionaryItemsFactory {

	public abstract IDictionaryItems createDictionaryItemsObject();
	
	public abstract IDictionaryItems cloneDictionaryItemsObject(IDictionaryItems dictionaryItems);

}