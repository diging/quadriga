package edu.asu.spring.quadriga.web.dictionary;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IDictionaryFactory;
import edu.asu.spring.quadriga.domain.implementation.Dictionary;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IDictionaryManager;
import edu.asu.spring.quadriga.service.IUserManager;

/**
 * This class will handle list dictionaries controller for the dictionary
 * 
 * @author : Lohith Dwaraka
 * 
 */
@Controller
public class DictionaryListController {
	@Autowired
	IDictionaryManager dictonaryManager;

	private static final Logger logger = LoggerFactory
			.getLogger(DictionaryListController.class);

	@Autowired
	IUserManager usermanager;

	public IUserManager getUsermanager() {
		return usermanager;
	}

	public void setUsermanager(IUserManager usermanager) {
		this.usermanager = usermanager;
	}

	@Autowired
	IDictionaryFactory dictionaryFactory;

	/**
	 * Admin can use this page to check the list of dictionary he is accessible
	 * to
	 * 
	 * @return Return to the dictionary home page of the Quadriga
	 */

	@RequestMapping(value = "auth/dictionaries", method = RequestMethod.GET)
	public String listDictionary(ModelMap model) {
		try {
			UserDetails user = (UserDetails) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			String userId = user.getUsername();
			List<IDictionary> dictionaryList = null;
			try {
				dictionaryList = dictonaryManager.getDictionariesList(user
						.getUsername());
			} catch (QuadrigaStorageException e) {
				throw new QuadrigaStorageException(
						"Oops the DB is an hard hangover, please try later");
			}
			model.addAttribute("dictinarylist", dictionaryList);
			model.addAttribute("userId", userId);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "auth/dictionaries";
	}

	/**
	 * Handles the bean mapping from form tag
	 * 
	 * 
	 * @return Used to handle the data from the form:form tag and map it to
	 *         Dicitonary object
	 */

	@RequestMapping(value = "auth/dictionaries/addDictionary", method = RequestMethod.GET)
	public String addDictionaryForm(Model m) {

		m.addAttribute("dictionary", dictionaryFactory.createDictionaryObject());
		return "auth/dictionaries/addDictionary";
	}

	/**
	 * Admin can use this page to new dictionary to his list
	 * 
	 * @return Return to the add dictionary status page
	 * @throws QuadrigaStorageException
	 */

	@RequestMapping(value = "auth/dictionaries/addDictionary", method = RequestMethod.POST)
	public String addDictionaryHandle(
			@ModelAttribute("SpringWeb") Dictionary dictionary, ModelMap model,
			Principal principal) throws QuadrigaStorageException {
		IUser user = usermanager.getUserDetails(principal.getName());
		dictionary.setOwner(user);

		String msg = "";
		try {
			msg = dictonaryManager.addNewDictionary(dictionary);
		} catch (QuadrigaStorageException e1) {
			msg = "DB Error";
			e1.printStackTrace();
		}
		if (msg.equals("")) {
			model.addAttribute("adddicsuccess", 1);
			model.addAttribute("adddicsuccessMsg",
					"Dictionary created successfully.");
			List<IDictionary> dictionaryList = null;
			dictionaryList = dictonaryManager.getDictionariesList(user
					.getUserName());
			model.addAttribute("dictinarylist", dictionaryList);
			model.addAttribute("userId", user.getUserName());
			return "auth/dictionaries";
		} else {
			model.addAttribute("dictionary", dictionary);
			model.addAttribute("success", 0);
			model.addAttribute("errormsg", msg);
			return "auth/dictionaries/addDictionary";
		}
	}

}
