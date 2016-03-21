package edu.asu.spring.quadriga.web.workspace;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceConceptCollection;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceCCManager;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class ConceptCollectionWorkspaceController {

    @Autowired
    private IWorkspaceManager wsManager;

    @Autowired
    private IWorkspaceCCManager workspaceCCManager;

    private static final Logger logger = LoggerFactory.getLogger(ConceptCollectionWorkspaceController.class);

    /**
     * Retrieves the concept collections associated with the given workspace
     * 
     * @param workspaceId
     * @param model
     * @return String - Url to redirect the page on success or failure
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE, paramIndex = 1, userRole = {
            RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/workspace/{workspaceid}/conceptcollections", method = RequestMethod.GET)
    public String listProjectConceptCollection(@PathVariable("workspaceid") String workspaceId, Model model,
            Principal principal) throws QuadrigaStorageException, QuadrigaAccessException {
        String userId = principal.getName();
        // List<IConceptCollection> conceptCollectionList = null;
        List<IWorkspaceConceptCollection> conceptCollectionList = null;

        conceptCollectionList = workspaceCCManager.listWorkspaceCC(workspaceId, userId);
        if (conceptCollectionList == null) {
            logger.info("Concept collection list is empty buddy");
        }
        model.addAttribute("conceptCollectionList", conceptCollectionList);
        IWorkSpace workspace = wsManager.getWorkspaceDetails(workspaceId, userId);
        model.addAttribute("workspacedetails", workspace);
        model.addAttribute("workspaceId", workspaceId);
        return "auth/workbench/workspace/conceptcollections";
    }

    /**
     * Retrieve the concept collections which are not associated to the given
     * workspace
     * 
     * @param workspaceId
     * @param model
     * @return String - String - Url to redirect the page on success or failure
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE, paramIndex = 1, userRole = {
            RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/workspace/{workspaceid}/addconceptcollection", method = RequestMethod.GET)
    public String addWorkspaceConceptCollection(@PathVariable("workspaceid") String workspaceId, Model model,
            Principal principal) throws QuadrigaStorageException, QuadrigaAccessException {
        String userId = principal.getName();

        List<IConceptCollection> conceptCollectionList = null;
        conceptCollectionList = workspaceCCManager.getNonAssociatedWorkspaceConcepts(workspaceId, userId);
        if (conceptCollectionList == null) {
            logger.info("conceptCollectionList list is empty");
        }
        model.addAttribute("conceptCollectionList", conceptCollectionList);
        IWorkSpace workspace = wsManager.getWorkspaceDetails(workspaceId, userId);
        model.addAttribute("workspacedetails", workspace);
        model.addAttribute("workspaceId", workspaceId);
        model.addAttribute("userId", userId);
        return "auth/workbench/workspace/addconceptcollections";
    }

    /**
     * Associate the concept collection with the given workspace
     * 
     * @param req
     * @param workspaceId
     * @param model
     * @return String - URL to direct the page on success or failure
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE, paramIndex = 2, userRole = {
            RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/workspace/{workspaceid}/addconceptcollection", method = RequestMethod.POST)
    public String addWorkspaceConceptCollection(HttpServletRequest req, @PathVariable("workspaceid") String workspaceId,
            Model model, RedirectAttributes attr, Principal principal)
                    throws QuadrigaStorageException, QuadrigaAccessException {
        String msg = "";
        int flag = 0;
        String userId = principal.getName();

        String[] values = req.getParameterValues("selected");
        if (values == null) {
            attr.addFlashAttribute("show_error_alert", true);
            attr.addFlashAttribute("error_alert_msg", "Please select a Concept Collection");
            return "redirect:/auth/workbench/workspace/" + workspaceId + "/addconceptcollection";
        }
        for (int i = 0; i < values.length; i++) {
            msg = workspaceCCManager.addWorkspaceCC(workspaceId, values[i], userId);
            if (!msg.equals("")) {
                flag = 1;
            }
        }
        if (flag == 0) {
            attr.addFlashAttribute("show_success_alert", true);
            attr.addFlashAttribute("success_alert_msg", "Concept Collection added to workspace successfully");
        } else {
            attr.addFlashAttribute("show_error_alert", true);
            attr.addFlashAttribute("error_alert_msg", "Concept Collection is already added or some internal issue");
        }
        List<IWorkspaceConceptCollection> conceptCollectionList = null;
        conceptCollectionList = workspaceCCManager.listWorkspaceCC(workspaceId, userId);
        if (conceptCollectionList == null) {
            logger.info("conceptCollectionList list is empty buddy");
        }
        model.addAttribute("conceptCollectionList", conceptCollectionList);
        IWorkSpace workspace = wsManager.getWorkspaceDetails(workspaceId, userId);
        model.addAttribute("workspacedetails", workspace);
        model.addAttribute("workspaceId", workspaceId);
        return "redirect:/auth/workbench/workspace/" + workspaceId + "/addconceptcollection";
    }

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

        List<IWorkspaceConceptCollection> conceptCollectionList = null;
        conceptCollectionList = workspaceCCManager.listWorkspaceCC(workspaceId, userId);
        if (conceptCollectionList == null) {
            logger.info("conceptCollectionList list is empty buddy");
        }
        model.addAttribute("conceptCollectionList", conceptCollectionList);
        IWorkSpace workspace = wsManager.getWorkspaceDetails(workspaceId, userId);
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
        String msg = "";
        int flag = 0;

        String[] values = req.getParameterValues("selected");
        if (values == null) {
            attr.addFlashAttribute("show_error_alert", true);
            attr.addFlashAttribute("error_alert_msg", "Please select a Concept Collection");
            return "redirect:/auth/workbench/workspace/" + workspaceId + "/deleteconceptcollections";
        }
        for (int i = 0; i < values.length; i++) {
            workspaceCCManager.deleteWorkspaceCC(workspaceId, userId, values[i]);
            if (!msg.equals("")) {
                flag = 1;
            }
        }
        if (flag == 0) {
            attr.addFlashAttribute("show_success_alert", true);
            attr.addFlashAttribute("success_alert_msg", "Concept Collection deleted from workspace successfully");
        } else {
            attr.addFlashAttribute("show_error_alert", true);
            attr.addFlashAttribute("error_alert_msg", "Unable to delete Concept Collection due to some internal issue");
        }
        List<IWorkspaceConceptCollection> conceptCollectionList = null;
        conceptCollectionList = workspaceCCManager.listWorkspaceCC(workspaceId, userId);
        if (conceptCollectionList == null) {
            logger.info("Dictionary list is empty buddy");
        }
        model.addAttribute("conceptCollectionList", conceptCollectionList);
        IWorkSpace workspace = wsManager.getWorkspaceDetails(workspaceId, userId);
        model.addAttribute("workspacedetails", workspace);
        model.addAttribute("projectid", workspaceId);
        return "redirect:/auth/workbench/workspace/" + workspaceId + "/deleteconceptcollections";
    }
}
