package edu.asu.spring.quadriga.dao.dictionary;

import java.util.List;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * Interface for the DBConnectionDictionaryManager Class.
 * 
 * @author Lohith Dwaraka
 * 
 */

public interface IDictionaryDAO extends IBaseDAO<DictionaryDTO> {


	/**
	 * Adds the dictionary in the database
	 * 
	 * @return Status message
	 * @throws QuadrigaStorageException
	 */
	public abstract void addDictionary(IDictionary dictionary)
			throws QuadrigaStorageException;

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
