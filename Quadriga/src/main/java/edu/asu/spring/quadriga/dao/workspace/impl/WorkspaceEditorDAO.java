package edu.asu.spring.quadriga.dao.workspace.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.workspace.IWorkspaceEditorDAO;
import edu.asu.spring.quadriga.dto.WorkspaceEditorDTO;
import edu.asu.spring.quadriga.dto.WorkspaceEditorDTOPK;

@Service
public class WorkspaceEditorDAO extends BaseDAO<WorkspaceEditorDTO> implements IWorkspaceEditorDAO {

    @Override
    public WorkspaceEditorDTO getWorkspaceEditorDTO(String workspaceId, String username) {
       return (WorkspaceEditorDTO) sessionFactory.getCurrentSession().get(WorkspaceEditorDTO.class, new WorkspaceEditorDTOPK(workspaceId, username));
    }
    
    @Override
    public void deleteWorkspaceEditorDTO(WorkspaceEditorDTO wsEdDTO) {
        deleteDTO(wsEdDTO);
    }

    @Override
    public WorkspaceEditorDTO getDTO(String id) {
        return getDTO(WorkspaceEditorDTO.class, id);
    }
}
