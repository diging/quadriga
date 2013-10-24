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
import edu.asu.spring.quadriga.domain.factories.impl.WorkspaceFormFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IArchiveWSManager;
import edu.asu.spring.quadriga.validator.WorkspaceFormValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;
import edu.asu.spring.quadriga.web.workspace.backing.ModifyWorkspace;
import edu.asu.spring.quadriga.web.workspace.backing.ModifyWorkspaceForm;
import edu.asu.spring.quadriga.web.workspace.backing.ModifyWorkspaceFormManager;

@Controller
public class ArchiveWSController 
{
	@Autowired
	IArchiveWSManager archiveWSManager;
	
	@Autowired
	ModifyWorkspaceFormManager workspaceFormManager;
	
	@Autowired
	WorkspaceFormFactory workspaceFormFactory;
	
	@Autowired
	WorkspaceFormValidator validator;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder)
	{
		binder.setValidator(validator);
	}
	
	/**
	 * This calls workspaceManger to list the workspace associated with a project for archival process.
	 * @param   model
	 * @param   projectid
	 * @return  String - URL of the form
	 * @throws  QuadrigaStorageException
	 * @author  Kiran Kumar Batna
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT,paramIndex = 1, userRole = {RoleNames.ROLE_COLLABORATOR_ADMIN,RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN,RoleNames.ROLE_PROJ_COLLABORATOR_CONTRIBUTOR})
			,@ElementAccessPolicy(type=CheckedElementType.WORKSPACE,paramIndex=0,userRole={})})
	@RequestMapping(value="auth/workbench/{projectid}/archiveworkspace", method=RequestMethod.GET)
	public ModelAndView archiveWorkspaceForm(@PathVariable("projectid") String projectid,Principal principal) throws QuadrigaStorageException
	{
		ModelAndView model;
		String userName;
		ModifyWorkspaceForm workspaceForm;
		List<ModifyWorkspace> archiveWorkspaceList;
		
		model = new ModelAndView("auth/workbench/workspace/archiveworkspace");
		userName = principal.getName();
		
		workspaceForm = workspaceFormFactory.createModifyWorkspaceForm();
		
		// retrieve the workspaces associated with the projects
		archiveWorkspaceList = workspaceFormManager.getActiveWorkspaceList(projectid, userName);
		workspaceForm.setWorkspaceList(archiveWorkspaceList);
		
		model.getModelMap().put("workspaceform", workspaceForm);
		model.getModelMap().put("wsprojectid",projectid);
		model.getModelMap().put("success", 0);
		return model;
	}
	
	/**
	 * This calls workspaceManager to archive the workspace submitted.
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
	@RequestMapping(value = "auth/workbench/{projectid}/archiveworkspace", method = RequestMethod.POST)
	public ModelAndView archiveWorkspace(@Validated @ModelAttribute("workspaceform") ModifyWorkspaceForm workspaceForm,BindingResult result,
			@PathVariable("projectid") String projectid,Principal principal) throws QuadrigaStorageException, QuadrigaAccessException
	{
		StringBuilder workspaceId;
		String userName;
		String wsInternalId;
		ModelAndView model;
		List<ModifyWorkspace> archiveWorkspaceList;
		
		model = new ModelAndView("auth/workbench/workspace/archiveworkspace");
		//fetch the user name
		userName = principal.getName();
		
		archiveWorkspaceList = new ArrayList<ModifyWorkspace>();
		workspaceId = new StringBuilder();
		
		if(result.hasErrors())
		{
			// retrieve the workspaces associated with the projects
			archiveWorkspaceList = workspaceFormManager.getActiveWorkspaceList(projectid, userName);
			
			workspaceForm.setWorkspaceList(archiveWorkspaceList);
			
			//frame the model objects
			model.getModelMap().put("success", 0);
			model.getModelMap().put("error", 1);
			model.getModelMap().put("workspaceform", workspaceForm);
			model.getModelMap().put("wsprojectid", projectid);
		}
		else
		{
			archiveWorkspaceList = workspaceForm.getWorkspaceList();
			
			//loop through the selected workspace
			for(ModifyWorkspace workspace : archiveWorkspaceList)
			{
				wsInternalId = workspace.getId();
				
				if(wsInternalId != null)
				{
					workspaceId.append(",");
					workspaceId.append(wsInternalId);
				}
			}
			
			archiveWSManager.archiveWorkspace(workspaceId.toString().substring(1), userName);
			
			//frame the model objects
			model.getModelMap().put("success", 1);
			model.getModelMap().put("wsprojectid", projectid);	
		}

      return model; 

	}
	
	/**
	 * This calls workspaceManger to list the archived workspace associated with a project for unarchival process.
	 * @param   model
	 * @param   projectid
	 * @return  String - URL of the form
	 * @throws  QuadrigaStorageException
	 * @author  Kiran Kumar Batna
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT,paramIndex = 1, userRole = {RoleNames.ROLE_COLLABORATOR_ADMIN,RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN,RoleNames.ROLE_PROJ_COLLABORATOR_CONTRIBUTOR})
	,@ElementAccessPolicy(type=CheckedElementType.WORKSPACE,paramIndex=0,userRole={})})
	@RequestMapping(value="auth/workbench/{projectid}/unarchiveworkspace", method=RequestMethod.GET)
	public ModelAndView unarchiveWorkspaceForm(@PathVariable("projectid") String projectid,Principal principal) throws QuadrigaStorageException
	{
		
		ModelAndView model;
		String userName;
		ModifyWorkspaceForm workspaceForm;
		List<ModifyWorkspace> unarchiveWSList;
		
		//check if the user has access
		userName = principal.getName();
		
		model = new ModelAndView("auth/workbench/workspace/unarchiveworkspace");
		workspaceForm = workspaceFormFactory.createModifyWorkspaceForm();
		
		// retrieve the workspaces associated with the projects
		unarchiveWSList = workspaceFormManager.getArchivedWorkspaceList(projectid, userName);
		workspaceForm.setWorkspaceList(unarchiveWSList);
		
		model.getModelMap().put("workspaceform", workspaceForm);
		model.getModelMap().put("wsprojectid", projectid);
		model.getModelMap().put("success", 0);
		return model;
	}
	
	/**
	 * This calls workspaceManager to unarchive the workspace submitted.
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
	@RequestMapping(value = "auth/workbench/{projectid}/unarchiveworkspace", method = RequestMethod.POST)
	public ModelAndView unarchiveWorkspace(@Validated @ModelAttribute("workspaceform") ModifyWorkspaceForm workspaceForm,BindingResult result,
			@PathVariable("projectid") String projectid,Principal principal) throws QuadrigaStorageException, QuadrigaAccessException
	{
		StringBuilder workspaceId;
		String userName;
		String wsInternalId;
		ModelAndView model;
		List<ModifyWorkspace> unarchiveWSList;
		
		model = new ModelAndView("auth/workbench/workspace/unarchiveworkspace");
		
		//fetch the user name
		userName = principal.getName();
		
		unarchiveWSList = new ArrayList<ModifyWorkspace>();
		workspaceId = new StringBuilder();
		
		if(result.hasErrors())
		{
			// retrieve the workspaces associated with the projects
			unarchiveWSList = workspaceFormManager.getArchivedWorkspaceList(projectid, userName);
			
			workspaceForm.setWorkspaceList(unarchiveWSList);
			
			//frame the model objects
			model.getModelMap().put("success", 0);
			model.getModelMap().put("error", 1);
			model.getModelMap().put("workspaceform", workspaceForm);
			model.getModelMap().put("wsprojectid", projectid);
		}
		else
		{
			unarchiveWSList = workspaceForm.getWorkspaceList();
			
			//loop through the selected workspace
			for(ModifyWorkspace workspace : unarchiveWSList)
			{
				wsInternalId = workspace.getId();
				
				if(wsInternalId != null)
				{
					workspaceId.append(",");
					workspaceId.append(wsInternalId);
				}
			}
			archiveWSManager.unArchiveWorkspace(workspaceId.toString().substring(1), userName);
			
			//frame the model objects
			model.getModelMap().put("success", 1);
			model.getModelMap().put("wsprojectid", projectid);			
		}
		return model;
	}

}
