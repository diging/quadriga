package edu.asu.spring.quadriga.service.impl.workbench;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.workbench.IModifyProjectManagerDAO;
import edu.asu.spring.quadriga.db.workbench.IDBConnectionModifyProjectManager;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.workbench.IModifyProjectManager;

/**
 * This class add/update/delete a project
 * @author kbatna
 */
@Service
public class ModifyProjectManager implements IModifyProjectManager 
{

	@Autowired
	@Qualifier("DBConnectionModifyProjectManagerBean")
	private IDBConnectionModifyProjectManager dbConnect;
	
	@Autowired
	private IModifyProjectManagerDAO modifyProjectManagerDAO;
	
	@Autowired
	private IUserManager userManager;
	
	private static final Logger logger = LoggerFactory.getLogger(ModifyProjectManager.class);
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void addProjectRequest(IProject project, String userName) throws QuadrigaStorageException
	{
		logger.info("Adding project details");
		modifyProjectManagerDAO.addProjectRequest(project,userName);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void updateProjectRequest(String projID, String projName,String projDesc,String projAccess, String unixName,String userName) throws QuadrigaStorageException
	{
		logger.info("Updating project details");
		//dbConnect.updateProjectRequest(project, userName);
		modifyProjectManagerDAO.updateProjectRequest(projID, projName, projDesc, projAccess, unixName, userName);
	}
	
	/**
	 * This method deletes a project into the database.
	 * @param project
	 * @return String - error message blank on success and contains error on failure.
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	@Override
	@Transactional
	public void deleteProjectRequest(String projectIdList) throws QuadrigaStorageException
	{
		logger.info("Deleting project details");
		modifyProjectManagerDAO.deleteProjectRequest(projectIdList);
	}
	
	/**
	 * This method transfers the project ownership to another user
	 * @param projectId
	 * @param oldOwner
	 * @param newOwner
	 * @param collabRole
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	@Override
	@Transactional
	public void transferProjectOwnerRequest(String projectId,String oldOwner,String newOwner,String collabRole) throws QuadrigaStorageException
	{
		modifyProjectManagerDAO.transferProjectOwnerRequest(projectId, oldOwner, newOwner, collabRole);
	}
	
	/**
	 * This method will provide owner editor role to the project 
	 * @param projectId
	 * @param owner
	 * @throws QuadrigaStorageException
	 * @author Lohith Dwaraka
	 */
	@Override
	@Transactional
	public String assignEditorToOwner(String projectId, String owner) throws QuadrigaStorageException{
		String errmsg = modifyProjectManagerDAO.assignProjectOwnerEditor(projectId, owner);
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
	@Transactional
	public String deleteEditorToOwner(String projectId, String owner) throws QuadrigaStorageException{
		String errmsg = modifyProjectManagerDAO.deleteProjectOwnerEditor(projectId, owner);
		return errmsg;
	}
}
