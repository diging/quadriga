package edu.asu.spring.quadriga.web.workspace;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.factory.impl.workspace.WorkspaceFormFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IArchiveWSManager;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;
import edu.asu.spring.quadriga.validator.WorkspaceFormValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;
import edu.asu.spring.quadriga.web.workspace.backing.ModifyWorkspace;
import edu.asu.spring.quadriga.web.workspace.backing.ModifyWorkspaceFormManager;

@Controller
public class ActivateWSController {
    @Autowired
    IArchiveWSManager archiveWSManager;
    
    @Autowired
    private IListWSManager listWsManager;

    @Autowired
    ModifyWorkspaceFormManager workspaceFormManager;

    @Autowired
    WorkspaceFormFactory workspaceFormFactory;

    @Autowired
    WorkspaceFormValidator validator;

    /**
     * Attach the custom validator to the Spring context
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {

        binder.setValidator(validator);
    }

	/**
	 * This method activates workspace for a given workspaceid and projectid.
	 * 
	 * @param workspaceid
	 * @param projectid
	 * @param principal
	 * @param redirectAttributes
	 * @return String - URL of the form
	 * @throws QuadrigaStorageException
	 * @throws QuadrigaAccessException
	 * @author Hari Chandana Kanchanapally
	 */
    @AccessPolicies({
            @ElementAccessPolicy(type = CheckedElementType.WORKSPACE, paramIndex = 1, userRole = {
                    RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN,
                    RoleNames.ROLE_WORKSPACE_COLLABORATOR_CONTRIBUTOR }),
            @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 2, userRole = {
                    RoleNames.ROLE_COLLABORATOR_OWNER,
                    RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN,
                    RoleNames.ROLE_PROJ_COLLABORATOR_CONTRIBUTOR }) })
    @RequestMapping(value = "auth/workbench/{workspaceid}/activateWorkspace", method = RequestMethod.GET)
    public ModelAndView activateWorkspace(
            @PathVariable("workspaceid") String workspaceid,
            @RequestParam("projectid") String projectid, Principal principal,
            RedirectAttributes redirectAttributes)
            throws QuadrigaStorageException, QuadrigaAccessException {

        // fetch the user name
        String userName = principal.getName();

        ModelAndView model = new ModelAndView("redirect:/auth/workbench/workspace/"
                + workspaceid);

        archiveWSManager.activateWorkspace(workspaceid, userName);

        // frame the model objects
        redirectAttributes.addFlashAttribute("show_success_alert", true);
        redirectAttributes.addFlashAttribute("success_alert_msg",
                "The workspace has been successfully activated.");

        return model;
    }

    @AccessPolicies({
        @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 1, userRole = {
                RoleNames.ROLE_COLLABORATOR_OWNER,
                RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN,
                RoleNames.ROLE_PROJ_COLLABORATOR_CONTRIBUTOR }) })
    @RequestMapping(value = "auth/workbench/{projectid}/showinactiveworkspace", method = RequestMethod.GET)
    public String showInactiveWorkspaces(
            @PathVariable("projectid") String projectId, Principal principal,
            Model model) throws QuadrigaStorageException,
            QuadrigaAccessException {
        List<ModifyWorkspace> deactivatedWSList = workspaceFormManager
                .getDeactivatedWorkspaceList(projectId, principal.getName());
        model.addAttribute("deactivatedWSList", deactivatedWSList);
        model.addAttribute("deactivatedWSProjectId", projectId);
        return "auth/workbench/workspace/showInactiveWorkspace";
    }

}
