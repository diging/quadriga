package edu.asu.spring.quadriga.service.impl.workspace;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.db.workspace.IDBConnectionListWSManager;
import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
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

	private static final Logger logger = LoggerFactory.getLogger(ListWSManager.class);
	
	@Autowired
	private IDBConnectionListWSManager dbConnect;
	
	/**
	 * This will list all the workspaces associated
	 * with the project.
	 * @param    projectid
	 * @return   List<IWorkSpace> - list of workspaces associated 
	 *           with the project.
	 * @throws   QuadrigaStorageException
	 * @author   Kiran Kumar Batna
	 */
	@Override
	@Transactional
	public List<IWorkSpace> listWorkspace(String projectid,String user) throws QuadrigaStorageException
	{
		List<IWorkSpace> workspaceList;
		workspaceList = dbConnect.listWorkspace(projectid,user);
		return workspaceList;
	}
	
	@Override
	@Transactional
	public List<IWorkSpace> listWorkspaceOfCollaborator(String projectid,String user) throws QuadrigaStorageException
	{
		List<IWorkSpace> workspaceList;
		workspaceList = dbConnect.listWorkspaceOfCollaborator(projectid, user);
		return workspaceList;
	}
	
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
	@Transactional
	public List<IWorkSpace> listActiveWorkspace(String projectid,String user) throws QuadrigaStorageException
	{
		List<IWorkSpace> ownerWorkspaceList;
		ownerWorkspaceList = dbConnect.listActiveWorkspaceOfOwner(projectid, user);
		return ownerWorkspaceList;
	}
	
	@Override
	@Transactional
	public List<IWorkSpace> listActiveWorkspaceByCollaborator(String projectid,String user) throws QuadrigaStorageException
	{
		List<IWorkSpace> collaboratorWorkspaceList;
		collaboratorWorkspaceList = dbConnect.listActiveWorkspaceOfCollaborator(projectid, user);
		return collaboratorWorkspaceList;
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
	@Transactional
	public List<IWorkSpace> listArchivedWorkspace(String projectid,String user) throws QuadrigaStorageException
	{
		List<IWorkSpace> workspaceList;
		workspaceList = dbConnect.listArchivedWorkspace(projectid,user);
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
	@Transactional
	public List<IWorkSpace> listDeactivatedWorkspace(String projectid,String user) throws QuadrigaStorageException
	{
		List<IWorkSpace> workspaceList;
		workspaceList = dbConnect.listDeactivatedWorkspace(projectid, user);
		return workspaceList;
	}
	
	/**
	 *This method display the workspace details for the workspace submitted.
	 * @param   workspaceId
	 * @return  IWorkSpace - workspace object
	 * @throws  QuadrigaStorageException
	 * @author  Kiran Kumar Batna
	 * @throws QuadrigaAccessException 
	 */
	@Override
	@Transactional
	public IWorkSpace getWorkspaceDetails(String workspaceId, String username) throws QuadrigaStorageException, QuadrigaAccessException
	{
		IWorkSpace workspace;
		workspace = dbConnect.getWorkspaceDetails(workspaceId,username);
		if(workspace!=null){
			workspace.setBitstreams(dbConnect.getBitStreams(workspaceId, username));
		}
		return workspace;
	}
	
	
	/**
	 * This method get the workspace name for the workspace id.
	 * @param   workspaceId
	 * @return  workspacename - String object
	 * @throws  QuadrigaStorageException
	 * @author  Lohith Dwaraka
	 * @throws QuadrigaAccessException 
	 */
	@Override
	@Transactional
	public String getWorkspaceName(String workspaceId) throws QuadrigaStorageException
	{
		String workspacename;
		workspacename = dbConnect.getWorkspaceName(workspaceId);
		return workspacename;
	}
	
	@Override
	@Transactional
	public List<INetwork> getWorkspaceNetworkList(String workspaceid)
			throws QuadrigaStorageException{
		
		List<INetwork> networkList=null;
		try{
			networkList=dbConnect.getWorkspaceNetworkList(workspaceid);
		}catch(QuadrigaStorageException e){
			logger.error("",e);
		}
		return networkList;
	}
	
	@Override
	@Transactional
	public List<INetwork> getWorkspaceRejectedNetworkList(String workspaceid)
			throws QuadrigaStorageException{
		
		List<INetwork> networkList=null;
		try{
			networkList=dbConnect.getWorkspaceRejectedNetworkList(workspaceid);
		}catch(QuadrigaStorageException e){
			logger.error("",e);
		}
		return networkList;
	}
}
