package edu.asu.spring.quadriga.web.resolver;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.asu.spring.quadriga.domain.resolver.IProjectHandleResolver;
import edu.asu.spring.quadriga.service.resolver.IProjectHandleResolverManager;

/**
 * This controller handles the deletion of project handle resolver
 * 
 * @author yoganandakishore
 *
 */
@Controller
public class DeleteResolverController {

    @Autowired
    private IProjectHandleResolverManager resolverManager;

    @RequestMapping(value = "/auth/resolvers/delete", method = RequestMethod.POST)
    public String preparePage(Principal principal, Model model, @RequestParam("resolverId") String resolverId) {

        IProjectHandleResolver projectHandleResolver = resolverManager.getProjectHandleResolver(resolverId);
        resolverManager.deleteProjectHandleResolver(projectHandleResolver);
        return "redirect:/auth/resolvers";
    }
}
