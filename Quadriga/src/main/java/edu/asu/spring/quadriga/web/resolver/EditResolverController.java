package edu.asu.spring.quadriga.web.resolver;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.asu.spring.quadriga.domain.resolver.IProjectHandleResolver;
import edu.asu.spring.quadriga.domain.resolver.impl.ProjectHandleResolver;
import edu.asu.spring.quadriga.service.resolver.IProjectHandleResolverManager;

@Controller
public class EditResolverController {

    @Autowired
    private IProjectHandleResolverManager resolverManager;

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

        resolver.setUsername(principal.getName());
        resolverManager.saveProjectHandleResolver(resolver, principal.getName());
        return "redirect:/auth/resolvers";
    }

}
