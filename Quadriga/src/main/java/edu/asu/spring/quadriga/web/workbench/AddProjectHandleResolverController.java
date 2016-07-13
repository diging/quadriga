package edu.asu.spring.quadriga.web.workbench;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.aspects.annotations.InjectProject;
import edu.asu.spring.quadriga.aspects.annotations.InjectProjectById;
import edu.asu.spring.quadriga.aspects.annotations.ProjectIdentifier;
import edu.asu.spring.quadriga.domain.resolver.IProjectHandleResolver;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.service.resolver.IProjectHandleResolverManager;
import edu.asu.spring.quadriga.service.workbench.IModifyProjectManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class AddProjectHandleResolverController {
    
    @Autowired
    private IProjectHandleResolverManager resolverManager;
    
    @Autowired
    private IModifyProjectManager projectManager;
    
    @RequestMapping(value = "/auth/workbench/{projectid}/resolvers/add", method = RequestMethod.GET)
    @InjectProjectById
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 1, userRole = {
            RoleNames.ROLE_COLLABORATOR_OWNER, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN}) })
    public String preparePage(@ProjectIdentifier @PathVariable("projectid") String projectid, @InjectProject IProject project,
            Principal principal, Model model) {
        List<IProjectHandleResolver> resolvers = resolverManager.getProjectHandleResolvers(principal.getName());
        model.addAttribute("resolvers", resolvers);
        model.addAttribute("project", project);
        
        return "auth/workbench/resolvers/add";
    }
    
    @RequestMapping(value = "/auth/workbench/{projectid}/resolvers/add", method = RequestMethod.POST)
    @InjectProjectById
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 1, userRole = {
            RoleNames.ROLE_COLLABORATOR_OWNER, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN}) })
    public String addResolver(@ProjectIdentifier @PathVariable("projectid") String projectid, @InjectProject IProject project,
            Principal principal, Model model, @RequestParam("resolverId") String resolverId) {
        if (resolverId != null) {
            projectManager.addResolverToProject(resolverId, project); 
        }
        return "redirect:/auth/workbench/projects/" + projectid;
    }
}
