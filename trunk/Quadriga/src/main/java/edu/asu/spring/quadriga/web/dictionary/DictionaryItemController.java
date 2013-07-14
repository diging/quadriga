package edu.asu.spring.quadriga.web.dictionary;

import java.security.Principal;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.IDictionaryItems;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.implementation.WordpowerReply.DictionaryEntry;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
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
	 * @throws QuadrigaAccessException 
	 */

	@RequestMapping(value = "auth/dictionaries/{dictionaryid}", method = RequestMethod.GET)
	public String getDictionaryPage(
			@PathVariable("dictionaryid") String dictionaryid, ModelMap model)
			throws QuadrigaStorageException, QuadrigaAccessException {
		UserDetails user = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		boolean result=dictonaryManager.userDictionaryPerm(user.getUsername(),dictionaryid);

		logger.info("User permission on this dicitonary : "+result);
		logger.info("came to getDictionaryPage");
		List<IDictionaryItems> dictionaryItemList = dictonaryManager
				.getDictionariesItems(dictionaryid,user.getUsername());
		if (dictionaryItemList == null) {
			logger.info("Dictionary ITem list is null");
		}
		String dictionaryName = "";
		dictionaryName = dictonaryManager.getDictionaryName(dictionaryid);

		model.addAttribute("dictionaryItemList", dictionaryItemList);
		model.addAttribute("dictName", dictionaryName);
		model.addAttribute("dictionaryid", dictionaryid);
		
		List<IUser> existingCollaborators = dictonaryManager.getCollaborators(dictionaryid);
		model.addAttribute("existingCollaborators", existingCollaborators);

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

		UserDetails user = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String[] values = req.getParameterValues("selected");
		String msg = "";
		String errormsg = "";
		int flag = 0;

		if(values == null){
			model.addAttribute("delsuccess", 0);
//			model.addAttribute("delerrormsg", "Items were not selected");
			List<IDictionaryItems> dictionaryItemList = dictonaryManager
					.getDictionariesItems(dictionaryId,user.getUsername());
			String dictionaryName = dictonaryManager
					.getDictionaryName(dictionaryId);
			model.addAttribute("dictionaryItemList", dictionaryItemList);
			model.addAttribute("dictName", dictionaryName);
			model.addAttribute("dictID", dictionaryId);
			return "auth/dictionary/dictionary";
		}else {
			for (int i = 0; i < values.length; i++) {
				logger.info("Deleting item for dictionary id: " + dictionaryId
						+ " and term id : " + i + " : " + values[i]);
				msg = dictonaryManager.deleteDictionariesItems(dictionaryId,
						values[i],user.getUsername());
				if (!msg.equals("")) {
					flag = 1;
					errormsg = msg;
				}
			}
		} 
		if (flag == 0) {
			model.addAttribute("delsuccess", 1);
//			model.addAttribute("delsuccessmsg", "Items  deleted successfully");
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
		}
		logger.info("Item Returned ");
		List<IDictionaryItems> dictionaryItemList = dictonaryManager
				.getDictionariesItems(dictionaryId,user.getUsername());
		String dictionaryName = dictonaryManager
				.getDictionaryName(dictionaryId);
		model.addAttribute("dictionaryItemList", dictionaryItemList);
		model.addAttribute("dictName", dictionaryName);
		model.addAttribute("dictID", dictionaryId);

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

		UserDetails user = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String[] values = req.getParameterValues("selected");
		String msg = "";
		String errormsg = "";
		int flag = 0;
		if(values == null){
			model.addAttribute("updatesuccess", 0);
//			model.addAttribute("updateerrormsg", "Items were not selected");
			List<IDictionaryItems> dictionaryItemList = dictonaryManager
					.getDictionariesItems(dictionaryId,user.getUsername());
			String dictionaryName = dictonaryManager
					.getDictionaryName(dictionaryId);
			model.addAttribute("dictionaryItemList", dictionaryItemList);
			model.addAttribute("dictName", dictionaryName);
			model.addAttribute("dictID", dictionaryId);
			return "auth/dictionary/dictionary";
		}else{
			for (int i = 0; i < values.length; i++) {
				
				List<DictionaryEntry> dictionaryEntryList = dictonaryManager
						.getUpdateFromWordPower(dictionaryId, values[i]);
				Iterator<DictionaryEntry> I = dictionaryEntryList.iterator();
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
				if (!msg.equals("")) {
					flag = 1;
					errormsg = msg;
				}
			}
		} 

		if (flag == 0) {
			// these things don't need to be logged.
			logger.debug("Successfully updated");
			model.addAttribute("updatesuccess", 1);
			model.addAttribute("updatesuccessmsg", "Items updated successfully");
		} else if (flag == 1) {
			logger.info("Please check errormsg : "+ errormsg);
			if (errormsg.equals("Item doesnot exists in this dictionary")) {
				model.addAttribute("updatesuccess", 0);
				model.addAttribute("updateerrormsg",
						"Items doesn't exist for dictionary id :"
								+ dictionaryId);
			} else {
				model.addAttribute("updatesuccess", 0);
				model.addAttribute("updateerrormsg", errormsg);
			}
		}
		logger.debug("Item Returned ");
		List<IDictionaryItems> dictionaryItemList = dictonaryManager
				.getDictionariesItems(dictionaryId,user.getUsername());
		String dictionaryName = dictonaryManager
				.getDictionaryName(dictionaryId);
		model.addAttribute("dictionaryItemList", dictionaryItemList);
		model.addAttribute("dictName", dictionaryName);
		model.addAttribute("dictID", dictionaryId);

		return "auth/dictionary/dictionary";
	}

}
