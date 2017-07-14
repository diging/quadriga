package edu.asu.spring.quadriga.web.workbench;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.aspects.annotations.InjectProject;
import edu.asu.spring.quadriga.aspects.annotations.InjectProjectById;
import edu.asu.spring.quadriga.aspects.annotations.ProjectIdentifier;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IProjectDictionaryManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class ListProjectDictionaryController {

    @Autowired
    private IProjectDictionaryManager projectDictionaryManager;

    @RequestMapping(value = "auth/workbench/{projectid}/dictionaries", method = RequestMethod.GET)
    @InjectProjectById
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 2, userRole = {
            RoleNames.ROLE_COLLABORATOR_OWNER, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN,
            RoleNames.ROLE_PROJ_COLLABORATOR_CONTRIBUTOR, RoleNames.ROLE_WORKSPACE_COLLABORATOR_EDITOR }) })
    public String listProjectDictionary(HttpServletRequest req,
            @ProjectIdentifier @PathVariable("projectid") String projectid, @InjectProject IProject project,
            Model model, Principal principal) throws QuadrigaStorageException {
        List<IDictionary> dictionaries = projectDictionaryManager.getDictionaries(projectid);
        model.addAttribute("dictionaries", dictionaries);
        model.addAttribute("project", project);
        return "auth/workbench/project/dictionaries";
    }
}
