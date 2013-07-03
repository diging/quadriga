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
import edu.asu.spring.quadriga.service.workspace.IListWSManager;
import edu.asu.spring.quadriga.service.workspace.IModifyWSManager;
import edu.asu.spring.quadriga.web.StringConstants;

@Controller
public class DeleteWSController 
{
	@Autowired
	IListWSManager wsManager;
	
	@Autowired
	IModifyWSManager modifyWSManger;
	
	/**
	 * This calls workspaceManger to list the deactivated workspace associated with a project for deletion.
	 * @param    model
	 * @param    projectid
	 * @return   String - URL of the form
	 * @throws   QuadrigaStorageException
	 * @author   Kiran Kumar Batna
	 */
	@RequestMapping(value="auth/workbench/{projectid}/deleteworkspace", method=RequestMethod.GET)
	public String deleteWorkspaceRequestForm(Model model,@PathVariable("projectid") String projectid) throws QuadrigaStorageException
	{
		List<IWorkSpace> workspaceList;
		// retrieve the workspaces associated with the projects
		workspaceList = wsManager.listDeactivatedWorkspace(projectid);
			model.addAttribute("workspaceList", workspaceList);
			model.addAttribute("wsprojectid", projectid);

		return "auth/workbench/workspace/deleteworkspace";
	}
	
	/**
	 * This calls workspaceManager to delete the workspace submitted.
	 * @param   projectid
	 * @param   req
	 * @param   model
	 * @param   principal
	 * @return  String - URL of the form
	 * @throws  QuadrigaStorageException
	 * @author  Kiran Kumar Batna
	 */
	@RequestMapping(value = "auth/workbench/{projectid}/deleteworkspace", method = RequestMethod.POST)
	public String deleteWorkspaceRequest(@PathVariable("projectid") String projectid,HttpServletRequest req, ModelMap model,Principal principal) throws QuadrigaStorageException
	{
		String[] values;
		String wsIdList = "";
		String errmsg;
		List<IWorkSpace> workspaceList = null;

		// fetch the selected values
		values = req.getParameterValues("wschecked");
		for(String workspaceid : values)
		{
			wsIdList = wsIdList + "," + workspaceid;
		}
		//removing the first ',' value
		wsIdList = wsIdList.substring(1,wsIdList.length());

		errmsg = modifyWSManger.deleteWorkspaceRequest(wsIdList);

		if(errmsg.equals(""))
		{
			model.addAttribute("success", 1);
			model.addAttribute("successMsg",StringConstants.WORKSPACE_DELETE_SUCCESS);
			model.addAttribute("wsprojectid", projectid);
			return "auth/workbench/workspace/deleteworkspaceStatus";
		}
		else
		{
            workspaceList = wsManager.listDeactivatedWorkspace(projectid);
			//adding the workspace details to the model
			model.addAttribute("workspaceList", workspaceList);
			model.addAttribute("wsprojectid", projectid);
			model.addAttribute("success", 0);
			model.addAttribute("errormsg", errmsg);
			return "auth/workbench/workspace/deleteworkspace";
		}
	}

}
