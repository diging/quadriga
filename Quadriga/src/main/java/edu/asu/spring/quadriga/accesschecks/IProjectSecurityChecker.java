package edu.asu.spring.quadriga.accesschecks;

import java.util.Map;

import edu.asu.spring.quadriga.exceptions.NoSuchRoleException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IProjectSecurityChecker {

    public abstract boolean checkProjectAccess(String userName, String projectId) throws QuadrigaStorageException;

    public abstract boolean isProjectOwner(String userName, String projectId) throws QuadrigaStorageException;

    public abstract boolean checkQuadrigaAdmin(String userName) throws QuadrigaStorageException;

    public abstract boolean isUserCollaboratorOnProject(String userName, String projectId, String collaboratorRole)
            throws QuadrigaStorageException;

    public abstract boolean isCollaborator(String userName, String collaboratorRole, String projectId)
            throws QuadrigaStorageException, NoSuchRoleException;

    public abstract boolean ownsAtLeastOneProject(String userName) throws QuadrigaStorageException;

    public abstract boolean collaboratesOnAtLeastOneProject(String userName, String collaboratorRole)
            throws QuadrigaStorageException;

    public abstract boolean isUnixnameInUse(String unixName, String projectId) throws QuadrigaStorageException;

    /**
     * The ID's of roles of a user are used as keys in HashMap. HashMap is
     * initialized with role ID's as keys, values as false. For the roles user
     * has on a project, the values are assigned to true. HashMap is returned.
     * 
     * @param userName
     * @param projectId
     * @return Map
     */
    public abstract Map<String, Boolean> getCollaboratorRoles(String userName, String projectId);

}
