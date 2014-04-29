package edu.asu.spring.quadriga.service.impl.workspace;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.db.workspace.IDBConnectionListWSManager;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceNetwork;
import edu.asu.spring.quadriga.dspace.service.IDspaceKeys;
import edu.asu.spring.quadriga.dspace.service.IDspaceManager;
import edu.asu.spring.quadriga.dspace.service.IDspaceMetadataItemEntity;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;
import edu.asu.spring.quadriga.service.workspace.mapper.IWorkspaceDeepMapper;
import edu.asu.spring.quadriga.service.workspace.mapper.IWorkspaceShallowMapper;
import edu.asu.spring.quadriga.web.network.INetworkStatus;

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
	
	@Autowired
	private IWorkspaceShallowMapper workspaceShallowMapper;
	
	@Autowired
	private IWorkspaceDeepMapper workspaceDeepMapper;
	
	@Autowired
	private IDspaceManager dspaceManager;
	
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
		workspaceList = workspaceShallowMapper.getWorkSpaceList(projectid, user);
		return workspaceList;
	}
	
	/**
	 * This method retrieves all the workspace associated with the given having the user as
	 * a collaborator.
	 * @param    projectid
	 * @return   List<IWorkSpace> - list of workspaces associated 
	 *           with the project.
	 * @throws   QuadrigaStorageException
	 * @author   Kiran Kumar Batna
	 */
	@Override
	@Transactional
	public List<IWorkSpace> listWorkspaceOfCollaborator(String projectid,String user) throws QuadrigaStorageException
	{
		List<IWorkSpace> workspaceList;
		workspaceList = workspaceShallowMapper.listWorkspaceOfCollaborator(projectid, user);
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
		ownerWorkspaceList = workspaceShallowMapper.listActiveWorkspaceOfOwner(projectid, user);
		return ownerWorkspaceList;
	}
	
	@Override
	@Transactional
	public List<IWorkSpace> listActiveWorkspaceByCollaborator(String projectid,String user) throws QuadrigaStorageException
	{
		List<IWorkSpace> collaboratorWorkspaceList;
		collaboratorWorkspaceList = workspaceShallowMapper.listActiveWorkspaceOfCollaborator(projectid, user);
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
		workspaceList = workspaceShallowMapper.listArchivedWorkspace(projectid,user);
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
		workspaceList = workspaceShallowMapper.listDeactivatedWorkspace(projectid, user);
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
		IWorkSpace workspace = workspaceDeepMapper.getWorkSpaceDetails(workspaceId,username);
		return workspace;
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
	public IWorkSpace getWorkspaceDetails(String workspaceId) throws QuadrigaStorageException, QuadrigaAccessException
	{
		IWorkSpace workspace = workspaceDeepMapper.getWorkSpaceDetails(workspaceId);
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
		workspacename = workspaceDeepMapper.getWorkSpaceDetails(workspaceId).getWorkspaceName();
		return workspacename;
	}
	
	@Override
	@Transactional
	public List<IWorkspaceNetwork> getWorkspaceNetworkList(String workspaceid)
			throws QuadrigaStorageException{
		
		IWorkSpace workspace = workspaceDeepMapper.getWorkSpaceDetails(workspaceid);
		List<IWorkspaceNetwork> workspaceNetworkList = null;
		if(workspace != null){
			 workspaceNetworkList =  workspace.getWorkspaceNetworks();
		}

		return workspaceNetworkList;
	}
	
	@Override
	@Transactional
	public List<IWorkspaceNetwork> getWorkspaceRejectedNetworkList(String workspaceid)
			throws QuadrigaStorageException{
		
		IWorkSpace workspace = workspaceDeepMapper.getWorkSpaceDetails(workspaceid);
		List<IWorkspaceNetwork> workspaceNetworkList=null;
		if(workspace != null){
			workspaceNetworkList = workspace.getWorkspaceNetworks();
		}
		List<Integer> removeList = null;
		if(workspaceNetworkList != null){
			for(int i=0;i<workspaceNetworkList.size();i++){
				IWorkspaceNetwork workspaceNetwork = workspaceNetworkList.get(i);
				INetwork network = workspaceNetwork.getNetwork();
				if(network!=null){
					if(network.getStatus().equals(INetworkStatus.REJECTED)){
						// do nothing
					}else{
						if(removeList == null){
							removeList = new ArrayList<Integer>();
						}
						removeList.add(i);
					}
				}else{
					if(removeList == null){
						removeList = new ArrayList<Integer>();
					}
					removeList.add(i);
				}
			}
			
			
			for(Integer i : removeList){
				workspaceNetworkList.remove(i);
			}
		}
			
		
		return workspaceNetworkList;
	}
	
	/**
	 * 
	 * {@inheritDoc}
	 * 
	 */
	@Override
	@Transactional
	public List<IWorkspaceNetwork> getWorkspaceApprovedNetworkList(String workspaceid)
			throws QuadrigaStorageException {
		IWorkSpace workspace = workspaceDeepMapper.getWorkSpaceDetails(workspaceid);
		List<IWorkspaceNetwork> workspaceNetworkList=null;
		if(workspace != null){
			workspaceNetworkList = workspace.getWorkspaceNetworks();
		}

		List<Integer> removeList = null;
		if(workspaceNetworkList != null){
			for(int i=0;i<workspaceNetworkList.size();i++){
				IWorkspaceNetwork workspaceNetwork = workspaceNetworkList.get(i);
				INetwork network = workspaceNetwork.getNetwork();
				if(network!=null){
					if(network.getStatus().equals(INetworkStatus.APPROVED)){
						// do nothing
					}else{
						if(removeList == null){
							removeList = new ArrayList<Integer>();
						}
						removeList.add(i);
					}
				}else{
					if(removeList == null){
						removeList = new ArrayList<Integer>();
					}
					removeList.add(i);
				}
			}
			
			
			for(Integer i : removeList){
				workspaceNetworkList.remove(i);
			}
		}
		return workspaceNetworkList;
	}
	
	@Override
	public String getItemMetadataAsJson(String fileid, String dspaceUsername, String dspacePassword, IDspaceKeys dspaceKeys) throws NoSuchAlgorithmException, QuadrigaStorageException, JSONException{
		
		IDspaceMetadataItemEntity metaData = dspaceManager.getItemMetadata(fileid, dspaceUsername, dspacePassword, dspaceKeys);
		
		String itemData = "";
		JSONArray ja = new JSONArray();
		JSONObject j = new JSONObject();
		
		j.put("filename", metaData.getName());
		j.put("modifieddate", metaData.getLastModifiedDate());
		j.put("submitter", metaData.getSubmitter().getFullname());
		
		ja.put(metaData);
		
		itemData = ja.toString();
		
		return itemData;
		
	}

}
