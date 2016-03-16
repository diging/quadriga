package edu.asu.spring.quadriga.web.sites;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.service.workbench.mapper.IProjectShallowMapper;

/**
 * This Controller is used to perform all the changes related to Quadriga public sites
 * @author Charan Thej Aware
 *
 */

@Controller
public class SitesController {
    
    @Autowired 
	private IRetrieveProjectManager retrieveProjectManager;
	
    /**
     * This method is used to access the public page -sites that enlists the public pages of all the projects 
     * @param locale
     * @param model
     * @return
     * @throws QuadrigaStorageException 
     */
    @RequestMapping(value = "sites", method = RequestMethod.GET)
    public String showQuadrigaPublicPages(Model model) throws QuadrigaStorageException {
    	List<IProject> projectList = retrieveProjectManager.getProjectListByAccessibility("PUBLIC");
        model.addAttribute("projectList", projectList);
        return "sites";
    }

}
