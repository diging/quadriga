package edu.asu.spring.quadriga.web.dictionary;

import java.security.Principal;
import java.util.Arrays;
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

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.factory.dictionary.IDictionaryFactory;
import edu.asu.spring.quadriga.domain.impl.dictionary.Dictionary;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryManager;
import edu.asu.spring.quadriga.service.impl.dictionary.DictionaryManager;

/**
 * This class will handle list dictionaries controller for the dictionary
 * 
 * @author : Lohith Dwaraka
 * 
 */
@Controller
public class DictionaryListController {
	@Autowired
	IDictionaryManager dictionaryManager;

	@Autowired
	IQuadrigaRoleManager collabRoleManager;
	
	
	private static final Logger logger = LoggerFactory
			.getLogger(DictionaryListController.class);

	@Autowired
	private IUserManager usermanager;

	public IDictionaryManager getDictonaryManager() {
		return dictionaryManager;
	}

	public void setDictonaryManager(IDictionaryManager dictonaryManager) {
		this.dictionaryManager = dictonaryManager;
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
				dictionaryList = dictionaryManager.getDictionariesList(userId);
				
				dictionaryCollabList=dictionaryManager.getDictionaryCollabOfUser(userId);
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
		IUser user = usermanager.getUser(principal.getName());
		dictionary.setOwner(user);

		String msg = "";
		try {
			dictionaryManager.addNewDictionary(dictionary);
		} catch (QuadrigaStorageException e1) {
			msg = "DB Error";
			logger.error("Issue while adding dictionary",e1);
		}
		if (msg.equals("")) {
			model.addAttribute("adddicsuccess", 1);
			List<IDictionary> dictionaryList = null;
			dictionaryList = dictionaryManager.getDictionariesList(user
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

	/**
	 * This method retrieves the dictionaries associated with
	 * the logged in user for deletion
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "auth/dictionaries/deleteDictionary", method = RequestMethod.GET)
	public String deleteDictionaryGet(Model model) {
		try {
			UserDetails user = (UserDetails) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			String userId = user.getUsername();
			List<IDictionary> dictionaryList = null;
			try {
				
				dictionaryList = dictionaryManager.getDictionariesList(user
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

	/**
	 * This method deletes the selected dictionaries.
	 * @param req
	 * @param model
	 * @param principal
	 * @return
	 * @throws QuadrigaStorageException
	 */
	@RequestMapping(value = "auth/dictionaries/deleteDictionary", method = RequestMethod.POST)
	public String deleteDictionary(HttpServletRequest req, ModelMap model,
			Principal principal) throws QuadrigaStorageException {
		IUser user = usermanager.getUser(principal.getName());

		List<String> dictionariesToDelete = Arrays.asList(req.getParameterValues("selected"));
		
		if (dictionariesToDelete == null || dictionariesToDelete.isEmpty()) {
			model.addAttribute("selection", 0);
			List<IDictionary> dictionaryList = null;
			
			dictionaryList = dictionaryManager.getDictionariesList(user
						.getUserName());
			
			model.addAttribute("dictinarylist", dictionaryList);
			model.addAttribute("userId", user.getUserName());
			return "auth/dictionaries/deleteDictionary";
		}
		
		
		StringBuffer errorMsg = new StringBuffer();
		StringBuffer successMsg = new StringBuffer();
		for (String dictId : dictionariesToDelete) {
		    String owner = dictionaryManager.getDictionaryOwner(dictId);
		    if (!owner.equals(principal.getName())) {
		        model.addAttribute("show_error_alert", true);
                errorMsg.append("You are not the owner of dictionary \"" + dictionaryManager.getDictionaryName(dictId) +"\" and can't delete it.");
                errorMsg.append("\n");
                continue;
		    }
	        
			try {
			    String dictName = dictionaryManager.getDictionaryName(dictId);
			    dictionaryManager.deleteDictionary(dictId);
			    model.addAttribute("show_success_alert", true);
			    successMsg.append("Dictionary \"" + dictName + "\" successfully deleted.");
			    successMsg.append("\n");
			} catch (QuadrigaStorageException ex) {
			    model.addAttribute("show_error_alert", true);
			    errorMsg.append(ex.getMessage());
			    errorMsg.append("\n");
			}
			
		}
		model.addAttribute("success_alert_msg", successMsg.toString());
		model.addAttribute("error_alert_msg", errorMsg.toString());
		
		List<IDictionary> dictionaryList = null;
		dictionaryList = dictionaryManager.getDictionariesList(user
					.getUserName());
		
		model.addAttribute("dictinarylist", dictionaryList);
		model.addAttribute("userId", user.getUserName());
		
		return "auth/dictionaries";
	}
}
