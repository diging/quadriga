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
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;
import edu.asu.spring.quadriga.service.workbench.IProjectConceptCollectionManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class AddProjectConceptCollectionController {

    @Autowired
    private IConceptCollectionManager conceptCollectionManager;

    @Autowired
    private IRetrieveProjectManager projectManager;

    @Autowired
    private IProjectConceptCollectionManager projectConceptCollectionManager;

    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 1, userRole = {
            RoleNames.ROLE_COLLABORATOR_OWNER, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/{projectid}/addconceptcollection", method = RequestMethod.GET)
    public String addProjectConceptCollection(@PathVariable("projectid") String projectid, Model model,
            Principal principal) throws QuadrigaAccessException, QuadrigaStorageException {
        List<IConceptCollection> conceptCollectionList = conceptCollectionManager
                .getNonAssociatedProjectConcepts(projectid);
        model.addAttribute("conceptCollectionList", conceptCollectionList);
        IProject project = projectManager.getProjectDetails(projectid);
        model.addAttribute("project", project);
        model.addAttribute("projectid", projectid);
        return "auth/workbench/project/addconceptcollections";
    }

    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 2, userRole = {
            RoleNames.ROLE_COLLABORATOR_OWNER, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/{projectid}/addconceptcollection", method = RequestMethod.POST)
    public String addProjectConceptCollection(HttpServletRequest req, @PathVariable("projectid") String projectid,
            Model model, Principal principal, RedirectAttributes attr)
                    throws QuadrigaStorageException, QuadrigaAccessException {
        String userId = principal.getName();
        String[] values = req.getParameterValues("selected");
        if (values == null) {
            attr.addFlashAttribute("show_error_alert", true);
            attr.addFlashAttribute("error_alert_msg", "Please select a Concept Collection.");
            return "redirect:/auth/workbench/" + projectid + "/addconceptcollection";
        }
        for (int i = 0; i < values.length; i++) {
            projectConceptCollectionManager.addProjectConceptCollection(projectid, values[i], userId);
        }
        attr.addFlashAttribute("show_success_alert", true);
        attr.addFlashAttribute("success_alert_msg", "Concept Collection added to project successfully.");
        return "redirect:/auth/workbench/projects/" + projectid;
    }

}
