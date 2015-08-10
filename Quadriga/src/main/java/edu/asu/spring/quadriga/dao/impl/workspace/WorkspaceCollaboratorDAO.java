package edu.asu.spring.quadriga.dao.impl.workspace;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.workspace.IWorkspaceCollaboratorDAO;
import edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTO;

@Service
public class WorkspaceCollaboratorDAO extends BaseDAO<WorkspaceCollaboratorDTO> implements IWorkspaceCollaboratorDAO {
 
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.dao.impl.workspace.IWorkspaceCollaboratorDAO#deleteWorkspaceCollaboratorDTO(edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTO)
     */
    @Override
    public void deleteWorkspaceCollaboratorDTO(WorkspaceCollaboratorDTO wsCollabDto) {
        deleteDTO(wsCollabDto);
    }
}
