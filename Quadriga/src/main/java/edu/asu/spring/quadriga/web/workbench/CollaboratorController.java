package edu.asu.spring.quadriga.web.workbench;

import java.beans.PropertyEditorSupport;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.implementation.Collaborator;
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
public class CollaboratorController {

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
					e.printStackTrace();
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

	@RequestMapping(value = "auth/workbench/{projectid}/showCollaborators", method = RequestMethod.GET)
	public String displayCollaborator(@PathVariable("projectid") String projectid, ModelMap model) throws QuadrigaStorageException{
		
		ICollaborator collaborator =  collaboratorFactory.createCollaborator();
		collaborator.setUserObj(userFactory.createUserObject());
		model.addAttribute("collaborator", collaborator);
		
		model.addAttribute("projectid", projectid);
		// retrieve the collaborators who are not associated with project
		List<IUser> nonCollaboratingUsers = projectCollabManager.getProjectNonCollaborators(projectid);

		for(IUser user : nonCollaboratingUsers)
		{
			//fetch the quadriga roles and eliminate the restricted user
			List<IQuadrigaRole> userQuadrigaRole = user.getQuadrigaRoles();
			for(IQuadrigaRole role : userQuadrigaRole)
			{
				if(role.getId().equals(RoleNames.ROLE_QUADRIGA_RESTRICTED))
				{
					nonCollaboratingUsers.remove(user);
				}
			}
		}
		
		model.addAttribute("notCollaboratingUsers", nonCollaboratingUsers);
		
		// mapping collaborator Roles to jsp and restricting ADMIN role for newly added collaborator

		List<ICollaborator> collaboratingUsers = retrieveprojectManager.getCollaboratingUsers(projectid);
		model.addAttribute("collaboratingUsers", collaboratingUsers);
		
		List<ICollaboratorRole> collaboratorRoles = collaboratorRoleManager.getProjectCollaboratorRoles();
		
		Iterator<ICollaboratorRole> rolesIterator = collaboratorRoles.iterator();
		
		while(rolesIterator.hasNext())
		{
			if(rolesIterator.next().getRoleid().equals(RoleNames.ROLE_COLLABORATOR_ADMIN))
			{
				rolesIterator.remove();
			}		
		}
		
		model.addAttribute("possibleCollaboratorRoles", collaboratorRoles);
		
		return "auth/workbench/showCollaborators";

	}

	@ModelAttribute
	public ICollaborator getCollaborator() {
		ICollaborator collaborator = collaboratorFactory.createCollaborator();
		collaborator.setUserObj(userFactory.createUserObject());
		return collaborator;
	} 

	@RequestMapping(value = "auth/workbench/{projectid}/addcollaborators", method = RequestMethod.POST)
	public ModelAndView addCollaborators(@PathVariable("projectid") String projectid,
			@Validated @ModelAttribute("collaborator") Collaborator collaborator, BindingResult result, RedirectAttributes redirectatt,
			Principal principal) throws QuadrigaStorageException
	{
		ModelAndView model = null;
		model = new ModelAndView("auth/workbench/showAddCollaborators");
		
		String errmsg=""; 
		
		String collaboratorUser = collaborator.getUserObj().getUserName();
		List<ICollaboratorRole> roles = collaborator.getCollaboratorRoles();

		if(result.hasErrors())
		{
			model.getModelMap().put("collaborator", collaborator);
		}
		else
		{	
			String userName;
			userName = principal.getName();
			errmsg = modifyProjectCollabManager.addCollaboratorRequest(collaborator, projectid,userName);
			model.getModelMap().put("collaborator", collaboratorFactory.createCollaborator());
		}
		
		List<IUser> nonCollaboratingUsers = retrieveProjCollabManager.getProjectNonCollaborators(projectid);
		model.getModelMap().put("notCollaboratingUsers", nonCollaboratingUsers);
		
		List<ICollaboratorRole> collaboratorRoles = new ArrayList<ICollaboratorRole>();
		collaboratorRoles = collaboratorRoleManager.getProjectCollaboratorRoles();
		Iterator<ICollaboratorRole> iterator = collaboratorRoles.iterator();
		while(iterator.hasNext())
		{
			if(iterator.next().getRoleid().equals(RoleNames.ROLE_COLLABORATOR_ADMIN))
			{
				iterator.remove();
			}
		}
		model.getModelMap().put("possibleCollaboratorRoles", collaboratorRoles);
		
		List<ICollaborator> collaborators = retrieveProjCollabManager.getProjectCollaborators(projectid);
		model.getModelMap().put("collaboratingUsers", collaborators);
		
		return model;
	}
	
	@RequestMapping(value = "auth/workbench/{projectid}/showAddCollaborators", method = RequestMethod.GET)
	public String displayAddCollaborator(@PathVariable("projectid") String projectid, ModelMap model) throws QuadrigaStorageException{
		
		
		ICollaborator collaborator =  collaboratorFactory.createCollaborator();
		collaborator.setUserObj(userFactory.createUserObject());
		model.addAttribute("collaborator", collaborator);
		
		model.addAttribute("projectid", projectid);
		
		// retrieve the collaborators who are not associated with project
		List<IUser> nonCollaboratingUsers = projectCollabManager.getProjectNonCollaborators(projectid);

		for(IUser user : nonCollaboratingUsers)
		{
			//fetch the quadriga roles and eliminate the restricted user
			List<IQuadrigaRole> userQuadrigaRole = user.getQuadrigaRoles();
			for(IQuadrigaRole role : userQuadrigaRole)
			{
				if(role.getId().equals(RoleNames.ROLE_QUADRIGA_RESTRICTED))
				{
					nonCollaboratingUsers.remove(user);
				}
			}
		}
		model.addAttribute("notCollaboratingUsers", nonCollaboratingUsers);

		List<ICollaborator> collaboratingUsers = retrieveprojectManager.getCollaboratingUsers(projectid);
		model.addAttribute("collaboratingUsers", collaboratingUsers);
		
		// mapping collaborator Roles to jsp and restricting ADMIN role for newly added collaborator
		List<ICollaboratorRole> collaboratorRoles = collaboratorRoleManager.getProjectCollaboratorRoles();
		Iterator<ICollaboratorRole> rolesIterator = collaboratorRoles.iterator();
		
		while(rolesIterator.hasNext())
		{
			if(rolesIterator.next().getRoleid().equals(RoleNames.ROLE_COLLABORATOR_ADMIN))
			{
				rolesIterator.remove();
			}		
		}
		
		model.addAttribute("possibleCollaboratorRoles", collaboratorRoles);
		
		return "auth/workbench/showAddCollaborators";
	}
	
	@RequestMapping(value = "auth/workbench/{projectid}/deletecollaborator", method = RequestMethod.POST)
	public String deleteCollaborators(@PathVariable("projectid") String projectid,Model model,HttpServletRequest req) throws QuadrigaStorageException
	{
		String[] collaborators = req.getParameterValues("selected");
		
		String errmsg = null;
		for(int i=0;i<collaborators.length;i++)
		{
		
			errmsg = modifyProjectCollabManager.deleteCollaboratorRequest(collaborators[i], projectid);
			
		} 
		
		if(errmsg.equals("no errors"))
		{
			return "redirect:/auth/workbench/"+projectid+"/showDeleteCollaborators";
		}
		
		return "redirect:/auth/workbench/{projectid}";
		
	}
	
	
	
	@RequestMapping(value = "auth/workbench/{projectid}/showDeleteCollaborators", method = RequestMethod.GET)
	public String displayDeleteCollaborator(@PathVariable("projectid") String projectid, ModelMap model) throws QuadrigaStorageException{
		
		
		ICollaborator collaborator =  collaboratorFactory.createCollaborator();
		collaborator.setUserObj(userFactory.createUserObject());
		model.addAttribute("collaborator", collaborator);
		
		model.addAttribute("projectid", projectid);
		// retrieve the collaborators who are not associated with project
		List<IUser> nonCollaboratingUsers = projectCollabManager.getProjectNonCollaborators(projectid);

		for(IUser user : nonCollaboratingUsers)
		{
			//fetch the quadriga roles and eliminate the restricted user
			List<IQuadrigaRole> userQuadrigaRole = user.getQuadrigaRoles();
			for(IQuadrigaRole role : userQuadrigaRole)
			{
				if(role.getId().equals(RoleNames.ROLE_QUADRIGA_RESTRICTED))
				{
					nonCollaboratingUsers.remove(user);
				}
			}
		}
		
		model.addAttribute("notCollaboratingUsers", nonCollaboratingUsers);
		
		List<ICollaborator> collaboratingUsers = retrieveprojectManager.getCollaboratingUsers(projectid);
		model.addAttribute("collaboratingUsers", collaboratingUsers);
		
		// mapping collaborator Roles to jsp and restricting ADMIN role for newly added collaborator

		List<ICollaboratorRole> collaboratorRoles = collaboratorRoleManager.getProjectCollaboratorRoles();
		Iterator<ICollaboratorRole> rolesIterator = collaboratorRoles.iterator();
		
		while(rolesIterator.hasNext())
		{
			if(rolesIterator.next().getRoleid().equals(RoleNames.ROLE_COLLABORATOR_ADMIN))
			{
				rolesIterator.remove();
			}		
		}
		
		model.addAttribute("possibleCollaboratorRoles", collaboratorRoles);
		
		return "auth/workbench/showDeleteCollaborators";
	}


}

 
