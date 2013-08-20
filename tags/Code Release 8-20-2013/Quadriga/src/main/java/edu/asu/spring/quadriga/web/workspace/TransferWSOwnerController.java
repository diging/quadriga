package edu.asu.spring.quadriga.web.workspace;

import java.beans.PropertyEditorSupport;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestDataBinder;
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
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.implementation.Collaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;
import edu.asu.spring.quadriga.service.workspace.IModifyWSManager;
import edu.asu.spring.quadriga.service.workspace.IRetrieveWSCollabManager;
import edu.asu.spring.quadriga.validator.CollaboratorValidator;

@Controller
public class TransferWSOwnerController 
{
	@Autowired
	private ICollaboratorRoleManager collaboratorRoleManager;
	
	@Autowired
	private CollaboratorValidator validator;
	
	@Autowired
	private IUserManager userManager;
	
	@Autowired
	private ICollaboratorFactory collaboratorFactory;
	
	@Autowired
	private IModifyWSManager workspaceManager;
	
	@Autowired
	private IListWSManager retrieveWSManager;
	
	@Autowired
	IRetrieveWSCollabManager wsCollabManager;
	
	private static final Logger logger = LoggerFactory.getLogger(TransferWSOwnerController.class);
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder,WebDataBinder validateBinder) throws Exception {
		
		validateBinder.setValidator(validator);
		
		binder.registerCustomEditor(IUser.class, "userObj", new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {

				IUser user;
				try {
					user = userManager.getUserDetails(text);
					setValue(user);
				} catch (QuadrigaStorageException e) {
					logger.error("In TransferWSOwnerController class :"+e);
				}
				
			}
		});
		binder.registerCustomEditor(List.class, "collaboratorRoles", new PropertyEditorSupport() {

			@Override
			public void setAsText(String text) {

				String[] roleIds = text.split(",");
				List<ICollaboratorRole> roles = new ArrayList<ICollaboratorRole>();
				for (String roleId : roleIds) {
					ICollaboratorRole role = collaboratorRoleManager.getWSCollaboratorRoleByDBId(roleId);
					roles.add(role);
				}
				setValue(roles);
			} 	
		}); 
	}
	
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE,paramIndex = 1, userRole = { "null" } )})
	@RequestMapping(value = "auth/workbench/workspace/{workspaceid}/transferworkspaceowner", method = RequestMethod.GET)
	public ModelAndView transferWSOwnerRequestForm(@PathVariable("workspaceid") String workspaceid,Principal principal) throws QuadrigaStorageException, QuadrigaAccessException
	{
		ModelAndView model;
		IWorkSpace workspace;
		List<ICollaboratorRole> collaboratorRoles;
		String owner;
		ICollaborator collaborator;
		List<ICollaborator> collaboratingUser = new ArrayList<ICollaborator>();
		List<IUser> userList = new ArrayList<IUser>();
		List<ICollaborator> collaboratorList;
		
		//create a view
		model = new ModelAndView("auth/workbench/workspace/transferworkspaceowner");
		
		owner = principal.getName();
		
		workspace = retrieveWSManager.getWorkspaceDetails(workspaceid, owner);
		
		//retrieve the collaborators associated with the workspace
		collaboratorList = wsCollabManager.getWorkspaceCollaborators(workspaceid);

		workspace.setCollaborators(collaboratorList);
		
			//adding the collaborator model
			collaborator =  collaboratorFactory.createCollaborator();
			model.getModelMap().put("collaborator", collaborator);
			
			model.getModelMap().put("wsname", workspace.getName());
			model.getModelMap().put("wsowner", workspace.getOwner().getUserName());
			model.getModelMap().put("workspaceid", workspace.getId());
			
			//fetch the collaborators
			collaboratingUser = workspace.getCollaborators();
			
			for(ICollaborator collabuser : collaboratingUser)
			{
				userList.add(collabuser.getUserObj());
			}
			
			model.getModelMap().put("collaboratinguser", userList);
			
			//get all the project collaborator roles
			collaboratorRoles = collaboratorRoleManager.getWsCollabRoles();
			
	        //add the collaborator roles to the model
			model.getModelMap().put("wscollabroles", collaboratorRoles);
			
			//create model attribute
			model.getModelMap().put("success", 0);
		return model;
	}
	
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE,paramIndex = 1, userRole = { "null" } )})
	@RequestMapping(value = "auth/workbench/workspace/{workspaceid}/transferworkspaceowner", method = RequestMethod.POST)
	public ModelAndView transferWSOwnerRequest(@PathVariable("workspaceid") String workspaceid,Principal principal,
			@Validated @ModelAttribute("collaborator") Collaborator collaborator,BindingResult result) throws QuadrigaStorageException, QuadrigaAccessException
	{
		ModelAndView model;
		String userName;
		String newOwner;
		IWorkSpace workspace;
		String roleIDList = "";
		List<ICollaboratorRole> collaboratorRoles;
		List<ICollaborator> collaboratingUser = new ArrayList<ICollaborator>();
		List<IUser> userList = new ArrayList<IUser>();
		List<ICollaborator> collaboratorList;
		
		//create a view
		model = new ModelAndView("auth/workbench/workspace/transferworkspaceowner");
		userName = principal.getName();
		
		//retrieve the workspace details
		workspace = retrieveWSManager.getWorkspaceDetails(workspaceid, userName);
		
		//retrieve the collaborators associated with the workspace
		collaboratorList = wsCollabManager.getWorkspaceCollaborators(workspaceid);

		workspace.setCollaborators(collaboratorList);
		
		model.getModelMap().put("workspaceid", workspace.getId());
		
			if(result.hasErrors())
			{
				model.getModelMap().put("collaborator", collaborator);
				
				model.getModelMap().put("wsname", workspace.getName());
				model.getModelMap().put("wsowner", workspace.getOwner().getUserName());
				
				//fetch the collaborators
				collaboratingUser = workspace.getCollaborators();
				
				for(ICollaborator collabuser : collaboratingUser)
				{
					userList.add(collabuser.getUserObj());
				}
				
				model.getModelMap().put("collaboratinguser", userList);
				
				//get all the project collaborator roles
				collaboratorRoles = collaboratorRoleManager.getWsCollabRoles();
				
		        //add the collaborator roles to the model
				model.getModelMap().put("wscollabroles", collaboratorRoles);
				
				//create model attribute
				model.getModelMap().put("success", 0);
				
			}
			else
			{
	        	newOwner = collaborator.getUserObj().getUserName();
	        	
	        	//if the collaborators are null
	        	if(collaborator.getCollaboratorRoles().equals(null))
	        	{
	        		logger.info("In TransferWSOwnerController class collaborator object is NULL");
	        		throw new QuadrigaStorageException();
	        	}
	        	//fetch the roles to the existing owner
				for(ICollaboratorRole role : collaborator.getCollaboratorRoles())
				{
					roleIDList = roleIDList + "," + role.getRoleDBid();
				}
				
				//removing the first ',' from the list
				roleIDList = roleIDList.substring(1);
				
				//call the method to transfer the ownership
				workspaceManager.transferWSOwnerRequest(workspaceid, userName, newOwner, roleIDList);
				
				model.getModelMap().put("success", 1);
				model.getModelMap().put("successmsg", "Workspace Ownership transferred successfully.");
				
				model.getModelMap().put("collaborator", collaboratorFactory.createCollaborator());
			}
		return model;
	}
}
