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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceDictionary;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceDictionaryManager;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class DictionaryWorkspaceController {

	@Autowired
	private IWorkspaceManager wsManager;
	
	@Autowired
	private IWorkspaceDictionaryManager workspaceDictionaryManager;

	private static final Logger logger = LoggerFactory
			.getLogger(DictionaryWorkspaceController.class);

	/**
	 * Retrieve dictionaries from the system to associate the selected dictionaries
	 * with the given workspace
	 * @param workspaceId
	 * @param model
	 * @return String - URL to redirect on success or failure
	 * @throws QuadrigaStorageException
	 * @throws QuadrigaAccessException
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE,paramIndex = 1, userRole = {RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN } )})
	@RequestMapping(value = "auth/workbench/workspace/{workspaceid}/adddictionary", method = RequestMethod.GET)
	public String addWorkspaceDictionary(
			@PathVariable("workspaceid") String workspaceId, Model model) 
					throws QuadrigaStorageException, QuadrigaAccessException
	{
		try {
			UserDetails user = (UserDetails) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			String userId = user.getUsername();
			logger.info("USer : " + user.getUsername()
					+ " trying to add dictionary into workspace");
			List<IDictionary> dictionaryList = null;
			try {
				dictionaryList = workspaceDictionaryManager.getNonAssociatedWorkspaceDictionaries(
						workspaceId, userId);
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
			logger.error("issue while adding dictionary " ,e);
		}
		return "auth/workbench/workspace/adddictionaries";
	}

	/**
	 * Associates the selected dictionaries with the given workspace
	 * @param req
	 * @param workspaceId
	 * @param model
	 * @return String - URL to redirect on success or failure
	 * @throws QuadrigaStorageException
	 * @throws QuadrigaAccessException
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE,paramIndex = 2, userRole = {RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN} )})
	@RequestMapping(value = "auth/workbench/workspace/{workspaceid}/adddictionaries", method = RequestMethod.POST)
	public String addWorkspaceDictionary(HttpServletRequest req,
			@PathVariable("workspaceid") String workspaceId, Model model, RedirectAttributes attr) throws QuadrigaStorageException, QuadrigaAccessException {
		String msg = "";
		int flag=0;
		UserDetails user = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String userId = user.getUsername();

		String[] values = req.getParameterValues("selected");
		if (values == null)
		{
			attr.addFlashAttribute("show_error_alert", true);
            attr.addFlashAttribute("error_alert_msg", "Please select a Dictionary");
            return "redirect:/auth/workbench/workspace/" + workspaceId + "/adddictionary";
		} else {
			for (int i = 0; i < values.length; i++) {
				logger.info("values " + values[i]);
				try {
					workspaceDictionaryManager.addWorkspaceDictionary(workspaceId,
							values[i], userId);
					if(!msg.equals("")){
						flag=1;
					}
				} catch (QuadrigaStorageException e) {
					logger.error("issue while adding dictionary " ,e);
				}
			}
		}
		if(flag==0){
			model.addAttribute("success", 1);
		}else{
			model.addAttribute("success", 0);
		}
		List<IWorkspaceDictionary> dicitonaryList = null;
		try {
			dicitonaryList = workspaceDictionaryManager.listWorkspaceDictionary(workspaceId, userId);
		} catch (QuadrigaStorageException e) {
			logger.error("issue while adding dictionary " ,e);
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

	/**
	 * Retrieve all the dictionaries associated with the workspace
	 * @param req
	 * @param workspaceId
	 * @param model
	 * @return String - URL to redirect on success or failure
	 * @throws QuadrigaStorageException
	 * @throws QuadrigaAccessException
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE,paramIndex = 2, userRole = {RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN} )})
	@RequestMapping(value = "auth/workbench/workspace/{workspaceid}/dictionaries", method = RequestMethod.GET)
	public String listWorkspaceDictionary(HttpServletRequest req,@PathVariable("workspaceid") String workspaceId, Model model) throws QuadrigaStorageException, QuadrigaAccessException {
		UserDetails user = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String userId = user.getUsername();
		
		List<IWorkspaceDictionary> dicitonaryList = null;
		try {
			dicitonaryList = workspaceDictionaryManager.listWorkspaceDictionary(workspaceId, userId);
		} catch (QuadrigaStorageException e) {
			logger.error("issue while adding dictionary " ,e);
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
	
	/**
	 * Retrieve all the dictionaries associated with workspace for deletion
	 * @param workspaceId
	 * @param model
	 * @return Stirng - URL to redirect on success or failure
	 * @throws QuadrigaStorageException
	 * @throws QuadrigaAccessException
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE,paramIndex = 1, userRole = {RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN} )})
	@RequestMapping(value = "auth/workbench/workspace/{workspaceid}/deletedictionary", method = RequestMethod.GET)
	public String deleteWorkspaceDictionary(@PathVariable("workspaceid") String workspaceId, Model model) throws QuadrigaStorageException, QuadrigaAccessException {
		UserDetails user = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String userId = user.getUsername();
		
		List<IWorkspaceDictionary> dicitonaryList = null;
		try {
			dicitonaryList = workspaceDictionaryManager.listWorkspaceDictionary(workspaceId, userId);
		} catch (QuadrigaStorageException e) {
			logger.error("issue while adding dictionary " ,e);
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
	
	/**
	 * Delete the association of selected dictionaries from the workspace.
	 * @param req
	 * @param workspaceId
	 * @param model
	 * @return String - URL to redirect the page on success or failure
	 * @throws QuadrigaStorageException
	 * @throws QuadrigaAccessException
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE,paramIndex = 2, userRole = {RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN } )})
	@RequestMapping(value = "auth/workbench/workspace/{workspaceid}/deletedictionaries", method = RequestMethod.POST)
	public String deleteWorkspaceDictionary(HttpServletRequest req,@PathVariable("workspaceid") String workspaceId, Model model, RedirectAttributes attr) throws QuadrigaStorageException, QuadrigaAccessException {
		UserDetails user = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String userId = user.getUsername();
		String msg = "";
		int flag=0;
		
		String[] values = req.getParameterValues("selected");
		if (values == null) {
			attr.addFlashAttribute("show_error_alert", true);
            attr.addFlashAttribute("error_alert_msg", "Please select a Dictionary");
            return "redirect:/auth/workbench/workspace/" + workspaceId + "/deletedictionary";
		} else {
			for (int i = 0; i < values.length; i++) {
				try {
					workspaceDictionaryManager.deleteWorkspaceDictionary(workspaceId, userId, values[i]);
				} catch (QuadrigaStorageException e) {
					logger.error("issue while adding dictionary " ,e);
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
		List<IWorkspaceDictionary> dicitonaryList = null;
		try {
			dicitonaryList = workspaceDictionaryManager.listWorkspaceDictionary(
					workspaceId, userId);
		} catch (QuadrigaStorageException e) {
			logger.error("issue while adding dictionary " ,e);
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
