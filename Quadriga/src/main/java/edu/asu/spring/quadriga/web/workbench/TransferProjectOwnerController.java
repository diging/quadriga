package edu.asu.spring.quadriga.web.workbench;

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
import edu.asu.spring.quadriga.aspects.annotations.InjectProject;
import edu.asu.spring.quadriga.aspects.annotations.InjectProjectById;
import edu.asu.spring.quadriga.aspects.annotations.ProjectIdentifier;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.impl.User;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IProjectCollaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.workbench.IModifyProjectManager;
import edu.asu.spring.quadriga.service.workbench.IProjectCollaboratorManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.validator.UserValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class TransferProjectOwnerController {
    @Autowired
    private IModifyProjectManager projectManager;

    @Autowired
    private IRetrieveProjectManager retrieveProjectManager;

    @Autowired
    private IProjectCollaboratorManager collabManager;

    @Autowired
    private UserValidator validator;

    @Autowired
    IUserFactory userFactory;

    @Autowired
    private IQuadrigaRoleManager roleManager;

    @Autowired
    private MessageSource messageSource;

    @InitBinder
    protected void initBinder(WebDataBinder validateBinder) {
        validateBinder.setValidator(validator);
    }

    /**
     * This method is used to load the project ownership transfer form
     * 
     * @param projectid
     * @param principal
     * @return ModelAndView object
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     * @author kiranbatna
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 1, userRole = {}) })
    @RequestMapping(value = "auth/workbench/projects/{projectid}/transfer", method = RequestMethod.GET)
    @InjectProjectById
    public ModelAndView transferProjectOwnerRequestForm(@ProjectIdentifier @PathVariable("projectid") String projectid,@InjectProject IProject project)
            throws QuadrigaStorageException, QuadrigaAccessException {
        // create a view
        ModelAndView model = new ModelAndView("auth/workbench/transferprojectowner");


        // create a model
        model.getModelMap().put("user", userFactory.createUserObject());
        model.getModelMap().put("projectname", project.getProjectName());
        model.getModelMap().put("projectowner", project.getOwner().getUserName());
        model.getModelMap().put("myprojectId", projectid);

        // fetch the collaborators
        List<IProjectCollaborator> projectcollaborators = project.getProjectCollaborators();
        if (projectcollaborators != null) {
            List<IUser> userList = new ArrayList<IUser>();

            if (projectcollaborators != null) {

                for (IProjectCollaborator projectCollaborator : projectcollaborators) {
                    userList.add(projectCollaborator.getCollaborator().getUserObj());
                }
            }

            model.getModelMap().put("collaboratinguser", userList);
        }

        return model;
    }

    /**
     * This method submits the transfer request form
     * 
     * @param projectid
     * @param principal
     * @param collaborator
     * @param result
     * @return ModelAndView object
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     * @throws QuadrigaException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 1, userRole = {}) })
    @RequestMapping(value = "auth/workbench/projects/{projectid}/transfer", method = RequestMethod.POST)
    @InjectProjectById
    public String transferProjectOwnerRequest(@ProjectIdentifier @PathVariable("projectid") String projectid,
            Principal principal, @InjectProject IProject project,
            @Validated @ModelAttribute("user") User collaboratorUser, BindingResult result, Model model,
            RedirectAttributes redirectAttrs, Locale locale) throws QuadrigaStorageException, QuadrigaAccessException,
            QuadrigaException {

        model.addAttribute("myprojectId", projectid);

        if (result.hasErrors()) {
            model.addAttribute("user", collaboratorUser);

            // create a model
            model.addAttribute("projectname", project.getProjectName());
            model.addAttribute("projectowner", project.getOwner().getUserName());

            // fetch the collaborators
            List<IProjectCollaborator> projectCollaborators = project.getProjectCollaborators();

            List<IUser> userList = new ArrayList<IUser>();
            for (IProjectCollaborator projectCollaborator : projectCollaborators) {
                userList.add(projectCollaborator.getCollaborator().getUserObj());
            }

            model.addAttribute("collaboratinguser", userList);
            model.addAttribute("show_error_alert", true);
            model.addAttribute("error_alert_msg",
                    messageSource.getMessage("project.transfer_ownership.failure", new String[] {}, locale));

            return "auth/workbench/transferprojectowner";
        }

        // fetch the new owner
        String newOwner = collaboratorUser.getUserName();

        String collaboratorRole = roleManager.getQuadrigaRoleById(IQuadrigaRoleManager.PROJECT_ROLES,
                RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN).getDBid();

        // call the method to transfer the ownership
        collabManager.transferOwnership(projectid, principal.getName(), newOwner, collaboratorRole);

        redirectAttrs.addFlashAttribute("show_success_alert", true);
        redirectAttrs.addFlashAttribute("success_alert_msg",
                messageSource.getMessage("project.transfer_ownership.success", new String[] {}, locale));

        return "redirect:/auth/workbench/projects/" + projectid;
    }

}
