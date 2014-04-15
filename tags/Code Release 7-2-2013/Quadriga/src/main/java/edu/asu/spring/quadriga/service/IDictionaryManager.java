package edu.asu.spring.quadriga.service;

import java.util.List;

import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.IDictionaryItems;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.implementation.DictionaryItems;
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
	 * Get dictionary list manager
	 * 
	 * @param sUserId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract List<IDictionary> getDictionariesList(String sUserId)
			throws QuadrigaStorageException;

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
	public abstract String addNewDictionariesItems(String dictionaryId,
			String item, String id, String pos, String owner)
			throws QuadrigaStorageException;

	/**
	 * Add a new dictionary
	 * @param newDictionary
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract String addNewDictionary(IDictionary newDictionary)
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
	public abstract String deleteDictionariesItems(String dictionaryId,
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
	public abstract String updateDictionariesItems(String dictionaryId,
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
	public abstract DictionaryItems getDictionaryItemIndex(String termId,
			DictionaryItems dictionaryItems);

	
	public abstract List<IUser> getCollaborators(String dictionaryid);
	
	public abstract List<IUser> showCollaboratingUsers(String collectionid);
	
	public abstract List<IUser> showNonCollaboratingUsers(String collectionid);
	
	public abstract String addCollaborators(ICollaborator collaborator, String dictionaryid, String userName);

	


	/**
	 * Delete a dictinary
	 * @param user
	 * @param dictionaryId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract String deleteDictionary(String user, String dictionaryId)throws QuadrigaStorageException;

}