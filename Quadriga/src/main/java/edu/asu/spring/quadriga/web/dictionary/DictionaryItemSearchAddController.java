package edu.asu.spring.quadriga.web.dictionary;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONObject;
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

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.dictionary.IDictionaryItems;
import edu.asu.spring.quadriga.domain.impl.dictionary.Item;
import edu.asu.spring.quadriga.domain.implementation.WordpowerReply.DictionaryEntry;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryManager;

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

	public IDictionaryManager getDictonaryManager() {
		return dictonaryManager;
	}

	public void setDictonaryManager(IDictionaryManager dictonaryManager) {
		this.dictonaryManager = dictonaryManager;
	}

	
	List<DictionaryEntry> dictionaryEntryList = null;

	/**
	 * Handles the add dictionary item page
	 * 
	 * @return Return to the adddictionaryitems JSP
	 */
	@RequestMapping(value = "auth/dictionaries/addDictionaryItems/{dictionaryid}", method = RequestMethod.GET)
	public String addDictionaryItemPage(
			@PathVariable("dictionaryid") String dictionaryid, ModelMap model) {

		model.addAttribute("dictionaryid", dictionaryid);
		return "auth/dictionaries/addDictionaryItems";
	}

	/**
	 * Handles the form tag for add dictionary item to dictionary
	 * 
	 * @return Return to list dictionary item page
	 * @throws QuadrigaUIAccessException 
	 */

	@RequestMapping(value = "auth/dictionaries/addDictionaryItems/{dictionaryid}", method = RequestMethod.POST)
	public String addDictionaryItem(HttpServletRequest req,
			@PathVariable("dictionaryid") String dictionaryId,
			@ModelAttribute("SpringWeb") Item dictionaryItems,
			ModelMap model, Principal principal)
			throws QuadrigaStorageException, QuadrigaAccessException {
		IUser user = usermanager.getUserDetails(principal.getName());
		boolean result=dictonaryManager.userDictionaryPerm(user.getUserName(),dictionaryId);
		logger.info("Came here "+ result);
		String msg = "";
		String[] values = req.getParameterValues("selected");
		if (values != null) {
			dictonaryManager.addDictionaryItems(dictionaryItems, values, dictionaryId);
		}else{
			model.addAttribute("additemsuccess", 2);
			
			//TODO: getDictionaryItems() should return IDictionaryItems
			List<IDictionaryItems> dictionaryItemList = dictonaryManager
					.getDictionariesItems(dictionaryId,user.getUserName());
			String dictionaryName = dictonaryManager
					.getDictionaryName(dictionaryId);
			model.addAttribute("dictionaryItemList", dictionaryItemList);
			model.addAttribute("dictName", dictionaryName);
			model.addAttribute("dictID", dictionaryId);

			return "auth/dictionary/dictionary";
		}

		if (msg.equals("")) {
			model.addAttribute("additemsuccess", 1);			
		} else {
			if (msg.equals("ItemExists")) {
				model.addAttribute("additemsuccess", 0);
				model.addAttribute("errormsg",
						"Items already exist for dictionary id :"
								+ dictionaryId);
			} else {
				model.addAttribute("additemssuccess", 0);
				model.addAttribute("errormsg", msg);
			}
		}
		List<IDictionaryItems> dictionaryItemList = dictonaryManager
				.getDictionariesItems(dictionaryId,user.getUserName());
		String dictionaryName = dictonaryManager
				.getDictionaryName(dictionaryId);
		model.addAttribute("dictionaryItemList", dictionaryItemList);
		model.addAttribute("dictName", dictionaryName);
		model.addAttribute("dictID", dictionaryId);
		JSONObject core = new JSONObject();
		model.addAttribute("core", core.toString());
		return "auth/dictionary/dictionary";
	}

	/**
	 * Admin can use this to search from term and pos from word power
	 * 
	 * @return Return to list dictionary item page
	 * @throws QuadrigaStorageException
	 * @throws QuadrigaUIAccessException 
	 */

	@RequestMapping(value = "auth/dictionaries/dictionary/wordSearch/{dictionaryid}", method = RequestMethod.POST)
	public String searchDictionaryItemRestHandle(
			@PathVariable("dictionaryid") String dictionaryid,
			@RequestParam("itemName") String item,
			@RequestParam("posdropdown") String pos,Principal principal, ModelMap model)
			throws QuadrigaStorageException, QuadrigaAccessException {
		IUser user = usermanager.getUserDetails(principal.getName());
		boolean result=dictonaryManager.userDictionaryPerm(user.getUserName(),dictionaryid);
		try {
			List<DictionaryEntry> dictionaryEntryList = null;
			if (!item.equals("")) {
				logger.debug("Query for Item :" + item + " and pos :" + pos);
				dictionaryEntryList = dictonaryManager.searchWordPower(item,
						pos);
			}
			model.addAttribute("status", 1);
			model.addAttribute("dictionaryEntryList", dictionaryEntryList);
			String dictionaryName = dictonaryManager
					.getDictionaryName(dictionaryid);
			model.addAttribute("dictName", dictionaryName);
			model.addAttribute("dictionaryid", dictionaryid);
			if (dictionaryEntryList == null ||dictionaryEntryList.size()==0 ) {
				model.addAttribute("errorstatus", 1);
			}else{
				logger.info("dict size returned : "+dictionaryEntryList.size());
				for(DictionaryEntry de : dictionaryEntryList){
					logger.info(de.getLemma());
				}
			}
			

		} catch (QuadrigaStorageException e) {
			throw new QuadrigaStorageException(
					"Oops the DB is an hard hangover, please try later");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		// return "auth/dictionaries/dictionary/wordSearch";
		// return "auth/dictionary/dictionary";
		return "auth/dictionaries/addDictionaryItems";
	}

}
