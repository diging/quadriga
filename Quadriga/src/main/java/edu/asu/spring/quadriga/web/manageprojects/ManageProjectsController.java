package edu.asu.spring.quadriga.web.manageprojects;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.workbench.IProjectCollaboratorManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

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
    
    @Autowired
    private IProjectCollaboratorManager projectCollaboratorManager;
    
    @Autowired
    private IQuadrigaRoleManager rolemanager;
    
    @RequestMapping(value = "auth/users/manageprojects", method = RequestMethod.GET)
    public String viewProjects(ModelMap model, Principal principal) throws QuadrigaStorageException{
        List<IProject> projectList =  projectManager.getProjectList();
        model.addAttribute("projectList", projectList);
        return "auth/users/project/manage";
    }
    
    @RequestMapping(value = "auth/users/manageprojects/addprojectadmin", method = RequestMethod.POST)
    public String addUserAsProjectAdmin(@RequestParam("projectIds") List<String> projectIds, ModelMap model, Principal principal) throws QuadrigaStorageException{
        String projAdminDBId = rolemanager.getQuadrigaRoleDBId(IQuadrigaRoleManager.PROJECT_ROLES, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN);
        if(projectIds != null){
            for(String projectId : projectIds){
                projectCollaboratorManager.addCollaborator(principal.getName(), projAdminDBId, projectId, principal.getName());
            }
        }
        List<IProject> projectList =  projectManager.getProjectList();
        model.addAttribute("projectList", projectList);
        return "auth/users/project/manage";
    }
}
