package edu.asu.spring.quadriga.service.workspace;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.workspace.IWorkspace;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IExternalWorkspaceManager {

    /**
     * Determines if a workspace with the given external workspace id exists or
     * not.
     * 
     * @param externalWorkspaceId
     *            The External workspace id.
     * @return True if a workspace exists, False otherwise.
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    IWorkspace getExternalWorkspace(String externalWorkspaceId, String projectId)
            throws QuadrigaStorageException, QuadrigaAccessException;

    /**
     * This method creates a workspace entry in the the database.
     * 
     * @param externalWorkspaceId
     *            The ID of the external workspace.
     * @param externalWorkspaceName
     *            The name of the external workspace.
     * @param projectId
     *            The ID of the project
     * @param user
     *            The user details object
     * @return The workspace id of the created entry.
     */
    public String createExternalWorkspace(String externalWorkspaceId, String externalWorkspaceName, String projectId,
            IUser user);

}
