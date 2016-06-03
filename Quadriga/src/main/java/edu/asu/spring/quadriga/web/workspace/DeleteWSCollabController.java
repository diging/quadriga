package edu.asu.spring.quadriga.web.workspace;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceCollaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceCollaboratorManager;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class DeleteWSCollabController {

    @Autowired
    private IWorkspaceCollaboratorManager wsCollabManager;

    @Autowired
    private IWorkspaceManager workspaceManager;

    @Autowired
    private MessageSource messageSource;

    /**
     * Retrieve the collaborators associated with the workspace for deletion
     * 
     * @param workspaceid
     * @param principal
     * @return ModelAndView
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE, paramIndex = 1, userRole = { RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/workspace/{workspaceid}/deletecollaborators", method = RequestMethod.GET)
    public ModelAndView deleteWorkspaceCollaboratorForm(@PathVariable("workspaceid") String workspaceid,
            Principal principal) throws QuadrigaStorageException, QuadrigaAccessException {
        ModelAndView model;
        List<IWorkspaceCollaborator> collaboratingUser = new ArrayList<IWorkspaceCollaborator>();

        model = new ModelAndView("auth/workbench/workspace/deletecollaborators");

        String userName = principal.getName();
        IWorkSpace workspace = workspaceManager.getWorkspaceDetails(workspaceid, userName);

        // fetch all the collaborating users and their roles
        collaboratingUser = wsCollabManager.getWorkspaceCollaborators(workspaceid);

        model.getModelMap().put("collaboratingusers", collaboratingUser);

        // set the workspace id
        model.getModelMap().put("workspaceid", workspaceid);
        model.getModelMap().put("workspacename", workspace.getWorkspaceName());
        model.getModelMap().put("workspacedesc", workspace.getDescription());

        return model;
    }

    /**
     * Deletes the selected collaborators association from the workspace
     * 
     * @param workspaceid
     * @param req
     * @param principal
     * @return ModelAndView
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE, paramIndex = 1, userRole = { RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/workspace/{workspaceid}/deletecollaborators", method = RequestMethod.POST)
    public String deleteWorkspaceCollaborator(@PathVariable("workspaceid") String workspaceid, HttpServletRequest req,
            Principal principal, Model model, Locale locale, RedirectAttributes redirectAttrs)
            throws QuadrigaStorageException, QuadrigaAccessException {

        String loggedInUser = principal.getName();
        IWorkSpace workspace = workspaceManager.getWorkspaceDetails(workspaceid, loggedInUser);

        // fetch the selected values
        String[] values = req.getParameterValues("wscollabchecked");

        if (values == null || values.length == 0) {
            // fetch all the collaborating users and their roles
            List<IWorkspaceCollaborator> collaboratingUser = wsCollabManager.getWorkspaceCollaborators(workspaceid);

            model.addAttribute("collaboratingusers", collaboratingUser);

            // set the workspace id
            model.addAttribute("workspaceid", workspaceid);
            model.addAttribute("workspacename", workspace.getWorkspaceName());
            model.addAttribute("workspacedesc", workspace.getDescription());

            model.addAttribute("show_error_alert", true);
            model.addAttribute("error_alert_msg",
                    messageSource.getMessage("workspace.collaborators.remove.failure", new String[] {}, locale));
            return "auth/workbench/workspace/deletecollaborators";
        }

        String userName = "";
        for (String user : values) {
            userName = userName + "," + user;
        }

        // remove the first ',' form the list
        userName = userName.substring(1);

        // delete the collaborators
        wsCollabManager.deleteCollaborators(userName, workspaceid);

        redirectAttrs.addFlashAttribute("show_success_alert", true);
        redirectAttrs.addFlashAttribute("success_alert_msg",
                messageSource.getMessage("workspace.collaborators.remove.success", new String[] {}, locale));

        return "redirect:/auth/workbench/workspace/" + workspaceid;
    }
}
