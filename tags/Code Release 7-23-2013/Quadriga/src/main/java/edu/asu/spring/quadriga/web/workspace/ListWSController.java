package edu.asu.spring.quadriga.web.workspace;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;
import edu.asu.spring.quadriga.service.workspace.IRetrieveWSCollabManager;

@Controller
public class ListWSController 
{
	@Autowired
	IListWSManager wsManager;
	
	@Autowired
	IRetrieveWSCollabManager wsCollabManager;
	
	/**
	 * This will list the details of workspaces 
	 * @param  workspaceid
	 * @param  model
	 * @return String - url of the page listing all the workspaces of the project.
	 * @throws QuadrigaStorageException
	 * @author Kiran Kumar Batna
	 * @throws QuadrigaAccessException 
	 */
	@RequestMapping(value="auth/workbench/workspace/workspacedetails/{workspaceid}", method = RequestMethod.GET)
	public String getWorkspaceDetails(@PathVariable("workspaceid") String workspaceid, Principal principal, ModelMap model) throws QuadrigaStorageException, QuadrigaAccessException
	{
		String userName;
		IWorkSpace workspace;
		List<ICollaborator> collaboratorList;
		
		userName = principal.getName();
		workspace = wsManager.getWorkspaceDetails(workspaceid,userName);
		
		//retrieve the collaborators associated with the workspace
		collaboratorList = wsCollabManager.getWorkspaceCollaborators(workspaceid);
		
		workspace.setCollaborators(collaboratorList);
				
		model.addAttribute("workspacedetails", workspace);
		return "auth/workbench/workspace/workspacedetails";
	}
}
