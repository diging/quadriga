package edu.asu.spring.quadriga.web.workbench;

import java.security.Principal;
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
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.IModifyCollaboratorFormFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IProjectCollaboratorManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.validator.CollaboratorFormDeleteValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaborator;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaboratorForm;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaboratorFormManager;

@Controller
public class DeleteProjectCollaboratorController {
	
	@Autowired
	private IProjectCollaboratorManager projectCollaboratorManager;
	
	@Autowired
	private IUserFactory userFactory;

	@Autowired
	private ICollaboratorFactory collaboratorFactory;

	@Autowired
	private IRetrieveProjectManager retrieveprojectManager;
	
	@Autowired
	private IProjectCollaboratorManager projectCollabManager;
	
	@Autowired
	private CollaboratorFormDeleteValidator validator;
	
	@Autowired
	private IModifyCollaboratorFormFactory collaboratorFormFactory;
	
	@Autowired
	private ModifyCollaboratorFormManager collaboratorFormManager;
	
	@InitBinder
	protected void initBinder(WebDataBinder validateBinder) throws Exception {
		validateBinder.setValidator(validator);
}
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT,paramIndex = 1, userRole = {RoleNames.ROLE_COLLABORATOR_ADMIN,RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN} )})
	@RequestMapping(value = "auth/workbench/{projectid}/deletecollaborators", method = RequestMethod.GET)
	public ModelAndView displayDeleteCollaborator(@PathVariable("projectid") String projectId) 
			throws QuadrigaStorageException, QuadrigaAccessException
			{
		
		ModelAndView modelAndView;
		modelAndView = new ModelAndView("auth/workbench/deletecollaborators");
		ModifyCollaboratorForm collaboratorForm;
		
		//retrieve project details
		IProject project = retrieveprojectManager.getProjectDetails(projectId);
		
		collaboratorForm = collaboratorFormFactory.createCollaboratorFormObject();
		
		List<ModifyCollaborator> modifyCollaborator = collaboratorFormManager.modifyProjectCollaboratorManager(projectId);
		
		collaboratorForm.setCollaborators(modifyCollaborator);
		
		modelAndView.getModelMap().put("collaboratorForm", collaboratorForm);
		modelAndView.getModelMap().put("projectId", projectId);
		modelAndView.getModel().put("projectname", project.getProjectName());
		modelAndView.getModelMap().put("projectdesc",project.getDescription());
		
		modelAndView.getModelMap().put("success", 0);
				
		return modelAndView;
	}
	
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT,paramIndex = 1, userRole = {RoleNames.ROLE_COLLABORATOR_ADMIN,RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN} )})
	@RequestMapping(value = "auth/workbench/{projectid}/deletecollaborators", method = RequestMethod.POST)
	public ModelAndView deleteCollaborators(@PathVariable("projectid") String projectId,
	@Validated @ModelAttribute("collaboratorForm") ModifyCollaboratorForm collaboratorForm, BindingResult result,
	Principal principal) throws QuadrigaStorageException, QuadrigaAccessException {
		
		ModelAndView modelAndView ;
		List<ModifyCollaborator>collaborators;
		String userName;
		modelAndView = new ModelAndView("auth/workbench/deletecollaborators");

		if(result.hasErrors())
		{
			collaborators = collaboratorFormManager.modifyProjectCollaboratorManager(projectId);
			collaboratorForm.setCollaborators(collaborators);
			
			//retrieve project details
			IProject project = retrieveprojectManager.getProjectDetails(projectId);
			modelAndView.getModelMap().put("collaboratorForm", collaboratorForm);
			modelAndView.getModelMap().put("success", 0);
			modelAndView.getModelMap().put("error", 1);
			modelAndView.getModelMap().put("projectId", projectId);
			modelAndView.getModel().put("projectname", project.getProjectName());
			modelAndView.getModelMap().put("projectdesc",project.getDescription());
		}
		
		else
		{
		    collaborators = collaboratorForm.getCollaborators();
			for(ModifyCollaborator collaborator: collaborators)
			{
			    userName = collaborator.getUserName();
			    if(userName!=null)
			    {
			        projectCollaboratorManager.deleteCollaboratorRequest(userName, projectId);
			    }
			}
			modelAndView.getModelMap().put("success", 1);
			modelAndView.getModelMap().put("projectId", projectId);
		}
		
		return modelAndView;	
	}
}
