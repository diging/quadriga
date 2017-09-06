package edu.asu.spring.quadriga.accesschecks.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.accesschecks.IProjectSecurityChecker;
import edu.asu.spring.quadriga.accesschecks.IWSSecurityChecker;
import edu.asu.spring.quadriga.dao.workspace.IWorkspaceAccessDAO;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceCollaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceCollaboratorManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Service
public class WSSecurityChecker implements IWSSecurityChecker {
    @Autowired
    private IProjectSecurityChecker projectSecurity;

    @Autowired
    private IWorkspaceCollaboratorManager wsCollabManager;

    @Autowired
    private IWorkspaceAccessDAO dbConnect;

    /**
     * This method checks if the user has access to create a worksapce.
     * 
     * @param userName
     * @param projectId
     * @return boolean - TRUE if user has access else FALSE
     * @throws QuadrigaStorageException
     * @author kiranbatna
     */
    @Override
    @Transactional
    public boolean hasPermissionToCreateWS(String userName, String projectId) throws QuadrigaStorageException {
        // check if the user is a project owner
        if (projectSecurity.isProjectOwner(userName, projectId)) {
            return true;
        }

        // check if the user is a project collaborator and has a ADMIN role
        if (projectSecurity.isUserCollaboratorOnProject(userName, projectId, RoleNames.ROLE_COLLABORATOR_OWNER)) {
            return true;
        }

        // check if the user is a project collaborator and has PROJECT_ADMIN
        // role
        if (projectSecurity.isUserCollaboratorOnProject(userName, projectId, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN)) {
            return true;
        }

        // check if the user is a project collaborator and has CONTRIBUTOR role
        if (projectSecurity.isUserCollaboratorOnProject(userName, projectId,
                RoleNames.ROLE_PROJ_COLLABORATOR_CONTRIBUTOR)) {
            return true;
        }

        return false;
    }

    /**
     * This method checks if the user has access to Archive/Deactivate/Delete
     * workspace
     * 
     * @param userName
     * @param projectId
     * @param workspaceId
     * @return boolean - TRUE if the user has access else FALSE
     * @throws QuadrigaStorageException
     * @author kiranbatna
     */
    @Override
    @Transactional
    public boolean hasAccessToWorkspace(String userName, String projectId, String workspaceId)
            throws QuadrigaStorageException {
        // check if the user is a project owner
        if (projectSecurity.isProjectOwner(userName, projectId)) {
            return true;
        }

        // check if the user is workspace owner
        if (dbConnect.chkWorkspaceOwner(userName, workspaceId)) {
            return true;
        }

        // check if the user is a project collaborator having ADMIN role
        if (projectSecurity.isUserCollaboratorOnProject(userName, projectId, RoleNames.ROLE_QUADRIGA_ADMIN)) {
            return true;
        }

        // check if user is project collaborator having PROJECT_ADMIN role
        if (projectSecurity.isUserCollaboratorOnProject(userName, projectId, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN)) {
            return true;
        }

        return false;
    }

    /**
     * This checks if the user has the specified collaborator role
     * 
     * @param userName
     * @param workspaceId
     * @param collaboratorRole
     * @return boolean - TRUE if the user role is same as supplied else FALSE
     * @throws QuadrigaStorageException
     * @author kiranbatna
     */
    @Override
    @Transactional
    public boolean chkCollabWorkspaceAccess(String userName, String workspaceId, String collaboratorRole)
            throws QuadrigaStorageException {
        boolean chkAccess = false;

        // fetch the collaborators associated with the workspace
        List<IWorkspaceCollaborator> workspaceCollaboratorList = wsCollabManager.getWorkspaceCollaborators(workspaceId);

        if (workspaceCollaboratorList != null) {
            for (IWorkspaceCollaborator workspaceCollaborator : workspaceCollaboratorList) {

                // check if the user is one of the collaborators
                if (workspaceCollaborator.getCollaborator() != null) {
                    if (workspaceCollaborator.getCollaborator().getUserObj().getUserName().equals(userName)) {
                        List<IQuadrigaRole> collaboratorRoles = workspaceCollaborator.getCollaborator().getCollaboratorRoles();

                        if (collaboratorRoles != null) {
                            // check if the collaborator is the supplied
                            // collaborator role
                            for (IQuadrigaRole role : collaboratorRoles) {
                                if (role.getId().equals(collaboratorRole)) {
                                    chkAccess = true;
                                    break;
                                }
                            }
                        }
                        // break through the outer loop
                        break;
                    }
                }
            }
        }
        return chkAccess;
    }

    /**
     * This method is used to check if the user has access to modify workspace
     * 
     * @param userName
     * @param workspaceId
     * @return boolean - TRUE if user has access else FALSE
     * @throws QuadrigaStorageException
     * @author kiranbatna
     */
    @Override
    @Transactional
    public boolean chkModifyWorkspaceAccess(String userName, String workspaceId) throws QuadrigaStorageException {
        boolean chkAccess = dbConnect.chkWorkspaceOwner(userName, workspaceId);

        if (!chkAccess) {
            // check if the user has collaborator role SINGLE WORKSPACE ADMIN
            chkAccess = this.chkCollabWorkspaceAccess(userName, workspaceId,
                    RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN);
        }

        return chkAccess;
    }

    @Override
    @Transactional
    public boolean checkWorkspaceOwner(String userName, String workspaceId) throws QuadrigaStorageException {
        return dbConnect.chkWorkspaceOwner(userName, workspaceId);

    }

    @Override
    @Transactional
    public boolean checkIsWorkspaceAssociated(String userName) throws QuadrigaStorageException {
        return dbConnect.chkIsWorkspaceAssocaited(userName);
    }

    @Override
    @Transactional
    public boolean chkIsCollaboratorWorkspaceAssociated(String userName, String role)
            throws QuadrigaStorageException, QuadrigaAccessException {
        return dbConnect.chkIsCollaboratorWorkspaceAssociated(userName, role);
    }

    /**
     * This method checks if the user is workspace owner and has editor role to
     * this workspace
     * 
     * @param userName
     * @return boolean - TRUE if the user is either a project owner or a
     *         quadriga admin else FALSE
     * @throws QuadrigaStorageException
     * @author kiranbatna
     */
    @Override
    @Transactional
    public boolean checkWorkspaceOwnerEditorAccess(String userName, String workspaceId)
            throws QuadrigaStorageException {
        return dbConnect.chkWorkspaceOwnerEditorRole(userName, workspaceId);
    }

    /**
     * This method checks if the user is project owner and has editor roles. If
     * this editor is inherit to workspace access
     * 
     * @param userName
     * @return boolean - TRUE if the user is either a project owner or a
     *         quadriga admin else FALSE
     * @throws QuadrigaStorageException
     * @author kiranbatna
     */
    @Override
    @Transactional
    public boolean checkWorkspaceProjectInheritOwnerEditorAccess(String userName, String workspaceId)
            throws QuadrigaStorageException {
        return dbConnect.chkWorkspaceProjectInheritOwnerEditorRole(userName, workspaceId);
    }

    @Override
    @Transactional
    public boolean checkIsWorkspaceExists(String workspaceId) throws QuadrigaStorageException {
        return dbConnect.chkWorkspaceExists(workspaceId);
    }
}
