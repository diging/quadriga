package edu.asu.spring.quadriga.web.workspace;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.accesschecks.IWSSecurityChecker;
import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IDspaceKeysFactory;
import edu.asu.spring.quadriga.domain.impl.workspace.WorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceBitStream;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceCollaborator;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceNetwork;
import edu.asu.spring.quadriga.dspace.service.IDspaceKeys;
import edu.asu.spring.quadriga.dspace.service.IDspaceManager;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.exceptions.RestException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;
import edu.asu.spring.quadriga.service.workspace.IModifyWSManager;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceCollaboratorManager;
import edu.asu.spring.quadriga.validator.WorkspaceValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class ModifyWSController {

	@Autowired
	private IWSSecurityChecker workspaceSecurity;

	@Autowired
	private IUserManager userManager;

	@Autowired
	private IModifyWSManager modifyWSManager;

	@Autowired
	private IListWSManager wsManager;

	@Autowired
	private WorkspaceValidator validator;

	@Autowired
	private IDspaceManager dspaceManager;
	
	@Autowired
	private IWorkspaceCollaboratorManager wsCollabManager;

	private String dspaceUsername;
	private String dspacePassword;
	private IDspaceKeys dspaceKeys;

	private static final Logger logger = LoggerFactory.getLogger(ModifyWSController.class);
	
	@Autowired
	private IDspaceKeysFactory dspaceKeysFactory;

	public IDspaceKeysFactory getDspaceKeysFactory() {
		return dspaceKeysFactory;
	}


	public void setDspaceKeysFactory(IDspaceKeysFactory dspaceKeysFactory) {
		this.dspaceKeysFactory = dspaceKeysFactory;
	}


	public IDspaceManager getDspaceManager() {
		return dspaceManager;
	}


	public void setDspaceManager(IDspaceManager dspaceManager) {
		this.dspaceManager = dspaceManager;
	}

	
	/**
	 * Attach the custom validator to the Spring context
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {

		binder.setValidator(validator);
	}

	/**
	 * This is called on the modifyworkspace on load.
	 * 
	 * @param model
	 * @return ModelAndView
	 * @throws QuadrigaStorageException
	 * @author Kiran Kumar Batna
	 * @throws QuadrigaAccessException
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE,paramIndex = 1, userRole = {RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN } )})
	@RequestMapping(value = "auth/workbench/workspace/updateworkspacedetails/{workspaceid}", method = RequestMethod.GET)
	public ModelAndView updateWorkSpaceRequestForm(
			@PathVariable("workspaceid") String workspaceid, Principal principal)
			throws QuadrigaStorageException, QuadrigaAccessException{
		ModelAndView model;
		IWorkSpace workspace;
		String userName;
		// fetch the workspace details
		userName = principal.getName();
		workspace = wsManager.getWorkspaceDetails(workspaceid, userName);
		model = new ModelAndView("auth/workbench/workspace/updateworkspace");
		model.getModelMap().put("workspace", workspace);
		model.getModelMap().put("success", 0);
		return model;
	}

	/**
	 * This is called on the modifyworkspace on form submission.
	 * 
	 * @param model
	 * @return ModelAndView
	 * @throws QuadrigaStorageException
	 * @author Kiran Kumar Batna
	 * @throws QuadrigaAccessException
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE,paramIndex = 3, userRole = {RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN } )})
	@RequestMapping(value = "auth/workbench/workspace/updateworkspacedetails/{workspaceid}", method = RequestMethod.POST)
	public ModelAndView updateWorkSpaceRequest(
			@Validated @ModelAttribute("workspace") WorkSpace workspace,
			BindingResult result,
			@PathVariable("workspaceid") String workspaceid, Principal principal)
			throws QuadrigaStorageException, QuadrigaAccessException {
		ModelAndView model;
		IUser wsOwner = null;
		String userName = principal.getName();
		model = new ModelAndView(
				"auth/workbench/workspace/updateworkspace");
			wsOwner = userManager.getUser(userName);

			// set the workspace owner
			workspace.setOwner(wsOwner);

			// set the workspace id
			workspace.setWorkspaceId(workspaceid);

			if (result.hasErrors()) {
				model.getModelMap().put("workspace", workspace);
				model.getModelMap().put("success", 0);
				return model;
			} else {
				modifyWSManager.updateWorkspace(workspace);
				model.getModelMap().put("success", 1);
				return model;
			}
	}
	
	/**
	 * Assign editor to owner for workspace level
	 * @param workspaceId
	 * @param model
	 * @param principal
	 * @return
	 * @throws QuadrigaStorageException
	 * @throws QuadrigaException 
	 * @throws QuadrigaAccessException 
	 * @throws RestException 
	 */
	@RequestMapping(value = "auth/workbench/workspace/assignEditorRoleToOwner/{workspaceid}", method = RequestMethod.GET)
	public String assignEditorRoleToOwner(@PathVariable("workspaceid") String workspaceId, ModelMap model,Principal principal) throws QuadrigaStorageException, QuadrigaException, QuadrigaAccessException, RestException{
		IUser user = userManager.getUser(principal.getName());
		String userName =user.getUserName();
		String msg="";
		modifyWSManager.assignEditorRole(workspaceId, userName);
		IWorkSpace workspace;
		List<IWorkspaceCollaborator> collaboratorList;

		userName = principal.getName();
		workspace = wsManager.getWorkspaceDetails(workspaceId,userName);
		
		//Check bitstream access in dspace.
		//TODO: Implement check for dspace keys and Username/password 
		this.dspaceKeys = dspaceManager.getDspaceKeys(principal.getName());
		
		
		
		List<IWorkspaceBitStream> workspaceBitStreams = dspaceManager.checkDspaceBitstreamAccess(workspace.getWorkspaceBitStreams(), this.dspaceKeys, this.dspaceUsername, this.dspacePassword);
		workspace.setWorkspaceBitStreams(workspaceBitStreams);

		//retrieve the collaborators associated with the workspace
		collaboratorList = wsCollabManager.getWorkspaceCollaborators(workspaceId);


		workspace.setWorkspaceCollaborators(collaboratorList);
		List<IWorkspaceNetwork> networkList = wsManager.getWorkspaceNetworkList(workspaceId);
		model.addAttribute("networkList", networkList);
		model.addAttribute("workspacedetails", workspace);

		//Load the Dspace Keys used by the user
		this.dspaceKeys = dspaceManager.getDspaceKeys(principal.getName());
		if(this.dspaceKeys != null)
		{
			model.addAttribute("dspaceKeys", "true");
		}
		else if((this.dspaceUsername != null && this.dspacePassword != null))
		{
			model.addAttribute("dspaceLogin", "true");
		}
		
		if(workspaceSecurity.checkWorkspaceOwner(userName, workspaceId)){
			model.addAttribute("owner", 1);
		}else{
			model.addAttribute("owner", 0);
		}
		if(workspaceSecurity.checkWorkspaceOwnerEditorAccess(userName, workspaceId)){
			model.addAttribute("editoraccess", 1);
		}else{
			model.addAttribute("editoraccess", 0);
		}
		if (workspaceSecurity.checkWorkspaceProjectInheritOwnerEditorAccess(userName, workspaceId)){
			model.addAttribute("projectinherit", 1);
		}else{
			model.addAttribute("projectinherit", 0);
		}
		if(msg.equals("")){
			model.addAttribute("AssignEditorSuccess",1);
		}else if(msg.equals("Owner already assigned as owner")){
			model.addAttribute("AssignEditorSuccess",2);
		}else{
			logger.error("Failure " +msg);
			model.addAttribute("AssignEditorSuccess",0);
		}
		return "auth/workbench/workspace/workspacedetails";
	}
	
	/**
	 * Assign editor to owner for workspace level
	 * @param workspaceId
	 * @param model
	 * @param principal
	 * @return
	 * @throws QuadrigaStorageException
	 * @throws QuadrigaException 
	 * @throws QuadrigaAccessException 
	 * @throws RestException 
	 */
	@RequestMapping(value = "auth/workbench/workspace/deleteEditorRoleToOwner/{workspaceid}", method = RequestMethod.GET)
	public String deleteEditorRoleToOwner(@PathVariable("workspaceid") String workspaceId, ModelMap model,Principal principal) throws QuadrigaStorageException, QuadrigaException, QuadrigaAccessException, RestException{
		IUser user = userManager.getUser(principal.getName());
		String userName =user.getUserName();
		boolean success = modifyWSManager.deleteEditorRole(workspaceId, userName);
		IWorkSpace workspace;
		List<IWorkspaceCollaborator> collaboratorList;

		userName = principal.getName();
		workspace = wsManager.getWorkspaceDetails(workspaceId,userName);
		
		//Check bitstream access in dspace.
		//TODO: Implement check for dspace keys and Username/password 
		this.dspaceKeys = dspaceManager.getDspaceKeys(principal.getName());
		List<IWorkspaceBitStream> workspaceBitstreams = dspaceManager.checkDspaceBitstreamAccess(workspace.getWorkspaceBitStreams(), this.dspaceKeys, this.dspaceUsername, this.dspacePassword);
		workspace.setWorkspaceBitStreams(workspaceBitstreams);

		//retrieve the collaborators associated with the workspace
		collaboratorList = wsCollabManager.getWorkspaceCollaborators(workspaceId);


		workspace.setWorkspaceCollaborators(collaboratorList);
		List<IWorkspaceNetwork> networkList = wsManager.getWorkspaceNetworkList(workspaceId);
		model.addAttribute("networkList", networkList);
		model.addAttribute("workspacedetails", workspace);

		//Load the Dspace Keys used by the user
		this.dspaceKeys = dspaceManager.getDspaceKeys(principal.getName());
		if(this.dspaceKeys != null)
		{
			model.addAttribute("dspaceKeys", "true");
		}
		else if((this.dspaceUsername != null && this.dspacePassword != null))
		{
			model.addAttribute("dspaceLogin", "true");
		}
		
		if(workspaceSecurity.checkWorkspaceOwner(userName, workspaceId)){
			model.addAttribute("owner", 1);
		}else{
			model.addAttribute("owner", 0);
		}
		if(workspaceSecurity.checkWorkspaceOwnerEditorAccess(userName, workspaceId)){
			model.addAttribute("editoraccess", 1);
		}else{
			model.addAttribute("editoraccess", 0);
		}
		if (workspaceSecurity.checkWorkspaceProjectInheritOwnerEditorAccess(userName, workspaceId)){
			model.addAttribute("projectinherit", 1);
		}
		if(success){
			model.addAttribute("DeleteEditorSuccess",1);
		} else {
			model.addAttribute("DeleteEditorSuccess",0);
		}
		return "auth/workbench/workspace/workspacedetails";
	}
}
