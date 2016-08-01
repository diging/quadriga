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

    public abstract Map<String, Boolean> getCollaboratorRoles(String userName, String projectId);

}
