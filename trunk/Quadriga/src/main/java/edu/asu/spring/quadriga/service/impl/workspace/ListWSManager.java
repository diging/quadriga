package edu.asu.spring.quadriga.service.impl.workspace;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.workspace.IDBConnectionListWSManager;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;

/**
 * Class implements {@link IListWSManager} to
 * display the active,archived and deactivated workspace associated with project.
 * @implements IListWSManager
 * @author Kiran Kumar Batna
 */
@Service
public class ListWSManager implements IListWSManager 
{

	@Autowired
	@Qualifier("DBConnectionListWSManagerBean")
	private IDBConnectionListWSManager dbConnect;
	
	/**
	 * This will list all the active workspaces associated
	 * with the project.
	 * @param    projectid
	 * @return   List<IWorkSpace> - list of active workspaces associated 
	 *           with the project.
	 * @throws   QuadrigaStorageException
	 * @author   Kiran Kumar Batna
	 */
	@Override
	public List<IWorkSpace> listActiveWorkspace(String projectid) throws QuadrigaStorageException
	{
		List<IWorkSpace> workspaceList;
		
		workspaceList = dbConnect.listActiveWorkspace(projectid);

		return workspaceList;
	}
	
	/**
	 * This will list all the archived workspaces associated
	 * with the project.
	 * @param    projectid
	 * @return   List<IWorkSpace> - list of archived workspaces associated 
	 *           with the project.
	 * @throws   QuadrigaStorageException
	 * @author   Kiran Kumar Batna
	 */
	@Override
	public List<IWorkSpace> listArchivedWorkspace(String projectid) throws QuadrigaStorageException
	{
		List<IWorkSpace> workspaceList;
		
		workspaceList = dbConnect.listArchivedWorkspace(projectid);

		return workspaceList;
	}
	
	/**
	 * This will list all the deactivated workspaces associated
	 * with the project.
	 * @param    projectid
	 * @return   List<IWorkSpace> - list of archived workspaces associated 
	 *           with the project.
	 * @throws   QuadrigaStorageException
	 * @author   Kiran Kumar Batna
	 */
	@Override
	public List<IWorkSpace> listDeactivatedWorkspace(String projectid) throws QuadrigaStorageException
	{
		List<IWorkSpace> workspaceList;
		
		workspaceList = dbConnect.listDeactivatedWorkspace(projectid);

		return workspaceList;
	}
	
	/**
	 *This method display the workspace details for the workspace submitted.
	 * @param   workspaceId
	 * @return  IWorkSpace - workspace object
	 * @throws  QuadrigaStorageException
	 * @author  Kiran Kumar Batna
	 */
	@Override
	public IWorkSpace getWorkspaceDetails(String workspaceId) throws QuadrigaStorageException
	{
		IWorkSpace workspace;
		workspace = dbConnect.getWorkspaceDetails(workspaceId);
		return workspace;
	}
}
