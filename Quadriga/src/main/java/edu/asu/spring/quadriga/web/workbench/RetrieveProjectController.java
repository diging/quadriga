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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.accesschecks.IProjectSecurityChecker;
import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.NoSuchRoleException;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class RetrieveProjectController {

    @Autowired
    private IRetrieveProjectManager projectManager;

    @Autowired
    private IProjectSecurityChecker projectSecurity;

    @Autowired
    private IListWSManager wsManager;

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

        // Fetch all the projects for which the user is collaborator
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

        // Fetch all the projects for which the user is associated workspace
        // owner
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

        // Fetch all the projects for which the user is associated workspace
        // collaborator
        List<IProject> projectListAsWSCollaborator = projectManager.getProjectListAsWorkspaceCollaborator(userName);
        if (projectListAsWSCollaborator != null) {
            for (IProject p : projectListAsWSCollaborator) {
                if (!projectIds.contains(p.getProjectId())) {
                    fullProjects.add(projectManager.getProjectDetails(p.getProjectId()));
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

        // model.getModelMap().put("projectlistasowner", projectListAsOwner);
        // model.getModelMap().put("projectlistascollaborator",
        // projectListAsCollaborator);
        // model.getModelMap().put("projectlistaswsowner",
        // projectListAsWorkspaceOwner);
        // model.getModelMap().put("projectlistaswscollaborator",
        // projectListAsWSCollaborator);

        return model;
    }

    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 1, userRole = {
            RoleNames.ROLE_COLLABORATOR_ADMIN, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN,
            RoleNames.ROLE_PROJ_COLLABORATOR_CONTRIBUTOR, RoleNames.ROLE_WORKSPACE_COLLABORATOR_EDITOR }) })
    @RequestMapping(value = "auth/workbench/projects/{projectid}", method = RequestMethod.GET)
    public ModelAndView getProjectDetails(@PathVariable("projectid") String projectid, Principal principal)
            throws QuadrigaStorageException, NoSuchRoleException, QuadrigaAccessException {
        ModelAndView model = new ModelAndView("auth/workbench/project");

        String userName = principal.getName();
        IProject project = projectManager.getProjectDetails(projectid);

        // retrieve all the workspaces associated with the project
        List<IWorkSpace> workspaceList = wsManager.listActiveWorkspace(projectid, userName);

        List<IWorkSpace> collaboratorWorkspaceList = wsManager.listActiveWorkspaceByCollaborator(projectid, userName);

        List<IWorkSpace> deactiveWorkspaceList = wsManager.listDeactivatedWorkspace(projectid, userName);

        List<IWorkSpace> archivedWorkspaceList = wsManager.listArchivedWorkspace(projectid, userName);

        int deactivatedWSSize = deactiveWorkspaceList == null ? 0 : deactiveWorkspaceList.size();

        int archivedWSSize = archivedWorkspaceList == null ? 0 : archivedWorkspaceList.size();

        model.getModelMap().put("project", project);
        model.getModelMap().put("workspaceList", workspaceList);
        model.getModelMap().put("collabworkspacelist", collaboratorWorkspaceList);
        model.getModelMap().put("deactivatedWSSize", deactivatedWSSize);
        model.getModelMap().put("archivedWSSize", archivedWSSize);

        if (projectSecurity.isProjectOwner(userName, projectid)) {
            model.getModelMap().put("owner", 1);
        } else {
            model.getModelMap().put("owner", 0);
        }
        if (projectSecurity.isEditor(userName, projectid)) {
            model.getModelMap().put("editoraccess", 1);
        } else {
            model.getModelMap().put("editoraccess", 0);
        }
        if (projectSecurity.isCollaborator(userName, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN, project.getProjectId())) {
            model.getModelMap().put("isProjectAdmin", true);
        } else {
            model.getModelMap().put("isProjectAdmin", false);
        }
        return model;
    }

    /*
     * @RequestMapping(value="sites/{ProjectUnixName}",
     * method=RequestMethod.GET) public String
     * showProject(@PathVariable("ProjectUnixName") String unixName,Model model)
     * throws QuadrigaStorageException {
     * 
     * 
     * 
     * IProject project = projectManager.getProjectDetailsByUnixName(unixName);
     * if(project!=null){
     * 
     * model.addAttribute("project", project); return "website"; } else return
     * "forbidden";
     * 
     * 
     * }
     */
}
