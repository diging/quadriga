package edu.asu.spring.quadriga.aspects;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.accesschecks.IProjectSecurityChecker;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IProjectCollaborator;
import edu.asu.spring.quadriga.exceptions.IllegalObjectException;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.passthroughproject.IPassThroughProjectManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

/**
 * Service level Implementation of {@link IAuthorization} for {@link IProject}.
 * This class specifically works on authorization check of user for
 * {@link IProject} access.
 * 
 * @author Kiran kumar
 *
 */
@Service("projectAuthorization")
public class ProjectAuthorization implements IAuthorization {
    @Autowired
    private IRetrieveProjectManager projectManager;

    @Autowired
    private IPassThroughProjectManager passThroughManager;

    @Autowired
    private IProjectSecurityChecker projectSecurityManager;

    private final Logger logger = LoggerFactory.getLogger(ProjectAuthorization.class);

    @Override
    public boolean chkAuthorization(String userName, Object accessObj, String[] userRoles)
            throws QuadrigaStorageException, QuadrigaAccessException {
        IProject project;
        // fetch the details of the concept collection

        if (accessObj instanceof String) {
            String projectId = (String) accessObj;
            project = projectManager.getProjectDetails(projectId);
        } else {
            try {
                project = (IProject) accessObj;
            } catch (ClassCastException cce) {
                throw new IllegalObjectException(cce);
            }
        }
        // fetch the details of the project

        String projectOwner = project.getOwner().getUserName();
        if (userName.equals(projectOwner)) {
            return true;
        } else { // check the user roles if he is not a project owner
            if (userRoles.length > 0) {
                List<String> roles = Arrays.asList(userRoles);
                List<IProjectCollaborator> projectCollaborators = project.getProjectCollaborators();
                if (projectCollaborators != null) {
                    for (IProjectCollaborator projectCollaborator : projectCollaborators) {
                        ICollaborator collaborator = projectCollaborator.getCollaborator();
                        // check if he is a collaborator to the project
                        String collaboratorName = collaborator.getUserObj().getUserName();
                        if (userName.equals(collaboratorName)) {
                            List<IQuadrigaRole> collaboratorRoles = collaborator.getCollaboratorRoles();
                            for (IQuadrigaRole collabRole : collaboratorRoles) {
                                if (roles.contains(collabRole.getId())) {
                                    return true;
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
    public boolean chkAuthorization(String userName, List<String> projectIds, String[] userRoles)
            throws QuadrigaStorageException, QuadrigaAccessException {

        for (String projectId : projectIds) {
            if (!chkAuthorization(userName, projectId, userRoles)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean chkAuthorizationByRole(String userName, String[] userRoles)
            throws QuadrigaStorageException, QuadrigaAccessException {

        // fetch the details of the project
        if (projectSecurityManager.ownsAtLeastOneProject(userName)) {
            return true;
        }

        // check the user roles if he is not a project owner
        else {
            if (userRoles.length > 0) {
                List<String> roles = Arrays.asList(userRoles);
                // check if the user associated with the role has any projects
                for (String role : roles) {
                    if (projectSecurityManager.collaboratesOnAtLeastOneProject(userName, role)) {
                        return true;
                    }

                }
            }
        }
        return false;
    }
}