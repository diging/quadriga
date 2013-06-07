package edu.asu.spring.quadriga.db;

import java.util.List;

import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.IDictionaryItems;

/**
 * Interface for the DBConnectionDictionaryManager Class.
 * 
 * @author Lohith Dwaraka
 *
 */

public interface IDBConnectionDictionaryManager {
	
	public abstract List<IDictionary> getDictionaryOfUser(String userName);
	public abstract String addDictionary(IDictionary dictionary);
	public abstract List<IDictionaryItems> getDictionaryItemsDetails(String dictionaryid);
	public abstract String getDictionaryName(String dictionaryId);
	public abstract String addDictionaryItems(String dictinaryId,String item,String owner);
}
