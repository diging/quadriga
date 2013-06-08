package edu.asu.spring.quadriga.service;


import java.util.List;

import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.IDictionaryItems;
import edu.asu.spring.quadriga.domain.implementation.Dictionary;
import edu.asu.spring.quadriga.domain.implementation.DictionaryEntry;
import edu.asu.spring.quadriga.domain.implementation.DictionaryEntryBackupXJC;
/**
 * Interface that has methods to be implemented on the DictionaryManager class
 * 
 * @author Lohith Dwaraka
 *
 */
public interface IDictionaryManager {
	public abstract List<IDictionary> getDictionariesList(String sUserId);
	
	public abstract String updateDictionariesItems(Dictionary existingDictionaryList);
	
	public abstract int deleteDictionariesItems(String dictionaryId);
	
	public abstract String addNewDictionariesItems(String dictionaryId,String item,String owner);
	
	public abstract String addNewDictionary(Dictionary newDictionary);
	
	public abstract List<IDictionaryItems> getDictionariesItems(String dictionaryid);
	
	public abstract String getDictionaryName(String dictionaryid);
	
	public abstract DictionaryEntry callRestUri(String url,String item,String pos);
}
