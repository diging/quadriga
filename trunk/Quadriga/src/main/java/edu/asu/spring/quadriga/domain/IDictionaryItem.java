package edu.asu.spring.quadriga.domain;

import java.util.List;

/**
 * Interface to implement DictionaryItems class.
 * 
 * @author        : Lohith Dwaraka
 *
 */
public interface IDictionaryItem {
	
	public abstract String getTerm();
	
	public abstract void setTerm(String term);
	
	public abstract String getDictionaryItemId();
	
	public abstract void setDictionaryItemId(String dictionaryItemId);
	
	public abstract String getPos();
	
	public abstract void setPos(String pos);
	
	public abstract String getVocabulary();
	
	public abstract void setVocabulary(String vocabulary);
	
	public abstract String getDescription();
	
	public abstract void setDescription(String description);
	
	public abstract List<IDictionary> getDictionaries();
	
	public abstract void setDictionaries(List<IDictionary> dictionaries);
	
}
