package edu.asu.spring.quadriga.web.workbench;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IModifyProjectManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class DeleteProjectController {
    @Autowired
    private IModifyProjectManager projectManager;

    /**
     * This method calls the user manager to delete the project.
     * 
     * @param projectId
     *            Project identifier corresponding to project to be deleted
     * @return model - URL on success and failure.
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 1, userRole = {
            RoleNames.ROLE_COLLABORATOR_OWNER, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/deleteproject/{projectId}", method = RequestMethod.GET)
    public ModelAndView deleteProjectRequest(@PathVariable("projectId") String projectId,
            RedirectAttributes redirectAttributes, Principal principal) throws QuadrigaStorageException,
            QuadrigaAccessException {
        ModelAndView model = new ModelAndView("redirect:/auth/workbench");

        ArrayList<String> projectIdList = new ArrayList<String>();
        projectIdList.add(projectId);

        projectManager.deleteProjectRequest(projectIdList, principal);

        redirectAttributes.addFlashAttribute("show_success_alert", true);
        redirectAttributes.addFlashAttribute("success_alert_msg", "The project has been successfully deleted.");

        return model;
    }
}
