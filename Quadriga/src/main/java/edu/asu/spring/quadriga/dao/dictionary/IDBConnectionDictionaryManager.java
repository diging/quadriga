package edu.asu.spring.quadriga.dao.dictionary;

import java.util.List;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.dictionary.IItem;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * Interface for the DBConnectionDictionaryManager Class.
 * 
 * @author Lohith Dwaraka
 * 
 */

public interface IDBConnectionDictionaryManager {


	/**
	 * Queries the database to get a list of dictionary objects list
	 * 
	 * @return List containing DictionaryDTO objects of dictionary of the users
	 * @throws QuadrigaStorageException
	 */
	public abstract List<DictionaryDTO> getDictionaryOfUser(String userName)
			throws QuadrigaStorageException;

	/**
	 * Adds the dictionary in the database
	 * 
	 * @return Status message
	 * @throws QuadrigaStorageException
	 */
	public abstract void addDictionary(IDictionary dictionary)
			throws QuadrigaStorageException;

	/**
	 * Queries the database to get a list of dictionary items objects list
	 * 
	 * @return List containing IDictionaryItems objects of dictionary items for
	 *         a dictionary
	 * @throws QuadrigaStorageException
	 */
	public abstract List<IItem> getDictionaryItemsDetails(
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
	public void addDictionaryItems(String dictionaryId, String item,
			String id, String pos, String owner)
			throws QuadrigaStorageException;

	/**
	 * Deletes the dictionary items from dictionary in the database
	 * 
	 * @return Status message
	 * @throws QuadrigaStorageException
	 */
	public abstract void deleteDictionaryItems(String dictinaryId,
			String itemid, String ownerName) throws QuadrigaStorageException;

	/**
	 * Updates the dictionary items in the dictionary in the database
	 * 
	 * @return Status message
	 * @throws QuadrigaStorageException
	 */
	public abstract void updateDictionaryItems(String dictinaryId,
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
	public abstract void addCollaborators(ICollaborator collaborator, String dictionaryid, String userName, String sessionUser) throws QuadrigaStorageException;

	/**
	 * makes db call to delete collaborator in dictionary
	 * 
	 * @param dictionaryid
	 * @param userName
	 * @return String 			error message from the database
	 * @author rohit pendbhaje
	 */
	public abstract void deleteCollaborators(String dictionaryid, String userName) throws QuadrigaStorageException;
	

	/**
	 * Deletes the dictionary 
	 * @param dictionaryId
	 * @return 
	 * @throws QuadrigaStorageException
	 */
	public abstract void deleteDictionary(String user, String dictionaryId) throws QuadrigaStorageException;

	/**
	 * Checks if user has permission to dictionary
	 * @param dictionaryId
	 * @param userId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract boolean userDictionaryPerm(String userId, String dictionaryId)
			throws QuadrigaStorageException;


	/**
	 * Get user dictionary of the user with the collaborator role
	 * @param user id
	 * @return List of DictionaryDTO objects
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	public abstract List<DictionaryDTO> getDictionaryCollabOfUser(String userId)
			throws QuadrigaStorageException;

	/**
	 * Get the user role of the collaborator with the user id and dictionary id
	 * @param user id and dictionary id
	 * @return Role of the user
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	public abstract List<String> getDictionaryCollaboratorRoles(String userId, String dicitonaryId)
			throws QuadrigaStorageException;

	/**
	 * Get the Dictionary Items corresponding to a dictionary ID
	 * @param udictionary id
	 * @return List of dictionary items
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	public abstract List<IItem> getDictionaryItemsDetailsCollab(String dictionaryid)
			throws QuadrigaStorageException;

	/**
	 * Delete Dictionary Items corresponding to a dictionary ID and Term id
	 * @param dictionary id and term id
	 * @return error messages if any
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	public abstract void deleteDictionaryItemsCollab(String dictinaryId, String itemid)
			throws QuadrigaStorageException;

	/**
	 * Get Dictionary owner name corresponding to a dictionary ID
	 * @param dictionary id
	 * @return Owner username
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	public abstract String getDictionaryOwner(String dictionaryId)
			throws QuadrigaStorageException;
	
	public abstract DictionaryDTO getDictionaryDetails(String userName) throws QuadrigaStorageException;
	
	public abstract List<DictionaryDTO> getDictionaryDTOList(String userName) throws QuadrigaStorageException;
	
	/**
	 * Get Dictionary ID name corresponding to a dictionary ID
	 * @param dictionary id
	 * @return Owner username
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	public abstract String getDictionaryId(String dictName) throws QuadrigaStorageException;

}
