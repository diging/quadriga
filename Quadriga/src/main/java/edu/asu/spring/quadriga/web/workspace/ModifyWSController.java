package edu.asu.spring.quadriga.web.workspace;

import java.security.Principal;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.workspace.IWorkspace;
import edu.asu.spring.quadriga.domain.workspace.impl.Workspace;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.exceptions.RestException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.workspace.IModifyWSManager;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceManager;
import edu.asu.spring.quadriga.validator.WorkspaceValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class ModifyWSController {

    @Autowired
    private IUserManager userManager;

    @Autowired
    private IModifyWSManager modifyWSManager;

    @Autowired
    private IWorkspaceManager wsManager;

    @Autowired
    private WorkspaceValidator validator;

    @Autowired
    private MessageSource messageSource;

    /**
     * Attach the custom validator to the Spring context
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {

        binder.setValidator(validator);
    }

    /**
     * This is called on the modifyworkspace on load.
     * 
     * @param model
     * @return ModelAndView
     * @throws QuadrigaStorageException
     * @author Kiran Kumar Batna
     * @throws QuadrigaAccessException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE, paramIndex = 1, userRole = { RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/workspace/{workspaceid}/update", method = RequestMethod.GET)
    public ModelAndView updateWorkSpaceRequestForm(@PathVariable("workspaceid") String workspaceid, Principal principal)
            throws QuadrigaStorageException, QuadrigaAccessException {
        // fetch the workspace details
        String userName = principal.getName();
        IWorkspace workspace = wsManager.getWorkspaceDetails(workspaceid, userName);
        ModelAndView model = new ModelAndView("auth/workbench/workspace/updateworkspace");
        model.getModelMap().put("workspace", workspace);
        return model;
    }

    /**
     * This is called on the modifyworkspace on form submission.
     * 
     * @param model
     * @return ModelAndView
     * @throws QuadrigaStorageException
     * @author Kiran Kumar Batna
     * @throws QuadrigaAccessException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE, paramIndex = 4, userRole = { RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/workspace/{workspaceid}/update", method = RequestMethod.POST)
    public String updateWorkSpaceRequest(@Validated @ModelAttribute("workspace") Workspace workspace,
            BindingResult result, Model model, @PathVariable("workspaceid") String workspaceid, Principal principal, Locale locale, RedirectAttributes redirectAttrs)
            throws QuadrigaStorageException, QuadrigaAccessException {
        
        String userName = principal.getName();
        IUser wsOwner = userManager.getUser(userName);

        // set the workspace owner
        workspace.setOwner(wsOwner);

        // set the workspace id
        workspace.setWorkspaceId(workspaceid);

        if (result.hasErrors()) {
            model.addAttribute("workspace", workspace);
            model.addAttribute("show_error_alert", true);
            model.addAttribute("error_alert_msg", messageSource.getMessage("workspace.update.failure", new String[] {}, locale));
            return "auth/workbench/workspace/updateworkspace";
        }
        
        modifyWSManager.updateWorkspace(workspace);
        redirectAttrs.addFlashAttribute("show_success_alert", true);
        redirectAttrs.addFlashAttribute("success_alert_msg", messageSource.getMessage("workspace.update.success", new String[] {}, locale));
        return "redirect:/auth/workbench/workspace/" + workspaceid;

    }

    /**
     * Assign editor to owner for workspace level
     * 
     * @param workspaceId
     * @param model
     * @param principal
     * @return
     * @throws QuadrigaStorageException
     * @throws QuadrigaException
     * @throws QuadrigaAccessException
     * @throws RestException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE, paramIndex = 1, userRole = { RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/workspace/assignEditorRoleToOwner/{workspaceid}", method = RequestMethod.GET)
    public String assignEditorRoleToOwner(@PathVariable("workspaceid") String workspaceId, ModelMap model,
            Principal principal, RedirectAttributes redirectAttrs, Locale locale) throws QuadrigaStorageException,
            QuadrigaException, QuadrigaAccessException, RestException {
        IUser user = userManager.getUser(principal.getName());
        String userName = user.getUserName();
        modifyWSManager.assignEditorRole(workspaceId, userName);

        redirectAttrs.addFlashAttribute("show_success_alert", true);
        redirectAttrs.addFlashAttribute("success_alert_msg",
                messageSource.getMessage("workspace.owner_is_editor.success", new String[] {}, locale));

        return "redirect:/auth/workbench/workspace/" + workspaceId;
    }

    /**
     * Assign editor to owner for workspace level
     * 
     * @param workspaceId
     * @param model
     * @param principal
     * @return
     * @throws QuadrigaStorageException
     * @throws QuadrigaException
     * @throws QuadrigaAccessException
     * @throws RestException
     */
    @RequestMapping(value = "auth/workbench/workspace/deleteEditorRoleToOwner/{workspaceid}", method = RequestMethod.GET)
    public String deleteEditorRoleToOwner(@PathVariable("workspaceid") String workspaceId, ModelMap model,
            Principal principal, RedirectAttributes redirectAttrs, Locale locale) throws QuadrigaStorageException,
            QuadrigaException, QuadrigaAccessException, RestException {
        IUser user = userManager.getUser(principal.getName());
        String userName = user.getUserName();
        boolean success = modifyWSManager.deleteEditorRole(workspaceId, userName);
        if (success) {
            redirectAttrs.addFlashAttribute("show_success_alert", true);
            redirectAttrs.addFlashAttribute("success_alert_msg",
                    messageSource.getMessage("workspace.owner_is_not_editor.success", new String[] {}, locale));
        }
        return "redirect:/auth/workbench/workspace/" + workspaceId;
    }
}
