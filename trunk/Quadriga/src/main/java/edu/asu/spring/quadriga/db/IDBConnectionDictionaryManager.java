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
	public String addDictionaryItems(String dictinaryId, String item, String id,
			String pos, String owner);
	public abstract String  deleteDictionaryItems(String dictinaryId, String item);
	public abstract String updateDictionaryItems(String dictinaryId,String item,String id);
}
