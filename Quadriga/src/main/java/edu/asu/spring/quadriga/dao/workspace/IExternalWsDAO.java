package edu.asu.spring.quadriga.dao.workspace;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dto.ExternalWorkspaceDTO;

public interface IExternalWsDAO extends IBaseDAO<ExternalWorkspaceDTO> {

    /**
     * This method returns an internal workspace id for a given external id.
     * 
     * @param externalId
     * @return
     */
    public abstract String getInternalWorkspaceId(String externalId);

    /**
     * This method returns an ExternalWorkspace object for a given external
     * workspace id.
     * 
     * @param externalWorkspaceid
     * @return
     */
    public abstract ExternalWorkspaceDTO getExternalWorkspace(String externalWorkspaceid);
}
