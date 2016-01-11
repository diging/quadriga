package edu.asu.spring.quadriga.service.workspace.mapper;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IExternalWSManager {
    boolean isExternalWorkspaceExists(String externalWorkspaceId)
            throws QuadrigaStorageException, QuadrigaAccessException;

    public void createExternalWorkspace(String externalWorkspaceId, String externalWorkspaceName, String workspaceId,
            String projectId, IUser user);

    public String getInternalWorkspaceId(String externalWorkspaceId);
}
