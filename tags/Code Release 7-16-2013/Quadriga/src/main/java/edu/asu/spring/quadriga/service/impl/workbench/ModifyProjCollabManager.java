package edu.asu.spring.quadriga.service.impl.workbench;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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
	@Qualifier("DBConnectionModifyProjCollabManagerBean")
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
	public String addCollaboratorRequest(ICollaborator collaborator, String projectid,String userName) throws QuadrigaStorageException
	{
		String errmsg;
		
		errmsg = dbConnect.addCollaboratorRequest(collaborator, projectid,userName);
		
		return errmsg;
	}

	@Override
	public String deleteCollaboratorRequest(String userName, String projectid) throws QuadrigaStorageException {
		
		String errmsg = dbConnect.deleteColloratorRequest(userName, projectid);
		
		return errmsg;
	}
}
