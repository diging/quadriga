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
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.web.login.RoleNames;
import edu.asu.spring.quadriga.web.workspace.backing.ModifyWorkspace;
import edu.asu.spring.quadriga.web.workspace.backing.ModifyWorkspaceFormManager;

@Controller
public class ListArchivedWSController {

    @Autowired
    private ModifyWorkspaceFormManager workspaceFormManager;
    
    @AccessPolicies({
        @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 1, userRole = {
                RoleNames.ROLE_COLLABORATOR_ADMIN,
                RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN,
                RoleNames.ROLE_PROJ_COLLABORATOR_CONTRIBUTOR }) })
    @RequestMapping(value = "auth/workbench/{projectid}/showarchivedworkspace", method = RequestMethod.GET)
    public String showArchivedWorkspaces(
            @PathVariable("projectid") String projectId, Principal principal,
            Model model) throws QuadrigaStorageException,
            QuadrigaAccessException {
        List<ModifyWorkspace> archivedWSList = workspaceFormManager
                .getArchivedWorkspaceList(projectId, principal.getName());
        model.addAttribute("archivedWSList", archivedWSList);
        model.addAttribute("listArchivedWSProjectid", projectId);
        return "auth/workbench/workspace/showArchivedWorkspace";
    }
}
