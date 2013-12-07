package edu.asu.spring.quadriga.db.workspace;

import java.util.List;

import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionListWSManager {

	public abstract List<IWorkSpace> listDeactivatedWorkspace(String projectid,String user)
			throws QuadrigaStorageException;

	public abstract List<IWorkSpace> listArchivedWorkspace(String projectid,String user)
			throws QuadrigaStorageException;

	public abstract List<IWorkSpace> listActiveWorkspace(String projectid,String user,String spName)
			throws QuadrigaStorageException;

	public abstract IWorkSpace getWorkspaceDetails(String workspaceId,String user)
			throws QuadrigaStorageException;

	public abstract List<IWorkSpace> listWorkspace(String projectid,String user)
			throws QuadrigaStorageException;

	public abstract List<IBitStream> getBitStreams(String workspaceId, String username)
			throws QuadrigaStorageException, QuadrigaAccessException;

	public abstract List<INetwork> getWorkspaceNetworkList(String workspaceid)
			throws QuadrigaStorageException;

	public abstract String getWorkspaceName(String workspaceId) throws QuadrigaStorageException;

	public abstract List<INetwork> getWorkspaceRejectedNetworkList(String workspaceid)
			throws QuadrigaStorageException;

	public abstract List<IWorkSpace> listWorkspaceOfCollaborator(String projectid, String user)
			throws QuadrigaStorageException;

}
