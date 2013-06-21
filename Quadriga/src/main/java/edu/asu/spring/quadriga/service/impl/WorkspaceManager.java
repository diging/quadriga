package edu.asu.spring.quadriga.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import edu.asu.spring.quadriga.db.IDBConnectionWorkspaceManager;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IWorkspaceManager;

/**
 * @description  WorkspaceManager class implementing the workspace
 *               functionality
 */
public class WorkspaceManager implements IWorkspaceManager 
{

	@Autowired
	@Qualifier("DBConnectionWorkspaceManagerBean")
	private IDBConnectionWorkspaceManager dbConnect;
	
	/**
	 * @description    : This calls dbConnectionWorkspaceManager to insert a 
	 *                   workspace record into database.
	 * @param          : workSpace
	 * @return         : errmsg - blank on success and error message on failure
	 * @throws         : QuadrigaStorageException
	 */
	@Override
	public String addNewWorkspace(IWorkSpace workSpace) throws QuadrigaStorageException
	{
		String errmsg;
		
		try
		{
		  errmsg = dbConnect.addWorkSpaceRequest(workSpace);
		}
		catch(QuadrigaStorageException e)
		{
			throw new QuadrigaStorageException(e.getMessage());
		}
		return errmsg;
	}
}
