package edu.asu.spring.quadriga.accesschecks.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.accesschecks.IProjectSecurityChecker;
import edu.asu.spring.quadriga.db.workbench.IProjectAccessManager;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.workbench.IProjectCollaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjCollabManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Service
public class ProjectSecurityChecker implements IProjectSecurityChecker {
    @Autowired
    private IUserManager userManager;

    @Autowired
    private IRetrieveProjCollabManager projectManager;

    @Autowired
    private IProjectAccessManager accessManager;

    /**
     * This method checks if the user is Quadriga Admin
     * 
     * @param userName
     * @return boolean - TRUE if the user is quadriga Admin else FALSE
     * @throws QuadrigaStorageException
     * @author kiranbatna
     */
    @Override
    @Transactional
    public boolean checkQudrigaAdmin(String userName)
            throws QuadrigaStorageException {
        boolean chkAccess;
        IUser user;
        List<IQuadrigaRole> quadrigaRoles;

        // initialize chkAccess variable
        chkAccess = false;

        user = userManager.getUser(userName);
        quadrigaRoles = user.getQuadrigaRoles();
        for (IQuadrigaRole quadRole : quadrigaRoles) {
            if (quadRole.getId().equals(RoleNames.ROLE_QUADRIGA_ADMIN)) {
                chkAccess = true;
                break;
            }
        }

        return chkAccess;
    }

    /**
     * This method checks if the user is project owner
     * 
     * @param userName
     * @return boolean - TRUE if the user is project owner else FALSE
     * @throws QuadrigaStorageException
     * @author kiranbatna
     */

    @Override
    @Transactional
    public boolean isProjectOwner(String userName, String projectId)
            throws QuadrigaStorageException {
        // check if the user is project owner
        String owner = accessManager.getProjectOwner(projectId);
        return owner.equals(userName);

    }

    @Override
    @Transactional
    public boolean ownsAtLeastOneProject(String userName)
            throws QuadrigaStorageException {
        // check if the use is associated with any project
        return accessManager.getNrOfOwnedProjects(userName) > 0;
    }

    @Override
    @Transactional
    public boolean collaboratesOnAtLeastOneProject(String userName,
            String collaboratorRole) throws QuadrigaStorageException {
        return accessManager.nrOfProjectsCollaboratingOn(userName,
                collaboratorRole) > 0;
    }

    /**
     * This method checks if the user is project owner
     * 
     * @param userName
     * @return boolean - TRUE if the user is project owner else FALSE
     * @throws QuadrigaStorageException
     * @author kiranbatna
     */
    @Override
    @Transactional
    public boolean isCollaborator(String userName, String collaboratorRole,
            String projectId) throws QuadrigaStorageException {
        return accessManager.isCollaborator(userName, collaboratorRole,
                projectId);
    }

    /**
     * This method checks if the project collaborator has access to perform
     * operations.
     * 
     * @param userName
     * @param projectId
     * @param collaboratorRole
     * @return boolean - TRUE if he has access else FALSE
     * @throws QuadrigaStorageException
     * @author kiranbatna
     */
    @Override
    @Transactional
    public boolean isUserCollaboratorOnProject(String userName,
            String projectId, String collaboratorRole)
            throws QuadrigaStorageException {
        // fetch the collaborators associated with the project
        List<IProjectCollaborator> projectCollaboratorList = projectManager
                .getProjectCollaborators(projectId);
        
        if (projectCollaboratorList == null)
            return false;

        // loop through each collaborator
        for (IProjectCollaborator projectCollaborator : projectCollaboratorList) {
            if (projectCollaborator.getCollaborator() != null
                    && projectCollaborator.getCollaborator().getUserObj()
                            .getUserName().equals(userName)) {
                List<IQuadrigaRole> collaboratorRoles = projectCollaborator
                        .getCollaborator().getCollaboratorRoles();
                if (collaboratorRoles != null) {
                    // check if the collaborator is Project Admin or
                    // Contributor
                    for (IQuadrigaRole role : collaboratorRoles) {
                        if (role.getId().equals(collaboratorRole)) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * This method checks if the user is either a project owner or a quadriga
     * admin
     * 
     * @param userName
     * @return boolean - TRUE if the user is either a project owner or a
     *         quadriga admin else FALSE
     * @throws QuadrigaStorageException
     * @author kiranbatna
     */
    @Override
    @Transactional
    public boolean checkProjectAccess(String userName, String projectId)
            throws QuadrigaStorageException {
        boolean chkAccess;

        // initialize chkAccess variable
        chkAccess = false;

        // check if the user is project owner
        chkAccess = this.isProjectOwner(userName, projectId);

        if (!chkAccess) {
            chkAccess = this.isUserCollaboratorOnProject(userName, projectId,
                    RoleNames.ROLE_QUADRIGA_ADMIN);
        }
        return chkAccess;
    }

    /**
     * This method checks if the user has the editor role to
     * this Project
     * 
     * @param userName
     * @return boolean - TRUE if the user is editor on the project; otherwise false
     * @throws QuadrigaStorageException
     * @author kiranbatna
     */
    @Override
    @Transactional
    public boolean isEditor(String userName,
            String projectId) throws QuadrigaStorageException {
        // check if the user is project owner
        return accessManager
                .isUserEditorOfProject(userName, projectId) > 0;
    }

    @Override
    @Transactional
    public boolean chkDuplicateProjUnixName(String unixName, String projectId)
            throws QuadrigaStorageException {
        boolean chkAccess;

        // initialize chkAccess variable
        chkAccess = false;

        chkAccess = accessManager.chkDuplicateProjUnixName(unixName, projectId);

        return chkAccess;
    }
}
