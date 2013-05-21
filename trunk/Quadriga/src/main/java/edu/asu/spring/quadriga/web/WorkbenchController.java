package edu.asu.spring.quadriga.web;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.service.IProjectManager;
import edu.asu.spring.quadriga.service.IUserManager;

@Controller
public class WorkbenchController {
	
	@Autowired IProjectManager projectmanager;
	List<IProject> projectlist;
	
	@Autowired IUserManager usermanager;
	IUser user;
	
	
	
	@RequestMapping(value="auth/workbench", method = RequestMethod.GET)
	public String userWorkbenchHandle(ModelMap model) throws SQLException {
	
		UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
	    String sUserId = principal.getUsername();
		
	    user =  usermanager.getUserDetails(sUserId);

		projectlist = projectmanager.getProjectsOfUser(sUserId);
		
		model.addAttribute("projectlist", projectlist);
		    
		return "auth/workbench"; 
	}
	
	@RequestMapping(value="auth/workbench/{projectid}", method = RequestMethod.GET)
	public String getProjectPage(@PathVariable("projectid") String id, ModelMap model) {
		IProject project = projectmanager.getProject(id);
		
		model.addAttribute("project", project);
		return "auth/workbench/project";
	}
}
	
