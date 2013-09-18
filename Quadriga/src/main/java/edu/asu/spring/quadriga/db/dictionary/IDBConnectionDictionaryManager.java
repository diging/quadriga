package edu.asu.spring.quadriga.db.dictionary;

import java.util.List;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.IDictionaryItem;
import edu.asu.spring.quadriga.domain.IUser;
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
	public abstract List<IDictionaryItem> getDictionaryItemsDetails(
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
	 * makes database call to show non-collaborating users of the current dictionary
	 * 
	 * @param dictionaryid
	 * @return List<IUser>
	 * @throws QuadrigaStorageException
	 * @author rohit pendbhaje
	 */
	public abstract List<IUser> showNonCollaboratingUsersRequest(String dictionaryid) throws QuadrigaStorageException;
	
	
	/**
	 * makes database call to show collaborating users of the current dictionary
	 * 
	 * @param dictionaryid
	 * @return List<ICollaborator>
	 * @throws QuadrigaStorageException
	 * @author rohit pendbhaje
	 */
	public abstract List<ICollaborator> showCollaboratingUsersRequest(String dictionaryid) throws QuadrigaStorageException;
	
	
	/**
	 * makes db call to add collaborator in dictionary
	 * 
	 * @param collaborator
	 * @param dictionaryid
	 * @param userName
	 * @param sessionUser
	 * @return String 			error message from the database
	 * @throws QuadrigaStorageException
	 * @author rohit pendbhaje
	 */
	public abstract String addCollaborators(ICollaborator collaborator, String dictionaryid, String userName, String sessionUser) throws QuadrigaStorageException;

	/**
	 * makes db call to delete collaborator in dictionary
	 * 
	 * @param dictionaryid
	 * @param userName
	 * @return String 			error message from the database
	 * @author rohit pendbhaje
	 */
	public abstract String deleteCollaborators(String dictionaryid, String userName);
	

	/**
	 * Deletes the dictionary 
	 * @param dictionaryId
	 * @return 
	 * @throws QuadrigaStorageException
	 */
	public abstract String deleteDictionary(String user, String dictionaryId) throws QuadrigaStorageException;

	/**
	 * Checks if user has permission to dictionary
	 * @param dictionaryId
	 * @param userId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract boolean userDictionaryPerm(String userId, String dictionaryId)
			throws QuadrigaStorageException;


	public abstract List<IDictionary> getDictionaryCollabOfUser(String userId)
			throws QuadrigaStorageException;

	public abstract String getDictionaryCollabPerm(String userId, String dicitonaryId)
			throws QuadrigaStorageException;

	public abstract List<IDictionaryItem> getDictionaryItemsDetailsCollab(String dictionaryid)
			throws QuadrigaStorageException;

	public abstract String deleteDictionaryItemsCollab(String dictinaryId, String itemid)
			throws QuadrigaStorageException;

	public abstract String getDictionaryOwner(String dictionaryId)
			throws QuadrigaStorageException;


}
