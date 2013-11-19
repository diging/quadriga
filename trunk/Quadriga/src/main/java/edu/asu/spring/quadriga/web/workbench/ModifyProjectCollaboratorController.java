package edu.asu.spring.quadriga.web.workbench;

import java.beans.PropertyEditorSupport;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
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
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.factories.IModifyCollaboratorFormFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.workbench.IModifyProjCollabManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjCollabManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.validator.CollaboratorFormValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaborator;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaboratorForm;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaboratorFormManager;

@Controller
public class ModifyProjectCollaboratorController 
{
	@Autowired
	private IRetrieveProjCollabManager retrieveProjManager;
	
	@Autowired
	private IModifyCollaboratorFormFactory collaboratorFactory;
	
	@Autowired
	private IModifyProjCollabManager projectManager;
	
	@Autowired
	private IRetrieveProjectManager projectDetailsManager;
	
	@Autowired
	private ICollaboratorRoleManager collaboratorRoleManager;
	
	@Autowired
	private CollaboratorFormValidator validator;
	
	@Autowired
	private ModifyCollaboratorFormManager collaboratorManager;
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder,WebDataBinder validateBinder) throws Exception {
		
		validateBinder.setValidator(validator);
		
		binder.registerCustomEditor(List.class, "collaborators.collaboratorRoles", new PropertyEditorSupport() {

			@Override
			public void setAsText(String text) {
				String[] roleIds = text.split(",");
				List<ICollaboratorRole> roles = new ArrayList<ICollaboratorRole>();
				for (String roleId : roleIds) {
					ICollaboratorRole role = collaboratorRoleManager.getProjectCollaboratorRoleById(roleId.trim());
					roles.add(role);
				}
				setValue(roles);
			} 	
		}); 
		
	}

	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT,paramIndex = 1, userRole = {RoleNames.ROLE_COLLABORATOR_ADMIN,RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN} )})
	@RequestMapping(value = "auth/workbench/{projectid}/updatecollaborators", method = RequestMethod.GET)
	public ModelAndView updateCollaboratorRequestForm(@PathVariable("projectid") String projectid) 
			throws QuadrigaStorageException, QuadrigaAccessException
	{
		ModelAndView model;
		ModifyCollaboratorForm collaboratorForm;
		List<ModifyCollaborator> collaboratorList = new ArrayList<ModifyCollaborator>();
		
		//create model view
		model = new ModelAndView("auth/workbench/updatecollaborators");
		
		//retrieve the project details
		IProject project = projectDetailsManager.getProjectDetails(projectid);
		
		//retrieve the list of Collaborators and their roles.
		collaboratorList = collaboratorManager.modifyProjectCollaboratorManager(projectid);
		
		//fetch the roles that can be associated to the workspace collaborator
		List<ICollaboratorRole> collaboratorRoles = collaboratorRoleManager.getProjectCollaboratorRoles();
		
		//create a model for collaborators
		collaboratorForm = collaboratorFactory.createCollaboratorFormObject();
		
		collaboratorForm.setCollaborators(collaboratorList);
		
        //add the collaborator roles to the model
		model.getModelMap().put("projcollabroles", collaboratorRoles);
		model.getModelMap().put("collaboratorform", collaboratorForm);
		model.getModelMap().put("projectid", projectid);
		model.getModel().put("projectname", project.getName());
		model.getModelMap().put("projectdesc",project.getDescription());
		model.getModelMap().put("success", 0);
		
		return model;
	}
	
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT,paramIndex = 3, userRole = {RoleNames.ROLE_COLLABORATOR_ADMIN,RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN} )})
	@RequestMapping(value = "auth/workbench/{projectid}/updatecollaborators", method = RequestMethod.POST)
	public ModelAndView updateCollaboratorRequest(@Validated @ModelAttribute("collaboratorform") ModifyCollaboratorForm collaboratorForm,
			BindingResult result,@PathVariable("projectid") String projectid,Principal principal) 
					throws QuadrigaStorageException, QuadrigaAccessException
	{
		ModelAndView model;
		List<ModifyCollaborator> projCollaborators;
		String userName;
		String collabUser;
		List<ICollaboratorRole> values;
		StringBuilder collabRoles;
		
		userName = principal.getName();
		try
		{
			//create model view
			model = new ModelAndView("auth/workbench/updatecollaborators");
			
			if(result.hasErrors())
			{
				//retrieve the project details
				IProject project = projectDetailsManager.getProjectDetails(projectid);
				
				//add a variable to display the entire page
				model.getModelMap().put("success", 0);

				//add the model map
				projCollaborators = collaboratorManager.modifyProjectCollaboratorManager(projectid);
				collaboratorForm.setCollaborators(projCollaborators);
				model.getModelMap().put("collaboratorform", collaboratorForm);
				model.getModel().put("projectname", project.getName());
				model.getModelMap().put("projectdesc",project.getDescription());
				model.getModelMap().put("projectid", projectid);
				
				//retrieve the collaborator roles and assign it to a map
				//fetch the roles that can be associated to the workspace collaborator
				List<ICollaboratorRole> collaboratorRoles = collaboratorRoleManager.getProjectCollaboratorRoles();
				model.getModelMap().put("projcollabroles", collaboratorRoles);
			}
			else
			{
				projCollaborators = collaboratorForm.getCollaborators();
				
				//fetch the user and his collaborator roles
				for(ModifyCollaborator collab : projCollaborators)
				{
					collabRoles = new StringBuilder();
					collabUser = collab.getUserName();
					values = collab.getCollaboratorRoles();
					
					//fetch the role names for the roles and form a string
					for(ICollaboratorRole role : values)
					{
						collabRoles.append(",");
						collabRoles.append(role.getRoleDBid());
					}
					
				    projectManager.updateCollaboratorRequest(projectid, collabUser, collabRoles.toString().substring(1), userName);
					
					model.getModelMap().put("success", 1);
				}
			}
		}
		catch(HibernateException ex)
		{
			throw new QuadrigaStorageException();
		}
		
		return model;
	}

}
