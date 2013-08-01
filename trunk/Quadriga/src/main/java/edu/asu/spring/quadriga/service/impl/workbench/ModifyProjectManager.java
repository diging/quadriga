package edu.asu.spring.quadriga.service.impl.workbench;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.workbench.IDBConnectionModifyProjectManager;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.workbench.IModifyProjectManager;

//add
//update
//delete
@Service
public class ModifyProjectManager implements IModifyProjectManager 
{

	@Autowired
	@Qualifier("DBConnectionModifyProjectManagerBean")
	private IDBConnectionModifyProjectManager dbConnect;
	
	@Autowired
	private IUserManager userManager;
	
	/**
	 * This method adds a project into the database.
	 * @param project
	 * @return String - error message blank on success and contains error on failure.
	 * @throws QuadrigaStorageException
	 * @author Kiran Kumar Batna
	 */
	@Override
	public String addProjectRequest(IProject project) throws QuadrigaStorageException
	{
		String errmsg;
		
		errmsg = dbConnect.addProjectRequest(project);
		
		return errmsg;
	}
	
	/**
	 * This method updates a project into the database.
	 * @param project
	 * @return String - error message blank on success and contains error on failure.
	 * @throws QuadrigaStorageException
	 * @author Kiran Kumar Batna
	 */
	@Override
	public String updateProjectRequest(IProject project,String userName) throws QuadrigaStorageException
	{
		String errmsg;
		
		errmsg = dbConnect.updateProjectRequest(project, userName);
		
		return errmsg;
	}
	
	/**
	 * This method deletes a project into the database.
	 * @param project
	 * @return String - error message blank on success and contains error on failure.
	 * @throws QuadrigaStorageException
	 * @author Kiran Kumar Batna
	 */
	@Override
	public String deleteProjectRequest(String projectIdList) throws QuadrigaStorageException
	{
		String errmsg;
		
		errmsg = dbConnect.deleteProjectRequest(projectIdList);
		
		return errmsg;
	}
	
	/**
	 * This method transfers the project ownership to another user
	 * @param projectId
	 * @param oldOwner
	 * @param newOwner
	 * @param collabRole
	 * @throws QuadrigaStorageException
	 * @author kiranbatna
	 */
	@Override
	public void transferProjectOwnerRequest(String projectId,String oldOwner,String newOwner,String collabRole) throws QuadrigaStorageException
	{
		dbConnect.transferProjectOwnerRequest(projectId, oldOwner, newOwner, collabRole);
		
	}
}
