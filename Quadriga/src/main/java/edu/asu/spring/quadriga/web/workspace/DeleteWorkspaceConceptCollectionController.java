package edu.asu.spring.quadriga.web.workspace;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.workspace.IWorkspace;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceCCManager;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class DeleteWorkspaceConceptCollectionController {

    @Autowired
    private IWorkspaceManager wsManager;

    @Autowired
    private IWorkspaceCCManager workspaceCCManager;

    /**
     * Retrieve the concept collection associated to the given workspace for
     * deletion
     * 
     * @param workspaceId
     * @param model
     * @return String - URL to redirect on success or failure
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE, paramIndex = 1, userRole = {
            RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/workspace/{workspaceid}/deleteconceptcollections", method = RequestMethod.GET)
    public String deleteWorkspaceConceptCollection(@PathVariable("workspaceid") String workspaceId, Model model,
            Principal principal) throws QuadrigaStorageException, QuadrigaAccessException {
        String userId = principal.getName();

        List<IConceptCollection> collections = workspaceCCManager.getConceptCollections(workspaceId);
        model.addAttribute("collections", collections);
        IWorkspace workspace = wsManager.getWorkspaceDetails(workspaceId, userId);
        model.addAttribute("workspacedetails", workspace);
        model.addAttribute("projectid", workspaceId);
        return "auth/workbench/workspace/deleteconceptcollections";
    }

    /**
     * Deletes the association of the selected concept collections from the
     * workspace
     * 
     * @param req
     * @param workspaceId
     * @param model
     * @return String - URL to redirect on success or failure
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE, paramIndex = 2, userRole = {
            RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/workspace/{workspaceid}/deleteconceptcollections", method = RequestMethod.POST)
    public String deleteWorkspaceConceptCollection(HttpServletRequest req,
            @PathVariable("workspaceid") String workspaceId, Model model, RedirectAttributes attr, Principal principal)
                    throws QuadrigaStorageException, QuadrigaAccessException {
        String userId = principal.getName();

        String[] values = req.getParameterValues("selected");
        if (values == null) {
            attr.addFlashAttribute("show_error_alert", true);
            attr.addFlashAttribute("error_alert_msg", "Please select a Concept Collection.");
            return "redirect:/auth/workbench/workspace/" + workspaceId + "/deleteconceptcollections";
        }
        for (int i = 0; i < values.length; i++) {
            workspaceCCManager.deleteWorkspaceCC(workspaceId, userId, values[i]);
        }
        attr.addFlashAttribute("show_success_alert", true);
        attr.addFlashAttribute("success_alert_msg", "Concept Collection deleted from workspace successfully.");
        return "redirect:/auth/workbench/workspace/" + workspaceId;
    }
}
