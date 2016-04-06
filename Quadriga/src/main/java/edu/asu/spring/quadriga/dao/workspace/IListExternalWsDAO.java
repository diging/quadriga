package edu.asu.spring.quadriga.dao.workspace;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dto.ExternalWorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IListExternalWsDAO extends IBaseDAO<ExternalWorkspaceDTO> {

    public abstract boolean isExternalWorkspaceExists(String workspaceId) throws QuadrigaStorageException;

    public abstract String getInternalWorkspaceId(String externalId);
}
