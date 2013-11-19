package edu.asu.spring.quadriga.service.impl.workbench;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.workbench.IModifyProjectCollaboratorDAO;
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
     private IModifyProjectCollaboratorDAO projectCollaboratorManager;
	
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
		
		projectCollaboratorManager.addCollaboratorRequest(collaborator, projectid, userName);
		
	}

	@Override
	@Transactional
	public void deleteCollaboratorRequest(String userName, String projectid) throws QuadrigaStorageException {
		
		projectCollaboratorManager.deleteColloratorRequest(userName, projectid);
	}
	
	@Override
	@Transactional
	public void updateCollaboratorRequest(String projectid,String collabUser,String collaboratorRole,String username) throws QuadrigaStorageException
	{
		projectCollaboratorManager.updateCollaboratorRequest(projectid, collabUser, collaboratorRole, username);
	}
}
