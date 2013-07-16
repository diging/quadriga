package edu.asu.spring.quadriga.web.workspace;

import java.security.Principal;

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
import edu.asu.spring.quadriga.domain.implementation.WorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.workspace.ICheckWSSecurity;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;
import edu.asu.spring.quadriga.service.workspace.IModifyWSManager;
import edu.asu.spring.quadriga.web.StringConstants;

@Controller
public class ModifyWSController 
{
	
	@Autowired
	ICheckWSSecurity workspaceSecurity;
	
	@Autowired
	IUserManager userManager;
	
	@Autowired
	IModifyWSManager modifyWSManager;
	
	@Autowired
	IListWSManager wsManager;
	
	@RequestMapping(value="auth/workbench/workspace/updateworkspacedetails/{workspaceid}", method=RequestMethod.GET)
	public String addWorkSpaceRequestForm(Model model,@PathVariable("workspaceid") String workspaceid,Principal principal) throws QuadrigaStorageException, QuadrigaAccessException
	{
		    IWorkSpace workspace;
		    String userName;
		    
		    //fetch the workspace details
		    userName = principal.getName();
		    workspace = wsManager.getWorkspaceDetails(workspaceid, userName);
		    
			model.addAttribute("workspace", workspace);
			return "auth/workbench/workspace/updateworkspace";
	}
	
	@RequestMapping(value = "auth/workbench/workspace/updateworkspacedetails/{workspaceid}", method = RequestMethod.POST)
	public String addWorkSpaceRequest(@ModelAttribute("SpringWeb")WorkSpace workspace,
			ModelMap model, Principal principal,@PathVariable("workspaceid") String workspaceid) throws QuadrigaStorageException
			{
		String errmsg;
		IUser wsOwner = null;
		String userName = principal.getName();

			wsOwner = userManager.getUserDetails(userName);

			//set the workspace owner
			workspace.setOwner(wsOwner);
			
			//set the workspace id
			workspace.setId(workspaceid);

			errmsg = modifyWSManager.updateWorkspaceRequest(workspace);
			if(errmsg.equals(""))
			{
				model.addAttribute("success", 1);
				model.addAttribute("successMsg",StringConstants.WORKSPACE_SUCCESS_MSG);
				return "auth/workbench/workspace/updateworkspacestatus";
			}
			else
			{
				model.addAttribute("workspace", workspace);
				model.addAttribute("success", 0);
				model.addAttribute("errormsg", errmsg);
				return "auth/workbench/workspace/updateworkspace";
			}
		}
}
