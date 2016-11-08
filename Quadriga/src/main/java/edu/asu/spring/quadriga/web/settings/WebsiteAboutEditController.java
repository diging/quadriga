package edu.asu.spring.quadriga.web.settings;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
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

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.aspects.annotations.InjectProject;
import edu.asu.spring.quadriga.aspects.annotations.InjectProjectById;
import edu.asu.spring.quadriga.aspects.annotations.ProjectIdentifier;
import edu.asu.spring.quadriga.domain.settings.IAboutText;
import edu.asu.spring.quadriga.domain.settings.impl.AboutText;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.publicwebsite.IAboutTextManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.validator.AboutTextValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;

/**
 * This controller manages public website's about page of a project. Information
 * of title and description is editable.
 *
 * @author Rajat Aggarwal
 *
 */

@Controller
public class WebsiteAboutEditController {

    @Autowired
    private IAboutTextManager aboutTextManager;

    @Autowired
    private IRetrieveProjectManager projectManager;

    @Autowired
    private AboutTextValidator validator;

    /**
     * Attach the custom validator to the Spring context
     */
    @InitBinder("aboutTextBean")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 1, userRole = {
            RoleNames.ROLE_COLLABORATOR_OWNER, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/projects/{ProjectId}/settings/editabout", method = RequestMethod.GET)
    @InjectProjectById
    public String editAbout(@ProjectIdentifier @PathVariable("ProjectId") String projectId,
            @InjectProject IProject project, Model model, Principal principal) throws QuadrigaStorageException {

        model.addAttribute("project", project);
        if (aboutTextManager.getAboutTextByProjectId(projectId) == null) {
            model.addAttribute("aboutTextBean", new AboutText());
        } else {
            IAboutText abtText = aboutTextManager.getAboutTextByProjectId(projectId);
            model.addAttribute("aboutTextBean", abtText);
        }
        return "auth/editabout";
    }

    /**
     * . Any change made in the about project page is updated into the database
     * here and a "You successfully edited the about text" message is displayed.
     * 
     * @author Rajat Aggarwal
     *
     */

    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 1, userRole = {
            RoleNames.ROLE_COLLABORATOR_OWNER, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/projects/{ProjectId}/settings/saveabout", method = RequestMethod.POST)
    @InjectProjectById
    public ModelAndView saveAbout(@ProjectIdentifier @PathVariable("ProjectId") String projectId,
            @InjectProject IProject project, @Validated @ModelAttribute("aboutTextBean") AboutText formBean,
            BindingResult result, ModelAndView model, Principal principal) throws QuadrigaStorageException {
        model = new ModelAndView("auth/editabout");
        model.addObject("project", project);
        if (result.hasErrors()) {
            model.addObject("aboutTextBean", formBean);
        } else {
            aboutTextManager.saveAbout(projectId, formBean);
            model.addObject("show_success_alert", true);
            model.addObject("success_alert_msg", "You successfully edited the about text");

        }
        return model;
    }

}
