package edu.asu.spring.quadriga.web.workspace;

import java.util.Iterator;
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

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceCCManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class ConceptCollectionWorkspaceController {

	@Autowired
	IConceptCollectionManager conceptCollectionManager;

	@Autowired
	IListWSManager wsManager;
	
	@Autowired
	private IWorkspaceCCManager workspaceCCManager;

	private static final Logger logger = LoggerFactory
			.getLogger(ConceptCollectionWorkspaceController.class);

	/**
	 * Retrieves the concept collections associated with the given workspace
	 * @param workspaceId
	 * @param model
	 * @return String - Url to redirect the page on success or failure
	 * @throws QuadrigaStorageException
	 * @throws QuadrigaAccessException
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE,paramIndex = 1, userRole = {RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN} )})
	@RequestMapping(value = "auth/workbench/workspace/{workspaceid}/conceptcollections", method = RequestMethod.GET)
	public String listProjectConceptCollection(@PathVariable("workspaceid") String workspaceId, Model model) throws QuadrigaStorageException, QuadrigaAccessException {
		UserDetails user = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String userId = user.getUsername();
		logger.info("Concept collection list is empty buddy");
		List<IConceptCollection> conceptCollectionList = null;
		try {
			conceptCollectionList = workspaceCCManager.listWorkspaceCC(workspaceId, userId);
		} catch (QuadrigaStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(conceptCollectionList == null){
			logger.info("Concept collection list is empty buddy");
		}
		Iterator<IConceptCollection> I = conceptCollectionList.iterator(); 
		while(I.hasNext()){
			IConceptCollection con = I.next();
			logger.info(" "+con.getName());
		}
		model.addAttribute("conceptCollectionList", conceptCollectionList);
		IWorkSpace workspace = wsManager.getWorkspaceDetails(workspaceId,userId);
		model.addAttribute("workspacedetails", workspace);
		model.addAttribute("workspaceId", workspaceId);
		return "auth/workbench/workspace/conceptcollections";
	}
	
	/**
	 * Retrieve the concept collections which are not associated to the given workspace
	 * @param workspaceId
	 * @param model
	 * @return String - String - Url to redirect the page on success or failure
	 * @throws QuadrigaStorageException
	 * @throws QuadrigaAccessException
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE,paramIndex = 1, userRole = {RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN} )})
	@RequestMapping(value = "auth/workbench/workspace/{workspaceid}/addconceptcollection", method = RequestMethod.GET)
	public String addWorkspaceConceptCollection(
			@PathVariable("workspaceid") String workspaceId, Model model) 
					throws QuadrigaStorageException, QuadrigaAccessException
	{
		try {
			UserDetails user = (UserDetails) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			String userId = user.getUsername();

			List<IConceptCollection> conceptCollectionList = null;
			try {
				conceptCollectionList = workspaceCCManager.getNonAssociatedWorkspaceConcepts(workspaceId, userId);
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (conceptCollectionList == null) {
				logger.info("conceptCollectionList list is empty");
			}
			Iterator<IConceptCollection> I = conceptCollectionList.iterator(); 
			while(I.hasNext()){
				IConceptCollection con = I.next();
				logger.info(" "+con.getName());
			}
			model.addAttribute("conceptCollectionList", conceptCollectionList);
			IWorkSpace workspace = wsManager.getWorkspaceDetails(workspaceId,userId);
			model.addAttribute("workspacedetails", workspace);
			model.addAttribute("workspaceId", workspaceId);
			model.addAttribute("userId", userId);
		} catch (Exception e) {
			logger.error(" ----" + e.getMessage());
		}
		return "auth/workbench/workspace/addconceptcollections";
	}
	
	/**
	 * Associate the concept collection with the given workspace
	 * @param req
	 * @param workspaceId
	 * @param model
	 * @return String - URL to direct the page on success or failure
	 * @throws QuadrigaStorageException
	 * @throws QuadrigaAccessException
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE,paramIndex = 2, userRole = {RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN} )})
	@RequestMapping(value = "auth/workbench/workspace/{workspaceid}/addconceptcollection", method = RequestMethod.POST)
	public String addWorkspaceConceptCollection(HttpServletRequest req,
			@PathVariable("workspaceid") String workspaceId, Model model) throws QuadrigaStorageException, QuadrigaAccessException {
		String msg = "";
		int flag=0;
		UserDetails user = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String userId = user.getUsername();

		String[] values = req.getParameterValues("selected");
		if (values == null) {
			model.addAttribute("deletesuccess", 0);
			List<IConceptCollection> conceptCollectionList = null;
			try {
				conceptCollectionList = workspaceCCManager.getNonAssociatedWorkspaceConcepts(workspaceId, userId);
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(conceptCollectionList == null){
				logger.info("Concept Collection list is empty buddy");
			}
			model.addAttribute("conceptCollectionList", conceptCollectionList);
			IWorkSpace workspace = wsManager.getWorkspaceDetails(workspaceId,userId);
			model.addAttribute("workspacedetails", workspace);
			model.addAttribute("workspaceId", workspaceId);
			return "auth/workbench/workspace/conceptcollections";
		} else {
			for (int i = 0; i < values.length; i++) {
				logger.info("values " + values[i]);
				try {
					msg=workspaceCCManager.addWorkspaceCC(workspaceId, values[i], userId);
					if(!msg.equals("")){
						flag=1;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error(" ",e);
				}
			}
		}
		if(flag==0){
			model.addAttribute("success", 1);
		}else{
			model.addAttribute("success", 0);
		}
		List<IConceptCollection> conceptCollectionList = null;
		try {
			conceptCollectionList = workspaceCCManager.listWorkspaceCC(workspaceId, userId);
		} catch (QuadrigaStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(conceptCollectionList == null){
			logger.info("conceptCollectionList list is empty buddy");
		}
		model.addAttribute("conceptCollectionList", conceptCollectionList);
		IWorkSpace workspace = wsManager.getWorkspaceDetails(workspaceId,userId);
		model.addAttribute("workspacedetails", workspace);
		model.addAttribute("workspaceId", workspaceId);
		return "auth/workbench/workspace/conceptcollections";
	}


	/**
	 * Retrieve the concept collection associated to the given workspace for deletion
	 * @param workspaceId
	 * @param model
	 * @return String - URL to redirect on success or failure
	 * @throws QuadrigaStorageException
	 * @throws QuadrigaAccessException
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE,paramIndex = 1, userRole = {RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN} )})
	@RequestMapping(value = "auth/workbench/workspace/{workspaceid}/deleteconceptcollections", method = RequestMethod.GET)
	public String deleteWorkspaceConceptCollection(@PathVariable("workspaceid") String workspaceId, Model model) 
			throws QuadrigaStorageException, QuadrigaAccessException {
		UserDetails user = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String userId = user.getUsername();
		
		List<IConceptCollection> conceptCollectionList = null;
		try {
			conceptCollectionList = workspaceCCManager.listWorkspaceCC(workspaceId, userId);
		} catch (QuadrigaStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(conceptCollectionList == null){
			logger.info("conceptCollectionList list is empty buddy");
		}
		model.addAttribute("conceptCollectionList", conceptCollectionList);
		IWorkSpace workspace = wsManager.getWorkspaceDetails(workspaceId,userId);
		model.addAttribute("workspacedetails", workspace);
		model.addAttribute("projectid", workspaceId);
		return "auth/workbench/workspace/deleteconceptcollections";
	}
	
	/**
	 * Deletes the association of the selected concept collections from the workspace
	 * @param req
	 * @param workspaceId
	 * @param model
	 * @return String - URL to redirect on success or failure
	 * @throws QuadrigaStorageException
	 * @throws QuadrigaAccessException
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE,paramIndex = 2, userRole = {RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN} )})
	@RequestMapping(value = "auth/workbench/workspace/{workspaceid}/deleteconceptcollections", method = RequestMethod.POST)
	public String deleteWorkspaceConceptCollection(HttpServletRequest req,@PathVariable("workspaceid") String workspaceId, Model model)
			throws QuadrigaStorageException, QuadrigaAccessException {
		UserDetails user = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String userId = user.getUsername();
		String msg = "";
		int flag=0;
		
		String[] values = req.getParameterValues("selected");
		if (values == null) {
			model.addAttribute("deletesuccess", 0);
			List<IConceptCollection> conceptCollectionList = null;
			try {
				conceptCollectionList = workspaceCCManager.listWorkspaceCC(workspaceId, userId);
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(conceptCollectionList == null){
				logger.info("Concept Collection list is empty buddy");
			}
			model.addAttribute("conceptCollectionList", conceptCollectionList);
			IWorkSpace workspace = wsManager.getWorkspaceDetails(workspaceId,userId);
			model.addAttribute("workspacedetails", workspace);
			model.addAttribute("projectid", workspaceId);
			return "auth/workbench/workspace/conceptcollections";
		} else {
			for (int i = 0; i < values.length; i++) {
				try {
			       workspaceCCManager.deleteWorkspaceCC(workspaceId, userId, values[i]);
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
		List<IConceptCollection> conceptCollectionList = null;
		try {
			conceptCollectionList = workspaceCCManager.listWorkspaceCC(workspaceId, userId);
		} catch (QuadrigaStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(conceptCollectionList == null){
			logger.info("Dictionary list is empty buddy");
		}
		model.addAttribute("conceptCollectionList", conceptCollectionList);
		IWorkSpace workspace = wsManager.getWorkspaceDetails(workspaceId,userId);
		model.addAttribute("workspacedetails", workspace);
		model.addAttribute("projectid", workspaceId);
		return "auth/workbench/workspace/conceptcollections";
	}
}
