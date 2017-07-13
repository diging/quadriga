package edu.asu.spring.quadriga.web.workspace;

import java.beans.PropertyEditorSupport;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestDataBinder;
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
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.factories.IModifyCollaboratorFormFactory;
import edu.asu.spring.quadriga.domain.workspace.IWorkspace;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceCollaboratorManager;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceManager;
import edu.asu.spring.quadriga.validator.CollaboratorFormValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaborator;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaboratorForm;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaboratorFormManager;

@Controller
public class UpdateWSCollabController {
    @Autowired
    private IQuadrigaRoleManager roleManager;

    @Autowired
    private IModifyCollaboratorFormFactory collaboratorFactory;

    @Autowired
    private IWorkspaceCollaboratorManager wsModifyCollabManager;

    @Autowired
    private CollaboratorFormValidator validator;

    @Autowired
    private ModifyCollaboratorFormManager collaboratorFormManager;

    @Autowired
    private IWorkspaceManager wsManager;

    @Autowired
    private MessageSource messageSource;

    /**
     * Custom validator to validate the input selection form the user
     * 
     * @param request
     * @param binder
     * @param validateBinder
     * @throws Exception
     */
    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder, WebDataBinder validateBinder)
            throws Exception {

        validateBinder.setValidator(validator);

        binder.registerCustomEditor(List.class, "collaborators.collaboratorRoles", new PropertyEditorSupport() {

            @Override
            public void setAsText(String text) {
                String[] roleIds = text.split(",");
                List<IQuadrigaRole> roles = new ArrayList<IQuadrigaRole>();
                for (String roleId : roleIds) {
                    IQuadrigaRole role = roleManager.getQuadrigaRoleById(IQuadrigaRoleManager.WORKSPACE_ROLES,
                            roleId.trim());
                    roles.add(role);
                }
                setValue(roles);
            }
        });
    }

    /**
     * Retrieve all the collaborators associated to the workspace to update
     * their roles
     * 
     * @param workspaceid
     * @param principal
     * @return ModelAndView
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE, paramIndex = 1, userRole = {
            RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/workspace/{workspaceid}/updatecollaborators", method = RequestMethod.GET)
    public ModelAndView updateWorkspaceCollaboratorForm(@PathVariable("workspaceid") String workspaceid,
            Principal principal) throws QuadrigaStorageException, QuadrigaAccessException {
        ModelAndView model;
        ModifyCollaboratorForm collaboratorForm;
        List<ModifyCollaborator> collaboratorList = new ArrayList<ModifyCollaborator>();

        // create model view
        model = new ModelAndView("auth/workbench/workspace/updatecollaborators");
        String userName = principal.getName();
        IWorkspace workspace = wsManager.getWorkspaceDetails(workspaceid, userName);

        // retrieve the list of Collaborators and their roles.
        collaboratorList = collaboratorFormManager.modifyWorkspaceCollaboratorManager(workspaceid);

        // fetch the roles that can be associated to the workspace collaborator
        List<IQuadrigaRole> collaboratorRoles = roleManager.getQuadrigaRoles(IQuadrigaRoleManager.WORKSPACE_ROLES);

        // create a model for collaborators
        collaboratorForm = collaboratorFactory.createCollaboratorFormObject();
        collaboratorForm.setCollaborators(collaboratorList);

        // add the collaborator roles to the model
        model.getModelMap().put("wscollabroles", collaboratorRoles);
        model.getModelMap().put("collaboratorform", collaboratorForm);
        model.getModelMap().put("workspaceid", workspaceid);
        model.getModelMap().put("workspacename", workspace.getWorkspaceName());
        model.getModelMap().put("workspacedesc", workspace.getDescription());

        return model;
    }

    /**
     * This method updates the roles of collaborators associated to the
     * workspace
     * 
     * @param collaboratorForm
     * @param result
     * @param workspaceid
     * @param principal
     * @return ModelAndView
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE, paramIndex = 3, userRole = {
            RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/workspace/{workspaceid}/updatecollaborators", method = RequestMethod.POST)
    public String updateWorkspaceCollaborator(
            @Validated @ModelAttribute("collaboratorform") ModifyCollaboratorForm collaboratorForm,
            BindingResult result, @PathVariable("workspaceid") String workspaceid, Principal principal, Model model,
            Locale locale, RedirectAttributes redirectAttrs) throws QuadrigaStorageException, QuadrigaAccessException {
        // create model view
        if (result.hasErrors()) {
            // add the workspace details
            IWorkspace workspace = wsManager.getWorkspaceDetails(workspaceid, principal.getName());
            model.addAttribute("workspacename", workspace.getWorkspaceName());
            model.addAttribute("workspacedesc", workspace.getDescription());

            // add the model map
            List<ModifyCollaborator> wsCollaborators = collaboratorFormManager
                    .modifyWorkspaceCollaboratorManager(workspaceid);
            collaboratorForm.setCollaborators(wsCollaborators);
            model.addAttribute("collaboratorform", collaboratorForm);
            model.addAttribute("workspaceid", workspaceid);

            // retrieve the collaborator roles and assign it to a map
            // fetch the roles that can be associated to the workspace
            // collaborator
            List<IQuadrigaRole> collaboratorRoles = roleManager.getQuadrigaRoles(IQuadrigaRoleManager.WORKSPACE_ROLES);
            model.addAttribute("wscollabroles", collaboratorRoles);
            model.addAttribute("show_error_alert", true);
            model.addAttribute("error_alert_msg",
                    messageSource.getMessage("workspace.collaborators.update.failure", new String[] {}, locale));

            return "auth/workbench/workspace/updatecollaborators";
        }

        List<ModifyCollaborator> wsCollaborators = collaboratorForm.getCollaborators();

        // fetch the user and his collaborator roles
        for (ModifyCollaborator collab : wsCollaborators) {
            String collabRoles = "";
            String collabUser = collab.getUserName();
            List<IQuadrigaRole> values = collab.getCollaboratorRoles();

            // fetch the role names for the roles and form a string
            for (IQuadrigaRole role : values) {
                collabRoles = collabRoles + "," + role.getDBid();
            }

            collabRoles = collabRoles.substring(1);

            // call the database to modify the record
            wsModifyCollabManager.updateCollaborators(workspaceid, collabUser, collabRoles, principal.getName());
        }

        redirectAttrs.addFlashAttribute("show_success_alert", true);
        redirectAttrs.addFlashAttribute("success_alert_msg",
                messageSource.getMessage("workspace.collaborators.update.success", new String[] {}, locale));

        return "redirect:/auth/workbench/workspace/" + workspaceid;
    }

}
