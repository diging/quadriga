package edu.asu.spring.quadriga.web.workspace;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IArchiveWSManager;
import edu.asu.spring.quadriga.service.workspace.ICheckWSSecurity;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;
import edu.asu.spring.quadriga.web.StringConstants;

@Controller
public class ActivateWSController 
{
	@Autowired
	IListWSManager wsManager;
	
	@Autowired
	IArchiveWSManager archiveWSManager;
	
	@Autowired
	ICheckWSSecurity wsSecurityManager;
	
	/**
	 *This calls workspaceManger to list all the workspace associated with a project to deactivate.
	 * @param    model
	 * @param    projectid
	 * @return   String - URL of the form
	 * @throws   QuadrigaStorageException
	 * @author   Kiran Kumar Batna
	 */
	@RequestMapping(value="auth/workbench/{projectid}/deactivateworkspace", method=RequestMethod.GET)
	public String deactivateWorkspaceForm(Model model,@PathVariable("projectid") String projectid,Principal principal) throws QuadrigaStorageException
	{
		String userName;
		List<IWorkSpace> workspaceList;
		
		//check if the user has access
		userName = principal.getName();
		
		// retrieve the workspaces associated with the projects
		workspaceList = wsManager.listWorkspace(projectid,userName);
			
			model.addAttribute("workspaceList", workspaceList);
			model.addAttribute("wsprojectid", projectid);
		    return "auth/workbench/workspace/deactivateworkspace";
	}
	
	/**
	 * This calls workspaceManager to archive the workspace submitted.
	 * @param    projectid
	 * @param    req
	 * @param    model
	 * @param    principal
	 * @return   String - URL of the form
	 * @throws   QuadrigaStorageException
	 * @author   Kiran Kumar Batna
	 */
	@RequestMapping(value = "auth/workbench/{projectid}/deactivateworkspace", method = RequestMethod.POST)
	public String deactivateWorkspace(@PathVariable("projectid") String projectid,HttpServletRequest req, ModelMap model,Principal principal) throws QuadrigaStorageException
	{
		String[] values;
		String wsIdList = "";
		String errmsg;
		String userName;
		boolean chkAccess;
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
		
		//check if the user has access
		chkAccess = wsSecurityManager.chkWorkspaceAccess(userName, projectid, wsIdList);
		
		if(chkAccess)
		{
			//deactivate the workspace'
			errmsg = archiveWSManager.deactivateWorkspace(wsIdList, userName);

			if(errmsg.equals(""))
			{
				model.addAttribute("success", 1);
				model.addAttribute("successMsg",StringConstants.WORKSPACE_DEACTIVE_SUCCESS);
				model.addAttribute("wsprojectid", projectid);
				return "auth/workbench/workspace/deactiveworkspaceStatus";
			}
			else
			{
				workspaceList = wsManager.listActiveWorkspace(projectid,userName);
				//adding the workspace details to the model
				model.addAttribute("workspaceList", workspaceList);
				model.addAttribute("wsprojectid", projectid);
				model.addAttribute("success", 0);
				model.addAttribute("errormsg", errmsg);
				return "auth/workbench/workspace/deactiveworkspace";
			}
		}
		else
		{
			throw new QuadrigaStorageException();
		}


	}
	
	/**
	 *This calls workspaceManger to list the workspace associated with a project to activate.
	 * @param    model
	 * @param    projectid
	 * @return   String - URL of the form
	 * @throws   QuadrigaStorageException
	 * @author   Kiran Kumar Batna
	 */
	@RequestMapping(value="auth/workbench/{projectid}/activateworkspace", method=RequestMethod.GET)
	public String activateWorkspaceForm(Model model,@PathVariable("projectid") String projectid,Principal principal) throws QuadrigaStorageException
	{
		String userName;
		List<IWorkSpace> workspaceList;
		
		userName = principal.getName();
		// retrieve the workspaces associated with the projects
			workspaceList = wsManager.listDeactivatedWorkspace(projectid,userName);
			model.addAttribute("workspaceList", workspaceList);
			model.addAttribute("wsprojectid", projectid);
		return "auth/workbench/workspace/activateworkspace";
	}
	
	/**
	 * This calls workspaceManager to activate the deactivated workspace.
	 * @param    projectid
	 * @param    req
	 * @param    model
	 * @param    principal
	 * @return   String - URL of the form
	 * @throws   QuadrigaStorageException
	 * @author   Kiran Kumar Batna
	 */
	@RequestMapping(value = "auth/workbench/{projectid}/activateworkspace", method = RequestMethod.POST)
	public String activateWorkspace(@PathVariable("projectid") String projectid,HttpServletRequest req, ModelMap model,Principal principal) throws QuadrigaStorageException
	{
		String[] values;
		String wsIdList = "";
		String errmsg;
		String userName;
		boolean chkAccess;
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
		
		//check if the user has access
		chkAccess = wsSecurityManager.chkWorkspaceAccess(userName, projectid, wsIdList);
		
		if(chkAccess)
		{
			//activate the workspace'
			errmsg = archiveWSManager.activateWorkspace(wsIdList, userName);

			if(errmsg.equals(""))
			{
				model.addAttribute("success", 1);
				model.addAttribute("successMsg",StringConstants.WORKSPACE_ACTIVE_SUCCESS);
				model.addAttribute("wsprojectid", projectid);
				return "auth/workbench/workspace/activeworkspaceStatus";
			}
			else
			{
				workspaceList = wsManager.listDeactivatedWorkspace(projectid,userName);
				//adding the workspace details to the model
				model.addAttribute("workspaceList", workspaceList);
				model.addAttribute("wsprojectid", projectid);
				model.addAttribute("success", 0);
				model.addAttribute("errormsg", errmsg);
				return "auth/workbench/workspace/deactiveworkspace";
			}
		}
		else
		{
			throw new QuadrigaStorageException();
		}
	}

}
