package edu.asu.spring.quadriga.web.workspace;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;

@Controller
public class ListWSController 
{
	@Autowired
	IListWSManager wsManager;
	
	
	/**
	 * This will list all the active workspaces associated with the project. 
	 * @param  projectid
	 * @param  model
	 * @return String - url of the page listing all the workspaces of the project.
	 * @throws  QuadrigaStorageException
	 * @author  Kiran Kumar Batna
	 */
	@RequestMapping(value="auth/workbench/workspace/{projectid}", method = RequestMethod.GET)
	public String listActiveWorkspace(@PathVariable("projectid") String projectid, ModelMap model) throws QuadrigaStorageException
	{
		List<IWorkSpace> workspaceList;

		// retrieve the workspaces associated with the projects
			workspaceList = wsManager.listActiveWorkspace(projectid); 

			model.addAttribute("workspaceList", workspaceList);
			model.addAttribute("wsprojectid", projectid);
		return "auth/workbench/workspace";
	}
	
	/**
	 * This will list all the workspaces associated with the project 
	 * @param  projectid
	 * @param  model
	 * @param  redirectAtt
	 * @return String - url of the page listing all the workspaces of the project.
	 * @throws QuadrigaStorageException
	 * @author Kiran Kumar Batna
	 */
	@RequestMapping(value="auth/workbench/workspace/workspacedetails/{workspaceid}", method = RequestMethod.GET)
	public String getWorkspaceDetails(@PathVariable("workspaceid") String workspaceid, ModelMap model) throws QuadrigaStorageException
	{
		IWorkSpace workspace;
		workspace = wsManager.getWorkspaceDetails(workspaceid);
		model.addAttribute("workspacedetails", workspace);
		return "auth/workbench/workspace/workspacedetails";
	}
}
