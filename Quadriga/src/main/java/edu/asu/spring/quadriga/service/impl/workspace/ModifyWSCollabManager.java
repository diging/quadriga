package edu.asu.spring.quadriga.service.impl.workspace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.workspace.IDBConnectionModifyWSCollabManager;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IModifyWSCollabManager;

@Service
public class ModifyWSCollabManager implements IModifyWSCollabManager 
{
	
	@Autowired
	IDBConnectionModifyWSCollabManager workspaceCollaboratorDAO;
	
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
		workspaceCollaboratorDAO.addWorkspaceCollaborator(collaborator, collabRoleList, workspaceid, userName);
	}
	
	/**
	 * This method deletes the collaborator associated to a workspace
	 * @param collaborator - collaborator user name
	 * @param workspaceid - associate workspace
	 * @throws QuadrigaStorageException
	 * @author kiranbatna
	 */
	@Override
	@Transactional
	public void deleteWorkspaceCollaborator(String collaborator,String workspaceid) throws QuadrigaStorageException
	{
		workspaceCollaboratorDAO.deleteWorkspaceCollaborator(collaborator, workspaceid);
	}
	
	@Override
	@Transactional
	public void updateWorkspaceCollaborator(String workspaceId,String collabUser,String collaboratorRole,String userName) throws QuadrigaStorageException
	{
		workspaceCollaboratorDAO.updateWorkspaceCollaborator(workspaceId, collabUser, collaboratorRole, userName);
	}
}
