package edu.asu.spring.quadriga.web.workspace;

import java.beans.PropertyEditorSupport;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
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
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.implementation.Collaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.impl.workspace.ModifyWSCollabManager;
import edu.asu.spring.quadriga.service.workspace.IModifyWSCollabManager;
import edu.asu.spring.quadriga.service.workspace.IRetrieveWSCollabManager;
import edu.asu.spring.quadriga.validator.CollaboratorValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class AddWSCollabController 
{
	
	@Autowired
	IModifyWSCollabManager wsManager;
	
	@Autowired
	IRetrieveWSCollabManager wsCollabManager;
	
	@Autowired
	private ICollaboratorRoleManager collaboratorRoleManager;
	
	@Autowired
	ICollaboratorFactory collaboratorFactory;
	
	@Autowired
	private IUserFactory userFactory;
	
	@Autowired
	private IUserManager userManager;
	
	@Autowired
	private CollaboratorValidator validator;
	
	private static final Logger logger = LoggerFactory.getLogger(ModifyWSCollabManager.class);
	
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
					logger.error("In ModifyWSCollabController class :"+e);
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
	
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE,paramIndex = 1, userRole = {RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN} )})
	@RequestMapping(value = "auth/workbench/workspace/{workspaceid}/addcollaborators", method = RequestMethod.GET)
	public ModelAndView addWorkspaceCollaboratorForm(@PathVariable("workspaceid") String workspaceid) throws QuadrigaStorageException
	{
		ModelAndView model;
		List<IUser> nonCollaboratingUser;
		ICollaborator collaborator;
		List<ICollaborator> collaboratingUser = new ArrayList<ICollaborator>();
		
		model = new ModelAndView("auth/workbench/workspace/addcollaborators");
		
		//adding the collaborator model
		collaborator =  collaboratorFactory.createCollaborator();
		model.getModelMap().put("collaborator", collaborator);
		
		//adding the workspace id
		model.getModelMap().put("workspaceid", workspaceid);
		
		
		//fetch the users who are not collaborators to the workspace
		nonCollaboratingUser = wsCollabManager.getWorkspaceNonCollaborators(workspaceid);
		
		//remove the restricted user
		Iterator<IUser> userIterator = nonCollaboratingUser.iterator();
		while(userIterator.hasNext())
		{
			//fetch the quadriga roles and eliminate the restricted user
			IUser user = userIterator.next();
			List<IQuadrigaRole> userQuadrigaRole = user.getQuadrigaRoles();
			for(IQuadrigaRole role : userQuadrigaRole)
			{
				if(role.getId().equals(RoleNames.ROLE_QUADRIGA_RESTRICTED))
				{
					userIterator.remove();
					break;
				}
			}
		}
		
		//add the users list to the model
		model.getModelMap().put("noncollabusers", nonCollaboratingUser);
		
		//fetch the roles that can be associated to the workspace collaborator
		List<ICollaboratorRole> collaboratorRoles = collaboratorRoleManager.getWsCollabRoles();
		
        //add the collaborator roles to the model
		model.getModelMap().put("wscollabroles", collaboratorRoles);
		
		//fetch all the collaborating users and their roles
		collaboratingUser = wsCollabManager.getWorkspaceCollaborators(workspaceid);
		
		model.getModelMap().put("collaboratingusers", collaboratingUser);
		
		return model;
	}
	
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE,paramIndex = 3, userRole = {RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN} )})
	@RequestMapping(value = "auth/workbench/workspace/{workspaceid}/addcollaborators", method = RequestMethod.POST)
	public ModelAndView addWorkspaceCollaborator(@Validated @ModelAttribute("collaborator") Collaborator collaborator,BindingResult result,
			@PathVariable("workspaceid") String workspaceid,Principal principal) throws QuadrigaStorageException
	{
		ModelAndView model;
		String userName;
		String collabUser;
		StringBuilder roleIdList;
		List<IUser> nonCollaboratingUser;
		List<ICollaborator> collaboratingUser = new ArrayList<ICollaborator>();
		
		//create the model view
		model = new ModelAndView("auth/workbench/workspace/addcollaborators");
		roleIdList = new StringBuilder();
		
		if(result.hasErrors())
		{
			model.getModelMap().put("collaborator", collaborator);

		}
		else
		{
			//get all the required input parameters
			userName = principal.getName();
			collabUser = collaborator.getUserObj().getUserName();
			for(ICollaboratorRole role : collaborator.getCollaboratorRoles())
			{
				roleIdList.append(",");
				roleIdList.append(role.getRoleDBid());
			}
			
			//call the method to insert the collaborator
			wsManager.addWorkspaceCollaborator(collabUser, roleIdList.toString().substring(1), workspaceid, userName);
			
			model.getModelMap().put("collaborator", collaboratorFactory.createCollaborator());

		}
		
		//adding the workspace id
		model.getModelMap().put("workspaceid", workspaceid);
		
		
		//fetch the users who are not collaborators to the workspace
		nonCollaboratingUser = wsCollabManager.getWorkspaceNonCollaborators(workspaceid);
		
		//remove the restricted user
		Iterator<IUser> userIterator = nonCollaboratingUser.iterator();
		while(userIterator.hasNext())
		{
			//fetch the quadriga roles and eliminate the restricted user
			IUser user = userIterator.next();
			List<IQuadrigaRole> userQuadrigaRole = user.getQuadrigaRoles();
			for(IQuadrigaRole role : userQuadrigaRole)
			{
				if(role.getId().equals(RoleNames.ROLE_QUADRIGA_RESTRICTED))
				{
					userIterator.remove();
					break;
				}
			}
		}
		
		//add the users list to the model
		model.getModelMap().put("noncollabusers", nonCollaboratingUser);
		
		//fetch the roles that can be associated to the workspace collaborator
		List<ICollaboratorRole> collaboratorRoles = collaboratorRoleManager.getWsCollabRoles();
		
        //add the collaborator roles to the model
		model.getModelMap().put("wscollabroles", collaboratorRoles);
		
		//fetch all the collaborating users and their roles
		collaboratingUser = wsCollabManager.getWorkspaceCollaborators(workspaceid);
		
		model.getModelMap().put("collaboratingusers", collaboratingUser);
		return model;
		

	}

}
