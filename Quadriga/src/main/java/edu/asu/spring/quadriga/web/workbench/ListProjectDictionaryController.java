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

import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IProjectDictionary;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IProjectDictionaryManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

@Controller
public class ListProjectDictionaryController {

    @Autowired
    IRetrieveProjectManager projectManager;

    @Autowired
    private IProjectDictionaryManager projectDictionaryManager;

    @RequestMapping(value = "auth/workbench/{projectid}/dictionaries", method = RequestMethod.GET)
    public String listProjectDictionary(HttpServletRequest req, @PathVariable("projectid") String projectid,
            Model model, Principal principal) throws QuadrigaStorageException {
        String userId = principal.getName();
        List<IProjectDictionary> dicitonaryList = projectDictionaryManager.listProjectDictionary(projectid, userId);
        model.addAttribute("dicitonaryList", dicitonaryList);
        IProject project = projectManager.getProjectDetails(projectid);
        model.addAttribute("project", project);
        return "auth/workbench/project/dictionaries";
    }
}
