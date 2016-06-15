package edu.asu.spring.quadriga.web.workbench;

import java.security.Principal;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factory.workbench.IProjectFactory;
import edu.asu.spring.quadriga.domain.impl.workbench.Project;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.workbench.IModifyProjectManager;
import edu.asu.spring.quadriga.validator.AddProjectValidator;

@Controller
public class AddProjectController {

    @Autowired
    private IProjectFactory projectFactory;

    @Resource(name = "projectconstants")
    private Properties messages;

    @Autowired
    private IUserManager userManager;

    @Autowired
    private IModifyProjectManager projectManager;

    @Autowired
    private AddProjectValidator validator;

    /**
     * Attach the custom validator to the Spring context
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    /**
     * This method is called during the load of add project request form
     * 
     * @return model - model object
     * @author Kiran Kumar Batna
     */
    @PreAuthorize("hasRole('ROLE_QUADRIGA_USER_ADMIN') OR hasRole('ROLE_QUADRIGA_USER_STANDARD')")
    @RequestMapping(value = "auth/workbench/addproject", method = RequestMethod.GET)
    public ModelAndView addProjectRequestForm() {
        ModelAndView model = new ModelAndView("auth/workbench/addproject");
        model.getModelMap().put("project", projectFactory.createProjectObject());
        model.getModelMap().put("unixnameurl", messages.getProperty("project_unix_name.url"));
        return model;
    }

    /**
     * This method call the user manager to insert the record in the database on
     * form submission
     * 
     * @param project
     *            - object containing the form details.
     * @param result
     *            - object containing the errors.
     * @param principal
     * @return model - model object
     * @throws QuadrigaStorageException
     * @author Kiran Kumar Batna
     */
    @PreAuthorize("hasRole('ROLE_QUADRIGA_USER_ADMIN') OR hasRole('ROLE_QUADRIGA_USER_STANDARD')")
    @RequestMapping(value = "auth/workbench/addproject", method = RequestMethod.POST)
    public ModelAndView addProjectRequest(@Validated @ModelAttribute("project") Project project, BindingResult result,
            Principal principal, RedirectAttributes redirectAttribtutes) throws QuadrigaStorageException {
        ModelAndView model;
        if (result.hasErrors()) {
            model = new ModelAndView("auth/workbench/addproject");
            model.getModelMap().put("project", project);
            model.getModelMap().put("unixnameurl", messages.getProperty("project_unix_name.url"));
            return model;
        }
        model = new ModelAndView("redirect:/auth/workbench");
        IUser user = userManager.getUser(principal.getName());
        project.setOwner(user);
        projectManager.addNewProject(project, principal.getName());
        redirectAttribtutes.addFlashAttribute("show_success_alert", true);
        redirectAttribtutes.addFlashAttribute("success_alert_msg", "Project created successfully.");
        return model;
    }
}
