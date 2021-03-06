package edu.asu.spring.quadriga.web.admin.projects;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

/**
* The controller to manage projects belonging to Quadriga users.
* Can be called only by pages accessible by admins
* 
* @author Chiraag Subramanian
*
*/
@Controller
public class ManageProjectsController {
    
    @Autowired
    private IRetrieveProjectManager projectManager;
     
    @RequestMapping(value = "auth/admin/projects", method = RequestMethod.GET)
    public String viewProjects(ModelMap model, Principal principal) throws QuadrigaStorageException{
        List<IProject> projectList =  projectManager.getProjectList(null);
        model.addAttribute("projectList", projectList);
        return "auth/users/project/manage";
    }  
    
}
