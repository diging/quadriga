package edu.asu.spring.quadriga.dao.workspace;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IListExternalWsDAO {

    public abstract boolean isExternalWorkspaceExists(String workspaceId) throws QuadrigaStorageException;

    public abstract void createExternalWorkspace(String externalId, String externalWorkspaceName, String workspaceId,
            String projectId, IUser user);

    public abstract String getInternalWorkspaceId(String externalId);
}
