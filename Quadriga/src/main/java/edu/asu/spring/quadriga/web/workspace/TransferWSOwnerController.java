package edu.asu.spring.quadriga.web.workspace;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.impl.User;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceCollaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;
import edu.asu.spring.quadriga.service.workspace.IModifyWSManager;
import edu.asu.spring.quadriga.service.workspace.IRetrieveWSCollabManager;
import edu.asu.spring.quadriga.validator.UserValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class TransferWSOwnerController 
{
	@Autowired
	private IQuadrigaRoleManager collaboratorRoleManager;
	
	@Autowired
	private UserValidator validator;
	
	@Autowired
	private IUserManager userManager;
	
	@Autowired
	private IModifyWSManager workspaceManager;
	
	@Autowired
	private IListWSManager retrieveWSManager;
	
	@Autowired
	IRetrieveWSCollabManager wsCollabManager;
	
	@Autowired
	IUserFactory userFactory;
    
	/**
	 * Custom validator to validate the input
	 * @param validateBinder
	 * @throws Exception
	 */
	@InitBinder
	protected void initBinder(WebDataBinder validateBinder) throws Exception {
		validateBinder.setValidator(validator);
	}
	
	/**
	 * Retrieve all the collaborators associated to the workspace
	 * to transfer the ownership to the selected collaborator
	 * @param workspaceid
	 * @param principal
	 * @return ModelAndView
	 * @throws QuadrigaStorageException
	 * @throws QuadrigaAccessException
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE,paramIndex = 1, userRole = {} )})
	@RequestMapping(value = "auth/workbench/workspace/transferworkspaceowner/{workspaceid}", method = RequestMethod.GET)
	public ModelAndView transferWSOwnerRequestForm(@PathVariable("workspaceid") String workspaceid,Principal principal) throws QuadrigaStorageException, QuadrigaAccessException
	{
		ModelAndView model;
		IWorkSpace workspace;
		String owner;
		List<IWorkspaceCollaborator> collaboratingUser = new ArrayList<IWorkspaceCollaborator>();
		List<IUser> userList = new ArrayList<IUser>();
		
		//create a view
		model = new ModelAndView("auth/workbench/workspace/transferworkspaceowner");
		
		owner = principal.getName();
		
		//retrieve the workspace details
		workspace = retrieveWSManager.getWorkspaceDetails(workspaceid, owner);
		
		//retrieve the collaborators associated with the workspace
		collaboratingUser = wsCollabManager.getWorkspaceCollaborators(workspaceid);

		//adding the collaborator model
		model.getModelMap().put("user", userFactory.createUserObject());
		model.getModelMap().put("wsname", workspace.getWorkspaceName());
		model.getModelMap().put("wsowner", workspace.getOwner().getUserName());
		model.getModelMap().put("workspaceid", workspace.getWorkspaceId());
		
		//fetch the collaborators
		if (collaboratingUser != null) {
    		for(IWorkspaceCollaborator collabuser : collaboratingUser)
    		{
    			userList.add(collabuser.getCollaborator().getUserObj());
    		}
		}
		
		model.getModelMap().put("collaboratinguser", userList);
		//create model attribute
		model.getModelMap().put("success", 0);
		return model;
	}
	
	/**
	 * This method transfer the owner of workspace to another user and adds the 
	 * old owner as collaborator to the workspace
	 * @param workspaceid
	 * @param principal
	 * @param collaboratorUser
	 * @param result
	 * @return ModelAndView
	 * @throws QuadrigaStorageException
	 * @throws QuadrigaAccessException
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE,paramIndex = 1, userRole = {} )})
	@RequestMapping(value = "auth/workbench/workspace/{workspaceid}/transferworkspaceowner", method = RequestMethod.POST)
	public ModelAndView transferWSOwnerRequest(@PathVariable("workspaceid") String workspaceid,Principal principal,
			@Validated @ModelAttribute("user")User collaboratorUser,BindingResult result) throws QuadrigaStorageException, QuadrigaAccessException
	{
		ModelAndView model;
		String userName;
		String newOwner;
		String collaboratorRole;
		IWorkSpace workspace;
		List<IWorkspaceCollaborator> collaboratingUser = new ArrayList<IWorkspaceCollaborator>();
		List<IUser> userList = new ArrayList<IUser>();
		
		//create a view
		model = new ModelAndView("auth/workbench/workspace/transferworkspaceowner");
		userName = principal.getName();
		
		//retrieve the workspace details
		workspace = retrieveWSManager.getWorkspaceDetails(workspaceid, userName);
		
		//retrieve the collaborators associated with the workspace
		collaboratingUser = wsCollabManager.getWorkspaceCollaborators(workspaceid);

		model.getModelMap().put("workspaceid", workspace.getWorkspaceId());
		
			if(result.hasErrors())
			{
				model.getModelMap().put("user", collaboratorUser);
				
				model.getModelMap().put("wsname", workspace.getWorkspaceName());
				model.getModelMap().put("wsowner", workspace.getOwner().getUserName());
				
				for(IWorkspaceCollaborator collabuser : collaboratingUser)
				{
					userList.add(collabuser.getCollaborator().getUserObj());
				}
				
				model.getModelMap().put("collaboratinguser", userList);
				//create model attribute
				model.getModelMap().put("success", 0);
				
			}
			else
			{
	        	newOwner = collaboratorUser.getUserName();
	        	
	        	//fetch the collaborator role
	        	collaboratorRole = collaboratorRoleManager.getQuadrigaRoleById(IQuadrigaRoleManager.WORKSPACE_ROLES, RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN).getDBid();
				//call the method to transfer the ownership
				workspaceManager.transferWSOwnerRequest(workspaceid, userName, newOwner, collaboratorRole);
				
				model.getModelMap().put("success", 1);
				model.getModelMap().put("user", userFactory.createUserObject());
			}
		return model;
	}
}
