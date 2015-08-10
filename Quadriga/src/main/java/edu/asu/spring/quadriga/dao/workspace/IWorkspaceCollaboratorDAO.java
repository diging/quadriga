package edu.asu.spring.quadriga.dao.workspace;

import edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTO;

public interface IWorkspaceCollaboratorDAO {

    public abstract void deleteWorkspaceCollaboratorDTO(
            WorkspaceCollaboratorDTO wsCollabDto);

}