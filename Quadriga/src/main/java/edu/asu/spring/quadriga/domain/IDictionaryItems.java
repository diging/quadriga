package edu.asu.spring.quadriga.domain;

/**
 * @description   : interface to implement DictionaryItems class.
 * 
 * @author        : Lohith Dwaraka
 *
 */
public interface IDictionaryItems {
	
	public abstract String getItems();
	
	public abstract void setItems(String items);

	public abstract String getDictionaryId();
	
	public abstract void setDictionaryId(String dictionaryId);
	
}
