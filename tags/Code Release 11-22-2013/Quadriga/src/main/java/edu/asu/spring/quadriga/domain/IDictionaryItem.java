package edu.asu.spring.quadriga.domain;

/**
 * Interface to implement DictionaryItems class.
 * 
 * @author        : Lohith Dwaraka
 *
 */
public interface IDictionaryItem {
	
	public abstract String getItems();
	
	public abstract void setItems(String items);

	public abstract String getDictionaryId();
	
	public abstract void setDictionaryId(String dictionaryId);
	
	public abstract String getId();
	
	public abstract void setId(String id);
	
	public abstract String getPos();
	
	public abstract void setPos(String pos);
	
	public abstract String getVocabulary();
	
	public abstract void setVocabulary(String vocabulary);
	
	public abstract String getDescription();
	
	public abstract void setDescription(String description);
	
}
