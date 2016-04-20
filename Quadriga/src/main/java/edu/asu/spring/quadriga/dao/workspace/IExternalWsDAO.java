package edu.asu.spring.quadriga.dao.workspace;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dto.ExternalWorkspaceDTO;

public interface IExternalWsDAO extends IBaseDAO<ExternalWorkspaceDTO> {

    public abstract String getInternalWorkspaceId(String externalId);

    public abstract ExternalWorkspaceDTO getExternalWorkspace(String externalWorkspaceid);
}
