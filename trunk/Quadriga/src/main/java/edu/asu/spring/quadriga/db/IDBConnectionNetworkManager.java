package edu.asu.spring.quadriga.db;

import java.util.List;

import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.INetworkNodeInfo;
import edu.asu.spring.quadriga.domain.INetworkOldVersion;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionNetworkManager {


	public abstract String addNetworkRequest(String networkName, IUser user, String workspaceid)
			throws QuadrigaStorageException;

	public abstract String addNetworkStatement(String networkId, String id, String type,
			String isTop, IUser user) throws QuadrigaStorageException;

	public abstract INetwork getNetworkStatus(String networkName, IUser user)
			throws QuadrigaStorageException;

	public abstract List<INetwork> getNetworkList(IUser user) throws QuadrigaStorageException;

	public abstract String getProjectIdForWorkspaceId(String workspaceid)
			throws QuadrigaStorageException;

	public abstract boolean hasNetworkName(String networkName,IUser user) throws QuadrigaStorageException;

	public abstract List<INetworkNodeInfo> getNetworkTopNodes(String networkId)
			throws QuadrigaStorageException;

	public abstract String archiveNetworkStatement(String networkId, String id)
			throws QuadrigaStorageException;

	public abstract List<INetworkNodeInfo> getAllNetworkNodes(String networkId)
			throws QuadrigaStorageException;

	public abstract String archiveNetwork(String networkId) throws QuadrigaStorageException;

	public abstract INetworkOldVersion getNetworkOldVersionDetails(String networkId)
			throws QuadrigaStorageException;

	public abstract List<INetworkNodeInfo> getNetworkOldVersionTopNodes(String networkId)
			throws QuadrigaStorageException;

	public abstract INetwork getNetworkDetails(String networkId)
			throws QuadrigaStorageException;
}