package edu.asu.spring.quadriga.web.workspace;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.domain.implementation.WorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.workspace.ICheckWSSecurity;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;
import edu.asu.spring.quadriga.service.workspace.IModifyWSManager;
import edu.asu.spring.quadriga.validator.WorkspaceValidator;

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

	@Autowired
	WorkspaceValidator validator;

	/**
	 * Attach the custom validator to the Spring context
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {

		binder.setValidator(validator);
	}

	/**
	 * This is called on the modifyworkspace on load.
	 * @param     model
	 * @return   ModelAndView
	 * @throws QuadrigaStorageException 
	 * @author    Kiran Kumar Batna
	 * @throws QuadrigaAccessException 
	 */
	@RequestMapping(value="auth/workbench/workspace/updateworkspacedetails/{workspaceid}", method=RequestMethod.GET)
	public ModelAndView updateWorkSpaceRequestForm(@PathVariable("workspaceid") String workspaceid,Principal principal) throws QuadrigaStorageException, QuadrigaAccessException
	{
		ModelAndView model;
		IWorkSpace workspace;
		String userName;

		//fetch the workspace details
		userName = principal.getName();
		workspace = wsManager.getWorkspaceDetails(workspaceid, userName);
		model = new ModelAndView("auth/workbench/workspace/updateworkspace");
		model.getModelMap().put("workspace", workspace);
		return model;
	}

	/**
	 * This is called on the modifyworkspace on form submission.
	 * @param     model
	 * @return    ModelAndView
	 * @throws QuadrigaStorageException 
	 * @author    Kiran Kumar Batna
	 * @throws QuadrigaAccessException 
	 */
	@RequestMapping(value = "auth/workbench/workspace/updateworkspacedetails/{workspaceid}", method = RequestMethod.POST)
	public ModelAndView updateWorkSpaceRequest(@Validated @ModelAttribute("workspace")WorkSpace workspace,
			@PathVariable("workspaceid") String workspaceid,BindingResult result,Principal principal) throws QuadrigaStorageException
			{
		ModelAndView model;
		IUser wsOwner = null;
		String userName = principal.getName();

		wsOwner = userManager.getUserDetails(userName);

		//set the workspace owner
		workspace.setOwner(wsOwner);

		//set the workspace id
		workspace.setId(workspaceid);


		if(result.hasErrors())
		{
			model = new ModelAndView("auth/workbench/workspace/updateworkspace");
			model.getModelMap().put("workspace", workspace);
			return model;
		}
		else
		{
			modifyWSManager.updateWorkspaceRequest(workspace);
			model = new ModelAndView("auth/workbench/workspace/updateworkspacestatus");
			model.getModelMap().put("success", 1);
			return model;
		}
			}
}
