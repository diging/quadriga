package edu.asu.spring.quadriga.web;

import java.security.Principal;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICommunity;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.IProjectFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.implementation.Project;
import edu.asu.spring.quadriga.dspace.service.IDspaceCollection;
import edu.asu.spring.quadriga.dspace.service.IDspaceCommunity;
import edu.asu.spring.quadriga.dspace.service.IDspaceManager;
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

	private static final Logger logger = LoggerFactory.getLogger(WorkbenchController.class);

	@Autowired 
	IProjectManager projectmanager;
	
	@Autowired 
	IUserManager usermanager;

	@Autowired 
	IProjectFactory projectFactory;
	
	@Autowired
	private IUserFactory userFactory;

	@Autowired
	ICollaboratorFactory collaboratorFactory;

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

		List<IProject>  projectlist = projectmanager.getProjectsOfUser(userName);
		model.addAttribute("projectlist", projectlist);

		IUser user =  usermanager.getUserDetails(userName);

		String username = user.getName();
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
	public String getProjectPage(@PathVariable("projectid") int projectid, ModelMap model) throws SQLException {

		IProject project = projectmanager.getProject(projectid);

		model.addAttribute("project", project);

		ICollaborator collaborator = null;
		/*	int success = projectmanager.addCollaborators(collaborator);
		if(success == 1)
		{
			model.addAttribute("success", 1);
		}*/


		return "auth/workbench/project";
	}

	/**
	 * @description  : This method is called during the load of add project
	 *                 request form
	 * @param        : m -  model object
	 * 
	 * @return       : String - the URL of the form
	 * 
	 * @author       : Kiran Kumar Batna
	 */
	@RequestMapping(value="auth/workbench/addproject", method=RequestMethod.GET)
	public String addprojectform(Model model)
	{
		model.addAttribute("project",projectFactory.createProjectObject());
		return "auth/workbench/addproject"; 
	}

	/**
	 * @description  : This method call the usermanager to insert the record in
	 *                 the database on form submission
	 * @param        : project - object containing the form details.
	 * @param        : model
	 * @param        : principal
	 * @return       : String - the URL on success and failure
	 * @author       : Kiran Kumar Batna
	 */
	@RequestMapping(value = "auth/workbench/addproject", method = RequestMethod.POST)
	public String addProject(@ModelAttribute("SpringWeb")Project project, 
			ModelMap model, Principal principal) 
	{
		String errmsg;
		IUser user = usermanager.getUserDetails(principal.getName());
		if(user!=null)
		{
			project.setOwner(user);

			errmsg = projectmanager.addNewProject(project);

			if(errmsg.equals(""))
			{
				model.addAttribute("success", 1);
				model.addAttribute("successMsg","Project created successfully.");
				return "auth/workbench/addProjectStatus";
			}else{
				model.addAttribute("project", project);
				model.addAttribute("success", 0);
				model.addAttribute("errormsg", errmsg);
				return "auth/workbench/addproject";
			}
		}
		return "auth/workbench/addProjectStatus";
	}

	/**
	 * @description : This method calls usermanager to delete the projects.
	 *                for deletion
	 * @param       : model
	 * @param       : principal
	 * @return      : String - URL on success or failure.
	 * @author      : Kiran Kumar Batna
	 */
	@RequestMapping(value="auth/workbench/deleteproject", method=RequestMethod.GET)
	public String deleteProjectform(Model model,Principal principal)
	{
		String userName;
		List<IProject> projectlist =null;

		try
		{
				userName = principal.getName();
				projectlist = projectmanager.getProjectsOfUser(userName);
			
			//adding the project details to the model
 			model.addAttribute("projectlist", projectlist);
			
		}
		catch(SQLException ex)
		{
			throw new RuntimeException(ex.getMessage());
		}
		return "auth/workbench/deleteproject";
	}
	
	/**
	 * @description :  This method calls the user manager to delete the projects.
	 * @param       :  req
	 * @param       :  model
	 * @param       : principal
	 * @return      : String - URL on success and failure.
	 * @author      : Kiran Kumar Batna
	 */
	@RequestMapping(value = "auth/workbench/deleteproject", method = RequestMethod.POST)
	public String deleteProject(HttpServletRequest req, ModelMap model,Principal principal)
	{
		String[] values;
		String projIdList = "";
		String errmsg;
		String userName;
		List<IProject> projectlist =null;
		
		// fetch the selected values
		values = req.getParameterValues("projchecked");
		
		for(String projid : values)
		{
			projIdList = projIdList + "," + projid;
		}
		
		//removing the first ',' value
		projIdList = projIdList.substring(1,projIdList.length());
		
		errmsg = projectmanager.deleteProject(projIdList);
		
		if(errmsg.equals(""))
		{
			model.addAttribute("success", 1);
			model.addAttribute("successMsg","Projects deleted successfully.");
			return "auth/workbench/deleteProjectStatus";
		}
		else
		{
			try
			{
					userName = principal.getName();
					projectlist = projectmanager.getProjectsOfUser(userName);
				
				//adding the project details to the model
				model.addAttribute("projectlist", projectlist);
				model.addAttribute("success", 0);
				model.addAttribute("errormsg", errmsg);
				return "auth/workbench/deleteproject";
			}
			catch(SQLException ex)
			{
				throw new RuntimeException(ex.getMessage());
			}
		}
	}
	
	/**
	 * Simply selects the workspace communities view to render by returning its path.
	 */
	@RequestMapping(value = "/auth/workbench/workspace", method = RequestMethod.GET)
	public String workspaceRequest(ModelMap model, Principal principal) {
		return "redirect:/auth/workbench/workspace/communities";
	}

	@RequestMapping(value = "/auth/workbench/workspace/communities", method = RequestMethod.GET)
	public String workspaceCommunityListRequest(ModelMap model, Principal principal) {
		String sPassword = (String)SecurityContextHolder.getContext().getAuthentication().getCredentials();

		List<ICommunity> communities = dspaceManager.getAllCommunities(principal.getName(),sPassword);

		model.addAttribute("communityList", communities);

		return "auth/workbench/workspace/communities";
	}
	
	@RequestMapping(value = "/auth/workbench/workspace/communities-collections", method = RequestMethod.GET)
	public @ResponseBody String getCommunitiesAndCollections() {
		String sUserName = (String)SecurityContextHolder.getContext().getAuthentication().getName();
		String sPassword = (String)SecurityContextHolder.getContext().getAuthentication().getCredentials();
		StringBuilder sResult = new StringBuilder();
		List<ICommunity> communities = dspaceManager.getAllCommunities(sUserName,sPassword);
		
		if(communities.size()>0)
		{
			sResult.append("<h2>Communities in HPS Repository</h2>");
			sResult.append("<span class=\"byline\">Select a community to browse its collections.</span>");
			sResult.append("<span style=\"font-weight: bold\">");
			for(ICommunity community: communities)
			{
				sResult.append("<a href='/quadriga/auth/workspace/community/"+community.getId()+"'>");
				sResult.append(community.getName());
				sResult.append("</a>");
				sResult.append("<br />");
			}
			sResult.append("</span>");
		}
		else
		{
			sResult.append("<h2>No Communities in HPS Repository</h2>");
		}
		
		return sResult.toString();
	}
	

	@RequestMapping(value = "/auth/workbench/workspace/community/{communityTitle}", method = RequestMethod.GET)
	public String workspaceCommunityRequest(@PathVariable("communityTitle") String communityTitle, ModelMap model, Principal principal) {

		String sPassword = (String)SecurityContextHolder.getContext().getAuthentication().getCredentials();
		List<IDspaceCollection> collections = dspaceManager.getAllCollections(principal.getName(),sPassword, communityTitle);

		model.addAttribute("communityTitle", communityTitle);
		model.addAttribute("collectionList", collections);

		return "auth/workbench/workspace/community/collection";
	}


	/*@RequestMapping(value = "auth/workbench/{projectid}/addcollaborator", method = RequestMethod.GET)
	public ModelAndView addCollaboratorForm()
	{
		return new ModelAndView("auth/workbench/{projectid}/addcollaborator","command", collaboratorFactory.createCollaborator());
<<<<<<< .mine
	}*/



//	@RequestMapping(value = "/auth/workbench/workspace/ajaxtest", method = RequestMethod.GET)
//	public @ResponseBody String getTime() {
//
//		Random rand = new Random();
//		float r = rand.nextFloat() * 100;
//		String result = "<br>The Random # is <b>" + r + "</b>. Generated on <b>" + new Date().toString() + "</b>";
//		System.out.println("Debug Message from Spring-Ajax-JQuery Controller.." + new Date().toString());
//		return result;
//	}

}

