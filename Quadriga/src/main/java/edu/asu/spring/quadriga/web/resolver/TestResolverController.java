package edu.asu.spring.quadriga.web.resolver;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.spring.quadriga.domain.resolver.IProjectHandleResolver;
import edu.asu.spring.quadriga.domain.resolver.impl.ProjectHandleResolver.STATUS;
import edu.asu.spring.quadriga.service.resolver.IProjectHandleResolverManager;

@Controller
public class TestResolverController {

    @Autowired
    private IProjectHandleResolverManager resolverManager;

    @RequestMapping(value = "/auth/resolvers/test", method = RequestMethod.POST)
    public String editPage(Principal principal, Model model, @RequestParam("resolverId") String resolverId,
            RedirectAttributes redirectAttributes) {

        IProjectHandleResolver projectHandleResolver = resolverManager.getProjectHandleResolver(resolverId);
        String resolvedHandle = projectHandleResolver.buildResolvedHandle(projectHandleResolver.getHandleExample());

        if ((projectHandleResolver.getResolvedHandleExample()).equals(resolvedHandle)) {
            projectHandleResolver.setValidation(STATUS.PASSED);
            redirectAttributes.addFlashAttribute("show_success_alert", true);
            redirectAttributes.addFlashAttribute("success_alert_msg", "Test on Resolver successful.");
        } else {
            projectHandleResolver.setValidation(STATUS.FAILED);
            redirectAttributes.addFlashAttribute("show_error_alert", true);
            redirectAttributes.addFlashAttribute("error_alert_msg", "Test on Resolver Unsuccessful.");
        }

        resolverManager.saveProjectHandleResolver(projectHandleResolver, projectHandleResolver.getUsername());
        return "redirect:/auth/resolvers";
    }
}
