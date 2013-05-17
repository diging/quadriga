package edu.asu.spring.quadriga.web;

import java.security.Principal;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.implementation.Project;
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
		String sUserStatus = null;
				
		user =  usermanager.getUserDetails(sUserId);

		String username = user.getUserName();
		
		projectlist = projectmanager.getProjectsOfUser(sUserId);
		
		model.addAttribute("projectlist", projectlist);
		    
		return "auth/workbench"; 
	}
}
	
