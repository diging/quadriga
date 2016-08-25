package edu.asu.spring.quadriga.web.resolver;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.resolver.impl.ProjectHandleResolver;
import edu.asu.spring.quadriga.service.resolver.IProjectHandleResolverManager;
import edu.asu.spring.quadriga.validator.ProjectHandleResolverValidator;

@Controller
public class AddResolverController {

    @Autowired
    private IProjectHandleResolverManager resolverManager;

    @Autowired
    private ProjectHandleResolverValidator validator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @RequestMapping(value = "/auth/resolvers/add", method = RequestMethod.GET)
    public String preparePage(Principal principal, Model model) {
        model.addAttribute("resolver", new ProjectHandleResolver());
        return "auth/resolvers/add";
    }

    @RequestMapping(value = "/auth/resolvers/add", method = RequestMethod.POST)
    public String addResolver(Principal principal,
            @Validated @ModelAttribute("resolver") ProjectHandleResolver resolver, BindingResult results, Model model) {

        if (results.hasErrors()) {
            model.addAttribute("resolver", resolver);
            return "auth/resolvers/add";
        }

        resolverManager.saveProjectHandleResolver(resolver, principal.getName());

        return "redirect:/auth/resolvers";
    }

}
