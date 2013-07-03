package edu.asu.spring.quadriga.web;

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
import org.springframework.web.bind.annotation.RequestParam;

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
		int archive;
		int active;

		// retrieve the workspaces associated with the projects
		try
		{
			archive = StringConstants.WORKSPACE_ACTIVE_ARCHIVE_VALUE;
			active = StringConstants.WORKSPACE_ACTIVATE_VALUE;
			workspaceList = wsManager.listWorkspace(projectid,archive,active);

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
	public String createWorkspaceForm(Model model,@RequestParam("projectid")String projectid)
	{
		model.addAttribute("workspace", workspaceFactory.createWorkspaceObject());
		model.addAttribute("wsprojectid", projectid);
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
	@RequestMapping(value = "auth/workbench/workspace/addworkspace/{projectid}", method = RequestMethod.POST)
	public String createWorkspace(@ModelAttribute("SpringWeb")WorkSpace workspace,
			ModelMap model, Principal principal,@PathVariable("projectid") String projectid) throws QuadrigaStorageException
			{
		String errmsg;
		IUser wsOwner = null;

		wsOwner = userManager.getUserDetails(principal.getName());

		if(wsOwner!=null)
		{
			//set the workspace owner
			workspace.setOwner(wsOwner);

			try
			{
				errmsg = wsManager.addNewWorkspace(workspace,Integer.parseInt(projectid));
			}
			catch(QuadrigaStorageException ex)
			{
				throw new QuadrigaStorageException(ex.getMessage());
			}
			if(errmsg.equals(""))
			{
				model.addAttribute("success", 1);
				model.addAttribute("successMsg",StringConstants.WORKSPACE_SUCCESS_MSG);
				return "auth/workbench/workspace/addworkspacestatus";
			}
			else
			{
				model.addAttribute("workspace", workspace);
				model.addAttribute("success", 0);
				model.addAttribute("errormsg", errmsg);
				model.addAttribute("wsprojectid", projectid);
				return "auth/workbench/workspace/addworkspace";
			}
		}
		else
		{
			model.addAttribute("success", 0);
			model.addAttribute("errormsg",StringConstants.FETCH_USER_ERROR);
			return "auth/workbench/workspace/addworkspace";
		}
			}

	/**
	 * @description  : This calls workspaceManger to list the workspace
	 *                 associated with a project for archival process.
	 * @param        : model
	 * @param        : projectid
	 * @return       : String - URL of the form
	 * @throws       : QuadrigaStorageException
	 * @author       : Kiran Kumar Batna
	 */
	@RequestMapping(value="auth/workbench/workspace/archiveworkspace", method=RequestMethod.GET)
	public String archiveWorkspaceForm(Model model,@RequestParam("projectid")String projectid) throws QuadrigaStorageException
	{
		List<IWorkSpace> workspaceList;
		int archive;
		int active;

		// retrieve the workspaces associated with the projects
		try
		{
			archive = StringConstants.WORKSPACE_ACTIVE_ARCHIVE_VALUE;
			active = StringConstants.WORKSPACE_ACTIVATE_VALUE;
			workspaceList = wsManager.listWorkspace(Integer.parseInt(projectid),archive,active);

			model.addAttribute("workspaceList", workspaceList);
			model.addAttribute("wsprojectid",projectid);
		}
		catch(QuadrigaStorageException e)
		{
			throw new QuadrigaStorageException(e.getMessage());
		}

		return "auth/workbench/workspace/archiveworkspace";
	}

	/**
	 * @description   : This calls workspaceManager to archive the workspace
	 *                  submitted.
	 * @param         : projectid
	 * @param         : req
	 * @param         : model
	 * @param         : principal
	 * @return        : String - URL of the form
	 * @throws        : QuadrigaStorageException
	 * @author        : Kiran Kumar Batna
	 */
	@RequestMapping(value = "auth/workbench/workspace/archiveworkspace/{projectid}", method = RequestMethod.POST)
	public String archiveWorkspace(@PathVariable("projectid") String projectid,HttpServletRequest req, ModelMap model,Principal principal) throws QuadrigaStorageException
	{
		String[] values;
		String wsIdList = "";
		String errmsg;
		String userName;
		int isArchive;
		int archive;
		int active;
		List<IWorkSpace> workspaceList = null;

		// fetch the selected values
		values = req.getParameterValues("wschecked");

		for(String workspaceid : values)
		{
			wsIdList = wsIdList + "," + workspaceid;
		}

		//removing the first ',' value
		wsIdList = wsIdList.substring(1,wsIdList.length());

		//fetch the user name
		userName = principal.getName();

		//fetch the archive value
		isArchive = StringConstants.WORKSPACE_ARCHIVE_VALUE;

		//archive the workspace
		errmsg = wsManager.archiveWorkspace(wsIdList, isArchive, userName);

		if(errmsg.equals(""))
		{
			model.addAttribute("success", 1);
			model.addAttribute("successMsg",StringConstants.WORKSPACE_ARCHIVE_SUCCESS);
			return "auth/workbench/workspace/archiveworkspaceStatus";
		}
		else
		{
			archive = StringConstants.WORKSPACE_ACTIVE_ARCHIVE_VALUE;
			active = StringConstants.WORKSPACE_ACTIVATE_VALUE;
			workspaceList = wsManager.listWorkspace(Integer.parseInt(projectid),archive,active);

			//adding the workspace details to the model
			model.addAttribute("workspaceList", workspaceList);
			model.addAttribute("wsprojectid", projectid);
			model.addAttribute("success", 0);
			model.addAttribute("errormsg", errmsg);
			return "auth/workbench/workspace/archiveworkspace";
		}
	}

	/**
	 * @description  : This calls workspaceManger to list the workspace
	 *                 associated with a project for archival process.
	 * @param        : model
	 * @param        : projectid
	 * @return       : String - URL of the form
	 * @throws       : QuadrigaStorageException
	 * @author       : Kiran Kumar Batna
	 */
	@RequestMapping(value="auth/workbench/workspace/deactivateworkspace", method=RequestMethod.GET)
	public String deactivateWorkspaceForm(Model model,@RequestParam("projectid")String projectid) throws QuadrigaStorageException
	{
		int archive;
		int active;
		List<IWorkSpace> workspaceList;
		// retrieve the workspaces associated with the projects
		try
		{
			archive = StringConstants.WORKSPACE_ACTIVE_ARCHIVE_VALUE;
			active = StringConstants.WORKSPACE_ACTIVATE_VALUE;
			workspaceList = wsManager.listWorkspace(Integer.parseInt(projectid),archive,active);

			model.addAttribute("workspaceList", workspaceList);
			model.addAttribute("wsprojectid", projectid);
		}
		catch(QuadrigaStorageException e)
		{
			throw new QuadrigaStorageException(e.getMessage());
		}

		return "auth/workbench/workspace/deactivateworkspace";
	}

	/**
	 * @description   : This calls workspaceManager to archive the workspace
	 *                  submitted.
	 * @param         : projectid
	 * @param         : req
	 * @param         : model
	 * @param         : principal
	 * @return        : String - URL of the form
	 * @throws        : QuadrigaStorageException
	 * @author        : Kiran Kumar Batna
	 */
	@RequestMapping(value = "auth/workbench/workspace/deactivateworkspace/{projectid}", method = RequestMethod.POST)
	public String deactivateWorkspace(@PathVariable("projectid") String projectid,HttpServletRequest req, ModelMap model,Principal principal) throws QuadrigaStorageException
	{
		String[] values;
		String wsIdList = "";
		String errmsg;
		String userName;
		int isDeActive;
		int archive;
		int active;
		List<IWorkSpace> workspaceList = null;

		// fetch the selected values
		values = req.getParameterValues("wschecked");

		for(String workspaceid : values)
		{
			wsIdList = wsIdList + "," + workspaceid;
		}

		//removing the first ',' value
		wsIdList = wsIdList.substring(1,wsIdList.length());

		//fetch the user name
		userName = principal.getName();

		//fetch the archive value
		isDeActive = StringConstants.WORKSPACE_DEACTIVATE_VALUE;

		//deactivate the workspace'
		errmsg = wsManager.deactivateWorkspace(wsIdList, isDeActive, userName);

		if(errmsg.equals(""))
		{
			model.addAttribute("success", 1);
			model.addAttribute("successMsg",StringConstants.WORKSPACE_DEACTIVE_SUCCESS);
			return "auth/workbench/workspace/deactiveworkspaceStatus";
		}
		else
		{
			archive = StringConstants.WORKSPACE_ACTIVE_ARCHIVE_VALUE;
			active = StringConstants.WORKSPACE_ACTIVATE_VALUE;
			workspaceList = wsManager.listWorkspace(Integer.parseInt(projectid),archive,active);

			//adding the workspace details to the model
			model.addAttribute("workspaceList", workspaceList);
			model.addAttribute("wsprojectid", projectid);
			model.addAttribute("success", 0);
			model.addAttribute("errormsg", errmsg);
			return "auth/workbench/workspace/deactiveworkspace";
		}
	}

	/**
	 * @description  : This calls workspaceManger to list the workspace
	 *                 associated with a project for deletion.
	 * @param        : model
	 * @param        : projectid
	 * @return       : String - URL of the form
	 * @throws       : QuadrigaStorageException
	 * @author       : Kiran Kumar Batna
	 */
	@RequestMapping(value="auth/workbench/workspace/deleteworkspace", method=RequestMethod.GET)
	public String deleteWorkspaceForm(Model model,@RequestParam("projectid")String projectid) throws QuadrigaStorageException
	{
		int archive;
		int active;
		List<IWorkSpace> workspaceList;
		// retrieve the workspaces associated with the projects
		try
		{
			archive = StringConstants.WORKSPACE_ACTIVE_ARCHIVE_VALUE;
			active = StringConstants.WORKSPACE_ACTIVATE_VALUE;
			workspaceList = wsManager.listWorkspace(Integer.parseInt(projectid),archive,active);

			model.addAttribute("workspaceList", workspaceList);
			model.addAttribute("wsprojectid", projectid);
		}
		catch(QuadrigaStorageException e)
		{
			throw new QuadrigaStorageException(e.getMessage());
		}

		return "auth/workbench/workspace/deleteworkspace";
	}
	
	/**
	 * @description   : This calls workspaceManager to delete the workspace
	 *                  submitted.
	 * @param         : projectid
	 * @param         : req
	 * @param         : model
	 * @param         : principal
	 * @return        : String - URL of the form
	 * @throws        : QuadrigaStorageException
	 * @author        : Kiran Kumar Batna
	 */
	@RequestMapping(value = "auth/workbench/workspace/deleteworkspace/{projectid}", method = RequestMethod.POST)
	public String deleteWorkspace(@PathVariable("projectid") String projectid,HttpServletRequest req, ModelMap model,Principal principal) throws QuadrigaStorageException
	{
		String[] values;
		String wsIdList = "";
		String errmsg;
		int archive;
		int active;
		List<IWorkSpace> workspaceList = null;

		// fetch the selected values
		values = req.getParameterValues("wschecked");

		for(String workspaceid : values)
		{
			wsIdList = wsIdList + "," + workspaceid;
		}

		//removing the first ',' value
		wsIdList = wsIdList.substring(1,wsIdList.length());

		errmsg = wsManager.deleteWorkspace(wsIdList);

		if(errmsg.equals(""))
		{
			model.addAttribute("success", 1);
			model.addAttribute("successMsg",StringConstants.WORKSPACE_DELETE_SUCCESS);
			return "auth/workbench/workspace/deleteworkspaceStatus";
		}
		else
		{
			archive = StringConstants.WORKSPACE_ACTIVE_ARCHIVE_VALUE;
			active = StringConstants.WORKSPACE_ACTIVATE_VALUE;
			workspaceList = wsManager.listWorkspace(Integer.parseInt(projectid),archive,active);

			//adding the workspace details to the model
			model.addAttribute("workspaceList", workspaceList);
			model.addAttribute("wsprojectid", projectid);
			model.addAttribute("success", 0);
			model.addAttribute("errormsg", errmsg);
			return "auth/workbench/workspace/deleteworkspace";
		}
	}

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
	@RequestMapping(value="auth/workbench/workspace/workspacedetails/{workspaceid}", method = RequestMethod.GET)
	public String getWorkspaceDetails(@PathVariable("workspaceid") String workspaceid, ModelMap model) throws QuadrigaStorageException
	{
		IWorkSpace workspace;
		long wsId = Long.valueOf(workspaceid);
		workspace = wsManager.getWorkspaceDetails(wsId);
		model.addAttribute("workspacedetails", workspace);
		return "auth/workbench/workspace/workspacedetails";
	}
}

