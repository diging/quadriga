package edu.asu.spring.quadriga.web.publicwebsite;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.publicwebsite.IAboutTextManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

/**
 * This controller handles any information edited in about page of a project's website. Any change is updated into the database here and a "Successfully saved" message is
 * displayed.
 * 
 * @author Rajat Aggarwal
 *
 */
@Controller
public class WebsiteAboutTextSaveController {

    @Autowired
    private IRetrieveProjectManager projectManager;

    @Autowired
    private IAboutTextManager aboutTextManager;

    @RequestMapping(value = "sites/{ProjectUnixName}/saveAbout", method = RequestMethod.POST)
    public String saveAbout(@PathVariable("ProjectUnixName") String unixName, @ModelAttribute("AboutTextBackingBean") AboutTextBackingBean formBean, Principal principal)
            throws QuadrigaStorageException {
        IProject project = projectManager.getProjectDetailsByUnixName(unixName);
        String projectId = project.getProjectId();
        aboutTextManager.saveAbout(projectId, formBean.getTitle(), formBean.getDescription());
        return "sites/public/saveAbout";
    }

}
