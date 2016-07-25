package edu.asu.spring.quadriga.web.resolver;

import java.security.Principal;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.spring.quadriga.domain.resolver.IProjectHandleResolver;
import edu.asu.spring.quadriga.domain.resolver.Status;
import edu.asu.spring.quadriga.service.resolver.IProjectHandleResolverManager;

@Controller
public class TestResolverController {

    @Autowired
    private IProjectHandleResolverManager resolverManager;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/auth/resolvers/test", method = RequestMethod.POST)
    public String editPage(Principal principal, Model model, @RequestParam("resolverId") String resolverId,
            RedirectAttributes redirectAttributes, Locale locale) {

        IProjectHandleResolver projectHandleResolver = resolverManager.getProjectHandleResolver(resolverId);

        resolverManager.validateProjectResolverHandle(projectHandleResolver, true);

        if (projectHandleResolver.getValidation() == Status.PASSED) {
            redirectAttributes.addFlashAttribute("show_success_alert", true);
            redirectAttributes.addFlashAttribute("success_alert_msg",
                    messageSource.getMessage("resolver.validate_success", new Object[] {}, locale));
        } else {
            redirectAttributes.addFlashAttribute("show_error_alert", true);
            redirectAttributes.addFlashAttribute("error_alert_msg",
                    messageSource.getMessage("resolver.validate_failure", new Object[] {}, locale));
        }

        resolverManager.saveProjectHandleResolver(projectHandleResolver, projectHandleResolver.getUsername());
        return "redirect:/auth/resolvers";
    }
}
