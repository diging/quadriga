package edu.asu.spring.quadriga.web.dictionary;

import java.security.Principal;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.IDictionaryItems;
import edu.asu.spring.quadriga.domain.implementation.WordpowerReply.DictionaryEntry;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IDictionaryManager;
import edu.asu.spring.quadriga.service.IUserManager;

/**
 * This class will handle list dictionaries items controller for the dictionary
 * 
 * @author : Lohith Dwaraka
 * 
 */
@Controller
public class DictionaryItemController {
	@Autowired
	IDictionaryManager dictonaryManager;

	private static final Logger logger = LoggerFactory
			.getLogger(DictionaryItemController.class);

	@Autowired
	IUserManager usermanager;

	public IUserManager getUsermanager() {
		return usermanager;
	}

	public void setUsermanager(IUserManager usermanager) {
		this.usermanager = usermanager;
	}

	/**
	 * Admin can use this page to check the list of dictionary items in a
	 * dictionary and to search and add items from the word power
	 * 
	 * @return Return to the list dictionary items page of the Quadriga
	 * @throws QuadrigaStorageException
	 */

	@RequestMapping(value = "auth/dictionaries/{dictionaryid}", method = RequestMethod.GET)
	public String getDictionaryPage(
			@PathVariable("dictionaryid") String dictionaryid, ModelMap model)
			throws QuadrigaStorageException {
		try {
			logger.info("came to getDictionaryPage");
			List<IDictionaryItems> dictionaryItemList = dictonaryManager
					.getDictionariesItems(dictionaryid);
			if (dictionaryItemList == null) {
				logger.info("Dictionary ITem list is null");
			}
			String dictionaryName = "";
			try {
				dictionaryName = dictonaryManager
						.getDictionaryName(dictionaryid);
			} catch (QuadrigaStorageException e) {
				throw new QuadrigaStorageException("Oops the DB is an hard hangover, please try later");
			}
			model.addAttribute("dictionaryItemList", dictionaryItemList);
			model.addAttribute("dictName", dictionaryName);
			model.addAttribute("dictionaryid", dictionaryid);
		} catch (QuadrigaStorageException e) {
			throw new QuadrigaStorageException("Oops the DB is an hard hangover, please try later");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "auth/dictionary/dictionary";
	}

	/**
	 * Admin can use this to delete a dictionary item to dictionary
	 * 
	 * @return Return to list dictionary item page
	 * @throws QuadrigaStorageException
	 */

	@RequestMapping(value = "auth/dictionaries/deleteDictionaryItems/{dictionaryid}", method = RequestMethod.POST)
	public String deleteDictionaryItem(HttpServletRequest req,
			@PathVariable("dictionaryid") String dictionaryId, ModelMap model,
			Principal principal) throws QuadrigaStorageException {
		try {
			String[] values = req.getParameterValues("selected");
			String msg = "";
			String errormsg = "";
			int flag = 0;
			if (values != null) {
				for (int i = 0; i < values.length; i++) {
					logger.info("Deleting item for dictionary id: "
							+ dictionaryId + " and term id : " + i + " : "
							+ values[i]);
					msg = dictonaryManager.deleteDictionariesItems(
							dictionaryId, values[i]);
					if (msg.equals("")) {

					} else {
						flag = 1;
						errormsg = msg;
					}

				}
			} else {
				flag = 2;
			}

			if (flag == 0) {
				model.addAttribute("delsuccess", 1);
				model.addAttribute("delsuccessmsg",
						"Items  deleted successfully");
			} else if (flag == 1) {
				if (errormsg.equals("Item doesnot exists in this dictionary")) {
					model.addAttribute("delsuccess", 0);
					model.addAttribute("delerrormsg",
							"Items doesn't exist for dictionary id :"
									+ dictionaryId);
				} else {
					model.addAttribute("delsuccess", 0);
					model.addAttribute("delerrormsg", errormsg);
				}
			} else {

			}
			logger.info("Item Returned ");
			List<IDictionaryItems> dictionaryItemList = dictonaryManager
					.getDictionariesItems(dictionaryId);
			String dictionaryName = dictonaryManager
					.getDictionaryName(dictionaryId);
			model.addAttribute("dictionaryItemList", dictionaryItemList);
			model.addAttribute("dictName", dictionaryName);
			model.addAttribute("dictID", dictionaryId);
		} catch (QuadrigaStorageException e) {
			throw new QuadrigaStorageException("Oops the DB is an hard hangover, please try later");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "auth/dictionary/dictionary";
	}

	/**
	 * Admin can use this to update a dictionary item's item to dictionary
	 * 
	 * @return Return to list dictionary item page
	 */

	@RequestMapping(value = "auth/dictionaries/updateDictionaryItems/{dictionaryid}", method = RequestMethod.POST)
	public String updateDictionaryItem(HttpServletRequest req,
			@PathVariable("dictionaryid") String dictionaryId, ModelMap model,
			Principal principal) throws QuadrigaStorageException {
		// DictionaryEntry
		// dictionaryEntry=dictonaryManager.callRestUri("http://digitalhps-develop.asu.edu:8080/wordpower/rest/Word/",item,pos);
		try {
			// String msg=
			// dictonaryManager.updateDictionariesItems(dictionaryId,item,dictionaryEntry.getId());
			String[] values = req.getParameterValues("selected");
			String msg = "";
			String errormsg = "";
			int flag = 0;

			if (values != null) {
				for (int i = 0; i < values.length; i++) {
					logger.info("Value " + i + " : " + values[i]);
					List<DictionaryEntry> dictionaryEntryList = dictonaryManager
							.getUpdateFromWordPower(dictionaryId, values[i]);
					Iterator<DictionaryEntry> I = dictionaryEntryList
							.iterator();
					if (I.hasNext()) {
						DictionaryEntry dictionaryEntry = I.next();
						msg = dictonaryManager.updateDictionariesItems(
								dictionaryId, values[i],
								dictionaryEntry.getLemma(),
								dictionaryEntry.getPos());
					} else {
						msg = "Error getting data from Word Power";
						flag = 1;
						errormsg = msg;
					}
					if (msg.equals("")) {

					} else {
						flag = 1;
						errormsg = msg;
					}
				}
			} else {
				flag = 2;
			}

			if (flag == 0) {
				logger.info("Successfully updated");
				model.addAttribute("updatesuccess", 1);
				model.addAttribute("updatesuccessmsg",
						"Items updated successfully");
			} else if (flag == 1) {
				logger.info("Please check :  errormsg");
				if (errormsg.equals("Item doesnot exists in this dictionary")) {
					model.addAttribute("updatesuccess", 0);
					model.addAttribute("updateerrormsg",
							"Items doesn't exist for dictionary id :"
									+ dictionaryId);
				} else {
					model.addAttribute("updatesuccess", 0);
					model.addAttribute("updateerrormsg", errormsg);
				}
			} else {

			}
			logger.info("Item Returned ");
			List<IDictionaryItems> dictionaryItemList = dictonaryManager
					.getDictionariesItems(dictionaryId);
			String dictionaryName = dictonaryManager
					.getDictionaryName(dictionaryId);
			model.addAttribute("dictionaryItemList", dictionaryItemList);
			model.addAttribute("dictName", dictionaryName);
			model.addAttribute("dictID", dictionaryId);
		} catch (QuadrigaStorageException e) {
			throw new QuadrigaStorageException("Oops the DB is an hard hangover, please try later");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "auth/dictionary/dictionary";
	}

}
