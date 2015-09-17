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
import edu.asu.spring.quadriga.domain.factory.impl.workspace.WorkspaceFormFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IModifyWSManager;
import edu.asu.spring.quadriga.validator.WorkspaceFormValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;
import edu.asu.spring.quadriga.web.workspace.backing.ModifyWorkspace;
import edu.asu.spring.quadriga.web.workspace.backing.ModifyWorkspaceForm;
import edu.asu.spring.quadriga.web.workspace.backing.ModifyWorkspaceFormManager;

@Controller
public class DeleteWSController 
{
	@Autowired
	IModifyWSManager modifyWSManger;
	
	@Autowired
	WorkspaceFormValidator validator;
	
	@Autowired
	ModifyWorkspaceFormManager workspaceFormManager;
	
	@Autowired
	WorkspaceFormFactory workspaceFormFactory;
	
	/**
	 * Attach the custom validator to the Spring context
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {

		binder.setValidator(validator);
	}
	
	/**
	 * This calls workspaceManger to list the deactivated workspace associated with a project for deletion.
	 * @param    model
	 * @param    projectid
	 * @return   String - URL of the form
	 * @throws   QuadrigaStorageException
	 * @author   Kiran Kumar Batna
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT,paramIndex = 1, userRole = {RoleNames.ROLE_COLLABORATOR_ADMIN,RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN,RoleNames.ROLE_PROJ_COLLABORATOR_CONTRIBUTOR})
	,@ElementAccessPolicy(type=CheckedElementType.WORKSPACE,paramIndex=0,userRole={})})
	@RequestMapping(value="auth/workbench/{projectid}/deleteworkspace", method=RequestMethod.GET)
	public ModelAndView deleteWorkspaceRequestForm(@PathVariable("projectid") String projectid,Principal principal) throws QuadrigaStorageException,QuadrigaAccessException
	{
		ModelAndView model;
		String userName;
		ModifyWorkspaceForm workspaceForm;
		List<ModifyWorkspace> deleteWSList;
		
		//check if the user has access
		userName = principal.getName();
		
		model = new ModelAndView("auth/workbench/workspace/deleteworkspace");
		
		// retrieve the workspaces associated with the projects
		deleteWSList = workspaceFormManager.getDeactivatedWorkspaceList(projectid,userName);
		
		workspaceForm = workspaceFormFactory.createModifyWorkspaceForm();
		
		workspaceForm.setWorkspaceList(deleteWSList);
		
		model.getModelMap().put("workspaceform", workspaceForm);
		model.getModelMap().put("wsprojectid", projectid);
		model.getModelMap().put("success", 0);
		return model;
	}
	
	/**
	 * This calls workspaceManager to delete the workspace submitted.
	 * @param   projectid
	 * @param   req
	 * @param   model
	 * @param   principal
	 * @return  String - URL of the form
	 * @throws  QuadrigaStorageException
	 * @author  Kiran Kumar Batna
	 * @throws QuadrigaAccessException 
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT,paramIndex = 3, userRole = {RoleNames.ROLE_COLLABORATOR_ADMIN,RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN,RoleNames.ROLE_PROJ_COLLABORATOR_CONTRIBUTOR})
	,@ElementAccessPolicy(type=CheckedElementType.WORKSPACE,paramIndex=0,userRole={})})
	@RequestMapping(value = "auth/workbench/{projectid}/deleteworkspace", method = RequestMethod.POST)
	public ModelAndView deleteWorkspaceRequest(@Validated @ModelAttribute("workspaceform") ModifyWorkspaceForm workspaceForm,BindingResult result,
			@PathVariable("projectid") String projectid,Principal principal) throws QuadrigaStorageException, QuadrigaAccessException
	{
		StringBuilder workspaceId;
		String userName;
		String wsInternalId;
		ModelAndView model;
		List<ModifyWorkspace> deleteWSList;
		//model = new ModelAndView("auth/workbench/workspace/deleteworkspace");
		
		//fetch the user name
		userName = principal.getName();
		
		deleteWSList = new ArrayList<ModifyWorkspace>();
		workspaceId = new StringBuilder();
		
		model = new ModelAndView("auth/workbench/workspace/deleteworkspace");
		
		if(result.hasErrors())
		{
//			model = new ModelAndView("auth/workbench/workspace/deleteworkspace");
			// retrieve the workspaces associated with the projects
			deleteWSList = workspaceFormManager.getDeactivatedWorkspaceList(projectid,userName);
			
			workspaceForm.setWorkspaceList(deleteWSList);
			
			//frame the model objects
			model.getModelMap().put("success", 0);
			model.getModelMap().put("error", 1);
			model.getModelMap().put("workspaceform", workspaceForm);
			//model.getModelMap().put("wsprojectid", projectid);
			model.getModelMap().put("projectid", projectid);
			
		}
		else
		{
//			model = new ModelAndView("auth/workbench/project");
			deleteWSList = workspaceForm.getWorkspaceList();
			
			//loop through the selected workspace	
			for(ModifyWorkspace workspace : deleteWSList)
			{
				wsInternalId = workspace.getId();
				
				if(wsInternalId != null)
				{
					workspaceId.append(",");
					workspaceId.append(wsInternalId);
				}
			}
			modifyWSManger.deleteWorkspace(workspaceId.toString().substring(1));
			
			//frame the model objects
			model.getModelMap().put("success", 1);
			model.getModelMap().put("wsprojectid", projectid);	
//			model.getModelMap().put("projectid", projectid);
			
		}			
		return model;
	}
	
	
	
	
	
	/**
	 * This calls workspaceManager to delete the workspace submitted.
	 * @param   projectid
	 * @param   req
	 * @param   model
	 * @param   principal
	 * @return  String - URL of the form
	 * @throws  QuadrigaStorageException
	 * @author  Kiran Kumar Batna
	 * @throws QuadrigaAccessException 
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE,paramIndex = 1, userRole = {} )})
	@RequestMapping(value = "auth/workbench/deleteSingleWorkspace/{workspaceId}", method = RequestMethod.GET)
	public ModelAndView deleteSingleWorkspaceRequest(@Validated @ModelAttribute("workspaceform") ModifyWorkspaceForm workspaceForm,BindingResult result,
			@PathVariable("workspaceId") String workspaceId,Principal principal) throws QuadrigaStorageException, QuadrigaAccessException
	{
			ModelAndView model;
			model = new ModelAndView("auth/workbench");
			modifyWSManger.deleteWorkspace(workspaceId.toString().substring(1));
			return model;
	}
	
	
	
	
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT,paramIndex = 3, userRole = {RoleNames.ROLE_COLLABORATOR_ADMIN,RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN,RoleNames.ROLE_PROJ_COLLABORATOR_CONTRIBUTOR})
	,@ElementAccessPolicy(type=CheckedElementType.WORKSPACE,paramIndex=0,userRole={})})
	@RequestMapping(value = "auth/workbench/{workspaceid}/deleteSingleWorkspaceWithProjectID", method = RequestMethod.POST)
	public ModelAndView deleteWorkspaceRequest1(
			@PathVariable("projectid") String projectid, @PathVariable("workspaceid") String workspaceid,Principal principal) throws QuadrigaStorageException, QuadrigaAccessException
	{
		ModelAndView model;
		
		
		
		model = new ModelAndView("auth/workbench/workspace/deleteworkspace");
		
			
			//loop through the selected workspace	
			modifyWSManger.deleteWorkspace(workspaceid.toString().substring(1));
			
			//frame the model objects
			model.getModelMap().put("success", 1);
			model.getModelMap().put("wsprojectid", projectid);	
			
		return model;
	}
	
	
}
