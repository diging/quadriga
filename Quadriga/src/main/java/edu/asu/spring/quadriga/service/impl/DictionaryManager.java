package edu.asu.spring.quadriga.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.db.IDBConnectionDictionaryManager;
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.IDictionaryItems;
import edu.asu.spring.quadriga.domain.factories.impl.DictionaryItemsFactory;
import edu.asu.spring.quadriga.domain.implementation.DictionaryItems;
import edu.asu.spring.quadriga.domain.implementation.WordpowerReply;
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
	@Qualifier("searchWordPowerURL")
	private String searchWordPowerURL;

	@Autowired
	@Qualifier("updateFromWordPowerURL")
	private String updateFromWordPowerURL;

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
		return searchWordPowerURL;
	}

	/**
	 * Gets the updateFromWordPowerURL
	 * 
	 * @return String updateFromWordPowerURL
	 */
	public String getUpdateFromWordPowerURL() {
		return updateFromWordPowerURL;
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
		} catch (QuadrigaStorageException e) {
			// TODO Auto-generated catch block
			throw new QuadrigaStorageException();
		}

		return dictionaryList;
	}

	/**
	 * Adds a new dictionaries for the user
	 * 
	 * @return Return to success or error msg to controller
	 */

	public String addNewDictionary(IDictionary dictionary)
			throws QuadrigaStorageException {

		String msg = "";
		try {
			msg = dbConnect.addDictionary(dictionary);
		} catch (QuadrigaStorageException e) {
			// TODO Auto-generated catch block
			msg = "DB Issue";
			throw new QuadrigaStorageException();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return msg;
	}

	/**
	 * Add a new dictionary item to dictionary of the user
	 * 
	 * @return Return to success or error message to controller
	 */

	public String addNewDictionariesItems(String dictionaryId, String item,
			String id, String pos, String owner)
			throws QuadrigaStorageException {
		String msg = "";
		try {
			msg = dbConnect.addDictionaryItems(dictionaryId, item, id, pos,
					owner);
		} catch (QuadrigaStorageException e) {
			msg = "Storage issue please try later";
			throw new QuadrigaStorageException();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return msg;

	}

	/**
	 * Delete the dictionary item from the dictionary of the user
	 * 
	 * @return Return success or error message to controller
	 */

	public String deleteDictionariesItems(String dictionaryId, String itemid)
			throws QuadrigaStorageException {
		String msg = "";
		try {
			msg = dbConnect.deleteDictionaryItems(dictionaryId, itemid);
		} catch (QuadrigaStorageException e) {
			msg = "Issue in the DB";
			throw new QuadrigaStorageException();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return msg;
	}

	/**
	 * Update the dictionary item of the dictionary from the word power
	 * 
	 * @return Return error or success message to controller
	 */

	public String updateDictionariesItems(String dictionaryId, String termid,
			String term, String pos) throws QuadrigaStorageException {
		String msg = "";
		try {
			msg = dbConnect.updateDictionaryItems(dictionaryId, termid, term,
					pos);
		} catch (QuadrigaStorageException e) {
			msg = "Issue in the DB";
			throw new QuadrigaStorageException();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return msg;
	}

	/**
	 * Gets all the dictionary item of the dictionary of the user
	 * 
	 * @return Return to list of dictionary item to controller
	 */

	public List<IDictionaryItems> getDictionariesItems(String dictionaryid)
			throws QuadrigaStorageException {

		List<IDictionaryItems> dictionaryItemList = null;
		try {
			dictionaryItemList = dbConnect
					.getDictionaryItemsDetails(dictionaryid);
		} catch (QuadrigaStorageException e) {

			throw new QuadrigaStorageException();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dictionaryItemList;
	}

	/**
	 * Gets dictionary name of the dictionary from dictionary ID
	 * 
	 * @return Return the dictionary name to controller
	 * @throws QuadrigaStorageException
	 */

	public String getDictionaryName(String dictionaryid)
			throws QuadrigaStorageException {

		String dictionaryName = "";
		try {
			dictionaryName = dbConnect.getDictionaryName(dictionaryid);
		} catch (QuadrigaStorageException e) {
			throw new QuadrigaStorageException();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dictionaryName;
	}

	/**
	 * Call the word power for a term and fetch the xml from word power rest
	 * 
	 * @return Return the dictionaryEntry bean to controller
	 */

	public List<WordpowerReply.DictionaryEntry> searchWordPower(String item,
			String pos) {

		List<WordpowerReply.DictionaryEntry> dictionaryEntry = null;
		try {

			String fullUrl = getSearchWordPowerURL() + "" + item + "/" + pos;
			logger.info("Search Word Power URL : " + fullUrl);
			WordpowerReply wordpowerReply = (WordpowerReply) restTemplate
					.getForObject(fullUrl, WordpowerReply.class);
			dictionaryEntry = wordpowerReply.getDictionaryEntry();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dictionaryEntry;
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
			logger.info("Update url from func : " + getUpdateFromWordPowerURL());
			itemid = itemid.substring(itemid.lastIndexOf("/") + 1,
					itemid.length());
			logger.info("Update Item ID : " + itemid);
			logger.info("URL From rest xml : --" + getUpdateFromWordPowerURL()
					+ "--");
			String fullUrl = getUpdateFromWordPowerURL() + "" + itemid;
			logger.info("Update Word Power URL : " + fullUrl);
			WordpowerReply wordpowerReply = (WordpowerReply) restTemplate
					.getForObject(fullUrl, WordpowerReply.class);
			dictionaryEntry = wordpowerReply.getDictionaryEntry();
		} catch (Exception e) {

			e.printStackTrace();
		}
		return dictionaryEntry;
	}

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

}
