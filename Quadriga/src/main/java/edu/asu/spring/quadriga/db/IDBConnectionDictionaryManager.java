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
	
	public abstract int setupTestEnvironment(String sQuery);
	/**
	 * Queries the database to get a list of dictionary objects list
	 * 
	 * @return List containing IDictionary objects of dictionary of the users
	 */
	public abstract List<IDictionary> getDictionaryOfUser(String userName);
	/**
	 * Adds the dictionary in the database
	 * 
	 * @return Status message
	 */
	public abstract String addDictionary(IDictionary dictionary);
	/**
	 * Queries the database to get a list of dictionary items objects list
	 * 
	 * @return List containing IDictionaryItems objects of dictionary items for a dictionary
	 */
	public abstract List<IDictionaryItems> getDictionaryItemsDetails(String dictionaryid);
	/**
	 * Queries the database to get dictionary name
	 * 
	 * @return dictionary name
	 */
	public abstract String getDictionaryName(String dictionaryId);
	/**
	 * Adds the dictionary items into dictionary in the database 
	 * 
	 * @return Status message
	 */
	public String addDictionaryItems(String dictinaryId, String item, String id,
			String pos, String owner);
	/**
	 * Deletes the dictionary items from  dictionary in the database 
	 * 
	 * @return Status message
	 */
	public abstract String  deleteDictionaryItems(String dictinaryId, String item);
	/**
	 * Updates the dictionary items in the dictionary in the database 
	 * 
	 * @return Status message
	 */
	public abstract String updateDictionaryItems(String dictinaryId,String termid,String term ,String pos);
}
