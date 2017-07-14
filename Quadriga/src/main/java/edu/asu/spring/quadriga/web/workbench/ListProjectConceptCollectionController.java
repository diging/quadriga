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
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IProjectConceptCollectionManager;

@Controller
public class ListProjectConceptCollectionController {

    @Autowired
    private IProjectConceptCollectionManager projectConceptCollectionManager;

    @RequestMapping(value = "auth/workbench/{projectid}/conceptcollections", method = RequestMethod.GET)
    @InjectProjectById
    public String listProjectConceptCollection(@ProjectIdentifier @PathVariable("projectid") String projectid,
            @InjectProject IProject project, Model model, Principal principal) throws QuadrigaStorageException {
        List<IConceptCollection> collections = projectConceptCollectionManager
                .getConceptCollections(projectid);
        model.addAttribute("collections", collections);
        model.addAttribute("project", project);
        model.addAttribute("projectid", projectid);
        return "auth/workbench/project/conceptcollections";
    }
}
