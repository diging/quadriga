package edu.asu.spring.quadriga.web.workbench;

import java.util.Properties;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import edu.asu.spring.quadriga.domain.factory.workbench.IPublicPageFactory;
import edu.asu.spring.quadriga.domain.impl.workbench.PublicPage;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.validator.PublicPageValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;

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
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 1, userRole = {
            RoleNames.ROLE_COLLABORATOR_ADMIN, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/{projectid}/addpublicpage", method = RequestMethod.GET)
    public ModelAndView publicPageSettingsForm(@PathVariable("projectid") String projectid)
            throws QuadrigaStorageException, QuadrigaAccessException {
        ModelAndView model = new ModelAndView("auth/workbench/addpublicpage");
        model.getModelMap().put("publicpage", publicPageFactory.createPublicPageObject());
        model.getModelMap().put("ppprojectid", projectid);
        return model;
    }

    /**
     * This method is called for the validation of the Public page settings form
     *
     * @return model - model object
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 3, userRole = {
            RoleNames.ROLE_COLLABORATOR_ADMIN, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/{projectid}/addpublicpage1", method = RequestMethod.GET)
    public ModelAndView publicPageSettingsFormSubmit(@Validated @ModelAttribute("publicpage") PublicPage publicPage,
            BindingResult result, @PathVariable("projectid") String projectid) {
        if (result.hasErrors()) {
            ModelAndView model = new ModelAndView("auth/workbench/addpublicpage");
            return model;
        }
        ModelAndView model = new ModelAndView("auth/workbench/addpublicpage");
        model.getModelMap().put("publicpage", publicPageFactory.createPublicPageObject());
        model.getModelMap().put("ppprojectid", projectid);
        return model;
    }
}