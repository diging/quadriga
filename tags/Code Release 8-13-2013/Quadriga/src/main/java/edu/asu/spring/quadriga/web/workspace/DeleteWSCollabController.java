package edu.asu.spring.quadriga.web.workspace;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IModifyWSCollabManager;
import edu.asu.spring.quadriga.service.workspace.IRetrieveWSCollabManager;

@Controller
public class DeleteWSCollabController 
{
	
	@Autowired
	IRetrieveWSCollabManager wsRetrieveCollabManager;
	
	@Autowired
	IModifyWSCollabManager wsModifyCollabManager;
	
	
	@RequestMapping(value = "auth/workbench/workspace/{workspaceid}/deletecollaborators", method = RequestMethod.GET)
	public ModelAndView deleteWorkspaceCollaboratorForm(@PathVariable("workspaceid") String workspaceid) throws QuadrigaStorageException
	{
		ModelAndView model;
		List<ICollaborator> collaboratingUser = new ArrayList<ICollaborator>();
		
		model = new ModelAndView("auth/workbench/workspace/deletecollaborators");
		
		//fetch all the collaborating users and their roles
		collaboratingUser = wsRetrieveCollabManager.getWorkspaceCollaborators(workspaceid);
		
		model.getModelMap().put("collaboratingusers", collaboratingUser);
		
		//set the workspace id
		model.getModelMap().put("workspaceid", workspaceid);
		
		return model;
	}

	@RequestMapping(value = "auth/workbench/workspace/{workspaceid}/deletecollaborators", method = RequestMethod.POST)
	public ModelAndView deleteWorkspaceCollaborator(@PathVariable("workspaceid") String workspaceid,HttpServletRequest req) throws QuadrigaStorageException
	{
		String[] values;
		String userName = "";
		ModelAndView model;
		List<ICollaborator> collaboratingUser = new ArrayList<ICollaborator>();
		
		//create a model view
		model = new ModelAndView("auth/workbench/workspace/deletecollaborators");
		
		
		// fetch the selected values
		values = req.getParameterValues("wscollabchecked");
		
		for(String user : values)
		{
			userName = userName + "," + user;
		}
		
		//remove the first ',' form the list
		userName = userName.substring(1);
		
		//delete the collaborators
		wsModifyCollabManager.deleteWorkspaceCollaborator(userName, workspaceid);
		
		//fetch all the collaborating users and their roles
		collaboratingUser = wsRetrieveCollabManager.getWorkspaceCollaborators(workspaceid);
				
		model.getModelMap().put("collaboratingusers", collaboratingUser);
		
		//set the workspace id
		model.getModelMap().put("workspaceid", workspaceid);
		
		//success message
		model.getModelMap().put("successmsg", "Deleted successfully");
		model.getModelMap().put("success", 1);
		
		return model;
	}
}
