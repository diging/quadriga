package edu.asu.spring.quadriga.accesschecks;

import java.util.List;

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
     * For the user, user's roles on the project are retrieved from the
     * database. For each of these roles, respective id's are stored in a list
     * and that list is returned.
     * 
     * @param userName
     * @param projectId
     * @return Map
     */
    public abstract List<String> getCollaboratorRoles(String userName, String projectId);

}
