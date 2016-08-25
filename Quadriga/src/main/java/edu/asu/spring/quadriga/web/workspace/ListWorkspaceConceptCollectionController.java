package edu.asu.spring.quadriga.web.workspace;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceConceptCollection;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceCCManager;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class ListWorkspaceConceptCollectionController {

    @Autowired
    private IWorkspaceManager wsManager;

    @Autowired
    private IWorkspaceCCManager workspaceCCManager;

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
        List<IWorkspaceConceptCollection> conceptCollectionList = workspaceCCManager.listWorkspaceCC(workspaceId);
        model.addAttribute("conceptCollectionList", conceptCollectionList);
        IWorkSpace workspace = wsManager.getWorkspaceDetails(workspaceId, userId);
        model.addAttribute("workspacedetails", workspace);
        model.addAttribute("workspaceId", workspaceId);
        return "auth/workbench/workspace/conceptcollections";
    }
}
