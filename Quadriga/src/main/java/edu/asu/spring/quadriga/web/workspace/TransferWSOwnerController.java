package edu.asu.spring.quadriga.web.workspace;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.impl.User;
import edu.asu.spring.quadriga.domain.workspace.IWorkspace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceCollaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceCollaboratorManager;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceManager;
import edu.asu.spring.quadriga.validator.UserValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class TransferWSOwnerController {
    @Autowired
    private IQuadrigaRoleManager collaboratorRoleManager;

    @Autowired
    private UserValidator validator;

    @Autowired
    private IWorkspaceManager wsManager;

    @Autowired
    private IWorkspaceCollaboratorManager wsCollabManager;

    @Autowired
    private IUserFactory userFactory;
    
    @Autowired
    private MessageSource messageSource;


    /**
     * Custom validator to validate the input
     * 
     * @param validateBinder
     * @throws Exception
     */
    @InitBinder
    protected void initBinder(WebDataBinder validateBinder) throws Exception {
        validateBinder.setValidator(validator);
    }

    /**
     * Retrieve all the collaborators associated to the workspace to transfer
     * the ownership to the selected collaborator
     * 
     * @param workspaceid
     * @param principal
     * @return ModelAndView
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE, paramIndex = 1, userRole = {}) })
    @RequestMapping(value = "auth/workbench/workspace/{workspaceid}/transfer", method = RequestMethod.GET)
    public ModelAndView transferWSOwnerRequestForm(@PathVariable("workspaceid") String workspaceid, Principal principal)
            throws QuadrigaStorageException, QuadrigaAccessException {

        // create a view
        ModelAndView model = new ModelAndView("auth/workbench/workspace/transferworkspaceowner");

        String owner = principal.getName();

        // retrieve the workspace details
        IWorkspace workspace = wsManager.getWorkspaceDetails(workspaceid, owner);

        // retrieve the collaborators associated with the workspace
        List<IWorkspaceCollaborator> collaboratingUser = wsCollabManager.getWorkspaceCollaborators(workspaceid);

        // adding the collaborator model
        model.getModelMap().put("user", userFactory.createUserObject());
        model.getModelMap().put("wsname", workspace.getWorkspaceName());
        model.getModelMap().put("wsowner", workspace.getOwner().getUserName());
        model.getModelMap().put("workspaceid", workspace.getWorkspaceId());

        // fetch the collaborators
        List<IUser> userList = new ArrayList<IUser>();
        if (collaboratingUser != null) {
            for (IWorkspaceCollaborator collabuser : collaboratingUser) {
                userList.add(collabuser.getCollaborator().getUserObj());
            }
        }

        model.getModelMap().put("collaboratinguser", userList);
        return model;
    }

    /**
     * This method transfer the owner of workspace to another user and adds the
     * old owner as collaborator to the workspace
     * 
     * @param workspaceid
     * @param principal
     * @param collaboratorUser
     * @param result
     * @return ModelAndView
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     * @throws QuadrigaException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE, paramIndex = 1, userRole = {}) })
    @RequestMapping(value = "auth/workbench/workspace/{workspaceid}/transfer", method = RequestMethod.POST)
    public String transferWSOwnerRequest(@PathVariable("workspaceid") String workspaceid, Principal principal,
            @Validated @ModelAttribute("user") User collaboratorUser, BindingResult result, Model model, RedirectAttributes redirectAttrs, Locale locale)
            throws QuadrigaStorageException, QuadrigaAccessException, QuadrigaException {
        
        String userName = principal.getName();

        // retrieve the workspace details
        IWorkspace workspace = wsManager.getWorkspaceDetails(workspaceid, userName);

        // retrieve the collaborators associated with the workspace
        List<IWorkspaceCollaborator> collaboratingUser = wsCollabManager.getWorkspaceCollaborators(workspaceid);

        model.addAttribute("workspaceid", workspace.getWorkspaceId());

        if (result.hasErrors()) {
            model.addAttribute("user", collaboratorUser);

            model.addAttribute("wsname", workspace.getWorkspaceName());
            model.addAttribute("wsowner", workspace.getOwner().getUserName());
            
            List<IUser> userList = new ArrayList<IUser>();
            for (IWorkspaceCollaborator collabuser : collaboratingUser) {
                userList.add(collabuser.getCollaborator().getUserObj());
            }

            model.addAttribute("collaboratinguser", userList);
            model.addAttribute("show_error_alert", true);
            model.addAttribute("error_alert_msg", messageSource.getMessage("workspace.transfer_ownership.failure", new String[] {}, locale));
            
            return "auth/workbench/workspace/transferworkspaceowner";
        }

        String newOwner = collaboratorUser.getUserName();

        // fetch the collaborator role
        String collaboratorRole = collaboratorRoleManager.getQuadrigaRoleById(IQuadrigaRoleManager.WORKSPACE_ROLES,
                RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN).getDBid();
        // call the method to transfer the ownership
        wsCollabManager.transferOwnership(workspaceid, userName, newOwner, collaboratorRole);

        redirectAttrs.addFlashAttribute("show_success_alert", true);
        redirectAttrs.addFlashAttribute("success_alert_msg", messageSource.getMessage("workspace.transfer_ownership.success", new String[] {}, locale));
        

        return "redirect:/auth/workbench/workspace/" + workspaceid;
    }
}
