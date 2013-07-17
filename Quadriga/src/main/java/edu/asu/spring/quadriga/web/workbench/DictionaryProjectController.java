package edu.asu.spring.quadriga.web.workbench;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IDictionaryManager;
import edu.asu.spring.quadriga.service.workbench.IProjectDictionaryManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

@Controller
public class DictionaryProjectController {

	@Autowired 
	IRetrieveProjectManager projectManager;
	
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
			IProject project = projectManager.getProjectDetails(projectid);
			model.addAttribute("project", project);
			model.addAttribute("projectid", projectid);
			model.addAttribute("userId", userId);
		} catch (Exception e) {
			logger.error(" ----" + e.getMessage());
		}
		return "auth/workbench/project/adddictionaries";
	}

	@RequestMapping(value = "auth/workbench/{projectid}/adddictionaries", method = RequestMethod.POST)
	public String addProjectDictionary(HttpServletRequest req,
			@PathVariable("projectid") String projectid, Model model) throws QuadrigaStorageException {
		String msg = "";
		int flag=0;
		UserDetails user = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String userId = user.getUsername();

		String[] values = req.getParameterValues("selected");
		if (values == null) {
			model.addAttribute("deletesuccess", 0);
			List<IDictionary> dicitonaryList = null;
			try {
				dicitonaryList = projectDictionaryManager.listProjectDictionary(
						projectid, userId);
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(dicitonaryList == null){
				logger.info("Dictionary list is empty buddy");
			}
			model.addAttribute("dicitonaryList", dicitonaryList);
			IProject project = projectManager.getProjectDetails(projectid);
			model.addAttribute("project", project);
			model.addAttribute("projectid", projectid);
			return "auth/workbench/workspace/dictionaries";
		} else {
			for (int i = 0; i < values.length; i++) {
				logger.info("values " + values[i]);
				try {
					msg=projectDictionaryManager.addProjectDictionary(projectid,
							values[i], userId);
					if(!msg.equals("")){
						flag=1;
					}
				} catch (QuadrigaStorageException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if(flag==0){
			model.addAttribute("success", 1);
		}else{
			model.addAttribute("success", 0);
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
		IProject project = projectManager.getProjectDetails(projectid);
		model.addAttribute("project", project);
		model.addAttribute("projectid", projectid);
		return "auth/workbench/project/dictionaries";
	}

	@RequestMapping(value = "auth/workbench/{projectid}/dictionaries", method = RequestMethod.GET)
	public String listProjectDictionary(HttpServletRequest req,@PathVariable("projectid") String projectid, Model model) throws QuadrigaStorageException {
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
		IProject project = projectManager.getProjectDetails(projectid);
		model.addAttribute("project", project);
		model.addAttribute("projectid", projectid);
		return "auth/workbench/project/dictionaries";
	}
	
	@RequestMapping(value = "auth/workbench/{projectid}/deletedictionary", method = RequestMethod.GET)
	public String deleteProjectDictionary(@PathVariable("projectid") String projectid, Model model) throws QuadrigaStorageException {
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
		IProject project = projectManager.getProjectDetails(projectid);
		model.addAttribute("project", project);
		model.addAttribute("projectid", projectid);
		return "auth/workbench/project/deletedictionaries";
	}
	
	@RequestMapping(value = "auth/workbench/{projectid}/deletedictionaries", method = RequestMethod.POST)
	public String deleteProjectDictionary(HttpServletRequest req,@PathVariable("projectid") String projectid, Model model) throws QuadrigaStorageException {
		UserDetails user = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String userId = user.getUsername();
		String msg = "";
		int flag=0;
		
		String[] values = req.getParameterValues("selected");
		if (values == null) {
			model.addAttribute("deletesuccess", 0);
			List<IDictionary> dicitonaryList = null;
			try {
				dicitonaryList = projectDictionaryManager.listProjectDictionary(
						projectid, userId);
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(dicitonaryList == null){
				logger.info("Dictionary list is empty buddy");
			}
			model.addAttribute("dicitonaryList", dicitonaryList);
			IProject project = projectManager.getProjectDetails(projectid);
			model.addAttribute("project", project);
			model.addAttribute("projectid", projectid);
			return "auth/workbench/workspace/dictionaries";
		} else {
			for (int i = 0; i < values.length; i++) {
				try {
					msg=projectDictionaryManager.deleteProjectDictionary(projectid, userId, values[i]);
				} catch (QuadrigaStorageException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(!msg.equals("")){
					flag=1;
				}
			}
		}
		if(flag==0){
			model.addAttribute("deletesuccess", 1);
		}else{
			model.addAttribute("deletesuccess", 0);
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
			logger.info("Dictionary list is empty buddy");
		}
		model.addAttribute("dicitonaryList", dicitonaryList);
		IProject project = projectManager.getProjectDetails(projectid);
		model.addAttribute("project", project);
		model.addAttribute("projectid", projectid);
		return "auth/workbench/project/dictionaries";
	}
}
