package edu.asu.spring.quadriga.web.workspace;


import java.security.Principal;

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
import edu.asu.spring.quadriga.domain.factories.IWorkspaceFactory;
import edu.asu.spring.quadriga.domain.implementation.WorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.workspace.ICheckWSSecurity;
import edu.asu.spring.quadriga.service.workspace.IModifyWSManager;
import edu.asu.spring.quadriga.validator.WorkspaceValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class AddWSController
{
	@Autowired
	private IWorkspaceFactory workspaceFactory;

	@Autowired 
	IUserManager userManager;

	@Autowired
	IModifyWSManager modifyWSManger;

	@Autowired
	ICheckWSSecurity workspaceSecurity;
	
	@Autowired
	WorkspaceValidator validator;
	
	/**
	 * Attach the custom validator to the Spring context
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {

		binder.setValidator(validator);
	}
	
	/**
	 * This is called on the addworkspace form load.
	 * @param     model
	 * @return    String - containing the path to addworkspace jsp page.
	 * @throws QuadrigaStorageException 
	 * @author    Kiran Kumar Batna
	 * @throws QuadrigaAccessException 
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT,paramIndex = 1, userRole = {RoleNames.ROLE_COLLABORATOR_ADMIN,RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN,RoleNames.ROLE_PROJ_COLLABORATOR_CONTRIBUTOR} )})
	@RequestMapping(value="auth/workbench/{projectid}/addworkspace", method=RequestMethod.GET)
	public ModelAndView addWorkSpaceRequestForm(@PathVariable("projectid") String projectid) throws QuadrigaStorageException, QuadrigaAccessException
	{
		ModelAndView model;
		
			model = new ModelAndView("auth/workbench/workspace/addworkspace");
			model.getModelMap().put("workspace", workspaceFactory.createWorkspaceObject());
			model.getModelMap().put("wsprojectid", projectid);
			model.getModelMap().put("success", 0);
			return model;
	}

	/**
	 * This calls workspace manager to add workspace details into the database.
	 * @param    workspace
	 * @param    model
	 * @param    principal
	 * @return   String - On success loads success page and on failure loads
	 *                   the same form with error messages.
	 * @throws  QuadrigaStorageException
	 * @author  Kiran Kumar Batna
	 * @throws QuadrigaAccessException 
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT,paramIndex = 3, userRole = {RoleNames.ROLE_COLLABORATOR_ADMIN,RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN,RoleNames.ROLE_PROJ_COLLABORATOR_CONTRIBUTOR} )})
	@RequestMapping(value = "auth/workbench/{projectid}/addworkspace", method = RequestMethod.POST)
	public ModelAndView addWorkSpaceRequest(@Validated @ModelAttribute("workspace")WorkSpace workspace,BindingResult result,
			@PathVariable("projectid") String projectid,Principal principal) throws QuadrigaStorageException, QuadrigaAccessException
	{
		ModelAndView model;
		String userName = principal.getName();
		IUser user;
		model = new ModelAndView("auth/workbench/workspace/addworkspace");
			if(result.hasErrors())
			{
				model.getModelMap().put("workspace", workspace);
				model.getModelMap().put("wsprojectid", projectid);
				model.getModelMap().put("success", 0);
				return model;
			}
			else
			{
			user = userManager.getUserDetails(userName);
			workspace.setOwner(user);
			modifyWSManger.addWorkSpaceRequest(workspace, projectid);
			model.getModelMap().put("success", 1);
			model.getModelMap().put("wsprojectid", projectid);
			return model;
			}
	}
}
