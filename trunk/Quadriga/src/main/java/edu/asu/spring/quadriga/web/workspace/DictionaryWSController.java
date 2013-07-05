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
import edu.asu.spring.quadriga.db.workspace.IDBConnectionWSDictionary;
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IDictionaryManager;
import edu.asu.spring.quadriga.web.dictionary.DictionaryListController;

@Controller
public class DictionaryWSController {

	@Autowired
	IDictionaryManager dictonaryManager;
	
	@Autowired
	@Qualifier("DBConnectionWSDictionary")
	private IDBConnectionWSDictionary dbConnect;
	
	private static final Logger logger = LoggerFactory
			.getLogger(DictionaryWSController.class);
	
	@RequestMapping(value = "auth/workbench/{projectid}/adddictionary", method = RequestMethod.GET)
	public String addWSDictionary(@PathVariable("projectid") String projectid , Model model) {
		try {
			UserDetails user = (UserDetails) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			String userId = user.getUsername();
			logger.info("USer : "+user.getUsername()+" trying to add dictionary into workspace");
			List<IDictionary> dictionaryList = null;
			try {
				dictionaryList = dictonaryManager.getDictionariesList(user
						.getUsername());
			} catch (QuadrigaStorageException e) {
				throw new QuadrigaStorageException(
						"Oops the DB is an hard hangover, please try later");
			}
			if(dictionaryList == null){
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
	public String listWSDictionary(HttpServletRequest req,@PathVariable("projectid") String projectid , Model model) {
		try {
			UserDetails user = (UserDetails) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			String userId = user.getUsername();
			String[] values = req.getParameterValues("selected");
			if(values == null){
			
			}else{
				for (int i = 0; i < values.length; i++) {
					logger.info("values " + values[i]);
					dbConnect.addWSDictionary(projectid, values[i], userId);
				}
			}
		} catch (Exception e) {
			logger.error(" ----" + e.getMessage());
		}
		return "auth/workbench/workspace/adddictionaries";
	}
}
