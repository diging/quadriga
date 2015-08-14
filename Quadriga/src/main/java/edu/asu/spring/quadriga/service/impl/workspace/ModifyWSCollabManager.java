package edu.asu.spring.quadriga.service.impl.workspace;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dao.impl.workspace.WorkspaceDAO;
import edu.asu.spring.quadriga.dao.workspace.IWorkspaceCollaboratorDAO;
import edu.asu.spring.quadriga.dao.workspace.IWorkspaceDAO;
import edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTO;
import edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTOPK;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.impl.CollaboratorManager;
import edu.asu.spring.quadriga.service.workspace.IModifyWSCollabManager;

@Service
public class ModifyWSCollabManager extends CollaboratorManager<WorkspaceCollaboratorDTO, WorkspaceCollaboratorDTOPK, WorkspaceDTO, WorkspaceDAO> implements IModifyWSCollabManager 
{
	
	@Autowired
	private IWorkspaceDAO workspaceDao;
	
	@Autowired
	private IWorkspaceCollaboratorDAO wsCollabDao;
	
	
	/**
	 * This method adds the collaborator to a workspace
	 * @param collaborator - collaborator user name
	 * @param collabRoleList - collaborator roles
	 * @param workspaceid - associate workspace
	 * @param userName - logged in user name
	 * @throws QuadrigaStorageException
	 * @author kiranbatna
	 */
	@Override
	@Transactional
	public void addWorkspaceCollaborator(String collaborator,String collabRoleList,String workspaceid,String userName) throws QuadrigaStorageException
	{
	    WorkspaceDTO wsDTO = workspaceDao.getWorkspaceDTO(workspaceid);
	    if (wsDTO == null)
	        return;
	    
	    List<WorkspaceCollaboratorDTO> collaboratorList = wsDTO.getWorkspaceCollaboratorDTOList();
        List<String> collabRoles = Arrays.asList(collabRoleList.split(","));
                
	    for(String role: collabRoles) {
	        WorkspaceCollaboratorDTO workspaceCollaborator = createWorkspaceCollaborator(
                    collaborator, userName, wsDTO, role);
            collaboratorList.add(workspaceCollaborator);
        }
	    
	    wsDTO.setWorkspaceCollaboratorDTOList(collaboratorList);
        workspaceDao.updateDTO(wsDTO);
	    
	}
	
	/**
	 * This method deletes the given collaborators from a workspace.
	 * @param collaboratorListAsString - collaborator user name
	 * @param workspaceid - associate workspace
	 * @throws QuadrigaStorageException
	 * @author kiranbatna, Julia Damerow
	 */
	@Override
	@Transactional
	public void deleteWorkspaceCollaborator(String collaboratorListAsString,String workspaceid) throws QuadrigaStorageException
	{
	    WorkspaceDTO wsDTO = workspaceDao.getWorkspaceDTO(workspaceid);
        if (wsDTO == null)
            return;
        
        List<WorkspaceCollaboratorDTO> collaboratorDtoList = wsDTO.getWorkspaceCollaboratorDTOList();
        if (collaboratorDtoList == null)
            return;
        
        List<String> collaborators = Arrays.asList(collaboratorListAsString.split(","));
        
	    Iterator<WorkspaceCollaboratorDTO> iterator = collaboratorDtoList.iterator();
        while(iterator.hasNext()) {
            WorkspaceCollaboratorDTO wsCollabDto = iterator.next();
            String userName = wsCollabDto.getCollaboratorDTOPK().getCollaboratoruser();
            if(collaborators.contains(userName)) {
                iterator.remove();
                wsCollabDao.deleteWorkspaceCollaboratorDTO(wsCollabDto);
            }
        }
        
        workspaceDao.updateDTO(wsDTO);
	}
	
	/*
	 * Private Methods
	 */
	
	/**
	 * Method to create a new workspace collaborator DTO object.
	 * @param collaborator Username of collaborator
	 * @param workspaceid  Id of workspace of the new collaborator.
	 * @param userName Username of user who added a new collaborator.
	 * @param wsDTO    Workspace to which the new collaborator is added to.
	 * @param role     Role of the new collaborator.
	 * @return a newly created {@link WorkspaceCollaboratorDTO} object
	 */
	private WorkspaceCollaboratorDTO createWorkspaceCollaborator(
            String collaborator, String userName,
            WorkspaceDTO wsDTO, String role) {
        WorkspaceCollaboratorDTO workspaceCollaborator = new WorkspaceCollaboratorDTO();
        WorkspaceCollaboratorDTOPK collaboratorPK = new WorkspaceCollaboratorDTOPK(wsDTO.getWorkspaceid(), collaborator, role);
        workspaceCollaborator.setWorkspaceDTO(wsDTO);
        workspaceCollaborator.setCollaboratorDTOPK(collaboratorPK);
        workspaceCollaborator.setQuadrigaUserDTO(workspaceDao.getUserDTO(collaborator));
        workspaceCollaborator.setCreatedby(userName);
        workspaceCollaborator.setCreateddate(new Date());
        workspaceCollaborator.setUpdatedby(userName);
        workspaceCollaborator.setUpdateddate(new Date());
        return workspaceCollaborator;
    }

    @Override
    public WorkspaceCollaboratorDTO createNewDTO() {
        return new WorkspaceCollaboratorDTO();
    }

    @Override
    public WorkspaceCollaboratorDTOPK createNewDTOPK(String id,
            String collabUser, String role) {
       return new WorkspaceCollaboratorDTOPK();
    }

    @Override
    public IBaseDAO<WorkspaceDTO> getDao() {
        return workspaceDao; 
    }
}
