package edu.asu.spring.quadriga.web.workbench;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.aspects.annotations.InjectProject;
import edu.asu.spring.quadriga.aspects.annotations.InjectProjectById;
import edu.asu.spring.quadriga.aspects.annotations.ProjectIdentifier;
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
    @InjectProjectById
    public String listProjectConceptCollection(@ProjectIdentifier @PathVariable("projectid") String projectid,
            @InjectProject IProject project, Model model, Principal principal) throws QuadrigaStorageException {
        List<IProjectConceptCollection> projectConceptCollectionList = projectConceptCollectionManager
                .listProjectConceptCollection(projectid);
        model.addAttribute("projectConceptCollectionList", projectConceptCollectionList);
        model.addAttribute("project", project);
        model.addAttribute("projectid", projectid);
        return "auth/workbench/project/conceptcollections";
    }
}
