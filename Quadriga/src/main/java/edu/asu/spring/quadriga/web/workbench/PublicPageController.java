package edu.asu.spring.quadriga.web.workbench;

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

import edu.asu.spring.quadriga.domain.factory.workbench.IPublicPageFactory;
import edu.asu.spring.quadriga.domain.impl.workbench.PublicPage;
import edu.asu.spring.quadriga.validator.PublicPageValidator;

@Controller
public class PublicPageController {

    @Autowired
    private IPublicPageFactory publicPageFactory;

    @Resource(name = "projectconstants")
    private Properties messages;

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
        model.getModelMap().put("publicpage", publicPageFactory.createPublicPageObject());
        return model;
    }

    /**
     * This method is called during the load of Public page settings form
     * 
     * @return model - model object
     */
    @PreAuthorize("hasRole('ROLE_QUADRIGA_USER_ADMIN')")
    @RequestMapping(value = "auth/workbench/addpublicpage1", method = RequestMethod.GET)
    public ModelAndView publicPageSettingsFormSubmit(@Validated @ModelAttribute("publicpage") PublicPage publicPage,
            BindingResult result) {

        if (result.hasErrors()) {
            System.out.println("Hold");
            ModelAndView model = new ModelAndView("auth/workbench/addpublicpage");
            return model;
        }

        ModelAndView model = new ModelAndView("auth/workbench/addpublicpage");
        model.getModelMap().put("publicpage", publicPageFactory.createPublicPageObject());
        return model;
    }
}