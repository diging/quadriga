package edu.asu.spring.quadriga.web;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.mbeans.RoleMBean;
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

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.service.ICollaboratorRoleMapper;
import edu.asu.spring.quadriga.service.IProjectManager;
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
	private ICollaboratorRoleMapper collaboratorRoleManager;
	
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
					ICollaboratorRole role = collaboratorRoleManager.getCollaboratorRoleId(roleId.trim());
					roles.add(role);
				}
				setValue(roles);
			}
	    	
	    });
	}
	
	@RequestMapping(value = "auth/workbench/{projectid}/showCollaborators", method = RequestMethod.GET)
	public String displayCollaborator(@PathVariable("projectid") int projectid, ModelMap model){

		IProject project = projectmanager.getProject(projectid);
		model.addAttribute("project", project);
		

		List<ICollaborator> collaborators = project.getCollaborators();	
		model.addAttribute("collaborators", collaborators);
		ICollaborator collaborator =  collaboratorFactory.createCollaborator();
		collaborator.setUserObj(userFactory.createUserObject());
		model.addAttribute("collaborator", collaborator);

		List<IUser> notCollaboratingUsers = projectmanager.getNotCollaboratingUsers(projectid);
//		IUser user = userFactory.createUserObject();
//		user.setName("test name");
//		user.setUserName("testid");
//		notCollaboratingUsers.add(user);
		
		model.addAttribute("notCollaboratingUsers", notCollaboratingUsers);
		model.addAttribute("possibleCollaboratorRoles", collaboratorRoleManager.getCollaboratorRoles());
		
		
		
		return "auth/workbench/showCollaborators";

	}
	
	@ModelAttribute
	public ICollaborator createCollaborator() {
		ICollaborator collaborator = collaboratorFactory.createCollaborator();
		collaborator.setUserObj(userFactory.createUserObject());
		return collaborator;
	}
	
	@RequestMapping(value = "auth/workbench/{projectid}/addcollaborator", method = RequestMethod.POST)
	public String addCollaborators( @PathVariable("projectid") int id,   Model model,
			@ModelAttribute ICollaborator collaborator)	
	{
			
		/*List<ICollaborator> collaborators =  project.getCollaborators();
		for(ICollaborator collaborator:collaborators)
		{
			IUser user = collaborator.getUserObj();
			System.out.println("--------- workbench addCollaborators getname():"+user.getName());
		}
		String success = projectmanager.addCollaborators(project);

		/*if(success == 1)
		{
			model.addAttribute("success", 1);
		}
		model.addAttribute("projectid", id);  */
		System.out.println("user " + collaborator.getUserObj().getUserName());
		System.out.println("roles " + collaborator.getCollaboratorRoles());
		
		return "redirect:/auth/workbench/{projectid}"; 
	}
	
//	@RequestMapping(value="auth/workbench/{projectid}/addcollaborator", method = RequestMethod.GET)
//	public String addCollaboratorForm(Model model)
//	{
//		model.addAttribute("project",projectFactory.createProjectObject());
//		return "redirect:/auth/workbench/{projectid}";
//		
//	}

}
