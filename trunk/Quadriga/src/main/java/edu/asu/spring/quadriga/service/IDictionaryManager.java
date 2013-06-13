package edu.asu.spring.quadriga.service;


import java.util.List;

import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.IDictionaryItems;
import edu.asu.spring.quadriga.domain.implementation.Dictionary;
import edu.asu.spring.quadriga.domain.implementation.WordpowerReply;
/**
 * Interface that has methods to be implemented on the DictionaryManager class
 * 
 * @author Lohith Dwaraka
 *
 */
public interface IDictionaryManager {
	public abstract List<IDictionary> getDictionariesList(String sUserId);
		
	public abstract String addNewDictionariesItems(String dictionaryId,String item,String id,String pos,String owner);
	
	public abstract String addNewDictionary(Dictionary newDictionary);
	
	public abstract List<IDictionaryItems> getDictionariesItems(String dictionaryid);
	
	public abstract String getDictionaryName(String dictionaryid);
	
	public abstract WordpowerReply.DictionaryEntry searchWordPower(String item,String pos);
	
	public abstract String deleteDictionariesItems(String dictionaryId,String item);
	
	public abstract String updateDictionariesItems(String dictionaryId,String itemid,String term,String pos);
	public abstract WordpowerReply.DictionaryEntry  getUpdateFromWordPower(String dictionaryId,String itemid);
}
