package edu.asu.spring.quadriga.service.dictionary;

import java.util.List;

import org.codehaus.jettison.json.JSONException;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.dictionary.IDictionaryCollaborator;
import edu.asu.spring.quadriga.domain.dictionary.IDictionaryItems;
import edu.asu.spring.quadriga.domain.impl.WordpowerReply.DictionaryEntry;
import edu.asu.spring.quadriga.domain.impl.dictionary.Item;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * Interface that has methods to be implemented on the DictionaryManager class
 * 
 * @author Lohith Dwaraka
 * 
 */
public interface IDictionaryManager {

	/**
	 * Get dictionary list manager
	 * 
	 * @param sUserId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract List<IDictionary> getDictionariesList(String sUserId)
			throws QuadrigaStorageException;
	
	
	/**
	 * @throws QuadrigaStorageException 
	 * 
	 * 
	 * 
	 */
	public abstract IDictionary getDictionaryDetails(String userName) throws QuadrigaStorageException;
	

	/**
	 * Adding a new item to a dictionary 
	 * @param dictionaryId
	 * @param item
	 * @param id
	 * @param pos
	 * @param owner
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract void addNewDictionariesItems(String dictionaryId,
			String item, String id, String pos, String owner)
			throws QuadrigaStorageException;

	/**
	 * Add a new dictionary
	 * @param newDictionary
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract void addNewDictionary(IDictionary newDictionary)
			throws QuadrigaStorageException;

	/**
	 *  List dictionary items
	 * @param dictionaryid
	 * @param ownerName
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract List<IDictionaryItems> getDictionariesItems(
			String dictionaryid,String ownerName) throws QuadrigaStorageException;

	/**
	 * get Dictionary name from dictionary id
	 * @param dictionaryid
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract String getDictionaryName(String dictionaryid)
			throws QuadrigaStorageException;

	/**
	 *  search for a term in word power through quadriga
	 * @param item
	 * @param pos
	 * @return
	 */
	public abstract List<DictionaryEntry> searchWordPower(String item,
			String pos);

	/**
	 * Delete dictionary items from the a dictionary
	 * @param dictionaryId
	 * @param itemid
	 * @param ownerName
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract void deleteDictionariesItems(String dictionaryId,
			String itemid, String ownerName) throws QuadrigaStorageException;

	/**
	 * Update the existing terms in the dictionary
	 * @param dictionaryId
	 * @param itemid
	 * @param term
	 * @param pos
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract void updateDictionariesItems(String dictionaryId,
			String itemid, String term, String pos)
			throws QuadrigaStorageException;

	/**
	 * Get data from word power
	 * @param dictionaryId
	 * @param itemid
	 * @return
	 */
	public abstract List<DictionaryEntry> getUpdateFromWordPower(
			String dictionaryId, String itemid);

	/**
	 * get Item index from dictionary for updating or deleting a term
	 * @param termId
	 * @param dictionaryItems
	 * @return
	 */
	public abstract Item getDictionaryItemIndex(String termId,
			Item dictionaryItems);

		
	/**
	 * this method used to call the db manager method and return collaborators which are 
	 * present in the current dictionary
	 * 
	 * @param dictionaryId
	 * @return List<IDictionaryCollaborator>
	 * @throws QuadrigaStorageException 
	 */
	public abstract List<IDictionaryCollaborator> showCollaboratingUsers(String dictionaryId) throws QuadrigaStorageException;
	
	/**
	 * this method used to call the db manager method and return collaborators which are 
	 * not present in the current dictionary
	 * 
	 * @param collectionid
	 * @return List<IUser>
	 */
	public abstract List<IUser> showNonCollaboratingUsers(String collectionid);
	
	
	/**
	 * this method used to add collaborators in the current dictionary
	 * 
	 * @param collaborator
	 * @param dictionaryid
	 * @param userName
	 * @param sessionUser
	 * @return String
	 */
	public abstract void addCollaborators(ICollaborator collaborator, String dictionaryid, String userName, String sessionUser);
	
	
	/**
	 * this method used to delete collaborators in the current dictionary
	 * 
	 * @param dictionaryid
	 * @param userName
	 * @return
	 */
	public abstract void deleteCollaborator(String dictionaryid, String userName) throws QuadrigaStorageException;

	
	/**
	 * Delete a dictinary
	 * @param user
	 * @param dictionaryId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract void deleteDictionary(String user, String dictionaryId)throws QuadrigaStorageException;

	/**
	 * Check for user permission on the dictionary
	 * @param userId
	 * @param dicitonaryId
	 * @return
	 * @throws QuadrigaStorageException
	 * @throws QuadrigaAccessException 
	 */
	public abstract boolean userDictionaryPerm(String userId, String dicitonaryId)
			throws QuadrigaStorageException, QuadrigaAccessException;

	public abstract List<IDictionary> getDictionaryCollabOfUser(String userId)
			throws QuadrigaStorageException, QuadrigaAccessException;

	public abstract List<String> getDictionaryCollabPerm(String userId, String dicitonaryId)
			throws QuadrigaStorageException;

	public abstract List<IDictionaryItems> getDictionaryItemsDetailsCollab(String dictionaryid)
			throws QuadrigaStorageException;

	public abstract void deleteDictionaryItemsCollab(String dictionaryId, String itemid)
			throws QuadrigaStorageException;

	public abstract String getDictionaryOwner(String dictionaryid)
			throws QuadrigaStorageException;

	public abstract void addDictionaryItems(Item dictionartItems, String[] values,
			String dictionaryId) throws QuadrigaStorageException;

	public abstract String getDictionaryId(String dictName) throws QuadrigaStorageException;
	
	public abstract String getProjectsTree(String userName, String dictionaryId) throws QuadrigaStorageException, JSONException;


}
