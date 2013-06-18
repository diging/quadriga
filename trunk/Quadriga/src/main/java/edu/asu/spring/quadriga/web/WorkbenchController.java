package edu.asu.spring.quadriga.web;

import java.security.Principal;
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

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.IProjectFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.implementation.Project;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
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



	/**
	 * @description this method acts as a controller for handling all the activities on the workbench
	 * 				home page 
	 * 
	 * @param 		model maps projectlist to view (jsp page) 
	 * 
	 * @return 		string for workbench url 
	 * @throws      QuadrigaStorageException
	 * 
	 * @author 		rohit sukleshwar pendbhaje
	 */

	@RequestMapping(value="auth/workbench", method = RequestMethod.GET)
	public String projectWorkbenchHandle(ModelMap model) throws QuadrigaStorageException {

		UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String userName = principal.getUsername();

		List<IProject> projectlist = null;
		try
		{
		  projectlist = projectmanager.getProjectsOfUser(userName);
		}
		catch(QuadrigaStorageException e){
			throw new QuadrigaStorageException();
		}
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
	 * @throws 		QuadrigaStorageException
	 *
	 * @author 		rohit sukleshwar pendbhaje
	 */
	@RequestMapping(value="auth/workbench/{projectid}", method = RequestMethod.GET)
	public String getProjectPage(@PathVariable("projectid") int projectid, ModelMap model) throws QuadrigaStorageException {

		IProject project = null;
		try
		{
		  project = projectmanager.getProject(projectid);
		}
		catch(QuadrigaStorageException e){
			throw new QuadrigaStorageException();
		}

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
	 * @description    : This method is called during editing a project.
	 * @param          : projectid - project internal id.
	 * @param          : model 
	 * @return         : String - URL for project editing page.
	 * @throws         : QuadrigaStorageException 
	 * @author         : Kiran Kumar Batna
	 * 
	 */
	@RequestMapping(value="auth/workbench/modifyproject/{projectid}", method = RequestMethod.GET)
	public String getEditProjectPage(@PathVariable("projectid") int projectid, ModelMap model,Principal principal) throws QuadrigaStorageException {
        
		IProject project=null;
		try
		{
		   project = projectmanager.getProject(projectid);
		}
		catch(QuadrigaStorageException e){
			throw new QuadrigaStorageException();
		}

		model.addAttribute("project", project);

		return "auth/workbench/modifyproject";
	}
	
	/**
	 * @description    : This method is called during editing a project.
	 * @param          : projectid - project internal id.
	 * @param          : project - Spring Project object.
	 * @param          : model 
	 * @param          : principal
	 * @return         : String - URL for project editing page.
	 * @throws         : QuadrigaStorageException 
	 * @author         : Kiran Kumar Batna
	 * 
	 */
	@RequestMapping(value = "auth/workbench/modifyproject/{projectid}", method = RequestMethod.POST)
	public String editProject(@PathVariable("projectid") int projectid,@ModelAttribute("SpringWeb")Project project, 
			ModelMap model, Principal principal) throws QuadrigaStorageException 
	{
		String errmsg;
		String user = principal.getName();
		
		    try
		    {
		      // assigning the internal id of the project
		      project.setInternalid(projectid);
			errmsg = projectmanager.updateProjectDetails(project, user);
		    }
		    catch(QuadrigaStorageException e){
				throw new QuadrigaStorageException();
			}

			if(errmsg.equals(""))
			{
				model.addAttribute("success", 1);
				model.addAttribute("successMsg","Project created successfully.");
				return "auth/workbench/modifyProjectStatus";
			}else{
				model.addAttribute("project", project);
				model.addAttribute("success", 0);
				model.addAttribute("errormsg", errmsg);
				return "auth/workbench/modifyproject";
			}
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
	 * @throws       : QuadrigaStorageException 
	 * @author       : Kiran Kumar Batna
	 * 
	 */
	@RequestMapping(value = "auth/workbench/addproject", method = RequestMethod.POST)
	public String addProject(@ModelAttribute("SpringWeb")Project project, 
			ModelMap model, Principal principal) throws QuadrigaStorageException 
	{
		String errmsg;
		IUser user = usermanager.getUserDetails(principal.getName());
		if(user!=null)
		{
			project.setOwner(user);

			try
			{
			errmsg = projectmanager.addNewProject(project);
			}
			catch(QuadrigaStorageException e){
				throw new QuadrigaStorageException();
			}
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
	 * @throws      : QuadrigaStorageException
	 * @author      : Kiran Kumar Batna
	  
	 */
	@RequestMapping(value="auth/workbench/deleteproject", method=RequestMethod.GET)
	public String deleteProjectform(Model model,Principal principal) throws QuadrigaStorageException
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
		catch(QuadrigaStorageException e){
			throw new QuadrigaStorageException();
		}
		return "auth/workbench/deleteproject";
	}
	
	/**
	 * @description :  This method calls the user manager to delete the projects.
	 * @param       :  req
	 * @param       :  model
	 * @param       : principal
	 * @return      : String - URL on success and failure.
	 * @throws      : QuadrigaStorageException 
	 * @author      : Kiran Kumar Batna
	 * 
	 */
	@RequestMapping(value = "auth/workbench/deleteproject", method = RequestMethod.POST)
	public String deleteProject(HttpServletRequest req, ModelMap model,Principal principal) throws QuadrigaStorageException
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
		
		try
		{
		errmsg = projectmanager.deleteProject(projIdList);
		}
		catch(QuadrigaStorageException e){
			throw new QuadrigaStorageException();
		}
		
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
			catch(QuadrigaStorageException e){
				throw new QuadrigaStorageException();
			}
		}
	}

}

