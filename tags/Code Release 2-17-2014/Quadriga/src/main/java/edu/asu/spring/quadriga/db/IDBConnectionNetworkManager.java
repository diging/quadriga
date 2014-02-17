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
	public abstract String addNetworkStatement(String rowid,String networkId, String id, String type,
			String isTop, IUser user) throws QuadrigaStorageException;


	public abstract INetwork getNetwork(String networkid, IUser user) throws QuadrigaStorageException;

	/**
	 * Get network list for a User
	 * @param user
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract List<INetwork> getNetworkList(IUser user) throws QuadrigaStorageException;

	/**
	 * Check if the network name is already used
	 * @param networkName
	 * @param user
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract boolean hasNetworkName(String networkName,IUser user) throws QuadrigaStorageException;

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
	public abstract List<INetworkNodeInfo> getNetworkNodes(String networkId)
			throws QuadrigaStorageException;

	/**
	 * Archive the network
	 * @param networkId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract String archiveNetwork(String networkId) throws QuadrigaStorageException;

	

	public abstract String updateNetworkName(String networkId,String networkName) throws QuadrigaStorageException;

	/**
	 * Get the list of networks associated with a project id. If the project id is null or the project does not contain any
	 * workspaces or networks then the return will be null.
	 * 
	 * @param projectid						The id of the project in which you need to find the list of networks.
	 * @author Ram Kumar Kumaresan
	 * @return								List of networks belonging to the given project id.
	 * @throws QuadrigaStorageException Exception will be thrown when the input paramets do not satisfy the system/database constraints or due to database connection troubles.
	 */
	public abstract List<INetwork> getNetworks(String projectid) throws QuadrigaStorageException;

	public abstract List<INetworkOldVersion> getNetworkVersions(String networkId, int archiveLevel) throws QuadrigaStorageException;
	
}