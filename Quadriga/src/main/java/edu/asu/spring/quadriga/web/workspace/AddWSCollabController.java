package edu.asu.spring.quadriga.web.workspace;

import java.beans.PropertyEditorSupport;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
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

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.impl.Collaborator;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceCollaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceCollaboratorManager;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceManager;
import edu.asu.spring.quadriga.service.workspace.impl.WorkspaceCollaboratorManager;
import edu.asu.spring.quadriga.validator.CollaboratorValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class AddWSCollabController {

    @Autowired
    private IWorkspaceCollaboratorManager wsManager;

    @Autowired
    private IQuadrigaRoleManager roleManager;

    @Autowired
    ICollaboratorFactory collaboratorFactory;

    @Autowired
    private IUserFactory userFactory;

    @Autowired
    private IUserManager userManager;

    @Autowired
    private CollaboratorValidator validator;

    @Autowired
    private IWorkspaceManager workspaceManager;

    @Autowired
    private MessageSource messageSource;

    private static final Logger logger = LoggerFactory.getLogger(WorkspaceCollaboratorManager.class);

    /**
     * This method binds the selected input by the user and validates the input.
     */
    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder, WebDataBinder validateBinder)
            throws Exception {

        validateBinder.setValidator(validator);

        binder.registerCustomEditor(IUser.class, "userObj", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {

                IUser user;
                try {
                    user = userManager.getUser(text);
                    setValue(user);
                } catch (QuadrigaStorageException e) {
                    logger.error("In ModifyWSCollabController class :" + e);
                }

            }
        });
        binder.registerCustomEditor(List.class, "collaboratorRoles", new PropertyEditorSupport() {

            @Override
            public void setAsText(String text) {

                String[] roleIds = text.split(",");
                List<IQuadrigaRole> roles = new ArrayList<IQuadrigaRole>();
                for (String roleId : roleIds) {
                    IQuadrigaRole role = roleManager
                            .getQuadrigaRoleByDbId(IQuadrigaRoleManager.WORKSPACE_ROLES, roleId);
                    roles.add(role);
                }
                setValue(roles);
            }
        });
    }

    /**
     * This method displays the form to add collaborators to workspace.
     * 
     * @param workspaceid
     * @param principal
     * @return ModelAndView
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE, paramIndex = 1, userRole = { RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/workspace/{workspaceid}/addcollaborators", method = RequestMethod.GET)
    public ModelAndView addWorkspaceCollaboratorForm(@PathVariable("workspaceid") String workspaceid,
            Principal principal) throws QuadrigaStorageException, QuadrigaAccessException {

        ModelAndView model = new ModelAndView("auth/workbench/workspace/addcollaborators");
        String userName = principal.getName();

        IWorkSpace workspace = workspaceManager.getWorkspaceDetails(workspaceid, userName);

        // adding the collaborator model
        ICollaborator collaborator = collaboratorFactory.createCollaborator();
        model.getModelMap().put("collaborator", collaborator);

        // adding the workspace id
        model.getModelMap().put("workspaceid", workspaceid);
        model.getModelMap().put("workspacename", workspace.getWorkspaceName());
        model.getModelMap().put("workspacedesc", workspace.getDescription());

        // fetch the users who are not collaborators to the workspace
        List<IUser> nonCollaboratingUser = wsManager.getUsersNotCollaborating(workspaceid);

        // remove the restricted user
        Iterator<IUser> userIterator = nonCollaboratingUser.iterator();
        while (userIterator.hasNext()) {
            // fetch the quadriga roles and eliminate the restricted user
            IUser user = userIterator.next();
            List<IQuadrigaRole> userQuadrigaRole = user.getQuadrigaRoles();
            for (IQuadrigaRole role : userQuadrigaRole) {
                if (role.getId().equals(RoleNames.ROLE_QUADRIGA_RESTRICTED)) {
                    userIterator.remove();
                    break;
                }
            }
        }

        // add the users list to the model
        model.getModelMap().put("noncollabusers", nonCollaboratingUser);

        // fetch the roles that can be associated to the workspace collaborator
        List<IQuadrigaRole> collaboratorRoles = roleManager.getQuadrigaRoles(IQuadrigaRoleManager.WORKSPACE_ROLES);

        // add the collaborator roles to the model
        model.getModelMap().put("wscollabroles", collaboratorRoles);

        // fetch all the collaborating users and their roles
        List<IWorkspaceCollaborator> collaboratingUser = wsManager.getWorkspaceCollaborators(workspaceid);

        model.getModelMap().put("collaboratingusers", collaboratingUser);

        return model;
    }

    /**
     * This method adds collaborator to given workspace.
     * 
     * @param collaborator
     * @param result
     * @param workspaceid
     * @param principal
     * @return ModelAndView
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE, paramIndex = 3, userRole = { RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/workspace/{workspaceid}/addcollaborators", method = RequestMethod.POST)
    public ModelAndView addWorkspaceCollaborator(@Validated @ModelAttribute("collaborator") Collaborator collaborator,
            BindingResult result, @PathVariable("workspaceid") String workspaceid, Principal principal, Locale locale)
            throws QuadrigaStorageException, QuadrigaAccessException {
        List<IWorkspaceCollaborator> collaboratingUser = new ArrayList<IWorkspaceCollaborator>();

        // create the model view
        ModelAndView model = new ModelAndView("auth/workbench/workspace/addcollaborators");
        String userName = principal.getName();

        IWorkSpace workspace = workspaceManager.getWorkspaceDetails(workspaceid, userName);
        model.getModelMap().put("workspacename", workspace.getWorkspaceName());
        model.getModelMap().put("workspacedesc", workspace.getDescription());

        if (result.hasErrors()) {
            model.getModelMap().put("collaborator", collaborator);
            model.getModelMap().addAttribute("show_error_alert", true);
            model.getModelMap().addAttribute("error_alert_msg",
                    messageSource.getMessage("workspace.collaborator.add.failure", new String[] {}, locale));
        } else {
            // get all the required input parameters

            // call the method to insert the collaborator
            wsManager.addCollaborator(collaborator, workspaceid, userName);

            model.getModelMap().put("collaborator", collaboratorFactory.createCollaborator());
            model.getModelMap().addAttribute("show_success_alert", true);
            model.getModelMap().addAttribute("success_alert_msg",
                    messageSource.getMessage("workspace.collaborator.add.success", new String[] {}, locale));
        }

        // adding the workspace id
        model.getModelMap().put("workspaceid", workspaceid);

        // fetch the users who are not collaborators to the workspace
        List<IUser> nonCollaboratingUser = wsManager.getUsersNotCollaborating(workspaceid);

        // remove the restricted user
        Iterator<IUser> userIterator = nonCollaboratingUser.iterator();
        while (userIterator.hasNext()) {
            // fetch the quadriga roles and eliminate the restricted user
            IUser user = userIterator.next();
            List<IQuadrigaRole> userQuadrigaRole = user.getQuadrigaRoles();
            for (IQuadrigaRole role : userQuadrigaRole) {
                if (role.getId().equals(RoleNames.ROLE_QUADRIGA_RESTRICTED)) {
                    userIterator.remove();
                    break;
                }
            }
        }

        // add the users list to the model
        model.getModelMap().put("noncollabusers", nonCollaboratingUser);

        // fetch the roles that can be associated to the workspace collaborator
        List<IQuadrigaRole> collaboratorRoles = roleManager.getQuadrigaRoles(IQuadrigaRoleManager.WORKSPACE_ROLES);

        // add the collaborator roles to the model
        model.getModelMap().put("wscollabroles", collaboratorRoles);

        // fetch all the collaborating users and their roles
        collaboratingUser = wsManager.getWorkspaceCollaborators(workspaceid);

        model.getModelMap().put("collaboratingusers", collaboratingUser);
        return model;

    }

}
