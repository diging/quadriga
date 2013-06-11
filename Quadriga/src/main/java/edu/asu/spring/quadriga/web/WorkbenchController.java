package edu.asu.spring.quadriga.web;

import java.security.Principal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.ICommunity;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.domain.factories.IProjectFactory;
import edu.asu.spring.quadriga.domain.implementation.Community;
import edu.asu.spring.quadriga.domain.implementation.Project;
import edu.asu.spring.quadriga.service.IDspaceManager;
import edu.asu.spring.quadriga.service.IProjectManager;
import edu.asu.spring.quadriga.service.IUserManager;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Description : this class will handle all workbench components for user projects like 
 * 				  displaying project details , workspace, collaborating users for the project 
 * 
 * @author 		: rohit
 * 
 */

@Controller
public class WorkbenchController {

	private static final Logger logger = LoggerFactory.getLogger(WorkbenchController.class);

	@Autowired IProjectManager projectmanager;
	List<IProject> projectlist;
	String username;
	IProject project;
	List<IUser> collaboratorList;

	@Autowired 
	IUserManager usermanager;

	@Autowired 
	IProjectFactory projectFactory;
	IUser user;
	
	@Autowired
	private IDspaceManager dspaceManager;

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

		String userName = principal.getUsername();
		
		projectlist = projectmanager.getProjectsOfUser(userName);
		model.addAttribute("projectlist", projectlist);

		user =  usermanager.getUserDetails(userName);
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
	public String getProjectPage(@PathVariable("projectid") String projectid, ModelMap model) throws SQLException {

		IProject project = projectmanager.getProject(projectid);
		model.addAttribute("project", project);

		return "auth/workbench/project";
	}

	@RequestMapping(value = "auth/workbench/addproject", method = RequestMethod.GET)
	public ModelAndView addprojectform() {
		return new ModelAndView("auth/workbench/addproject", "command",projectFactory.createProjectObject());
	}

	@RequestMapping(value = "auth/workbench/addproject", method = RequestMethod.POST)
	public String addProject(@ModelAttribute("SpringWeb")Project project, 
			ModelMap model, Principal principal) 
	{
		int success;
		IUser user = usermanager.getUserDetails(principal.getName());
		if(user!=null)
		{
			project.setOwner(user);

			success = projectmanager.addNewProject(project);
			if(success == 1)
			{
				model.addAttribute("success", 1);
			}

		}
		return "redirect:auth/workbench/addProjectStatus";
	}
	
	
	/**
	 * Simply selects the workspace communities view to render by returning its path.
	 */
	@RequestMapping(value = "/auth/workbench/workspace", method = RequestMethod.GET)
	public String workspaceRequest(ModelMap model, Principal principal) {
		String sPassword = (String)SecurityContextHolder.getContext().getAuthentication().getCredentials();
		dspaceManager.checkRestConnection(principal.getName(),sPassword);
		return "redirect:/auth/workbench/workspace/communities";
	}

	@RequestMapping(value = "/auth/workbench/workspace/communities", method = RequestMethod.GET)
	public String workspaceCommunityListRequest(ModelMap model, Principal principal) {
		
		String sPassword = (String)SecurityContextHolder.getContext().getAuthentication().getCredentials();
		
		List<ICommunity> communities = dspaceManager.getAllCommunities(principal.getName(),sPassword);

		model.addAttribute("communityList", communities);

		return "auth/workbench/workspace/communities";
	}
	
	@RequestMapping(value = "/auth/workbench/workspace/community/{communityTitle}", method = RequestMethod.GET)
	public String workspaceCommunityRequest(@PathVariable("communityTitle") String communityTitle, ModelMap model, Principal principal) {
		
		String sPassword = (String)SecurityContextHolder.getContext().getAuthentication().getCredentials();
		List<ICollection> collections = dspaceManager.getAllCollections(principal.getName(),sPassword, communityTitle);

		model.addAttribute("communityTitle", communityTitle);
		model.addAttribute("collectionList", collections);

		return "auth/workbench/workspace/community/collection";
	}
}

