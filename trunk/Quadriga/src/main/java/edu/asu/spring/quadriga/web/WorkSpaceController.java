package edu.asu.spring.quadriga.web;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.domain.factories.IWorkspaceFactory;
import edu.asu.spring.quadriga.domain.implementation.WorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.IWorkspaceManager;

/**
 * @Description : this class will handle all Workspace for projects
 * 
 */

@Controller
public class WorkSpaceController 
{
	@Autowired
	private IWorkspaceFactory workspaceFactory;
	
	@Autowired 
	IUserManager userManager;
	
	@Autowired
	IWorkspaceManager wsManager;
	
	/**
	 * @description   : This will list all the workspaces associated with the 
	 *                  project 
	 * @param         : projectid
	 * @param         : model
	 * @param         : redirectAtt
	 * @return        : String - url of the page listing all the workspaces of the project.
	 * @throws        : QuadrigaStorageException
	 * @author        : Kiran Kumar Batna
	 */
	@RequestMapping(value="auth/workbench/workspace/{projectid}", method = RequestMethod.GET)
	public String listWorkspace(@PathVariable("projectid") int projectid, ModelMap model) throws QuadrigaStorageException
	{
		List<IWorkSpace> workspaceList;
		
		// retrieve the workspaces associated with the projects
		try
		{
		  workspaceList = wsManager.listWorkspace(projectid);
		  
		  model.addAttribute("workspaceList", workspaceList);
		  model.addAttribute("wsprojectid", projectid);
		}
		catch(QuadrigaStorageException e)
		{
			throw new QuadrigaStorageException(e.getMessage());
		}
		return "auth/workbench/workspace";
	}
	
	/**
	 * @description   : This is called on the addworkspace form load.
	 * @param         : model
	 * @return        : String - containing the path to addworkspace jsp page.
	 * @author        : Kiran Kumar Batna
	 */
	@RequestMapping(value="auth/workbench/workspace/addworkspace", method=RequestMethod.GET)
	public String createWorkspaceForm(Model model)
	{
		model.addAttribute("workspace", workspaceFactory.createWorkspaceObject());
		return "auth/workbench/workspace/addworkspace";
	}
	
	/**
	 * @description    : This calls workspace manager to add workspace details 
	 *                   into the database.
	 * @param          : workspace
	 * @param          : model
	 * @param          : principal
	 * @return         : String - On success loads success page and on failure loads
	 *                   the same form with error messages.
	 * @throws         : QuadrigaStorageException
	 * @author         : Kiran Kumar Batna
	 */
	@RequestMapping(value = "auth/workbench/workspace/addworkspace", method = RequestMethod.POST)
	public String createWorkspace(@ModelAttribute("SpringWeb")WorkSpace workspace,
			ModelMap model, Principal principal) throws QuadrigaStorageException
	{
		String errmsg;
		int projectId;
		IUser wsOwner = null;
		
		wsOwner = userManager.getUserDetails(principal.getName());
		
        /////////////////////////
		projectId = 1;
		///////////////////////
		
		if(wsOwner!=null)
		{
			//set the workspace owner
			workspace.setOwner(wsOwner);
			
			try
			{
			errmsg = wsManager.addNewWorkspace(workspace,projectId);
			}
			catch(QuadrigaStorageException ex)
			{
				throw new QuadrigaStorageException(ex.getMessage());
			}
			if(errmsg.equals(""))
			{
				model.addAttribute("success", 1);
				model.addAttribute("successMsg","Workspace created successfully.");
				return "auth/workbench/workspace/addworkspacestatus";
			}
			else
			{
				model.addAttribute("workspace", workspace);
				model.addAttribute("success", 0);
				model.addAttribute("errormsg", errmsg);
				return "auth/workbench/workspace/addworkspace";
			}
		}
		else
		{
			model.addAttribute("success", 0);
			model.addAttribute("errormsg","Error occurred while retrieving user details.");
			return "auth/workbench/workspace/addworkspace";
		}
	}

}
