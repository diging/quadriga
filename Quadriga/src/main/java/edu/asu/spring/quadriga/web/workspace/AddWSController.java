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
import edu.asu.spring.quadriga.domain.factories.IWorkspaceFactory;
import edu.asu.spring.quadriga.domain.implementation.WorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.workspace.ICheckWSSecurity;
import edu.asu.spring.quadriga.service.workspace.IModifyWSManager;
import edu.asu.spring.quadriga.web.StringConstants;

@Controller
public class AddWSController
{
	@Autowired
	private IWorkspaceFactory workspaceFactory;

	@Autowired 
	IUserManager userManager;

	@Autowired
	IModifyWSManager modifyWSManger;

	@Autowired
	ICheckWSSecurity workspaceSecurity;
	
	/**
	 * This is called on the addworkspace form load.
	 * @param     model
	 * @return    String - containing the path to addworkspace jsp page.
	 * @throws QuadrigaStorageException 
	 * @author    Kiran Kumar Batna
	 */
	@RequestMapping(value="auth/workbench/{projectid}/addworkspace", method=RequestMethod.GET)
	public String addWorkSpaceRequestForm(Model model,@PathVariable("projectid") String projectid,Principal principal) throws QuadrigaStorageException
	{
		boolean chkAccess;
		String userName = principal.getName();

		//check if the user has access create a workspace
		chkAccess = workspaceSecurity.chkCreateWSAccess(userName, projectid);

		if(chkAccess)
		{
			model.addAttribute("workspace", workspaceFactory.createWorkspaceObject());
			model.addAttribute("wsprojectid", projectid);
			return "auth/workbench/workspace/addworkspace";
		}
		else
		{
			throw new QuadrigaStorageException();
		}

	}

	/**
	 * This calls workspace manager to add workspace details into the database.
	 * @param    workspace
	 * @param    model
	 * @param    principal
	 * @return   String - On success loads success page and on failure loads
	 *                   the same form with error messages.
	 * @throws  QuadrigaStorageException
	 * @author  Kiran Kumar Batna
	 */
	@RequestMapping(value = "auth/workbench/{projectid}/addworkspace", method = RequestMethod.POST)
	public String addWorkSpaceRequest(@ModelAttribute("SpringWeb")WorkSpace workspace,
			ModelMap model, Principal principal,@PathVariable("projectid") String projectid) throws QuadrigaStorageException
			{
		boolean chkAccess;
		String errmsg;
		IUser wsOwner = null;
		String userName = principal.getName();

		//check if the user has access to create a workspace
		chkAccess = workspaceSecurity.chkCreateWSAccess(userName, projectid);

		if(chkAccess)
		{
			wsOwner = userManager.getUserDetails(userName);

			//set the workspace owner
			workspace.setOwner(wsOwner);

			errmsg = modifyWSManger.addWorkSpaceRequest(workspace, projectid);
			if(errmsg.equals(""))
			{
				model.addAttribute("success", 1);
				model.addAttribute("successMsg",StringConstants.WORKSPACE_SUCCESS_MSG);
				model.addAttribute("wsprojectid", projectid);
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
			throw new QuadrigaStorageException();
		}
			}

}
