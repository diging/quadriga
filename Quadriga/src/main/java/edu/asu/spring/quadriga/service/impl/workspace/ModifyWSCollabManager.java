package edu.asu.spring.quadriga.service.impl.workspace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dao.impl.workspace.WorkspaceDAO;
import edu.asu.spring.quadriga.dao.workspace.IWorkspaceCollaboratorDAO;
import edu.asu.spring.quadriga.dao.workspace.IWorkspaceDAO;
import edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTO;
import edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTOPK;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.service.impl.CollaboratorManager;
import edu.asu.spring.quadriga.service.workspace.IModifyWSCollabManager;

@Service
public class ModifyWSCollabManager extends CollaboratorManager<WorkspaceCollaboratorDTO, WorkspaceCollaboratorDTOPK, WorkspaceDTO, WorkspaceDAO> implements IModifyWSCollabManager 
{
	
	@Autowired
	private IWorkspaceDAO workspaceDao;
	
	@Autowired
	private IWorkspaceCollaboratorDAO wsCollabDao;
	
	@Override
    public WorkspaceCollaboratorDTO createNewCollaboratorDTO() {
        return new WorkspaceCollaboratorDTO();
    }

    @Override
    public WorkspaceCollaboratorDTOPK createNewCollaboratorDTOPK(String id,
            String collabUser, String role) {
       return new WorkspaceCollaboratorDTOPK(id, collabUser, role);
    }

    @Override
    public IBaseDAO<WorkspaceDTO> getDao() {
        return workspaceDao; 
    }

    @Override
    public IBaseDAO<WorkspaceCollaboratorDTO> getCollaboratorDao() {
        return wsCollabDao;
    }
}
