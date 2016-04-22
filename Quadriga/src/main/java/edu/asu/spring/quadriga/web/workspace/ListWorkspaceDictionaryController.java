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

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceDictionary;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceDictionaryManager;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class ListWorkspaceDictionaryController {
    @Autowired
    private IWorkspaceManager wsManager;

    @Autowired
    private IWorkspaceDictionaryManager workspaceDictionaryManager;

    /**
     * Retrieve all the dictionaries associated with the workspace
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
    @RequestMapping(value = "auth/workbench/workspace/{workspaceid}/dictionaries", method = RequestMethod.GET)
    public String listWorkspaceDictionary(HttpServletRequest req, @PathVariable("workspaceid") String workspaceId,
            Model model, Principal principal) throws QuadrigaStorageException, QuadrigaAccessException {
        String userId = principal.getName();

        List<IWorkspaceDictionary> dicitonaryList = workspaceDictionaryManager.listWorkspaceDictionary(workspaceId,
                userId);
        model.addAttribute("dicitonaryList", dicitonaryList);
        IWorkSpace workspace = wsManager.getWorkspaceDetails(workspaceId, userId);
        model.addAttribute("workspacedetails", workspace);
        model.addAttribute("workspaceId", workspaceId);
        return "auth/workbench/workspace/dictionaries";
    }
}
