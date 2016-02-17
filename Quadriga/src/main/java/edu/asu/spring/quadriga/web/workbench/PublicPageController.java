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
import edu.asu.spring.quadriga.validator.PublicPageValidator;

@Controller
public class PublicPageController {

    @Autowired
    private IProjectFactory projectFactory;

    @Resource(name = "projectconstants")
    private Properties messages;

    @Autowired
    private IUserManager userManager;

    @Autowired
    private IModifyProjectManager projectManager;

    @Autowired
    private PublicPageValidator validator;

    /**
     * Attach the custom validator to the Spring context
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    /**
     * This method is called during the load of Public page settings form
     * 
     * @return model - model object
     */
    @PreAuthorize("hasRole('ROLE_QUADRIGA_USER_ADMIN')")
    @RequestMapping(value = "auth/workbench/addpublicpage", method = RequestMethod.GET)
    public ModelAndView publicPageSettingsForm() {
        ModelAndView model = new ModelAndView("auth/workbench/addpublicpage");
        model.getModelMap().put("project", projectFactory.createProjectObject());
        return model;
    }

}
