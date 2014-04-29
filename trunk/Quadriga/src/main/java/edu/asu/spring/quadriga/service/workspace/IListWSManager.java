package edu.asu.spring.quadriga.service.workspace;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.codehaus.jettison.json.JSONException;

import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.dspace.service.IDspaceKeys;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IListWSManager {

	public abstract List<IWorkSpace> listDeactivatedWorkspace(String projectid,String user)
			throws QuadrigaStorageException;

	public abstract List<IWorkSpace> listArchivedWorkspace(String projectid,String user)
			throws QuadrigaStorageException;

	public abstract List<IWorkSpace> listActiveWorkspace(String projectid,String user)
			throws QuadrigaStorageException;

	public abstract IWorkSpace getWorkspaceDetails(String workspaceId, String username)
			throws QuadrigaStorageException, QuadrigaAccessException;

	public abstract List<IWorkSpace> listWorkspace(String projectid,String user)
			throws QuadrigaStorageException;

	public abstract List<INetwork> getWorkspaceNetworkList(String workspaceid)
			throws QuadrigaStorageException;

	public abstract String getWorkspaceName(String workspaceId) throws QuadrigaStorageException;

	public abstract List<INetwork> getWorkspaceRejectedNetworkList(String workspaceid)
			throws QuadrigaStorageException;
	
	public abstract List<INetwork> getWorkspaceApprovedNetworkList(String workspaceid)
			throws QuadrigaStorageException;

	public abstract List<IWorkSpace> listActiveWorkspaceByCollaborator(String projectid,
			String user) throws QuadrigaStorageException;

	List<IWorkSpace> listWorkspaceOfCollaborator(String projectid, String user)
			throws QuadrigaStorageException;

	IWorkSpace getWorkspaceDetails(String workspaceId)
			throws QuadrigaStorageException, QuadrigaAccessException;

	String getItemMetadataAsJson(String fileid, String dspaceUsername,
			String dspacePassword, IDspaceKeys dspaceKeys)
			throws NoSuchAlgorithmException, QuadrigaStorageException,
			JSONException;

	
}
