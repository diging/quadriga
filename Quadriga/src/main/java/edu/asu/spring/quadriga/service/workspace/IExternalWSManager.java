package edu.asu.spring.quadriga.service.workspace;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IExternalWSManager {
    boolean isExternalWorkspaceExists(String externalWorkspaceId)
            throws QuadrigaStorageException, QuadrigaAccessException;

    public String createExternalWorkspace(String externalWorkspaceId, String externalWorkspaceName, String projectId,
            IUser user);

    /**
     * This method returns an internal workspace id for a given external id.
     * 
     * @param externalId
     *            The given external Id.
     * @return The internal id for the external workspace.
     */
    public String getInternalWorkspaceId(String externalWorkspaceId);
}
