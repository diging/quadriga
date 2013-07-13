package edu.asu.spring.quadriga.web.workbench;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IProjectFactory;
import edu.asu.spring.quadriga.domain.implementation.Project;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.workbench.ICheckProjectSecurity;
import edu.asu.spring.quadriga.service.workbench.IModifyProjectManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.web.StringConstants;

@Controller
public class ModifyProjectController 
{
	@Autowired
	IModifyProjectManager projectManager;
	
	@Autowired
	IRetrieveProjectManager retrieveProjectManager;
	
	@Autowired 
	IProjectFactory projectFactory;
	
	@Autowired
	ICheckProjectSecurity projectSecurity;
	
	@Autowired 
	IUserManager userManager;

	/**
	 * This method is called during the load of add project request form
	 * @param  model -  model object
	 * @return String - the URL of the form
	 * @author Kiran Kumar Batna
	 */
	@RequestMapping(value="auth/workbench/addproject", method=RequestMethod.GET)
	public String addProjectRequestForm(Model model)
	{
		model.addAttribute("project",projectFactory.createProjectObject());
		model.addAttribute("unixnameurl",StringConstants.PROJECT_UNIX_NAME_URL);
		return "auth/workbench/addproject";
	}
	
	/**
	 * This method call the usermanager to insert the record in
	 * the database on form submission
	 * @param  project - object containing the form details.
	 * @param  model
	 * @param  principal
	 * @return String - the URL on success and failure
	 * @throws QuadrigaStorageException 
	 * @author Kiran Kumar Batna
	 */
	@RequestMapping(value = "auth/workbench/addproject", method = RequestMethod.POST)
	public String addProjectRequest(@ModelAttribute("SpringWeb")Project project, 
			ModelMap model, Principal principal) throws QuadrigaStorageException
	{
		String errmsg;
		IUser user = userManager.getUserDetails(principal.getName());
		if(user!=null)
		{
			project.setOwner(user);
			
			errmsg = projectManager.addProjectRequest(project);
			//On success
			if(errmsg.equals(""))
			{
				model.addAttribute("success", 1);
				model.addAttribute("successMsg","Project created successfully.");
				return "auth/workbench/addProjectStatus";
			}
			else
			{
				//on failure
				model.addAttribute("project", project);
				model.addAttribute("success", 0);
				model.addAttribute("errormsg", errmsg);
				model.addAttribute("unixnameurl",StringConstants.PROJECT_UNIX_NAME_URL);
				return "auth/workbench/addproject";
			}
		}
		return "auth/workbench/addProjectStatus";
	}
	
	/**
	 *This method is called during editing a project.
	 * @param   projectid - project internal id.
	 * @param   model 
	 * @return  String - URL for project editing page.
	 * @throws  QuadrigaStorageException 
	 * @author  Kiran Kumar Batna
	 */
	@RequestMapping(value="auth/workbench/modifyproject/{projectid}", method = RequestMethod.GET)
	public String updateProjectRequestForm(@PathVariable("projectid") String projectid, ModelMap model,Principal principal) throws QuadrigaStorageException
	{
		IProject project;
		boolean chkAccess;
		String userName = principal.getName();
		
		//Check if the user has access to update the project details
		chkAccess = projectSecurity.checkProjectAccess(userName,projectid);
		
		if(chkAccess)
		{
			project = retrieveProjectManager.getProjectDetails(projectid);
			model.addAttribute("project", project);
			model.addAttribute("unixnameurl",StringConstants.PROJECT_UNIX_NAME_URL);

			return "auth/workbench/modifyproject";
		}
		else
		{
			throw new QuadrigaStorageException();
		}
	}
	
	/**
	 * This method is called during editing a project.
	 * @param  projectid - project internal id.
	 * @param  project - Spring Project object.
	 * @param  model 
	 * @param  principal
	 * @return String - URL for project editing page.
	 * @throws QuadrigaStorageException 
	 * @author Kiran Kumar Batna
	 */
	@RequestMapping(value = "auth/workbench/modifyproject/{projectid}", method = RequestMethod.POST)
	public String updateProjectRequest(@PathVariable("projectid") String projectid,@ModelAttribute("SpringWeb")Project project, 
			ModelMap model, Principal principal) throws QuadrigaStorageException
	{
		String errmsg;
		boolean chkAccess;
		
		
		String userName = principal.getName();
		
		//Check if the user has access to update the project details
		chkAccess = projectSecurity.checkProjectAccess(userName, projectid);
		
		if(chkAccess)
		{
			project.setInternalid(projectid);
			errmsg = projectManager.updateProjectRequest(project, userName);
			if(errmsg.equals(""))
			{
				model.addAttribute("success", 1);
				model.addAttribute("successMsg","Project created successfully.");
				return "auth/workbench/modifyProjectStatus";
			}else{
				model.addAttribute("project", project);
				model.addAttribute("success", 0);
				model.addAttribute("errormsg", errmsg);
				model.addAttribute("unixnameurl",StringConstants.PROJECT_UNIX_NAME_URL);
				return "auth/workbench/modifyproject";
			}
		}
		else
		{
			throw new QuadrigaStorageException();
		}
		

	}
	
	/**
	 * This method is called during form load to display all projects.
	 * @param   model
	 * @param   principal
	 * @return  String - URL on success or failure.
	 * @throws  QuadrigaStorageException
	 * @author  Kiran Kumar Batna
	 */
	@RequestMapping(value="auth/workbench/deleteproject", method=RequestMethod.GET)
	public String deleteProjectRequestForm(Model model,Principal principal) throws QuadrigaStorageException
	{
		String userName;
		List<IProject> projectlist;
		
		userName = principal.getName();
		
		projectlist = retrieveProjectManager.getProjectList(userName); 
		
		    //adding the project details to the model
			model.addAttribute("projectlist", projectlist);
			return "auth/workbench/deleteproject";
	}
	
	/**
	 * This method calls the user manager to delete the projects.
	 * @param    req
	 * @param    model
	 * @param    principal
	 * @return   String - URL on success and failure.
	 * @throws   QuadrigaStorageException 
	 * @author   Kiran Kumar Batna
	 */
	@RequestMapping(value = "auth/workbench/deleteproject", method = RequestMethod.POST)
	public String deleteProjectRequest(HttpServletRequest req, ModelMap model,Principal principal) throws QuadrigaStorageException
	{
		String[] values;
		String projIdList = "";
		String errmsg;
		String userName;
		boolean chkAccess;
		List<IProject> projectlist;
		
		// fetch the selected values
		values = req.getParameterValues("projchecked");
		for(String projid : values)
		{
			projIdList = projIdList + "," + projid;
		}
		//removing the first ',' value
		projIdList = projIdList.substring(1,projIdList.length());
		
		//check if the user has access to delete the rows
		userName = principal.getName();
		
		chkAccess = projectSecurity.checkProjectAccess(userName, projIdList);
		
		if(chkAccess)
		{
			errmsg = projectManager.deleteProjectRequest(projIdList);
			
			if(errmsg.equals(""))
			{
				model.addAttribute("success", 1);
				model.addAttribute("successMsg","Projects deleted successfully.");
				return "auth/workbench/deleteProjectStatus";
			}
			else
			{
				
				projectlist = retrieveProjectManager.getProjectList(userName); 
			
			    //adding the project details to the model
			    model.addAttribute("projectlist", projectlist);
			    model.addAttribute("success", 0);
			    model.addAttribute("errormsg", errmsg);
			    return "auth/workbench/deleteproject";
			}
		}
		else
		{
			throw new QuadrigaStorageException();
		}
		

	}

}
