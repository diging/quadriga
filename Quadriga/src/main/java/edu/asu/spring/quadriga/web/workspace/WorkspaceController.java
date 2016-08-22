package edu.asu.spring.quadriga.web.workspace;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.accesschecks.IWSSecurityChecker;
import edu.asu.spring.quadriga.aspects.IAuthorization;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IProjectCollaborator;
import edu.asu.spring.quadriga.domain.workspace.ITextFile;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceCollaborator;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceNetwork;
import edu.asu.spring.quadriga.exceptions.Quadriga404Exception;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.textfile.ITextFileManager;
import edu.asu.spring.quadriga.service.workbench.IProjectCollaboratorManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

/**
 * Controller to handle all the workspace requests for Quadriga.
 * 
 * The (Dspace) design flow is to load the list of all communities accessible to
 * the user and then load all collections within the community selected by the
 * user. This second call for collection loads all the collections and the items
 * within them. The last call is to load the set of bitstreams within a selected
 * item. Any deviation from the above flow is handled by the concerned classes.
 * 
 * 
 * @author Kiran Kumar Batna
 * @author Ram Kumar Kumaresan
 */

@Controller
@Scope(value = "session")
public class WorkspaceController {
    public final static int SUCCESS = 1;
    public final static int FAILURE = 0;

    @Autowired
    private IWorkspaceManager wsManager;

    @Autowired
    private IWSSecurityChecker workspaceSecurity;

    @Autowired
    private ITextFileManager tfManager;

    @Autowired
    private IProjectCollaboratorManager projectCollaboratorManager;

    @Autowired
    private IRetrieveProjectManager projectManager;

    @Autowired
    @Qualifier("workspaceAuthorization")
    private IAuthorization authorization;

    private static final Logger logger = LoggerFactory.getLogger(WorkspaceController.class);

    /**
     * This will list the details of workspaces
     * 
     * @param workspaceid
     * @param model
     * @return String - url of the page listing all the workspaces of the
     *         project.
     * @throws QuadrigaStorageException
     * @author Kiran Kumar Batna
     * @throws QuadrigaAccessException
     * @throws QuadrigaException
     * @author Kiran Kumar Batna
     * @throws Quadriga404Exception
     */
    @RequestMapping(value = "auth/workbench/workspace/{workspaceid}", method = RequestMethod.GET)
    public String getWorkspaceDetails(@PathVariable("workspaceid") String workspaceid, Principal principal,
            ModelMap model) throws QuadrigaStorageException, QuadrigaAccessException, Quadriga404Exception {
        String userName = principal.getName();
        IWorkSpace workspace = wsManager.getWorkspaceDetails(workspaceid, userName);
        String projectId = wsManager.getProjectIdFromWorkspaceId(workspaceid);
        IProject project = projectManager.getProjectDetails(projectId);

        // List<IProjectCollaborator> collaboratorsList =
        // projectCollaboratorManager.getProjectCollaborators(projectId);
        // collaboratorsList.get(0).getCollaborator().getCollaboratorRoles().co

        if (workspace == null) {
            throw new Quadriga404Exception("Workspace with ID " + workspaceid + " does not exist.");
        }

        // retrieve the collaborators associated with the workspace
        List<IWorkspaceCollaborator> workspaceCollaboratorList = workspace.getWorkspaceCollaborators();
        workspace.setWorkspaceCollaborators(workspaceCollaboratorList);

        List<ITextFile> tfList = tfManager.retrieveTextFiles(workspaceid);

        List<IWorkspaceNetwork> workspaceNetworkList = wsManager.getWorkspaceNetworkList(workspaceid);

        List<IProjectCollaborator> projectCollaborators = projectCollaboratorManager.getProjectCollaborators(projectId);

        List<IProjectCollaborator> projectAdmins = new ArrayList<IProjectCollaborator>();

        for (IProjectCollaborator collaborator : projectCollaborators) {
            List<IQuadrigaRole> collaboratorRoles = collaborator.getCollaborator().getCollaboratorRoles();

            List<String> roleIds = new ArrayList<String>();
            collaboratorRoles.forEach(collaboratorRole -> roleIds.add(collaboratorRole.getId()));

            if (roleIds.contains(RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN)) {
                projectAdmins.add(collaborator);
            }
        }

        model.addAttribute("projectAdmins", projectAdmins);
        model.addAttribute("projectOwner", project.getOwner());
        model.addAttribute("networkList", workspaceNetworkList);
        model.addAttribute("workspacedetails", workspace);
        model.addAttribute("textFileList", tfList);

        String adminRoles[] = { RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN, RoleNames.ROLE_COLLABORATOR_OWNER };

        boolean isAdmin = authorization.chkAuthorization(userName, workspaceid, adminRoles);

        if (workspaceSecurity.checkWorkspaceOwner(userName, workspaceid)) {
            model.addAttribute("owner", 1);
        } else {
            model.addAttribute("owner", 0);
        }
        if (workspaceSecurity.checkWorkspaceOwnerEditorAccess(userName, workspaceid)) {
            model.addAttribute("editoraccess", 1);
        } else {
            model.addAttribute("editoraccess", 0);
        }
        if (workspaceSecurity.checkWorkspaceProjectInheritOwnerEditorAccess(userName, workspaceid)) {
            model.addAttribute("projectinherit", 1);
        } else {
            model.addAttribute("projectinherit", 0);
        }
        if (isAdmin) {
            model.addAttribute("wsadmin", 1);
        } else {
            model.addAttribute("wsadmin", 0);
        }

        String projectid = wsManager.getProjectIdFromWorkspaceId(workspaceid);
        model.addAttribute("myprojectid", projectid);

        // Including a condition to check if the workspace is not deactive. If
        // the workspace is deactive adding attribute to make delete button
        // disabled
        model.addAttribute("isDeactivated", wsManager.getDeactiveStatus(workspaceid));
        model.addAttribute("isArchived", wsManager.isWorkspaceArchived(workspaceid));

        return "auth/workbench/workspace/workspacedetails";
    }

}