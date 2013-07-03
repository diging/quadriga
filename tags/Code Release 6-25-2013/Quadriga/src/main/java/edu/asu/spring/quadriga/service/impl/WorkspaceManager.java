package edu.asu.spring.quadriga.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.IDBConnectionWorkspaceManager;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IWorkspaceManager;

/**
 * @description  WorkspaceManager class implementing the workspace
 *               functionality
 */
@Service
public class WorkspaceManager implements IWorkspaceManager 
{

	@Autowired
	@Qualifier("DBConnectionWorkspaceManagerBean")
	private IDBConnectionWorkspaceManager dbConnect;
	
	/**
	 * @description   : This will list all the workspaces associated
	 *                  with the project.
	 * @param         : projectid
	 * @return        : List<IWorkSpace> - list of workspaces associated 
	 *                  with the project.
	 * @throws        : QuadrigaStorageException
	 * @author        : Kiran Kumar Batna
	 */
	@Override
	public List<IWorkSpace> listWorkspace(int projectid,int archive,int active) throws QuadrigaStorageException
	{
		List<IWorkSpace> workspaceList;
		
	    try
	    {
		workspaceList = dbConnect.listWorkspace(projectid,archive,active);
	    }
	    catch(QuadrigaStorageException e)
	    {
	    	throw new QuadrigaStorageException(e.getMessage());
	    }
		return workspaceList;
	}
	
	/**
	 * @description    : This calls dbConnectionWorkspaceManager to insert a 
	 *                   workspace record into database.
	 * @param          : workSpace
	 * @return         : errmsg - blank on success and error message on failure
	 * @throws         : QuadrigaStorageException
	 */
	@Override
	public String addNewWorkspace(IWorkSpace workSpace,int projectId) throws QuadrigaStorageException
	{
		String errmsg;
		
		try
		{
		  errmsg = dbConnect.addWorkSpaceRequest(workSpace,projectId);
		}
		catch(QuadrigaStorageException e)
		{
			throw new QuadrigaStorageException(e.getMessage());
		}
		return errmsg;
	}
	
	/**
	 * @description  : This method calls DBConnectionWorkspaceManager to archive 
	 *                 requested workspace.
	 * @param        : workspaceIdList
	 * @param        : archive
	 * @param        : wsUser
	 * @return       : String - errmsg blank on success and error message on failure
	 * @throws       : QuadrigaStorageException
	 */
	@Override
	public String archiveWorkspace(String workspaceIdList,int archive,String wsUser) throws QuadrigaStorageException
	{
		String errmsg;
		
		try
		{
			errmsg = dbConnect.archiveWorkspaceRequest(workspaceIdList, archive, wsUser);
		}
		catch (QuadrigaStorageException e) {
			throw new QuadrigaStorageException(e.getMessage());
		}
		return errmsg;
	}
	
	/**
	 * @description  : This method calls DBConnectionWorkspaceManager to deactivate 
	 *                 requested workspace.
	 * @param        : workspaceIdList
	 * @param        : archive
	 * @param        : wsUser
	 * @return       : String - errmsg blank on success and error message on failure
	 * @throws       : QuadrigaStorageException
	 */
	@Override
	public String deactivateWorkspace(String workspaceIdList,int deactivate,String wsUser) throws QuadrigaStorageException
	{
		String errmsg;
		
		try
		{
			errmsg = dbConnect.deactivateWorkspace(workspaceIdList, deactivate, wsUser);
		}
		catch (QuadrigaStorageException e) {
			throw new QuadrigaStorageException(e.getMessage());
		}
		return errmsg;
	}
	
	/**
	 * @description  : This method calls DBConnectionWorkspaceManager to delete 
	 *                 requested workspace.
	 * @param        : workspaceIdList
	 * @return       : String - errmsg blank on success and error message on failure
	 * @throws       : QuadrigaStorageException
	 */
	@Override
	public String deleteWorkspace(String workspaceIdList) throws QuadrigaStorageException
	{
		String errmsg;
		
		try
		{
			errmsg = dbConnect.deleteWorkspaceRequest(workspaceIdList);
		}
		catch (QuadrigaStorageException e) {
			throw new QuadrigaStorageException(e.getMessage());
		}
		return errmsg;
	}
	
	/**
	 * @description  : This method display the workspace details.
	 * @param        : workspaceId
	 * @return       : IWorkSpace - workspace object
	 * @throws       : QuadrigaStorageException
	 * @author       : Kiran Kumar Batna
	 */
	@Override
	public IWorkSpace getWorkspaceDetails(long workspaceId) throws QuadrigaStorageException
	{
		IWorkSpace workspace ;
		
		workspace = dbConnect.getWorkspaceDetails(workspaceId);
		
		return workspace;
		
	}
}
