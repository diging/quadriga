package edu.asu.spring.quadriga.web.workbench;

import java.beans.PropertyEditorSupport;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.IModifyCollaboratorFormFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.workbench.IModifyProjCollabManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjCollabManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.validator.CollaboratorFormDeleteValidator;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaborator;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaboratorForm;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaboratorFormManager;

@Controller
public class DeleteProjectCollaboratorController {
	
	@Autowired
	private IModifyProjCollabManager modifyProjectCollabManager;

	@Autowired
	private IRetrieveProjCollabManager retrieveProjCollabManager;
	
	@Autowired
	private IUserFactory userFactory;

	@Autowired
	private ICollaboratorFactory collaboratorFactory;

	@Autowired
	private ICollaboratorRoleManager collaboratorRoleManager;

	@Autowired
	private IQuadrigaRoleManager quadrigaRoleManager;
	
	@Autowired
	private IRetrieveProjectManager retrieveprojectManager;
	
	@Autowired
	private IRetrieveProjCollabManager projectCollabManager;
	
	@Autowired
	private CollaboratorFormDeleteValidator validator;
	
	@Autowired
	private IModifyCollaboratorFormFactory collaboratorFormFactory;
	
	@Autowired
	private ModifyCollaboratorFormManager collaboratorFormManager;
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder, WebDataBinder validateBinder) throws Exception {
		
		validateBinder.setValidator(validator);
		
		binder.registerCustomEditor(List.class, "collaborators.collaboratorRoles", new PropertyEditorSupport() {
		
		@Override
		public void setAsText(String text) {						
		String[] roleIds = text.split(",");
		List<ICollaboratorRole> collaboratorRoles = new ArrayList<ICollaboratorRole>();
		for(String role:roleIds){			
			ICollaboratorRole collabrole = collaboratorRoleManager.getProjectCollaboratorRoleById(role.trim());
			collaboratorRoles.add(collabrole);
		}
				
		setValue(collaboratorRoles);
		}
		
	 });
}
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT,paramIndex = 1, userRole = {"ADMIN","PROJECT_ADMIN" } )})
	@RequestMapping(value = "auth/workbench/{projectid}/deletecollaborators", method = RequestMethod.GET)
	public ModelAndView displayDeleteCollaborator(@PathVariable("projectid") String projectId) 
			throws QuadrigaStorageException, QuadrigaAccessException
			{
		
		ModelAndView modelAndView;
		modelAndView = new ModelAndView("auth/workbench/deletecollaborators");
		ModifyCollaboratorForm collaboratorForm;
		
		collaboratorForm = collaboratorFormFactory.createCollaboratorFormObject();
		
		List<ModifyCollaborator> modifyCollaborator = collaboratorFormManager.modifyCollaboratorManager(projectId);
		
		collaboratorForm.setCollaborators(modifyCollaborator);
		
		modelAndView.getModelMap().put("collaboratorForm", collaboratorForm);
		modelAndView.getModelMap().put("projectId", projectId);
		
		modelAndView.getModelMap().put("success", 0);
				
		return modelAndView;
	}
	
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT,paramIndex = 1, userRole = {"ADMIN","PROJECT_ADMIN" } )})
	@RequestMapping(value = "auth/workbench/{projectid}/deletecollaborators", method = RequestMethod.POST)
	public ModelAndView deleteCollaborators(@PathVariable("projectid") String projectId,
	@Validated @ModelAttribute("collaboratorForm") ModifyCollaboratorForm collaboratorForm, BindingResult result,
	Principal principal) throws QuadrigaStorageException, QuadrigaAccessException {
		
		ModelAndView modelAndView ;
		List<ModifyCollaborator>collaborators;
		modelAndView = new ModelAndView("auth/workbench/deletecollaborators");

		if(result.hasErrors())
		{
			collaborators = collaboratorFormManager.modifyCollaboratorManager(projectId);
			collaboratorForm.setCollaborators(collaborators);
			
			modelAndView.getModelMap().put("collaboratorForm", collaboratorForm);
			modelAndView.getModelMap().put("success", 0);
			modelAndView.getModelMap().put("projectId", projectId);
		}
		
		else
		{
		    collaborators = collaboratorForm.getCollaborators();
			for(ModifyCollaborator collaborator: collaborators)
			{
				modifyProjectCollabManager.deleteCollaboratorRequest(collaborator.getUserName(), projectId);
			}
			
			List<ModifyCollaborator> collaboratorsList = collaboratorFormManager.modifyCollaboratorManager(projectId);
			collaboratorForm.setCollaborators(collaboratorsList);
		
			modelAndView.getModelMap().put("collaboratorForm", collaboratorForm);
			modelAndView.getModelMap().put("success", 0);
			modelAndView.getModelMap().put("projectId", projectId);
		}
		
		return modelAndView;	
	}
}
