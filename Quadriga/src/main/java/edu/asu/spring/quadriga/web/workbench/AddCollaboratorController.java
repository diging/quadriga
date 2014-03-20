package edu.asu.spring.quadriga.web.workbench;

import java.beans.PropertyEditorSupport;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateSystemException;
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
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.implementation.Collaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.workbench.IModifyProjCollabManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjCollabManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.validator.CollaboratorValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class AddCollaboratorController {

	@Autowired
	IRetrieveProjectManager retrieveprojectManager;

	@Autowired
	IRetrieveProjCollabManager projectCollabManager;

	@Autowired
	IModifyProjCollabManager modifyProjectCollabManager;
	
	@Autowired
	IRetrieveProjCollabManager retrieveProjCollabManager;

	@Autowired 
	IUserManager usermanager;

	@Autowired
	private IUserFactory userFactory;

	@Autowired
	ICollaboratorFactory collaboratorFactory;

	@Autowired
	private ICollaboratorRoleManager collaboratorRoleManager;

	@Autowired
	private IQuadrigaRoleManager quadrigaRoleManager;
	
	@Autowired
	private CollaboratorValidator collaboratorValidator;
	
	private static final Logger logger = LoggerFactory
			.getLogger(AddCollaboratorController.class);
	

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder, WebDataBinder validateBinder) throws Exception {
		
		validateBinder.setValidator(collaboratorValidator);
		
		binder.registerCustomEditor(IUser.class, "userObj", new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {

				IUser user;
				try {
					user = usermanager.getUserDetails(text);
					setValue(user);
				} catch (QuadrigaStorageException e) {
					logger.error("collaborator validator UserObj ",e);
				}
				
			}
		});
		binder.registerCustomEditor(List.class, "collaboratorRoles", new PropertyEditorSupport() {

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

	@ModelAttribute
	public ICollaborator getCollaborator() {
		ICollaborator collaborator = collaboratorFactory.createCollaborator();
		collaborator.setUserObj(userFactory.createUserObject());
		return collaborator;
	} 

	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT,paramIndex = 1, userRole = {RoleNames.ROLE_COLLABORATOR_ADMIN,RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN} )})
	@RequestMapping(value = "auth/workbench/{projectid}/addcollaborators", method = RequestMethod.GET)
	public ModelAndView displayAddCollaborator(@PathVariable("projectid") String projectid, Principal principal) 
			throws QuadrigaStorageException, QuadrigaAccessException 
	{
		
		ModelAndView model;
		
		//retrieve the project details
		IProject project = retrieveprojectManager.getProjectDetails(projectid);
		ICollaborator collaborator =  collaboratorFactory.createCollaborator();
		collaborator.setUserObj(userFactory.createUserObject());
		
		model = new ModelAndView("auth/workbench/addcollaborators");
		model.getModel().put("collaborator", collaborator);
		model.getModel().put("projectname", project.getName());
		model.getModelMap().put("projectdesc",project.getDescription());
		model.getModelMap().put("projectid", projectid);
		
		
		// retrieve the collaborators who are not associated with project
		List<IUser> nonCollaboratingUsers = projectCollabManager.getProjectNonCollaborators(projectid);

		//remove the restricted user
		Iterator<IUser> userIterator = nonCollaboratingUsers.iterator();
		while(userIterator.hasNext())
		{
			//fetch the quadriga roles and eliminate the restricted user
			IUser user = userIterator.next();
			List<IQuadrigaRole> userQuadrigaRole = user.getQuadrigaRoles();
			for(IQuadrigaRole role : userQuadrigaRole)
			{
				if( (role.getId().equals(RoleNames.ROLE_QUADRIGA_RESTRICTED)) || (user.getUserName().equals(principal.getName())) )
				{
					userIterator.remove();
					break;
				}
			}
		}
		model.getModelMap().put("notCollaboratingUsers", nonCollaboratingUsers);

		List<ICollaborator> collaboratingUsers = retrieveprojectManager.getCollaboratingUsers(projectid);
		model.getModelMap().put("collaboratingUsers", collaboratingUsers);
		
		// mapping collaborator Roles to jsp and restricting ADMIN role for newly added collaborator
		List<ICollaboratorRole> collaboratorRoles = collaboratorRoleManager.getProjectCollaboratorRoles();
		
		Iterator<ICollaboratorRole> collabRoleIterator = collaboratorRoles.iterator();
		while(collabRoleIterator.hasNext())
		{
			ICollaboratorRole collabRole = collabRoleIterator.next();
			if(collabRole.getRoleid().equals(RoleNames.ROLE_COLLABORATOR_ADMIN))
			{
				collabRoleIterator.remove();
			}
		}
		
		model.getModelMap().put("possibleCollaboratorRoles", collaboratorRoles);
		
		return model;
	}
	
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT,paramIndex = 1, userRole = {RoleNames.ROLE_COLLABORATOR_ADMIN,RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN} )})
	@RequestMapping(value = "auth/workbench/{projectid}/addcollaborators", method = RequestMethod.POST)
	public ModelAndView addCollaborators(@PathVariable("projectid") String projectid,
			@Validated @ModelAttribute("collaborator") Collaborator collaborator, BindingResult result,
			Principal principal) throws QuadrigaStorageException, QuadrigaAccessException
	{
		
		ModelAndView model = null;
		model = new ModelAndView("auth/workbench/addcollaborators");
		try
		{
			//retrieve the project details
			IProject project = retrieveprojectManager.getProjectDetails(projectid);
			model.getModelMap().put("projectname", project.getName());
			model.getModelMap().put("projectdesc", project.getDescription());
			model.getModelMap().put("projectid", projectid);
			
			if(result.hasErrors())
			{
				model.getModelMap().put("collaborator", collaborator);
			}
			else
			{	
				String userName;
				userName = principal.getName();
				modifyProjectCollabManager.addCollaboratorRequest(collaborator, projectid,userName);
				model.getModelMap().put("collaborator", collaboratorFactory.createCollaborator());
			}
			
			List<IUser> nonCollaboratingUsers = retrieveProjCollabManager.getProjectNonCollaborators(projectid);
			//remove the restricted user
			Iterator<IUser> userIterator = nonCollaboratingUsers.iterator();
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
			model.getModelMap().put("notCollaboratingUsers", nonCollaboratingUsers);
			
			List<ICollaboratorRole> collaboratorRoles = new ArrayList<ICollaboratorRole>();
			collaboratorRoles = collaboratorRoleManager.getProjectCollaboratorRoles();
			Iterator<ICollaboratorRole> collabRoleIterator = collaboratorRoles.iterator();
			while(collabRoleIterator.hasNext())
			{
				ICollaboratorRole collabRole = collabRoleIterator.next();
				if(collabRole.getRoleid().equals(RoleNames.ROLE_COLLABORATOR_ADMIN))
				{
					collabRoleIterator.remove();
				}
			}
			model.getModelMap().put("possibleCollaboratorRoles", collaboratorRoles);
			
			List<ICollaborator> collaborators = retrieveProjCollabManager.getProjectCollaborators(projectid);
			model.getModelMap().put("collaboratingUsers", collaborators);
		}
		catch(HibernateSystemException ex)
		{
			throw new QuadrigaStorageException();
		}

		
		return model;
	}
}

 
