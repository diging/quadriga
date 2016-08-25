package edu.asu.spring.quadriga.dao.workspace;

import java.util.List;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dto.ExternalWorkspaceDTO;

public interface IExternalWorkspaceDAO extends IBaseDAO<ExternalWorkspaceDTO> {

    /**
     * This method returns an ExternalWorkspace object for a given external
     * workspace id.
     * 
     * @param externalWorkspaceid
     * @return
     */
    public abstract List<ExternalWorkspaceDTO> getExternalWorkspace(String externalWorkspaceid);
}
