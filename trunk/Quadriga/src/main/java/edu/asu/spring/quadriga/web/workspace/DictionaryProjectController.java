package edu.asu.spring.quadriga.web.workspace;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.db.IDBConnectionDictionaryManager;
import edu.asu.spring.quadriga.db.workspace.IDBConnectionProjectDictionary;
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IDictionaryManager;
import edu.asu.spring.quadriga.service.workbench.IProjectDictionaryManager;
import edu.asu.spring.quadriga.web.dictionary.DictionaryListController;

@Controller
public class DictionaryProjectController {

	@Autowired
	IDictionaryManager dictonaryManager;

	@Autowired
	private IProjectDictionaryManager projectDictionaryManager;

	private static final Logger logger = LoggerFactory
			.getLogger(DictionaryProjectController.class);

	@RequestMapping(value = "auth/workbench/{projectid}/adddictionary", method = RequestMethod.GET)
	public String addProjectDictionary(
			@PathVariable("projectid") String projectid, Model model) {
		try {
			UserDetails user = (UserDetails) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			String userId = user.getUsername();
			logger.info("USer : " + user.getUsername()
					+ " trying to add dictionary into workspace");
			List<IDictionary> dictionaryList = null;
			try {
				dictionaryList = dictonaryManager.getDictionariesList(user
						.getUsername());
			} catch (QuadrigaStorageException e) {
				throw new QuadrigaStorageException(
						"Oops the DB is an hard hangover, please try later");
			}
			if (dictionaryList == null) {
				logger.info("Dictionary list is empty");
			}
			model.addAttribute("dictinarylist", dictionaryList);
			model.addAttribute("projectid", projectid);
			model.addAttribute("userId", userId);
		} catch (Exception e) {
			logger.error(" ----" + e.getMessage());
		}
		return "auth/workbench/workspace/adddictionaries";
	}

	@RequestMapping(value = "auth/workbench/{projectid}/adddictionaries", method = RequestMethod.POST)
	public String addProjectDictionary(HttpServletRequest req,
			@PathVariable("projectid") String projectid, Model model) {
		String msg = "";
		UserDetails user = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String userId = user.getUsername();

		String[] values = req.getParameterValues("selected");
		if (values == null) {

		} else {
			for (int i = 0; i < values.length; i++) {
				logger.info("values " + values[i]);
				try {
					projectDictionaryManager.addProjectDictionary(projectid,
							values[i], userId);
				} catch (QuadrigaStorageException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		List<IDictionary> dicitonaryList = null;
		try {
			dicitonaryList = projectDictionaryManager.listProjectDictionary(
					projectid, userId);
		} catch (QuadrigaStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(dicitonaryList == null){
			logger.info("Dictionar list is empty buddy");
		}
		model.addAttribute("dicitonaryList", dicitonaryList);
		model.addAttribute("projectid", projectid);
		return "auth/workbench/workspace/dictionaries";
	}

	@RequestMapping(value = "auth/workbench/{projectid}/dictionaries", method = RequestMethod.GET)
	public String listProjectDictionary(HttpServletRequest req,@PathVariable("projectid") String projectid, Model model) {
		UserDetails user = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String userId = user.getUsername();
		List<IDictionary> dicitonaryList = null;
		try {
			dicitonaryList = projectDictionaryManager.listProjectDictionary(
					projectid, userId);
		} catch (QuadrigaStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(dicitonaryList == null){
			logger.info("Dictionar list is empty buddy");
		}
		model.addAttribute("dicitonaryList", dicitonaryList);
		model.addAttribute("projectid", projectid);
		return "auth/workbench/workspace/dictionaries";
	}
}
