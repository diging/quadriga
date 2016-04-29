package edu.asu.spring.quadriga.dao.workspace;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dto.WorkspaceEditorDTO;

public interface IWorkspaceEditorDAO extends IBaseDAO<WorkspaceEditorDTO> {

    public abstract WorkspaceEditorDTO getWorkspaceEditorDTO(String workspaceId, String username);

    public abstract void deleteWorkspaceEditorDTO(WorkspaceEditorDTO wsEdDTO);

}