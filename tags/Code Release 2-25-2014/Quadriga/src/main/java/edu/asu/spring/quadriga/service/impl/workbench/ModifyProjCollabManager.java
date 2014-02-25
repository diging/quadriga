package edu.asu.spring.quadriga.service.impl.workbench;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.db.workbench.IDBConnectionModifyProjCollabManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IModifyProjCollabManager;

//add
//update
//delete
@Service
public class ModifyProjCollabManager implements IModifyProjCollabManager 
{
     @Autowired
     private IDBConnectionModifyProjCollabManager dbConnect;
	
     /**
	 * This method adds a collaborator for the project supplied.
	 * @param collaborator
	 * @param projectid
	 * @return String - error message blank on success and error on failure.
	 * @throws QuadrigaStorageException
	 * @author rohit pendbhaje
	 */
	@Override
	@Transactional
	public void addCollaboratorRequest(ICollaborator collaborator, String projectid,String userName) throws QuadrigaStorageException
	{
		
		dbConnect.addCollaboratorRequest(collaborator, projectid, userName);
		
	}

	/**
	 * This method deletes the collaborator associated with the project
	 * @param userName - collaborator user name
	 * @param projectid - project id
	 * @throws QuarigaStorageException
	 */
	@Override
	@Transactional
	public void deleteCollaboratorRequest(String userName, String projectid) throws QuadrigaStorageException {
		dbConnect.deleteColloratorRequest(userName, projectid);
	}
	
	/**
	 * This method updates the roles associated with the collaborator.
	 * @param projectid - project id
	 * @param collabUser - collaborator user name
	 * @param collaboratorRole - roles associated with the collaborator.
	 * @param userName - logged in user
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public void updateCollaboratorRequest(String projectid,String collabUser,String collaboratorRole,String username) throws QuadrigaStorageException
	{
		dbConnect.updateCollaboratorRequest(projectid, collabUser, collaboratorRole, username);
	}
}
