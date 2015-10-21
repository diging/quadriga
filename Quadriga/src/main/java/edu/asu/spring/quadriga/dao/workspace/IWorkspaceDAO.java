package edu.asu.spring.quadriga.dao.workspace;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;

public interface IWorkspaceDAO extends IBaseDAO<WorkspaceDTO> {

    public abstract WorkspaceDTO getWorkspaceDTO(String workspaceId);

    public abstract boolean deleteWorkspace(String wsId);

}