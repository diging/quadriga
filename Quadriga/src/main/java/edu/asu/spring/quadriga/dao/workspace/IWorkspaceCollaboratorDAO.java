package edu.asu.spring.quadriga.dao.workspace;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTO;

public interface IWorkspaceCollaboratorDAO extends IBaseDAO<WorkspaceCollaboratorDTO> {

    public abstract void deleteWorkspaceCollaboratorDTO(
            WorkspaceCollaboratorDTO wsCollabDto);

}