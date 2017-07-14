package edu.asu.spring.quadriga.aspects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.accesschecks.IWSSecurityChecker;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workspace.IWorkspace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceCollaborator;
import edu.asu.spring.quadriga.exceptions.IllegalObjectException;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

/**
 * Service level Implementation of {@link IAuthorization} for {@link IWorkspace}
 * . This class specifically works on authorization check of user for
 * {@link IWorkspace} access.
 * 
 * @author Kiran kumar
 *
 */
@Service("workspaceAuthorization")
public class WorkspaceAuthorization implements IAuthorization {
    @Autowired
    private IWorkspaceManager wsManager;

    @Autowired
    private IWSSecurityChecker wsSecurityManager;

    @Autowired
    private ProjectAuthorization projectAuthorization;

    private final Logger logger = LoggerFactory.getLogger(WorkspaceAuthorization.class);

    @Override
    @Transactional
    public boolean chkAuthorization(String userName, Object workspaceObj, String[] userRoles)
            throws QuadrigaStorageException, QuadrigaAccessException {

        // fetch the details of the workspace

        IWorkspace workspace;

        if (workspaceObj instanceof String) {
            String workspaceId = (String) workspaceObj;
            workspace = wsManager.getWorkspaceDetails(workspaceId, userName);
        } else {
            try {
                workspace = (IWorkspace) workspaceObj;
            } catch (ClassCastException cce) {
                throw new IllegalObjectException(cce);
            }
        }
        IProject project = workspace.getProject();
        List<String> projects = new ArrayList<String>();
        projects.add(project.getProjectId());
        boolean isProjectOwnerOrAdmin = projectAuthorization.chkAuthorization(userName, projects,
                new String[] { RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN });

        if (isProjectOwnerOrAdmin) {
            return true;
        }

        // check if the logged in user is workspace owner
        if (workspace != null) {
            String workspaceOwner = workspace.getOwner().getUserName();

            if (userName.equals(workspaceOwner)) {
                return true;
            } else {
                if (userRoles.length > 0) {
                    List<String> roles = Arrays.asList(userRoles);
                    List<IWorkspaceCollaborator> workspaceCollaboratorList = workspace.getWorkspaceCollaborators();
                    if (workspaceCollaboratorList != null) {
                        for (IWorkspaceCollaborator workspaceCollaborator : workspaceCollaboratorList) {
                            String collaboratorName = null;
                            if (workspaceCollaborator.getCollaborator() != null) {
                                // check if he is a collaborator to the project
                                collaboratorName = workspaceCollaborator.getCollaborator().getUserObj().getUserName();
                            }
                            if (collaboratorName != null) {
                                if (userName.equals(collaboratorName)) {
                                    List<IQuadrigaRole> collaboratorRoles = null;
                                    if (workspaceCollaborator.getCollaborator() != null) {
                                        collaboratorRoles = workspaceCollaborator.getCollaborator()
                                                .getCollaboratorRoles();
                                    }
                                    if (collaboratorRoles != null) {
                                        for (IQuadrigaRole collabRole : collaboratorRoles) {
                                            String collaboratorRoleId = collabRole.getId();
                                            if (roles.contains(collaboratorRoleId)) {
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    @Transactional
    public boolean chkAuthorizationByRole(String userName, String[] userRoles)
            throws QuadrigaStorageException, QuadrigaAccessException {

        if (wsSecurityManager.checkIsWorkspaceAssociated(userName)) {
            return true;
        } else {
            if (userRoles.length > 0) {
                List<String> roles = Arrays.asList(userRoles);
                // check if the user associated with the role has any projects
                for (String role : roles) {
                    if (wsSecurityManager.chkIsCollaboratorWorkspaceAssociated(userName, role)) {
                        return true;
                    }
                }
            }
        }
        return false;

    }

    @Override
    public boolean chkAuthorization(String userName, List<String> accessObjectId, String[] userRoles)
            throws QuadrigaStorageException, QuadrigaAccessException {
        // TODO Auto-generated method stub
        return false;
    }
}
