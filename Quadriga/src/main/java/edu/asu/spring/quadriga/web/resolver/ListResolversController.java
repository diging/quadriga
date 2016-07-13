package edu.asu.spring.quadriga.web.resolver;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.asu.spring.quadriga.domain.resolver.IProjectHandleResolver;
import edu.asu.spring.quadriga.service.resolver.IProjectHandleResolverManager;

@Controller
public class ListResolversController {

    @Autowired
    private IProjectHandleResolverManager resolverManager;
    
    @RequestMapping(value = "/auth/resolvers")
    public String listResolvers(Principal principal, Model model) {
        List<IProjectHandleResolver> resolvers = resolverManager.getProjectHandleResolvers(principal.getName());
        
        model.addAttribute("resolvers", resolvers);
        
        return "auth/resolvers";
    }
}
