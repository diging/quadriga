package edu.asu.spring.quadriga.service;


import java.util.List;

import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.IDictionaryItems;
import edu.asu.spring.quadriga.domain.implementation.DictionaryItems;
import edu.asu.spring.quadriga.domain.implementation.WordpowerReply;
import edu.asu.spring.quadriga.domain.implementation.WordpowerReply.DictionaryEntry;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
/**
 * Interface that has methods to be implemented on the DictionaryManager class
 * 
 * @author Lohith Dwaraka
 *
 */
public interface IDictionaryManager {
	
	/**
	 * there is a lot of javadoc missing here!
	 * @param sUserId
	 * @return
	 * @throws QuadrigaStorageException 
	 */
	public abstract List<IDictionary> getDictionariesList(String sUserId) throws QuadrigaStorageException;
		
	public abstract String addNewDictionariesItems(String dictionaryId,String item,String id,String pos,String owner) throws QuadrigaStorageException;
	
	public abstract String addNewDictionary(IDictionary newDictionary) throws QuadrigaStorageException;
	
	public abstract List<IDictionaryItems> getDictionariesItems(String dictionaryid) throws QuadrigaStorageException;
	
	public abstract String getDictionaryName(String dictionaryid) throws QuadrigaStorageException;
	
	public abstract List<DictionaryEntry> searchWordPower(String item,String pos);
	
	public abstract String deleteDictionariesItems(String dictionaryId,String itemid) throws QuadrigaStorageException;
	
	public abstract String updateDictionariesItems(String dictionaryId,String itemid,String term,String pos) throws QuadrigaStorageException;
	public abstract List<DictionaryEntry>  getUpdateFromWordPower(String dictionaryId,String itemid);
	
	public abstract DictionaryItems getDictionaryItemIndex(String termId, DictionaryItems dictionaryItems);
}
