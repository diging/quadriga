package edu.asu.spring.quadriga.web.resolver;

import java.security.Principal;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.spring.quadriga.domain.resolver.IProjectHandleResolver;
import edu.asu.spring.quadriga.service.resolver.IProjectHandleResolverManager;

/**
 * This controller handles the deletion of project handle resolver.
 * 
 * @author yoganandakishore
 *
 */
@Controller
public class DeleteResolverController {

    @Autowired
    private IProjectHandleResolverManager resolverManager;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/auth/resolvers/{resolverId}/delete", method = RequestMethod.GET)
    public String preparePage(Principal principal, Model model, @PathVariable("resolverId") String resolverId,
            RedirectAttributes redirectAttributes, Locale locale) {

        IProjectHandleResolver projectHandleResolver = resolverManager.getProjectHandleResolver(resolverId);

        if (!resolverManager.deleteProjectHandleResolver(projectHandleResolver)) {
            redirectAttributes.addFlashAttribute("show_error_alert", true);
            redirectAttributes.addFlashAttribute("error_alert_msg",
                    messageSource.getMessage("resolver.delete_failure", new Object[] {}, locale));
        } else {
            redirectAttributes.addFlashAttribute("show_success_alert", true);
            redirectAttributes.addFlashAttribute("success_alert_msg",
                    messageSource.getMessage("resolver.delete_success", new Object[] {}, locale));
        }

        return "redirect:/auth/resolvers";
    }
}
