package edu.asu.spring.quadriga.web.workspace;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IModifyWSManager;
import edu.asu.spring.quadriga.web.login.RoleNames;
import edu.asu.spring.quadriga.web.workspace.backing.ModifyWorkspaceForm;

@Controller
public class DeleteWSController {
	@Autowired
	IModifyWSManager modifyWSManger;

	/**
	 * This calls workspaceManager to delete the workspace submitted.
	 * 
	 * @param projectid
	 * @param req
	 * @param model
	 * @param principal
	 * @return String - URL of the form
	 * @throws QuadrigaStorageException
	 * @author Karthikeyan Mohan
	 * @throws QuadrigaAccessException
	 */
	@AccessPolicies({
			@ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 1, userRole = {
					RoleNames.ROLE_COLLABORATOR_ADMIN, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN,
					RoleNames.ROLE_PROJ_COLLABORATOR_CONTRIBUTOR }),
			@ElementAccessPolicy(type = CheckedElementType.WORKSPACE, paramIndex = 0, userRole = {
					RoleNames.ROLE_COLLABORATOR_ADMIN, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN,
					RoleNames.ROLE_PROJ_COLLABORATOR_CONTRIBUTOR }) })
	@RequestMapping(value = "auth/workbench/deleteSingleWorkspace/{workspaceid}", method = RequestMethod.GET)
	public ModelAndView deleteSingleWorkspaceRequest(@RequestParam("projectId") String myprojectid,
			@Validated @ModelAttribute("workspaceform") ModifyWorkspaceForm workspaceForm, BindingResult result,
			@PathVariable("workspaceid") String workspaceid, Principal principal)
					throws QuadrigaStorageException, QuadrigaAccessException {
		ModelAndView model;
		model = new ModelAndView("auth/workbench/workspace/deleteworkspace");
		modifyWSManger.deleteWorkspace(workspaceid.toString());
		model.getModelMap().put("success", 1);
		model.getModelMap().put("wsprojectid", myprojectid);
		return model;
	}

}
