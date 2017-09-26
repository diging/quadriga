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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.aspects.annotations.InjectProject;
import edu.asu.spring.quadriga.aspects.annotations.InjectProjectById;
import edu.asu.spring.quadriga.aspects.annotations.ProjectIdentifier;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.impl.Project;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IModifyProjectManager;
import edu.asu.spring.quadriga.validator.ProjectURLValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class EditProjectUrlController {

    @Autowired
    private IModifyProjectManager projectManager;

    @Autowired
    private ProjectURLValidator validator;

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

    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 1, userRole = {
            RoleNames.ROLE_COLLABORATOR_OWNER, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/editProjectPageURL/{projectid}", method = RequestMethod.GET)
    @InjectProjectById
    public String editProjectPageURL(@ProjectIdentifier @PathVariable("projectid") String projectid,@InjectProject IProject project, Principal principal, ModelMap model)
            throws QuadrigaStorageException, QuadrigaAccessException {
        model.addAttribute("project", project);
        model.addAttribute("unixnameurl", messages.getProperty("project_unix_name.url"));
        return "auth/workbench/editProjectPageURL";
    }

    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 3, userRole = {
            RoleNames.ROLE_COLLABORATOR_OWNER, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/editProjectPageURL/{projectid}", method = RequestMethod.POST)
    public String editProjectPageURL(@Validated @ModelAttribute("project") Project project, BindingResult result,
            @PathVariable("projectid") String projectid, Principal principal, ModelMap model,
            RedirectAttributes redirectAttributes, Locale locale) throws QuadrigaStorageException, QuadrigaAccessException {
        if (result.hasErrors()) {
            model.addAttribute("project", project);
            model.addAttribute("unixnameurl", messages.getProperty("project_unix_name.url"));
            model.addAttribute("show_error_alert", true);
            model.addAttribute("error_alert_msg", messageSource.getMessage("project.update.url.errors", new String[] {}, locale));
            return "auth/workbench/editProjectPageURL";
        }
        String userName = principal.getName();
        projectManager.updateProjectURL(projectid, project.getUnixName(), userName);
        redirectAttributes.addFlashAttribute("show_success_alert", true);
        redirectAttributes.addFlashAttribute("success_alert_msg", "Project URL has been updated successfully");
        return "redirect:/auth/workbench/projects/" + projectid;

    }

}
