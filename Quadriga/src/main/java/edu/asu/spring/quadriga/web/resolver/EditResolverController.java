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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.spring.quadriga.domain.resolver.IProjectHandleResolver;
import edu.asu.spring.quadriga.domain.resolver.impl.ProjectHandleResolver;
import edu.asu.spring.quadriga.service.resolver.IProjectHandleResolverManager;
import edu.asu.spring.quadriga.validator.ProjectHandleResolverValidator;

@Controller
public class EditResolverController {

    @Autowired
    private IProjectHandleResolverManager resolverManager;

    @Autowired
    private ProjectHandleResolverValidator validator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @RequestMapping(value = "/auth/resolvers/edit", method = RequestMethod.POST)
    public String editPage(Principal principal, Model model, @RequestParam("resolverId") String resolverId) {

        IProjectHandleResolver projectHandleResolver = resolverManager.getProjectHandleResolver(resolverId);
        model.addAttribute("resolver", projectHandleResolver);
        return "auth/resolvers/edit";
    }

    @RequestMapping(value = "/auth/resolvers/update", method = RequestMethod.POST)
    public String editResolver(Principal principal,
            @Validated @ModelAttribute("resolver") ProjectHandleResolver resolver, BindingResult results, Model model) {

        if (results.hasErrors()) {
            model.addAttribute("resolver", resolver);
            return "auth/resolvers/edit";
        }

        resolverManager.saveProjectHandleResolver(resolver, principal.getName());
        return "redirect:/auth/resolvers";
    }

    @RequestMapping(value = "/auth/resolvers/update", method = RequestMethod.POST, params = "test")
    public String editResolverTest(Principal principal,
            @Validated @ModelAttribute("resolver") ProjectHandleResolver projectHandleResolver, BindingResult results,
            Model model, RedirectAttributes redirectAttributes) {

        String resolvedHandle = projectHandleResolver.buildResolvedHandle(projectHandleResolver.getHandleExample());

        if ((projectHandleResolver.getResolvedHandleExample()).equals(resolvedHandle)) {
            model.addAttribute("show_success_alert", true);
            model.addAttribute("success_alert_msg", "Test on Resolver Unsuccessful");
        } else {
            model.addAttribute("show_error_alert", true);
            model.addAttribute("error_alert_msg", "Test on Resolver Unsuccessful");
        }
        return "auth/resolvers/edit";
    }

}
