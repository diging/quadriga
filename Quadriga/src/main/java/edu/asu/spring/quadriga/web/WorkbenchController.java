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

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.service.IProjectManager;
import edu.asu.spring.quadriga.service.IUserManager;

/**
 * @Description : this class will handle all workbench components for user projects like 
 * 				  displaying project details , workspace, collaborating users for the project 
 * 
 * @author 		: rohit
 * 
 */

@Controller
public class WorkbenchController {
	
	@Autowired IProjectManager projectmanager;
	List<IProject> projectlist;
	String username;
	IProject project;
	List<IUser> collaboratorList;
	
	@Autowired IUserManager usermanager;
	IUser user;
	
	/**
	 * @description this method acts as a controller for handling all the activities on the workbench
	 * 				home page 
	 * 
	 * @param 		model maps projectlist to view (jsp page) 
	 * 
	 * @return 		string for workbench url 
	 * 
	 * @throws 		SQLException
	 * 
	 * @author 		rohit sukleshwar pendbhaje
	 *
	 */
	
	@RequestMapping(value="auth/workbench", method = RequestMethod.GET)
	public String projectWorkbenchHandle(ModelMap model) throws SQLException {
	
		UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
	    String userId = principal.getUsername();
	    
	    projectlist = projectmanager.getProjectsOfUser(userId);
	    model.addAttribute("projectlist", projectlist);
	   
	    user =  usermanager.getUserDetails(userId);
	    username = user.getName();
	    model.addAttribute("username", username);
 
	  // collaboratorList = project.getCollaborator();

	  // model.addAttribute("collaboratorList",collaboratorList);
	   
	    
	   	return "auth/workbench"; 
	}
	
	/**
	 * @description this method used to map user request to particular project 
	 * 
	 * @param 		model maps projectlist to view (jsp page) 
	 * 
	 * @param		projectid id of the selected project
	 * 
	 * @return 		string of project details url
	 * 
	 * @throws 		SQLException
	 *
	 * @author 		rohit sukleshwar pendbhaje
	 */
	
	
	@RequestMapping(value="auth/workbench/{projectid}", method = RequestMethod.GET)
	public String getProjectPage(@PathVariable("projectid") String id, ModelMap model) throws SQLException {
		
		IProject project = projectmanager.getProject(id);
		
		model.addAttribute("project", project);

		return "auth/workbench/project";
	}
}
	
