package edu.asu.spring.quadriga.web.workspace;

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
	 * This will list the details of workspaces 
	 * @param  workspaceid
	 * @param  model
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