package edu.asu.spring.quadriga.service.workspace.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dao.ICollaboratorDAO;
import edu.asu.spring.quadriga.dao.workspace.IWorkspaceCollaboratorDAO;
import edu.asu.spring.quadriga.dao.workspace.IWorkspaceDAO;
import edu.asu.spring.quadriga.dao.workspace.impl.WorkspaceDAO;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceCollaborator;
import edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTO;
import edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTOPK;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.workspace.IWorkspaceDeepMapper;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.impl.CollaboratorManager;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceCollaboratorManager;

@Service
public class WorkspaceCollaboratorManager extends CollaboratorManager<WorkspaceCollaboratorDTO, WorkspaceCollaboratorDTOPK, WorkspaceDTO, WorkspaceDAO> implements IWorkspaceCollaboratorManager 
{
	
	@Autowired
	private IWorkspaceDAO workspaceDao;
	
	@Autowired
	private IWorkspaceCollaboratorDAO wsCollabDao;
	
	@Autowired
	private IQuadrigaRoleManager roleManager;

	@Autowired
	private IWorkspaceDeepMapper workspaceDeepMapper;
	
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
    public ICollaboratorDAO<WorkspaceCollaboratorDTO> getCollaboratorDao() {
        return wsCollabDao;
    }
    
    /**
	 * This method returns the collaborators list for a workspace
	 * @param workspaceId
	 * @return List<ICollaborator>
	 * @throws QuadrigaStorageException
	 * @author kiranbatna
	 */
	@Override
	@Transactional
	public List<IWorkspaceCollaborator> getWorkspaceCollaborators(String workspaceId) throws QuadrigaStorageException
	{
		List<IWorkspaceCollaborator> workspaceCollaboratorList = null;
		IQuadrigaRole role;
		List<IQuadrigaRole> roleList;

		WorkspaceDTO workspaceDTO  = workspaceDao.getDTO(workspaceId);
        IWorkSpace workspace = workspaceDeepMapper.mapWorkspaceDTO(workspaceDTO);
		//retrieve the collaborators associated with project
		if(workspace != null){
			workspaceCollaboratorList = workspace.getWorkspaceCollaborators();
		}

		//map the collaborators to UI XML values
		if(workspaceCollaboratorList != null){
			for (IWorkspaceCollaborator workspaceCollaborator : workspaceCollaboratorList) 
			{
				roleList = new ArrayList<IQuadrigaRole>();
				if(workspaceCollaborator.getCollaborator()!=null && workspaceCollaborator.getCollaborator().getCollaboratorRoles() != null){
					for (IQuadrigaRole collaboratorRole : workspaceCollaborator.getCollaborator().getCollaboratorRoles()) {
						role = roleManager.getQuadrigaRoleByDbId(IQuadrigaRoleManager.WORKSPACE_ROLES, collaboratorRole.getDBid());
						roleList.add(role);
					}
				}
				workspaceCollaborator.getCollaborator().setCollaboratorRoles(roleList);
			}
		}
		return workspaceCollaboratorList;
	}
}
