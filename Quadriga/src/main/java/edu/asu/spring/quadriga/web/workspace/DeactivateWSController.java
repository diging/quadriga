package edu.asu.spring.quadriga.web.workspace;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IArchiveWSManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class DeactivateWSController {
    @Autowired
    private IArchiveWSManager archiveWSManager;

    /**
     * This method deactivates the workpace for the project
     * 
     * @param projectid Project identifier
     * @param workspaceid Workspace identifier
     * @param principal User for the workspace
     * @param redirectAttributes Attributes to be forwarded to another controller
     * @return Model containing view
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    @AccessPolicies({
            @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 1, userRole = {
                    RoleNames.ROLE_COLLABORATOR_OWNER,
                    RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN,
                    RoleNames.ROLE_PROJ_COLLABORATOR_CONTRIBUTOR }),
            @ElementAccessPolicy(type = CheckedElementType.WORKSPACE, paramIndex = 2, userRole = {
                    RoleNames.ROLE_COLLABORATOR_OWNER,
                    RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN,
                    RoleNames.ROLE_PROJ_COLLABORATOR_CONTRIBUTOR }) })
    @RequestMapping(value = "auth/workbench/{workspaceid}/deactivateworkspace", method = RequestMethod.GET)
    public ModelAndView deactivateWorkspaceForm(
            @RequestParam("projectid") String projectid,
            @PathVariable("workspaceid") String workspaceid,
            Principal principal, RedirectAttributes redirectAttributes)
            throws QuadrigaStorageException, QuadrigaAccessException {

        ModelAndView model = new ModelAndView("redirect:/auth/workbench/workspace/"
                + workspaceid);
        
        archiveWSManager.deactivateWorkspace(workspaceid, principal.getName());

        redirectAttributes.addFlashAttribute("show_success_alert", true);
        redirectAttributes.addFlashAttribute("success_alert_msg",
                "The workspace has been successfully deactivated.");
        return model;
    }
}