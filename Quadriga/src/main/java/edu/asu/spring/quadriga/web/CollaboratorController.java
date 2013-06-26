package edu.asu.spring.quadriga.web;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.IProjectManager;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;

@Controller
public class CollaboratorController {

	@Autowired 
	IProjectManager projectmanager;
	
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
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
	    binder.registerCustomEditor(IUser.class, "userObj", new PropertyEditorSupport() {
	    @Override
	    public void setAsText(String text) {

	        IUser user = usermanager.getUserDetails(text);
	        setValue(user);
	    }
	    });
	    binder.registerCustomEditor(List.class, "collaboratorRoles", new PropertyEditorSupport() {

			@Override
			public void setAsText(String text) {

			   String[] roleIds = text.split(",");
				List<ICollaboratorRole> roles = new ArrayList<ICollaboratorRole>();
				for (String roleId : roleIds) {
					ICollaboratorRole role = collaboratorRoleManager.getCollaboratorRoleById(roleId.trim());
					roles.add(role);
				}
				setValue(roles);
			} 	
	    }); 
	}
	
	@RequestMapping(value = "auth/workbench/{projectid}/showCollaborators", method = RequestMethod.GET)
	public String displayCollaborator(@PathVariable("projectid") int projectid, ModelMap model) throws QuadrigaStorageException{
        IProject project = null;
		try
        {
		 project = projectmanager.getProject(projectid);
        }
        catch(QuadrigaStorageException e){
			throw new QuadrigaStorageException();
		} 
		model.addAttribute("project", project); 
		
		ICollaborator collaborator =  collaboratorFactory.createCollaborator();
		collaborator.setUserObj(userFactory.createUserObject());
		model.addAttribute("collaborator", collaborator); 

		// mapping collaborators which are not part of the project and restricting users which
		// have user role as Restricted User
		try
		{
			List<IUser> notCollaboratingUsers = projectmanager.getNotCollaboratingUsers(projectid);
			Iterator<IUser> notCollaboratingUsersIterator = notCollaboratingUsers.iterator();
						
			while(notCollaboratingUsersIterator.hasNext())
			{   IUser user = notCollaboratingUsersIterator.next();
				List<IQuadrigaRole> nonCollabUserQuadrigaRole = user.getQuadrigaRoles();
				Iterator<IQuadrigaRole> nonCollabUserRoleIterator = nonCollabUserQuadrigaRole.iterator();
				
				while(nonCollabUserRoleIterator.hasNext())
				{
					if((nonCollabUserRoleIterator.next().getDBid()).equals("role6"))
					{
						notCollaboratingUsersIterator.remove();
					}   
				}
				
			}
			model.addAttribute("notCollaboratingUsers", notCollaboratingUsers);
			
			// mapping collaborator Roles to jsp and restricting ADMIN role for newly added collaborator
						
			List<ICollaboratorRole> collaboratorRoles = new ArrayList<ICollaboratorRole>();
			collaboratorRoles = collaboratorRoleManager.getProjectCollaboratorRoles();
			Iterator<ICollaboratorRole> collaboratorRoleIterator = collaboratorRoles.iterator();
			
			while(collaboratorRoleIterator.hasNext())
			{
				if((collaboratorRoleIterator.next().getRoleid()).equals("ADMIN"))
				{
					collaboratorRoleIterator.remove();
				}
			}
			model.addAttribute("possibleCollaboratorRoles", collaboratorRoles);	
		
			List<IUser> existingCollaborators =  projectmanager.showExistingCollaborator(projectid);
			model.addAttribute("existingCollaborators", existingCollaborators);	
		}
		catch(QuadrigaStorageException e){
			throw new QuadrigaStorageException();
		}		
		
		return "auth/workbench/showCollaborators";

	}
	
	@ModelAttribute
	public ICollaborator getCollaborator() {
		ICollaborator collaborator = collaboratorFactory.createCollaborator();
		collaborator.setUserObj(userFactory.createUserObject());
		return collaborator;
	} 
	
	@RequestMapping(value = "auth/workbench/{projectid}/addcollaborator", method = RequestMethod.POST)
	public String addCollaborators(
			@PathVariable("projectid") int projectid, Model model, @ModelAttribute ICollaborator collaborator, RedirectAttributes redirectatt)
	{
		
		String errmsg = null;
		try {
			 errmsg = projectmanager.addCollaborators(collaborator,projectid);


		} catch (QuadrigaStorageException e) {
			e.printStackTrace();
		}

		if(errmsg.equals(""))
		{
			redirectatt.addFlashAttribute("success", "1");
		//	redirectatt.addAttribute("successmsg", "collaborator added successfully");
			return "redirect:/auth/workbench/{projectid}"; 
		}	
		else
		{
			model.addAttribute("errormsg", errmsg);
			return "redirect:/auth/workbench/" + projectid + "/showCollaborators";
		}
	}
	
	
}
