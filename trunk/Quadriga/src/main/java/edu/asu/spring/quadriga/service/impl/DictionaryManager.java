package edu.asu.spring.quadriga.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.db.IDBConnectionDictionaryManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.IDictionaryItems;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.impl.DictionaryItemsFactory;
import edu.asu.spring.quadriga.domain.implementation.DictionaryItems;
import edu.asu.spring.quadriga.domain.implementation.WordpowerReply;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IDictionaryManager;

/**
 * This class acts as a Dictionary manager which adds list of Dictionary words
 * and their descriptions on the dictionary page.
 * 
 * @implements : IDictionaryManager Interface
 * 
 * @Called By : DictionaryController.java
 * 
 * @author : Lohith Dwaraka
 * 
 * 
 */
@Service
public class DictionaryManager implements IDictionaryManager {

	@Autowired
	@Qualifier("restTemplate")
	RestTemplate restTemplate;

	@Autowired
	@Qualifier("wordPowerURL")
	private String wordPowerURL;
	
	@Autowired
	@Qualifier("searchWordPowerURLPath")
	private String searchWordPowerURLPath;

	@Autowired
	@Qualifier("updateFromWordPowerURLPath")
	private String updateFromWordPowerURLPath;
	
	
	private static final Logger logger = LoggerFactory
			.getLogger(DictionaryManager.class);

	@Autowired
	@Qualifier("DBConnectionDictionaryManagerBean")
	private IDBConnectionDictionaryManager dbConnect;

	@Autowired
	DictionaryItemsFactory dictionaryItemsFactory;

	/**
	 * Gets the searchWordPowerURL
	 * 
	 * @return String URL
	 */
	public String getSearchWordPowerURL() {
		return wordPowerURL+""+searchWordPowerURLPath;
	}

	/**
	 * Gets the updateFromWordPowerURL
	 * 
	 * @return String updateFromWordPowerURL
	 */
	public String getUpdateFromWordPowerURL() {
		return wordPowerURL+""+updateFromWordPowerURLPath;
	}

	/**
	 * Gets all the dictionaries of the user
	 * 
	 * @return Return to list dictionary to controller
	 * @throws QuadrigaStorageException
	 */
	public List<IDictionary> getDictionariesList(String userId)
			throws QuadrigaStorageException {

		List<IDictionary> dictionaryList = new ArrayList<IDictionary>();

		try {
			dictionaryList = dbConnect.getDictionaryOfUser(userId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("",e);
		}

		return dictionaryList;
	}

	/**
	 * Checks for user permission on dictionary
	 * @param userId
	 * @param dicitonaryId
	 * @return
	 * @throws QuadrigaStorageException
	 * @throws QuadrigaAccessException 
	 */
	@Override
	public boolean userDictionaryPerm(String userId, String dicitonaryId) throws QuadrigaStorageException, QuadrigaAccessException{
		boolean result=dbConnect.userDictionaryPerm(userId,dicitonaryId);
		logger.info("result of perfmission "+result);
		if(result == false){
			throw new QuadrigaAccessException();
		}
		return result;
	}
	
	/**
	 * Checks for user permission on dictionary
	 * @param userId
	 * @param dicitonaryId
	 * @return
	 * @throws QuadrigaStorageException
	 * @throws QuadrigaAccessException 
	 */
	@Override
	public List<IDictionary> getDictionaryCollabOfUser(String userId) throws QuadrigaStorageException, QuadrigaAccessException{
		List<IDictionary> dictionaryList=dbConnect.getDictionaryCollabOfUser(userId);
		return dictionaryList;
	}
	
	@Override
	public String getDictionaryCollabPerm(String userId,String dicitonaryId) throws QuadrigaStorageException {
		String role=dbConnect.getDictionaryCollabPerm(userId, dicitonaryId);
		return role;
	}
	/**
	 * Adds a new dictionaries for the user
	 * 
	 * @return Return to success or error msg to controller
	 */

	@Override
	public String addNewDictionary(IDictionary dictionary)
			throws QuadrigaStorageException {

		String msg = "";
		try {
			msg = dbConnect.addDictionary(dictionary);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return msg;
	}

	/**
	 * Add a new dictionary item to dictionary of the user
	 * 
	 * @return Return to success or error message to controller
	 */

	@Override
	public String addNewDictionariesItems(String dictionaryId, String item,
			String id, String pos, String owner)
			throws QuadrigaStorageException {
		String msg = "";
		try {
			msg = dbConnect.addDictionaryItems(dictionaryId, item, id, pos,
					owner);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return msg;

	}

	/**
	 * Delete the dictionary item from the dictionary of the user
	 * 
	 * @return Return success or error message to controller
	 */
	@Override
	public String deleteDictionariesItems(String dictionaryId, String itemid,
			String ownerName) throws QuadrigaStorageException {
		String msg = "";
		try {
			msg = dbConnect.deleteDictionaryItems(dictionaryId, itemid,
					ownerName);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return msg;
	}
	
	@Override
	public String deleteDictionaryItemsCollab(String dictionaryId, String itemid) throws QuadrigaStorageException {
		String msg = "";
		try {
			msg = dbConnect.deleteDictionaryItemsCollab(dictionaryId, itemid);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return msg;
	}
	
	/**
	 * Update the dictionary item of the dictionary from the word power
	 * 
	 * @return Return error or success message to controller
	 */
	@Override
	public String updateDictionariesItems(String dictionaryId, String termid,
			String term, String pos) throws QuadrigaStorageException {
		String msg = "";
		try {
			msg = dbConnect.updateDictionaryItems(dictionaryId, termid, term,
					pos);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return msg;
	}

	/**
	 * Gets all the dictionary item of the dictionary of the user
	 * 
	 * @return Return to list of dictionary item to controller
	 */
	@Override
	public List<IDictionaryItems> getDictionariesItems(String dictionaryid,
			String ownerName) throws QuadrigaStorageException {

		List<IDictionaryItems> dictionaryItemList = null;
		try {
			dictionaryItemList = dbConnect.getDictionaryItemsDetails(
					dictionaryid, ownerName);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return dictionaryItemList;
	}
	@Override
	public List<IDictionaryItems> getDictionaryItemsDetailsCollab(String dictionaryid) throws QuadrigaStorageException {

		List<IDictionaryItems> dictionaryItemList = null;
		try {
			dictionaryItemList = dbConnect.getDictionaryItemsDetailsCollab(
					dictionaryid);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return dictionaryItemList;
	}
	
	/**
	 * Gets dictionary name of the dictionary from dictionary ID
	 * 
	 * @return Return the dictionary name to controller
	 * @throws QuadrigaStorageException
	 */
	@Override
	public String getDictionaryName(String dictionaryid)
			throws QuadrigaStorageException {

		String dictionaryName = "";
		try {
			dictionaryName = dbConnect.getDictionaryName(dictionaryid);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return dictionaryName;
	}
	
	@Override
	public String getDictionaryOwner(String dictionaryid)
			throws QuadrigaStorageException {

		String dictionaryOwner = "";
		try {
			dictionaryOwner = dbConnect.getDictionaryName(dictionaryid);
			logger.info(" checking --- "+dictionaryOwner);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return dictionaryOwner;
	}
	
	/**
	 * Call the word power for a term and fetch the xml from word power rest
	 * 
	 * @return Return the dictionaryEntry bean to controller
	 */
	@Override
	public List<WordpowerReply.DictionaryEntry> searchWordPower(String item,
			String pos) {

		List<WordpowerReply.DictionaryEntry> dictionaryEntry = null;
		try {

			String fullUrl = getSearchWordPowerURL() + "" + item + "/" + pos;
			logger.debug("Search Word Power URL : " + fullUrl);
			WordpowerReply wordpowerReply = (WordpowerReply) restTemplate
					.getForObject(fullUrl, WordpowerReply.class);
			dictionaryEntry = wordpowerReply.getDictionaryEntry();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return dictionaryEntry;
	}

	@Override
	public String deleteDictionary(String user, String dictionaryId)
			throws QuadrigaStorageException {

		String msg = "";
		try {
			logger.debug("deleting from dictionary manager");
			msg = dbConnect.deleteDictionary(user, dictionaryId);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return msg;
	}

	/**
	 * Call the word power for a term and fetch the xml from word power rest
	 * 
	 * @return Return the dictionaryEntry bean to controller
	 */
	
	public List<WordpowerReply.DictionaryEntry> getUpdateFromWordPower(
			String dictionaryId, String itemid) {

		List<WordpowerReply.DictionaryEntry> dictionaryEntry = null;
		try {
			logger.debug("Update url from func : " + getUpdateFromWordPowerURL());
			itemid = itemid.substring(itemid.lastIndexOf("/") + 1,
					itemid.length());
			logger.debug("Update Item ID : " + itemid);
			logger.debug("URL From rest xml : --" + getUpdateFromWordPowerURL()
					+ "--");
			String fullUrl = getUpdateFromWordPowerURL() + "" + itemid;
			logger.debug("Update Word Power URL : " + fullUrl);
			WordpowerReply wordpowerReply = (WordpowerReply) restTemplate
					.getForObject(fullUrl, WordpowerReply.class);
			dictionaryEntry = wordpowerReply.getDictionaryEntry();
		} catch (Exception e) {

			logger.error(e.getMessage());
		}
		return dictionaryEntry;
	}

	/**
	 * Get index of term from a list for update and deleting term from
	 * dictionary
	 * 
	 * @return Return the dictionaryEntry bean to controller
	 */
	public DictionaryItems getDictionaryItemIndex(String termId,
			DictionaryItems dictionaryItems) {

		String terms[] = dictionaryItems.getItems().split(",");
		String id[] = dictionaryItems.getId().split(",");
		String pos[] = dictionaryItems.getPos().split(",");
		int index = -1;
		if (id.length > 0) {
			for (int i = 0; i < id.length; i++) {
				if (id[i].equals(termId)) {
					index = i;
					i = id.length;
				}
			}
		}
		DictionaryItems di = dictionaryItemsFactory
				.createDictionaryItemsObject();
		di.setId(id[index]);
		di.setItems(terms[index]);
		di.setPos(pos[index]);

		return di;
	}

	@Override
	public List<IUser> getCollaborators(String dictionaryid) {
		
		List<IUser> userList = dbConnect.getDictionaryCollaborators(dictionaryid);		
		return userList;
	}	
	
	@Override
	public List<IUser> showNonCollaboratingUsers(String dictionaryid) {

		List<IUser> nonCollabUsers = null;
		try {
			nonCollabUsers = dbConnect.showNonCollaboratingUsersRequest(dictionaryid);
		} catch (QuadrigaStorageException e) {
			e.printStackTrace();
		}
		return nonCollabUsers;
	}

	@Override
	public String addCollaborators(String[] collaboratorRole, String dictionaryid, String userName, String sessionUser) {
		
		String errmsg=null;
		
		try {
			
		 errmsg =	dbConnect.addCollaborators(collaboratorRole, dictionaryid, userName, sessionUser);
			
		} catch (QuadrigaStorageException e) {
			e.printStackTrace();
		}
		
		return errmsg;
	}

	@Override
	public List<ICollaborator> showCollaboratingUsers(String dictionaryid) {
		
		List<ICollaborator> collaborators = null;
		try {
			
		collaborators = dbConnect.showCollaboratingUsersRequest(dictionaryid);
		
		} catch (QuadrigaStorageException e) {
			e.printStackTrace();
		}
	
		return collaborators;
	}

	@Override
	public String deleteCollaborator(String dictionaryid, String userName) {
		
		String errmsg = dbConnect.deleteCollaborators(dictionaryid, userName);
		
		return errmsg;
	}
	
	

}
