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
	public void addProjectRequest(IProject project) throws QuadrigaStorageException
	{
		
		dbConnect.addProjectRequest(project);
		
	}
	
	/**
	 * This method updates a project into the database.
	 * @param project
	 * @return String - error message blank on success and contains error on failure.
	 * @throws QuadrigaStorageException
	 * @author Kiran Kumar Batna
	 */
	@Override
	public void updateProjectRequest(IProject project,String userName) throws QuadrigaStorageException
	{
		
		dbConnect.updateProjectRequest(project, userName);
	}
	
	/**
	 * This method deletes a project into the database.
	 * @param project
	 * @return String - error message blank on success and contains error on failure.
	 * @throws QuadrigaStorageException
	 * @author Kiran Kumar Batna
	 */
	@Override
	public void deleteProjectRequest(String projectIdList) throws QuadrigaStorageException
	{
		dbConnect.deleteProjectRequest(projectIdList);
		
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
	
	/**
	 * This method will provide owner editor role to the project 
	 * @param projectId
	 * @param owner
	 * @throws QuadrigaStorageException
	 * @author Lohith Dwaraka
	 */
	@Override
	public String assignEditorToOwner(String projectId, String owner) throws QuadrigaStorageException{
		String errmsg = dbConnect.assignProjectOwnerEditor(projectId, owner);
		return errmsg;
	}
	
	/**
	 * This method will remove owner editor role from the project 
	 * @param projectId
	 * @param owner
	 * @throws QuadrigaStorageException
	 * @author Lohith Dwaraka
	 */
	@Override
	public String deleteEditorToOwner(String projectId, String owner) throws QuadrigaStorageException{
		String errmsg = dbConnect.deleteProjectOwnerEditor(projectId, owner);
		return errmsg;
	}
}