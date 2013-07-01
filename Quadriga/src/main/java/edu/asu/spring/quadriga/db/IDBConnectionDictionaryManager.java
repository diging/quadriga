package edu.asu.spring.quadriga.db;

import java.util.List;

import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.IDictionaryItems;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * Interface for the DBConnectionDictionaryManager Class.
 * 
 * @author Lohith Dwaraka
 * 
 */

public interface IDBConnectionDictionaryManager {

	/**
	 * 
	 * @param sQuery
	 * @return
	 */
	public abstract int setupTestEnvironment(String sQuery);

	/**
	 * Queries the database to get a list of dictionary objects list
	 * 
	 * @return List containing IDictionary objects of dictionary of the users
	 * @throws QuadrigaStorageException
	 */
	public abstract List<IDictionary> getDictionaryOfUser(String userName)
			throws QuadrigaStorageException;

	/**
	 * Adds the dictionary in the database
	 * 
	 * @return Status message
	 * @throws QuadrigaStorageException
	 */
	public abstract String addDictionary(IDictionary dictionary)
			throws QuadrigaStorageException;

	/**
	 * Queries the database to get a list of dictionary items objects list
	 * 
	 * @return List containing IDictionaryItems objects of dictionary items for
	 *         a dictionary
	 * @throws QuadrigaStorageException
	 */
	public abstract List<IDictionaryItems> getDictionaryItemsDetails(
			String dictionaryid,String ownerName) throws QuadrigaStorageException;

	/**
	 * Queries the database to get dictionary name
	 * 
	 * @return dictionary name
	 * @throws QuadrigaStorageException
	 */
	public abstract String getDictionaryName(String dictionaryId)
			throws QuadrigaStorageException;

	/**
	 * Adds the dictionary items into dictionary in the database
	 * 
	 * @return Status message
	 * @throws QuadrigaStorageException
	 */
	public String addDictionaryItems(String dictinaryId, String item,
			String id, String pos, String owner)
			throws QuadrigaStorageException;

	/**
	 * Deletes the dictionary items from dictionary in the database
	 * 
	 * @return Status message
	 * @throws QuadrigaStorageException
	 */
	public abstract String deleteDictionaryItems(String dictinaryId,
			String itemid, String ownerName) throws QuadrigaStorageException;

	/**
	 * Updates the dictionary items in the dictionary in the database
	 * 
	 * @return Status message
	 * @throws QuadrigaStorageException
	 */
	public abstract String updateDictionaryItems(String dictinaryId,
			String termid, String term, String pos)
			throws QuadrigaStorageException;

	/**
	 * Deletes the dictionary 
	 * @param dictionaryId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract String deleteDictionary(String user, String dictionaryId) throws QuadrigaStorageException;
}
