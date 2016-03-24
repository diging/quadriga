package edu.asu.spring.quadriga.web.publicwebsite;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

/**
 * This controller manages public website's about page of a project. Information of title and description is editable.
 *
 * @author Rajat Aggarwal
 *
 */

@Controller
public class WebsiteAboutEditController {

    @Autowired
    private IRetrieveProjectManager projectManager;

    @RequestMapping(value = "auth/editabout/{ProjectUnixName}", method = RequestMethod.GET)
    public String showAbout(@PathVariable("ProjectUnixName") String unixName, Model model, Principal principal) throws QuadrigaStorageException {
        System.out.println("here");
        IProject project = projectManager.getProjectDetailsByUnixName(unixName);
        String title = "Project Title will be here";
        String aboutProject = "<i>This line describes project in italics</i><br> <b>This is bold</b>";
        model.addAttribute("project", project);
        model.addAttribute("title", title);
        model.addAttribute("aboutProject", aboutProject);
        return "auth/editabout";
    }

}
