package edu.asu.spring.quadriga.domain.factory.dictionary.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.dictionary.impl.Item;
import edu.asu.spring.quadriga.domain.factory.dictionary.IDictionaryItemFactory;

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

	@Override
	public Item cloneDictionaryItemObject(Item dictionaryItem) 
	{
		Item clone = createDictionaryItemObject();
		clone.setDictionaryItemId(dictionaryItem.getDictionaryItemId());
		clone.setPos(dictionaryItem.getPos());
		clone.setTerm(dictionaryItem.getTerm());
		clone.setVocabulary(dictionaryItem.getVocabulary());
		clone.setItemDictionaries(dictionaryItem.getItemDictionaries());
		clone.setDescription(dictionaryItem.getDescription());
		clone.setCreatedBy(dictionaryItem.getCreatedBy());
		clone.setCreatedDate(dictionaryItem.getCreatedDate());
		clone.setUpdatedBy(dictionaryItem.getUpdatedBy());
		clone.setUpdatedDate(dictionaryItem.getUpdatedDate());
		return clone;
	}

}
