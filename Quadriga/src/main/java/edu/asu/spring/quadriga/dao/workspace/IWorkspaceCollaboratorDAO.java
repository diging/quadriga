package edu.asu.spring.quadriga.dao.workspace;

import edu.asu.spring.quadriga.dao.ICollaboratorDAO;
import edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTO;

public interface IWorkspaceCollaboratorDAO extends ICollaboratorDAO<WorkspaceCollaboratorDTO> {

    public abstract void deleteWorkspaceCollaboratorDTO(
            WorkspaceCollaboratorDTO wsCollabDto);

}