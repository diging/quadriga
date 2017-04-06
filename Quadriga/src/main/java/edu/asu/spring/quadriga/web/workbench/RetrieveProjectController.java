package edu.asu.spring.quadriga.web.workbench;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.accesschecks.IProjectSecurityChecker;
import edu.asu.spring.quadriga.accesschecks.IWSSecurityChecker;
import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IProjectWorkspace;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.NoSuchRoleException;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.passthroughproject.IPassThroughProjectManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class RetrieveProjectController {

    @Autowired
    private IRetrieveProjectManager projectManager;

    @Autowired
    private IPassThroughProjectManager passThroughManager;

    @Autowired
    private IProjectSecurityChecker projectSecurity;

    @Autowired
    private IListWSManager wsManager;

    @Autowired
    private IWSSecurityChecker securityChecker;
    
    /**
     * this method acts as a controller for handling all the activities on the
     * workbench home page
     * 
     * @param principal
     * @return string for workbench url
     * @throws QuadrigaStorageException
     * @author rohit sukleshwar pendbhaje
     * @throws JSONException
     */
    @RequestMapping(value = "auth/workbench", method = RequestMethod.GET)
    public ModelAndView getProjectList(Principal principal) throws QuadrigaStorageException, JSONException {
        String userName;
        ModelAndView model;

        userName = principal.getName();

        List<IProject> projectListAsOwner = projectManager.getProjectList(userName);
        List<IProject> fullProjects = new ArrayList<IProject>();

        model = new ModelAndView("auth/workbench");
        List<String> projectIds = new ArrayList<String>();
        Map<String, Boolean> accessibleProjects = new HashMap<String, Boolean>();

        if (projectListAsOwner != null) {
            for (IProject p : projectListAsOwner) {
                fullProjects.add(projectManager.getProjectDetails(p.getProjectId()));
                projectIds.add(p.getProjectId());
                accessibleProjects.put(p.getProjectId(), true);
            }
        }

        // Fetch all the projects for which the user is collaborator.
        List<IProject> projectListAsCollaborator = projectManager.getCollaboratorProjectList(userName);
        if (projectListAsCollaborator != null) {
            for (IProject p : projectListAsCollaborator) {
                if (!projectIds.contains(p.getProjectId())) {
                    fullProjects.add(projectManager.getProjectDetails(p.getProjectId()));
                    projectIds.add(p.getProjectId());
                    accessibleProjects.put(p.getProjectId(), true);
                }
            }
        }

        // Fetch all the projects for which the user is associated workspace owner.
        List<IProject> projectListAsWorkspaceOwner = projectManager.getProjectListAsWorkspaceOwner(userName);
        if (projectListAsWorkspaceOwner != null) {
            for (IProject p : projectListAsWorkspaceOwner) {
                if (!projectIds.contains(p.getProjectId())) {
                    fullProjects.add(projectManager.getProjectDetails(p.getProjectId()));
                    projectIds.add(p.getProjectId());
                    accessibleProjects.put(p.getProjectId(), false);
                }
            }
        }
    
        // Fetch all the projects for which the user is associated workspace collaborator.
        List<IProject> projectListAsWSCollaborator = projectManager.getProjectListAsWorkspaceCollaborator(userName);
     
        if (projectListAsWSCollaborator != null) {
            for (IProject p : projectListAsWSCollaborator) {
               // Process the project details if it has not been evaluated.
                if (!projectIds.contains(p.getProjectId())) {
                    // Get details of the project using the project id.
                    IProject tempProject = projectManager.getProjectDetails(p.getProjectId());
                    if(tempProject != null && tempProject.getProjectWorkspaces() != null){
                        List<IProjectWorkspace> tempProjectWorkspaces = new ArrayList<IProjectWorkspace>();
                        // Examine the workspace details associated with the project. Include details of only those workspaces, where the user is a collaborator.
                        for(IProjectWorkspace projectWorkspace : tempProject.getProjectWorkspaces()){
                            String workspaceId = projectWorkspace.getWorkspace().getWorkspaceId();
                            if(securityChecker.chkCollabWorkspaceAccess(userName, workspaceId, RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN) || 
                               securityChecker.chkCollabWorkspaceAccess(userName, workspaceId, RoleNames.ROLE_WORKSPACE_COLLABORATOR_CONTRIBUTOR) || 
                               securityChecker.chkCollabWorkspaceAccess(userName, workspaceId, RoleNames.ROLE_WORKSPACE_COLLABORATOR_EDITOR)){
                                tempProjectWorkspaces.add(projectWorkspace);
                            }
                        }
                        tempProject.setProjectWorkspaces(tempProjectWorkspaces);
                        fullProjects.add(tempProject);                    
                    }
                    projectIds.add(p.getProjectId());
                    accessibleProjects.put(p.getProjectId(), false);  
                }
            }
        }

        Collections.sort(fullProjects, new Comparator<IProject>() {

            @Override
            public int compare(IProject o1, IProject o2) {
                return o1.getProjectName().compareTo(o2.getProjectName());
            }
        });

        model.getModelMap().put("projects", fullProjects);
        model.getModelMap().put("accessibleProjects", accessibleProjects);

        return model;
    }

    @RequestMapping(value = "auth/workbench/projects/{extId:[0-9a-zA-Z-_]+}+{client:[0-9a-zA-Z-]+}", method = RequestMethod.GET)
    public String getProjectByExternalId(@PathVariable String extId, @PathVariable String client)
            throws QuadrigaStorageException {
        IProject project = passThroughManager.getPassthroughProject(extId, client);
        if (project == null) {
            return "auth/404";
        }
        return "redirect:/auth/workbench/projects/" + project.getProjectId();
    }

    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 1, userRole = {
            RoleNames.ROLE_COLLABORATOR_OWNER, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN,
            RoleNames.ROLE_PROJ_COLLABORATOR_CONTRIBUTOR, RoleNames.ROLE_WORKSPACE_COLLABORATOR_EDITOR }) })
    @RequestMapping(value = "auth/workbench/projects/{projectid:[0-9a-zA-Z-_]+}", method = RequestMethod.GET)
    public String getProjectDetails(@PathVariable("projectid") String projectid, Principal principal, Model model)
            throws QuadrigaStorageException, NoSuchRoleException, QuadrigaAccessException {

        String userName = principal.getName();
        IProject project = projectManager.getProjectDetails(projectid);

        // retrieve all the workspaces associated with the project
        List<IWorkSpace> workspaceList = wsManager.listActiveWorkspace(projectid, userName);

        List<IWorkSpace> collaboratorWorkspaceList = wsManager.listActiveWorkspaceByCollaborator(projectid, userName);

        List<IWorkSpace> deactiveWorkspaceList = wsManager.listDeactivatedWorkspace(projectid, userName);

        List<IWorkSpace> archivedWorkspaceList = wsManager.listArchivedWorkspace(projectid, userName);

        int deactivatedWSSize = deactiveWorkspaceList == null ? 0 : deactiveWorkspaceList.size();

        int archivedWSSize = archivedWorkspaceList == null ? 0 : archivedWorkspaceList.size();

        model.addAttribute("project", project);
        model.addAttribute("workspaceList", workspaceList);
        model.addAttribute("collabworkspacelist", collaboratorWorkspaceList);
        model.addAttribute("deactivatedWSSize", deactivatedWSSize);
        model.addAttribute("archivedWSSize", archivedWSSize);

        List<String> collaboratorRoles = projectSecurity.getCollaboratorRoles(userName, projectid);

        if (projectSecurity.isProjectOwner(userName, projectid)) {
            model.addAttribute("owner", true);
        } else {
            model.addAttribute("owner", false);
        }
        if (collaboratorRoles.contains(RoleNames.ROLE_PROJ_COLLABORATOR_EDITOR)) {
            model.addAttribute("isProjectEditor", true);
        } else {
            model.addAttribute("isProjectEditor", false);
        }
        if (collaboratorRoles.contains(RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN)) {
            model.addAttribute("isProjectAdmin", true);
        } else {
            model.addAttribute("isProjectAdmin", false);
        }
        if (collaboratorRoles.contains(RoleNames.ROLE_PROJ_COLLABORATOR_CONTRIBUTOR)) {
            model.addAttribute("isProjectContributor", true);
        } else {
            model.addAttribute("isProjectContributor", false);
        }

        return "auth/workbench/project";
    }
}
