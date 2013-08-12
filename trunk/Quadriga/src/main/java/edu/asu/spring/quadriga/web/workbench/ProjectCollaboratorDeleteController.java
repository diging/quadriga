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
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.workbench.IModifyProjCollabManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjCollabManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.web.login.RoleNames;
import edu.asu.spring.quadriga.web.workbench.backing.CollaboratorsBackBean;

@Controller
public class ProjectCollaboratorDeleteController {
	
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
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		
		binder.registerCustomEditor(List.class, "collaboratorList", new PropertyEditorSupport() {

			@Override
			public void setAsText(String text) {

				String[] userNames = text.split(",");
			
				List<ICollaborator> collaboratorList = new ArrayList<ICollaborator>();
				for (String userName : userNames) {
					ICollaborator collaborator = collaboratorFactory.createCollaborator();
					IUser user = userFactory.createUserObject();
					user.setUserName(userName);
					collaborator.setUserObj(user);
					collaboratorList.add(collaborator);	
				}
				
				setValue(collaboratorList);
			} 	
		}); 
	}
	
	
	@RequestMapping(value = "auth/workbench/{projectid}/deletecollaborator", method = RequestMethod.POST)
	public String deleteCollaborators(@PathVariable("projectid") String projectid,
	@ModelAttribute("collaboratorBackingBean") CollaboratorsBackBean collaboratorBackingBean, BindingResult result,
	Principal principal, Model model ) throws QuadrigaStorageException {
		
		
	System.out.println("--------collaboratorList object "+collaboratorBackingBean.getCollaboratorList());
	
		
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
		
		//ModelAndView model = null;
		//model = new ModelAndView("auth/workbench/showDeleteCollaborators");
		
	/*	String userName = collaborator.getUserObj().getUserName();
		
		String errmsg=""; 
		
		if(result.hasErrors()){	
			model.getModelMap().put("collaborator", collaborator);	
		}
		
		else{
			
			errmsg = modifyProjectCollabManager.deleteCollaboratorRequest(userName, projectid);
			model.getModelMap().put("collaborator", collaboratorFactory.createCollaborator());
		}
		
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
		
		model.getModelMap().put("projectid", projectid); */
		
		//return model;	
	}
	
	
	
	
	
	/*public String deleteCollaborators(@PathVariable("projectid") String projectid,Model model,HttpServletRequest req) throws QuadrigaStorageException
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
		
	}*/
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder, WebDataBinder validateBinder) throws Exception {
		
		binder.registerCustomEditor(List.class, "collaboratorList", new PropertyEditorSupport() {

			@Override
			public void setAsText(String text) {

				String[] roleIds = text.split(",");
//				List<ICollaboratorRole> roles = new ArrayList<ICollaboratorRole>();
//				for (String roleId : roleIds) {
//					ICollaboratorRole role = collaboratorRoleManager.getProjectCollaboratorRoleById(roleId.trim());
//					roles.add(role);
//				}
//				setValue(roles);
				System.out.println(text);
			} 	
		}); 
	}

	
	
	
	@RequestMapping(value = "auth/workbench/{projectid}/showDeleteCollaborators", method = RequestMethod.GET)
	public String displayDeleteCollaborator(@PathVariable("projectid") String projectid, ModelMap model) throws QuadrigaStorageException{
		
		CollaboratorsBackBean collaboratorList = new CollaboratorsBackBean();
		model.addAttribute("collaboratorBackingBean", collaboratorList);
		
		
		
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
