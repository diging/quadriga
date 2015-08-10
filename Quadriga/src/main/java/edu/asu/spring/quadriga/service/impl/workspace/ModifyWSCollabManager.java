package edu.asu.spring.quadriga.service.impl.workspace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.workspace.IWorkspaceCollaboratorDAO;
import edu.asu.spring.quadriga.dao.workspace.IWorkspaceDAO;
import edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTO;
import edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTOPK;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IModifyWSCollabManager;

@Service
public class ModifyWSCollabManager implements IModifyWSCollabManager 
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
            String userName = wsCollabDto.getWorkspaceCollaboratorDTOPK().getCollaboratoruser();
            if(collaborators.contains(userName)) {
                iterator.remove();
                wsCollabDao.deleteWorkspaceCollaboratorDTO(wsCollabDto);
            }
        }
        
        workspaceDao.updateDTO(wsDTO);
	}
	
	@Override
	@Transactional
	public void updateWorkspaceCollaborator(String workspaceId,String collabUser,String collaboratorRole,String userName) throws QuadrigaStorageException
	{
	    WorkspaceDTO workspace = workspaceDao.getWorkspaceDTO(workspaceId);
        if (workspace == null)
            return;
        
        List<WorkspaceCollaboratorDTO> collaboratorDtoList = workspace.getWorkspaceCollaboratorDTOList();
        List<String> collaboratorRoles = Arrays.asList(collaboratorRole.split(","));
        List<String> existingRoles = new ArrayList<String>();
        
        //delete all the roles associated with the user
        Iterator<WorkspaceCollaboratorDTO> iterator = collaboratorDtoList.iterator();
        while(iterator.hasNext())
        {
            WorkspaceCollaboratorDTO collaborator = iterator.next();
            WorkspaceCollaboratorDTOPK collaboratorPK = collaborator.getWorkspaceCollaboratorDTOPK();
            
            String wsCollaborator = collaborator.getQuadrigaUserDTO().getUsername();
            String wsCollabRole = collaboratorPK.getCollaboratorrole();
            if(wsCollaborator.equals(collabUser)) {
                if(!collaboratorRoles.contains(wsCollabRole)) {
                    iterator.remove();
                    wsCollabDao.deleteWorkspaceCollaboratorDTO(collaborator);
                } else {
                    existingRoles.add(wsCollabRole);
                }
            }
        }

        //add the user with new roles
        //create a collaboratorDTO with the given details
        for(String value : collaboratorRoles)
        {
            if(!existingRoles.contains(value)) {
                WorkspaceCollaboratorDTO collaborator = createWorkspaceCollaborator(
                        collabUser, userName, workspace, value);
                collaboratorDtoList.add(collaborator);
            }
        }
        
        workspaceDao.updateDTO(workspace);
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
        workspaceCollaborator.setWorkspaceCollaboratorDTOPK(collaboratorPK);
        workspaceCollaborator.setQuadrigaUserDTO(workspaceDao.getUserDTO(collaborator));
        workspaceCollaborator.setCreatedby(userName);
        workspaceCollaborator.setCreateddate(new Date());
        workspaceCollaborator.setUpdatedby(userName);
        workspaceCollaborator.setUpdateddate(new Date());
        return workspaceCollaborator;
    }
}
