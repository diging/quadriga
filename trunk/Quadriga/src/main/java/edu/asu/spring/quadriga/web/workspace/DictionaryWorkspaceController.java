package edu.asu.spring.quadriga.web.workspace;

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
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IDictionaryManager;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceDictionaryManager;

@Controller
public class DictionaryWorkspaceController {

	@Autowired
	IDictionaryManager dictonaryManager;

	@Autowired
	IListWSManager wsManager;
	
	@Autowired
	private IWorkspaceDictionaryManager workspaceDictionaryManager;

	private static final Logger logger = LoggerFactory
			.getLogger(DictionaryWorkspaceController.class);

	@RequestMapping(value = "auth/workbench/workspace/{workspaceid}/adddictionary", method = RequestMethod.GET)
	public String addProjectDictionary(
			@PathVariable("workspaceid") String workspaceId, Model model) {
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
			model.addAttribute("workspaceId", workspaceId);
			IWorkSpace workspace = wsManager.getWorkspaceDetails(workspaceId,userId);
			model.addAttribute("workspacedetails", workspace);
			model.addAttribute("userId", userId);
		} catch (Exception e) {
			logger.error(" ----" + e.getMessage());
		}
		return "auth/workbench/workspace/adddictionaries";
	}

	@RequestMapping(value = "auth/workbench/workspace/{workspaceid}/adddictionaries", method = RequestMethod.POST)
	public String addProjectDictionary(HttpServletRequest req,
			@PathVariable("workspaceid") String workspaceId, Model model) throws QuadrigaStorageException, QuadrigaAccessException {
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
				dicitonaryList = workspaceDictionaryManager.listWorkspaceDictionary(
						workspaceId, userId);
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(dicitonaryList == null){
				logger.info("Dictionary list is empty buddy");
			}
			model.addAttribute("dicitonaryList", dicitonaryList);
			IWorkSpace workspace = wsManager.getWorkspaceDetails(workspaceId,userId);
			model.addAttribute("workspacedetails", workspace);
			model.addAttribute("workspaceId", workspaceId);
			return "auth/workbench/workspace/dictionaries";
		} else {
			for (int i = 0; i < values.length; i++) {
				logger.info("values " + values[i]);
				try {
					msg=workspaceDictionaryManager.addWorkspaceDictionary(workspaceId,
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
			dicitonaryList = workspaceDictionaryManager.listWorkspaceDictionary(
					workspaceId, userId);
		} catch (QuadrigaStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(dicitonaryList == null){
			logger.info("Dictionar list is empty buddy");
		}
		model.addAttribute("dicitonaryList", dicitonaryList);
		model.addAttribute("workspaceId", workspaceId);
		return "auth/workbench/workspace/dictionaries";
	}

	@RequestMapping(value = "auth/workbench/workspace/{workspaceid}/dictionaries", method = RequestMethod.GET)
	public String listWorkspaceDictionary(HttpServletRequest req,@PathVariable("workspaceid") String workspaceId, Model model) throws QuadrigaStorageException, QuadrigaAccessException {
		UserDetails user = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String userId = user.getUsername();
		
		List<IDictionary> dicitonaryList = null;
		try {
			dicitonaryList = workspaceDictionaryManager.listWorkspaceDictionary(workspaceId, userId);
		} catch (QuadrigaStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(dicitonaryList == null){
			logger.info("Dictionar list is empty buddy");
		}
		model.addAttribute("dicitonaryList", dicitonaryList);
		IWorkSpace workspace = wsManager.getWorkspaceDetails(workspaceId,userId);
		model.addAttribute("workspacedetails", workspace);
		model.addAttribute("workspaceId", workspaceId);
		return "auth/workbench/workspace/dictionaries";
	}
	
	@RequestMapping(value = "auth/workbench/workspace/{workspaceid}/deletedictionary", method = RequestMethod.GET)
	public String deleteProjectDictionary(@PathVariable("workspaceid") String workspaceId, Model model) throws QuadrigaStorageException, QuadrigaAccessException {
		UserDetails user = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String userId = user.getUsername();
		
		List<IDictionary> dicitonaryList = null;
		try {
			dicitonaryList = workspaceDictionaryManager.listWorkspaceDictionary(workspaceId, userId);
		} catch (QuadrigaStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(dicitonaryList == null){
			logger.info("Dictionar list is empty buddy");
		}
		model.addAttribute("dicitonaryList", dicitonaryList);
		IWorkSpace workspace = wsManager.getWorkspaceDetails(workspaceId,userId);
		model.addAttribute("workspacedetails", workspace);
		model.addAttribute("workspaceId", workspaceId);
		return "auth/workbench/workspace/deletedictionaries";
	}
	
	@RequestMapping(value = "auth/workbench/workspace/{workspaceid}/deletedictionaries", method = RequestMethod.POST)
	public String deleteWorkspaceDictionary(HttpServletRequest req,@PathVariable("workspaceid") String workspaceId, Model model) throws QuadrigaStorageException, QuadrigaAccessException {
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
				dicitonaryList = workspaceDictionaryManager.listWorkspaceDictionary(
						workspaceId, userId);
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(dicitonaryList == null){
				logger.info("Dictionary list is empty buddy");
			}
			model.addAttribute("dicitonaryList", dicitonaryList);
			IWorkSpace workspace = wsManager.getWorkspaceDetails(workspaceId,userId);
			model.addAttribute("workspacedetails", workspace);
			model.addAttribute("workspaceId", workspaceId);
			return "auth/workbench/workspace/dictionaries";
		} else {
			for (int i = 0; i < values.length; i++) {
				try {
					msg=workspaceDictionaryManager.deleteWorkspaceDictionary(workspaceId, userId, values[i]);
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
			dicitonaryList = workspaceDictionaryManager.listWorkspaceDictionary(
					workspaceId, userId);
		} catch (QuadrigaStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(dicitonaryList == null){
			logger.info("Dictionary list is empty buddy");
		}
		model.addAttribute("dicitonaryList", dicitonaryList);
		IWorkSpace workspace = wsManager.getWorkspaceDetails(workspaceId,userId);
		model.addAttribute("workspacedetails", workspace);
		model.addAttribute("workspaceId", workspaceId);
		return "auth/workbench/workspace/dictionaries";
	}
}
