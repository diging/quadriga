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
import edu.asu.spring.quadriga.service.workspace.IListWSManager;
import edu.asu.spring.quadriga.web.StringConstants;

@Controller
public class ArchiveWSController 
{
	@Autowired
	IArchiveWSManager archiveWSManager;
	
	@Autowired
	IListWSManager wsManager;
	
	/**
	 * This calls workspaceManger to list the workspace associated with a project for archival process.
	 * @param   model
	 * @param   projectid
	 * @return  String - URL of the form
	 * @throws  QuadrigaStorageException
	 * @author  Kiran Kumar Batna
	 */
	@RequestMapping(value="auth/workbench/{projectid}/archiveworkspace", method=RequestMethod.GET)
	public String archiveWorkspaceForm(Model model,@PathVariable("projectid") String projectid) throws QuadrigaStorageException
	{
		List<IWorkSpace> workspaceList;
		// retrieve the workspaces associated with the projects
		    workspaceList = wsManager.listActiveWorkspace(projectid);
			model.addAttribute("workspaceList", workspaceList);
			model.addAttribute("wsprojectid",projectid);
		return "auth/workbench/workspace/archiveworkspace";
	}
	
	/**
	 * This calls workspaceManager to archive the workspace submitted.
	 * @param   projectid
	 * @param   req
	 * @param   model
	 * @param   principal
	 * @return  String - URL of the form
	 * @throws  QuadrigaStorageException
	 * @author  Kiran Kumar Batna
	 */
	@RequestMapping(value = "auth/workbench/{projectid}/archiveworkspace", method = RequestMethod.POST)
	public String archiveWorkspace(@PathVariable("projectid") String projectid,HttpServletRequest req, ModelMap model,Principal principal) throws QuadrigaStorageException
	{
		String[] values;
		String wsIdList = "";
		String errmsg;
		String userName;
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

		//archive the workspace
		errmsg = archiveWSManager.archiveWorkspace(wsIdList, userName);

		if(errmsg.equals(""))
		{
			model.addAttribute("success", 1);
			model.addAttribute("successMsg",StringConstants.WORKSPACE_ARCHIVE_SUCCESS);
			model.addAttribute("wsprojectid", projectid);
			return "auth/workbench/workspace/archiveworkspaceStatus";
		}
		else
		{
			workspaceList = wsManager.listActiveWorkspace(projectid);
			//adding the workspace details to the model
			model.addAttribute("workspaceList", workspaceList);
			model.addAttribute("wsprojectid", projectid);
			model.addAttribute("success", 0);
			model.addAttribute("errormsg", errmsg);
			return "auth/workbench/workspace/archiveworkspace";
		}
	}

}
