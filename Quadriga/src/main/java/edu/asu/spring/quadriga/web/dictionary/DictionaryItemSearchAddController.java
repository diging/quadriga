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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.asu.spring.quadriga.domain.IDictionaryItems;
import edu.asu.spring.quadriga.domain.implementation.DictionaryItems;
import edu.asu.spring.quadriga.domain.implementation.WordpowerReply.DictionaryEntry;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IDictionaryManager;
import edu.asu.spring.quadriga.service.IUserManager;

/**
 * This class will handle add and search dictionaries items controller for the
 * dictionary from word power
 * 
 * @author : Lohith Dwaraka
 * 
 */
@Controller
public class DictionaryItemSearchAddController {
	@Autowired
	IDictionaryManager dictonaryManager;

	private static final Logger logger = LoggerFactory
			.getLogger(DictionaryItemSearchAddController.class);

	@Autowired
	IUserManager usermanager;

	public IUserManager getUsermanager() {
		return usermanager;
	}

	public void setUsermanager(IUserManager usermanager) {
		this.usermanager = usermanager;
	}

	List<DictionaryEntry> dictionaryEntryList = null;

	/**
	 * Handles the add dictionary item page
	 * 
	 * @return Return to the adddictionaryitems JSP
	 */
	@RequestMapping(value = "auth/dictionaries/addDictionaryItems/{dictionaryid}", method = RequestMethod.GET)
	public String addDictionaryPage(
			@PathVariable("dictionaryid") String dictionaryid, ModelMap model) {

		logger.info("came to addDictionaryPage");

		model.addAttribute("dictionaryid", dictionaryid);
		return "auth/dictionaries/addDictionaryItems";
	}

	/**
	 * Handles the form tag for add dictionary item to dictionary
	 * 
	 * @return Return to list dictionary item page
	 */

	@RequestMapping(value = "auth/dictionaries/addDictionaryItems/{dictionaryid}", method = RequestMethod.POST)
	public String addDictionaryItem(HttpServletRequest req,
			@PathVariable("dictionaryid") String dictionaryId,
			@ModelAttribute("SpringWeb") DictionaryItems dictionaryItems,
			ModelMap model, Principal principal)
			throws QuadrigaStorageException {
		String msg = "";
		logger.info("came to addDictionaryItemForm post");
		String[] values = req.getParameterValues("selected");
		String owner = usermanager.getUserDetails(principal.getName())
				.getUserName();
		logger.info("items : " + dictionaryItems.getItems() + " , id: "
				+ dictionaryItems.getId() + "pos" + dictionaryItems.getPos());

		if (values != null) {
			for (int i = 0; i < values.length; i++) {

				DictionaryItems di = dictonaryManager.getDictionaryItemIndex(
						values[i], dictionaryItems);
				msg = dictonaryManager.addNewDictionariesItems(dictionaryId,
						di.getItems(), di.getId(), di.getPos(), owner);

			}
		}

		if (msg.equals("")) {
			model.addAttribute("success", 1);
			model.addAttribute("successmsg", "Items added successfully");
		} else {
			if (msg.equals("ItemExists")) {
				model.addAttribute("success", 0);
				model.addAttribute("errormsg",
						"Items already exist for dictionary id :"
								+ dictionaryId);
			} else {
				model.addAttribute("success", 0);
				model.addAttribute("errormsg", msg);
			}
		}
		List<IDictionaryItems> dictionaryItemList = dictonaryManager
				.getDictionariesItems(dictionaryId);
		String dictionaryName = dictonaryManager
				.getDictionaryName(dictionaryId);
		model.addAttribute("dictionaryItemList", dictionaryItemList);
		model.addAttribute("dictName", dictionaryName);
		model.addAttribute("dictID", dictionaryId);

		return "auth/dictionary/dictionary";
	}

	/**
	 * Admin can use this to search from term and pos from word power
	 * 
	 * @return Return to list dictionary item page
	 * @throws QuadrigaStorageException
	 */

	@RequestMapping(value = "auth/dictionaries/dictionary/wordSearch/{dictionaryid}", method = RequestMethod.POST)
	public String searchDictionaryItemRestHandle(
			@PathVariable("dictionaryid") String dictionaryid,
			@RequestParam("itemName") String item,
			@RequestParam("posdropdown") String pos, ModelMap model)
			throws QuadrigaStorageException {
		try {
			logger.info("came to searchDictionaryItemRestHandle post");
			dictionaryEntryList = null;
			if (!item.equals("")) {
				logger.info("Query for Item :" + item + " and pos :" + pos);
				dictionaryEntryList = dictonaryManager.searchWordPower(item,
						pos);
			}
			model.addAttribute("status", 1);
			model.addAttribute("dictionaryEntryList", dictionaryEntryList);

			Iterator<DictionaryEntry> I = dictionaryEntryList.iterator();
			while (I.hasNext()) {
				DictionaryEntry dictionaryEntry = I.next();
				logger.info("Lemma :" + dictionaryEntry.getLemma());
			}
			List<IDictionaryItems> dictionaryItemList = dictonaryManager
					.getDictionariesItems(dictionaryid);
			String dictionaryName = dictonaryManager
					.getDictionaryName(dictionaryid);
			model.addAttribute("dictionaryItemList", dictionaryItemList);
			model.addAttribute("dictName", dictionaryName);
			model.addAttribute("dictionaryid", dictionaryid);
			if (dictionaryEntryList == null) {
				model.addAttribute("errorstatus", 1);
			}

		} catch (QuadrigaStorageException e) {
			throw new QuadrigaStorageException(
					"Oops the DB is an hard hangover, please try later");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// return "auth/dictionaries/dictionary/wordSearch";
		// return "auth/dictionary/dictionary";
		return "auth/dictionaries/addDictionaryItems";
	}

}
