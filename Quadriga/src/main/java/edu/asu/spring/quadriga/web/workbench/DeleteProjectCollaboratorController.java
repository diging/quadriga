package edu.asu.spring.quadriga.web.workbench;

import java.security.Principal;
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
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.IModifyCollaboratorFormFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IProjectCollaboratorManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.validator.CollaboratorFormDeleteValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaborator;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaboratorForm;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaboratorFormManager;

@Controller
public class DeleteProjectCollaboratorController {

    @Autowired
    private IProjectCollaboratorManager projectCollaboratorManager;

    @Autowired
    private IUserFactory userFactory;

    @Autowired
    private ICollaboratorFactory collaboratorFactory;

    @Autowired
    private IRetrieveProjectManager retrieveprojectManager;

    @Autowired
    private IProjectCollaboratorManager projectCollabManager;

    @Autowired
    private CollaboratorFormDeleteValidator validator;

    @Autowired
    private IModifyCollaboratorFormFactory collaboratorFormFactory;

    @Autowired
    private ModifyCollaboratorFormManager collaboratorFormManager;

    @Autowired
    private MessageSource messageSource;

    @InitBinder
    protected void initBinder(WebDataBinder validateBinder) throws Exception {
        validateBinder.setValidator(validator);
    }

    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 1, userRole = {
            RoleNames.ROLE_COLLABORATOR_OWNER, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/{projectid}/collaborators/delete", method = RequestMethod.GET)
    @InjectProjectById
    public ModelAndView displayDeleteCollaborator(@ProjectIdentifier @PathVariable("projectid") String projectId,
            @InjectProject IProject project) throws QuadrigaStorageException, QuadrigaAccessException {

        ModelAndView modelAndView = new ModelAndView("auth/workbench/deletecollaborators");

        // retrieve project details
        ModifyCollaboratorForm collaboratorForm = collaboratorFormFactory.createCollaboratorFormObject();

        List<ModifyCollaborator> modifyCollaborator = collaboratorFormManager.getProjectCollaborators(projectId);

        collaboratorForm.setCollaborators(modifyCollaborator);

        modelAndView.getModelMap().put("collaboratorForm", collaboratorForm);
        modelAndView.getModelMap().put("myprojectId", projectId);
        modelAndView.getModel().put("projectname", project.getProjectName());
        modelAndView.getModelMap().put("projectdesc", project.getDescription());

        return modelAndView;
    }

    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 1, userRole = {
            RoleNames.ROLE_COLLABORATOR_OWNER, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/{projectid}/collaborators/delete", method = RequestMethod.POST)
    @InjectProjectById
    public String deleteCollaborators(@ProjectIdentifier @PathVariable("projectid") String projectId,
            @InjectProject IProject project,
            @Validated @ModelAttribute("collaboratorForm") ModifyCollaboratorForm collaboratorForm,
            BindingResult result, Principal principal, Model model, Locale locale, RedirectAttributes redirectAttrs)
            throws QuadrigaStorageException, QuadrigaAccessException {

        if (result.hasErrors()) {
            List<ModifyCollaborator> collaborators = collaboratorFormManager.getProjectCollaborators(projectId);
            collaboratorForm.setCollaborators(collaborators);

            model.addAttribute("collaboratorForm", collaboratorForm);

            model.addAttribute("myprojectId", projectId);
            model.addAttribute("projectname", project.getProjectName());
            model.addAttribute("projectdesc", project.getDescription());

            // show error message
            model.addAttribute("show_error_alert", true);
            model.addAttribute("error_alert_msg",
                    messageSource.getMessage("collaborators.remove.failure", new Object[] {}, locale));

            return "auth/workbench/deletecollaborators";
        }

        List<ModifyCollaborator> collaborators = collaboratorForm.getCollaborators();
        for (ModifyCollaborator collaborator : collaborators) {
            String userName = collaborator.getUserName();
            if (userName != null) {
                projectCollaboratorManager.deleteCollaborators(userName, projectId);
            }
        }

        redirectAttrs.addFlashAttribute("show_success_alert", true);
        redirectAttrs.addFlashAttribute("success_alert_msg",
                messageSource.getMessage("collaborators.remove.success", new Object[] {}, locale));

        return "redirect:/auth/workbench/projects/" + projectId;
    }
}
