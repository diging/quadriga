package edu.asu.spring.quadriga.service.impl.workspace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.workspace.IDBConnectionModifyWSCollabManager;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IModifyWSCollabManager;

@Service
public class ModifyWSCollabManager implements IModifyWSCollabManager 
{
	
	@Autowired
	@Qualifier("DBConnectionModifyWSCollabManagerBean")
	IDBConnectionModifyWSCollabManager dbConnect;
	
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
	public void addWorkspaceCollaborator(String collaborator,String collabRoleList,String workspaceid,String userName) throws QuadrigaStorageException
	{
		dbConnect.addWorkspaceCollaborator(collaborator, collabRoleList, workspaceid, userName);
	}

}
