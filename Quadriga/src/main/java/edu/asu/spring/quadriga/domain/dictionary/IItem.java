package edu.asu.spring.quadriga.domain.dictionary;

import java.util.Date;
import java.util.List;

/**
 * Interface to implement DictionaryItems class.
 * 
 * @author        : Lohith Dwaraka
 *
 */
public interface IItem {
	
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
	
	public abstract List<IDictionaryItems> getItemDictionaries();
	
	public abstract void setItemDictionaries(List<IDictionaryItems> itemDictionaries);
	
	public abstract String getCreatedBy();
	
	public abstract void setCreatedBy(String createdBy);
	
	public abstract Date getCreatedDate();
	
	public abstract void setCreatedDate(Date createdDate);
	
	public abstract String getUpdatedBy();
	
	public abstract void setUpdatedBy(String updatedBy);
	
	public abstract Date getUpdatedDate();
	
	public abstract void setUpdatedDate(Date updatedDate);
	
}
