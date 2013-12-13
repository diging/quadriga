package edu.asu.spring.quadriga.db;

import java.util.List;

import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.INetworkNodeInfo;
import edu.asu.spring.quadriga.domain.INetworkOldVersion;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionNetworkManager {


	/**
	 * Add a new network into workspace
	 * @param networkName
	 * @param user
	 * @param workspaceid
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract String addNetworkRequest(String networkName, IUser user, String workspaceid)
			throws QuadrigaStorageException;
	
	/**
	 * Add Network statements (like AE, RE ) associated to networks
	 * @param networkId
	 * @param id
	 * @param type
	 * @param isTop
	 * @param user
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract String addNetworkStatement(String networkId, String id, String type,
			String isTop, IUser user) throws QuadrigaStorageException;

	/**
	 * Get network status for vogon
	 * @param networkName
	 * @param user
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract INetwork getNetworkStatus(String networkName, IUser user)
			throws QuadrigaStorageException;

	/**
	 * Get network list for a User
	 * @param user
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract List<INetwork> getNetworkList(IUser user) throws QuadrigaStorageException;

	/**
	 * Get the project id for a workspace id
	 * @param workspaceid
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract String getProjectIdForWorkspaceId(String workspaceid)
			throws QuadrigaStorageException;

	/**
	 * Check if the network name is already used
	 * @param networkName
	 * @param user
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract boolean hasNetworkName(String networkName,IUser user) throws QuadrigaStorageException;

	/**
	 * Get the Relation events and appellation event in the outer part
	 * @param networkId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract List<INetworkNodeInfo> getNetworkTopNodes(String networkId)
			throws QuadrigaStorageException;

	/**
	 * Archive the network statements when rejected and reuploaded networks
	 * @param networkId
	 * @param id
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract String archiveNetworkStatement(String networkId, String id)
			throws QuadrigaStorageException;

	/**
	 * Get all the network statements for a network
	 * @param networkId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract List<INetworkNodeInfo> getAllNetworkNodes(String networkId)
			throws QuadrigaStorageException;

	/**
	 * Archive the network
	 * @param networkId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract String archiveNetwork(String networkId) throws QuadrigaStorageException;

	/**
	 * Get the previous version on network
	 * @param networkId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract INetworkOldVersion getNetworkOldVersionDetails(String networkId)
			throws QuadrigaStorageException;

	/**
	 * Get the top nodes of previous version of networks
	 * @param networkId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract List<INetworkNodeInfo> getNetworkOldVersionTopNodes(String networkId)
			throws QuadrigaStorageException;

	/**
	 * Get network details based on network id
	 * @param networkId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract INetwork getNetworkDetails(String networkId)
			throws QuadrigaStorageException;
}