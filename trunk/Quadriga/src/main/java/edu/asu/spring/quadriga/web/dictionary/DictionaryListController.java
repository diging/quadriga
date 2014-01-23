package edu.asu.spring.quadriga.web.dictionary;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryManager;

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

	@Autowired
	ICollaboratorRoleManager collabRoleManager;
	
	
	private static final Logger logger = LoggerFactory
			.getLogger(DictionaryListController.class);

	@Autowired
	IUserManager usermanager;

	public ICollaboratorRoleManager getCollabRoleManager() {
		return collabRoleManager;
	}

	public void setCollabRoleManager(ICollaboratorRoleManager collabRoleManager) {
		this.collabRoleManager = collabRoleManager;
	}
	
	public IDictionaryManager getDictonaryManager() {
		return dictonaryManager;
	}

	public void setDictonaryManager(IDictionaryManager dictonaryManager) {
		this.dictonaryManager = dictonaryManager;
	}
	
	public IUserManager getUsermanager() {
		return usermanager;
	}

	public void setUsermanager(IUserManager usermanager) {
		this.usermanager = usermanager;
	}
	
	public IDictionaryFactory getDictionaryFactory() {
		return dictionaryFactory;
	}

	public void setDictionaryFactory(IDictionaryFactory dictionaryFactory) {
		this.dictionaryFactory = dictionaryFactory;
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
	public String listDictionary(ModelMap model, Principal principal) {
		try {
			String userId= principal.getName();
			List<IDictionary> dictionaryList = null;
			List<IDictionary> dictionaryCollabList = null;
			logger.debug("Username "+userId);
			try {
				dictionaryList = dictonaryManager.getDictionariesList(userId);
				dictionaryCollabList=dictonaryManager.getDictionaryCollabOfUser(userId);
			} catch (QuadrigaStorageException e) {
				logger.error("2Stack trace", e);
				throw new QuadrigaStorageException(
						"Oops the DB is an hard hangover, please try later");
			}catch(Exception e){
				logger.error("Stack trace", e);
			}
			model.addAttribute("dictinarylist", dictionaryList);
			model.addAttribute("dictionaryCollabList", dictionaryCollabList);
			model.addAttribute("userId", userId);
		} catch (Exception e) {
			logger.error("1Stack trace", e);
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
			dictonaryManager.addNewDictionary(dictionary);
		} catch (QuadrigaStorageException e1) {
			msg = "DB Error";
			e1.printStackTrace();
		}
		if (msg.equals("")) {
			model.addAttribute("adddicsuccess", 1);
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

	@RequestMapping(value = "auth/dictionaries/deleteDictionary", method = RequestMethod.GET)
	public String deleteDictionaryGet(Model model) {
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
			logger.error(" ----" + e.getMessage());
		}
		return "auth/dictionaries/deleteDictionary";
	}

	@RequestMapping(value = "auth/dictionaries/deleteDictionary", method = RequestMethod.POST)
	public String deleteDictionary(HttpServletRequest req, ModelMap model,
			Principal principal) throws QuadrigaStorageException {
		IUser user = usermanager.getUserDetails(principal.getName());

		String[] values = req.getParameterValues("selected");
		String msg = "";
		String errormsg = "";
		int flag = 0;

		if (values == null) {
			model.addAttribute("selection", 0);
			List<IDictionary> dictionaryList = null;
			logger.info("value null");
			try {
				dictionaryList = dictonaryManager.getDictionariesList(user
						.getUserName());
			} catch (QuadrigaStorageException e) {
				throw new QuadrigaStorageException(
						"Oops the DB is an hard hangover, please try later");
			}
			model.addAttribute("dictinarylist", dictionaryList);
			model.addAttribute("userId", user.getUserName());
			return "auth/dictionaries/deleteDictionary";
		} else {
			for (int i = 0; i < values.length; i++) {
				try {
					logger.info("Deleting dictionary id : " + values[i]);
					dictonaryManager.deleteDictionary(
							user.getUserName(), values[i]);
					if (!msg.equals("")) {
						flag = 1;
						errormsg = msg;
					} 
				} catch (QuadrigaStorageException e) {
					throw new QuadrigaStorageException(
							"Oops the DB is an hard hangover, please try later");
				}
			}
		}
		if (flag == 0) {
			model.addAttribute("deldicitonarysuccess", 1);
		} else if (flag == 1) {
			if (errormsg.equals("User don't have access to this dictionary")) {
				model.addAttribute("dictionaryaccesserror", 0);
			} else {
				model.addAttribute("deldicitonarysuccess", 0);
			}
		}
		List<IDictionary> dictionaryList = null;
		try {
			dictionaryList = dictonaryManager.getDictionariesList(user
					.getUserName());
		} catch (QuadrigaStorageException e) {
			throw new QuadrigaStorageException(
					"Oops the DB is an hard hangover, please try later");
		}
		model.addAttribute("dictinarylist", dictionaryList);
		model.addAttribute("userId", user.getUserName());
		return "auth/dictionaries";

	}
}
