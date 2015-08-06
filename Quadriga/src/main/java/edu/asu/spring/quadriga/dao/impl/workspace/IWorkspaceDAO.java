package edu.asu.spring.quadriga.dao.impl.workspace;

import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IWorkspaceDAO {

    public abstract WorkspaceDTO getWorkspaceDTO(String workspaceId);

    public abstract boolean updateWorkspaceDTO(WorkspaceDTO wsDto);

}