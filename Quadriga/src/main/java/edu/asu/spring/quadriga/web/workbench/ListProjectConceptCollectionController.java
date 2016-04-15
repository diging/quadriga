package edu.asu.spring.quadriga.web.workbench;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IProjectConceptCollection;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IProjectConceptCollectionManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

@Controller
public class ListProjectConceptCollectionController {

    @Autowired
    private IRetrieveProjectManager projectManager;

    @Autowired
    private IProjectConceptCollectionManager projectConceptCollectionManager;

    @RequestMapping(value = "auth/workbench/{projectid}/conceptcollections", method = RequestMethod.GET)
    public String listProjectConceptCollection(@PathVariable("projectid") String projectid, Model model,
            Principal principal) throws QuadrigaStorageException {
        String userId = principal.getName();
        List<IProjectConceptCollection> projectConceptCollectionList = projectConceptCollectionManager
                .listProjectConceptCollection(projectid, userId);
        model.addAttribute("projectConceptCollectionList", projectConceptCollectionList);
        IProject project = projectManager.getProjectDetails(projectid);
        model.addAttribute("project", project);
        model.addAttribute("projectid", projectid);
        return "auth/workbench/project/conceptcollections";
    }
}
