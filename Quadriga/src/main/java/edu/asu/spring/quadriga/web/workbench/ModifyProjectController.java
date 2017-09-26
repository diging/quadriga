package edu.asu.spring.quadriga.web.workbench;

import java.security.Principal;
import java.util.Locale;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
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
import edu.asu.spring.quadriga.aspects.annotations.InjectProject;
import edu.asu.spring.quadriga.aspects.annotations.InjectProjectById;
import edu.asu.spring.quadriga.aspects.annotations.ProjectIdentifier;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.impl.Project;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.workbench.IModifyProjectManager;
import edu.asu.spring.quadriga.validator.ProjectValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class ModifyProjectController {
    @Autowired
    private IModifyProjectManager projectManager;

    @Autowired
    private IUserManager userManager;

    @Autowired
    private ProjectValidator validator;

    @Resource(name = "projectconstants")
    private Properties messages;

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
     * This method is called during editing a project.
     * 
     * @param projectid
     *            - project internal id.
     * @param model
     * @return String - URL for project editing page.
     * @throws QuadrigaStorageException
     * @author Kiran Kumar Batna
     * @throws QuadrigaAccessException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 1, userRole = {
            RoleNames.ROLE_COLLABORATOR_OWNER, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/modifyproject/{projectid}", method = RequestMethod.GET)
    @InjectProjectById
    public ModelAndView updateProjectRequestForm(@ProjectIdentifier @PathVariable("projectid") String projectid,
            @InjectProject IProject project, Principal principal) throws QuadrigaStorageException,
            QuadrigaAccessException {
        ModelAndView model = new ModelAndView("auth/workbench/modifyproject");
        model.getModelMap().put("project", project);
        model.getModelMap().put("unixnameurl", messages.getProperty("project_unix_name.url"));
        return model;
    }

    /**
     * This method is called during editing a project.
     * 
     * @param projectid
     *            - project internal id.
     * @param project
     *            - Spring Project object.
     * @param model
     * @param principal
     * @return String - URL for project editing page.
     * @throws QuadrigaStorageException
     * @author Kiran Kumar Batna
     * @throws QuadrigaAccessException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 3, userRole = {
            RoleNames.ROLE_COLLABORATOR_OWNER, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/modifyproject/{projectid}", method = RequestMethod.POST)
    public ModelAndView updateProjectRequest(@Validated @ModelAttribute("project") Project project,
            BindingResult result, @PathVariable("projectid") String projectid, Principal principal,
            RedirectAttributes redirectAttributes) throws QuadrigaStorageException, QuadrigaAccessException {
        ModelAndView model;
        String userName = principal.getName();
        if (result.hasErrors()) {
            model = new ModelAndView("auth/workbench/modifyproject");
            model.getModelMap().put("project", project);
            return model;
        }
        projectManager.updateProject(project.getProjectId(), project.getProjectName(), project.getDescription(),
                project.getProjectAccess().name(), userName);
        redirectAttributes.addFlashAttribute("show_success_alert", true);
        redirectAttributes.addFlashAttribute("success_alert_msg", "Project has been updated successfully.");
        model = new ModelAndView("redirect:/auth/workbench/projects/" + projectid);
        return model;
    }

    /**
     * This controller function would assign editor roles to project owner
     * 
     * @param projectId
     * @param model
     * @param principal
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 1, userRole = {
            RoleNames.ROLE_COLLABORATOR_OWNER, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/assignownereditor/{projectid}", method = RequestMethod.GET)
    @InjectProjectById
    public String assignOwnerEditorRole(@ProjectIdentifier @PathVariable("projectid") String projectId,
            @InjectProject Project project, ModelMap model, Principal principal, RedirectAttributes redirectAttrs,
            Locale locale) throws QuadrigaStorageException, QuadrigaAccessException {
        IUser user = userManager.getUser(principal.getName());
        String userName = user.getUserName();
        projectManager.assignEditorRole(projectId, userName);

        redirectAttrs.addFlashAttribute("show_success_alert", true);
        redirectAttrs.addFlashAttribute("success_alert_msg",
                messageSource.getMessage("project.owner_is_editor.success", new String[] {}, locale));

        return "redirect:/auth/workbench/projects/" + projectId;
    }

    /**
     * This controller function would assign editor roles to project owner
     * 
     * @param projectId
     * @param model
     * @param principal
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 1, userRole = {
            RoleNames.ROLE_COLLABORATOR_OWNER, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/deleteownereditor/{projectid}", method = RequestMethod.GET)
    @InjectProjectById
    public String deleteOwnerEditorRole(@ProjectIdentifier @PathVariable("projectid") String projectId,
            @InjectProject Project project, ModelMap model, Principal principal, RedirectAttributes redirectAttrs,
            Locale locale) throws QuadrigaStorageException, QuadrigaAccessException {
        String userName = principal.getName();
        projectManager.removeEditorRole(projectId, userName);

        redirectAttrs.addFlashAttribute("show_success_alert", true);
        redirectAttrs.addFlashAttribute("success_alert_msg",
                messageSource.getMessage("project.owner_is_not_editor.success", new String[] {}, locale));

        return "redirect:/auth/workbench/projects/" + projectId;
    }

}
