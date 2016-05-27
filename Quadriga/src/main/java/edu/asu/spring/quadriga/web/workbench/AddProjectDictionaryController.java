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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.aspects.annotations.InjectProject;
import edu.asu.spring.quadriga.aspects.annotations.InjectProjectById;
import edu.asu.spring.quadriga.aspects.annotations.ProjectIdentifier;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryManager;
import edu.asu.spring.quadriga.service.workbench.IProjectDictionaryManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class AddProjectDictionaryController {

    @Autowired
    IRetrieveProjectManager projectManager;

    @Autowired
    IDictionaryManager dictonaryManager;

    @Autowired
    private IProjectDictionaryManager projectDictionaryManager;

    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 1, userRole = {
            RoleNames.ROLE_COLLABORATOR_OWNER, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/{projectid}/adddictionary", method = RequestMethod.GET)
    @InjectProjectById
    public String addProjectDictionary(@ProjectIdentifier @PathVariable("projectid") String projectid,
            @InjectProject IProject project, Model model) throws QuadrigaAccessException, QuadrigaStorageException {
        List<IDictionary> dictionaryList = dictonaryManager.getNonAssociatedProjectDictionaries(projectid);
        model.addAttribute("dictinarylist", dictionaryList);
        model.addAttribute("project", project);
        return "auth/workbench/project/adddictionaries";
    }

    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 2, userRole = {
            RoleNames.ROLE_COLLABORATOR_OWNER, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/{projectid}/adddictionaries", method = RequestMethod.POST)
    public String addProjectDictionary(HttpServletRequest req, @PathVariable("projectid") String projectid,
            Model model, RedirectAttributes attr, Principal principal) throws QuadrigaStorageException,
            QuadrigaAccessException {
        String userId = principal.getName();

        String[] values = req.getParameterValues("selected");
        if (values == null) {
            attr.addFlashAttribute("show_error_alert", true);
            attr.addFlashAttribute("error_alert_msg", "Please select a Dictionary.");
            return "redirect:/auth/workbench/" + projectid + "/adddictionary";
        }
        for (int i = 0; i < values.length; i++) {
            projectDictionaryManager.addDictionaryToProject(projectid, values[i], userId);
        }
        attr.addFlashAttribute("show_success_alert", true);
        attr.addFlashAttribute("success_alert_msg", "Dictionaries added to project successfully.");
        return "redirect:/auth/workbench/projects/" + projectid;
    }

}
