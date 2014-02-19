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
	 * Creates a new Network ID  and assigns owner to network object, Adds a network into the Workspace. 
	 * So Vogon can access this network from its Workspace
	 * @param networkName
	 * @param user
	 * @param workspaceid
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract String addNetworkRequest(String networkName, IUser user, String workspaceid)
			throws QuadrigaStorageException;
	
	/**
	 * Add Network statements (like AE, RE ) associated to networks.
	 * This method would also add more info of the network statement like top node, type of node.
	 * Also adds all the statements in the XML for further use cases.
	 * At present only the statements with isTop = 1 are been used for displaying networks on UI.
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


	/**
	 * Get {@link INetwork} object of the User using the network ID.
	 * This object would have info of project and workspace the network belongs to.
	 * Used for showing the network in the UI.
	 * @param networkid
	 * @param user
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract INetwork getNetwork(String networkid, IUser user) throws QuadrigaStorageException;

	/**
	 * This would give the list of {@link INetwork} belonging the {@link IUser}.
	 * Mainly used to display list of Networks and project and workspace it belong to, in the UI. 
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
	 * Archive the network statements when network is re-uploaded after the network is rejected by admin.
	 * The network statement has isarchived field as metadata to understand whether network is archived.
	 * isarchived = 0 is network is active
	 * isarchived = 1 is network is archived as is most recently archived
	 * isarchived = 2 is network is archived and never used again. kept in DB for future references.
	 * @param networkId
	 * @param id
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract String archiveNetworkStatement(String networkId, String id)
			throws QuadrigaStorageException;

	/**
	 * Get all the network statements {@link INetworkNodeInfo} for a network.
	 * Function can get Network statements with isTop =1, 
	 * means only the top node ID so we can fetch the whole Node from QStore.
	 * Used to access Relation event or Appellation events from QStore.
	 * We can use this method for visualize network or edit network purpose.
	 * 
	 * @param networkId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract List<INetworkNodeInfo> getNetworkNodes(String networkId)
			throws QuadrigaStorageException;

	/**
	 * This method should be called when network is reuploaded after admin has rejected the network.
	 * Archive the network, would mark the network statements with isarchived =1 or isarchived=2.
	 * isarchived = 1 is network is archived as is most recently archived
	 * isarchived = 2 is network is archived and never used again. kept in DB for future references.
	 * @param networkId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract String archiveNetwork(String networkId) throws QuadrigaStorageException;

	
	/**
	 * Update network name, when vogon tries to re-upload the network with a different name
	 * This method could be called if User's network has been rejected and user prefers to store the network with an alternative name (Like version name) 
	 * @param networkId
	 * @param networkName
	 * @return
	 * @throws QuadrigaStorageException
	 */
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

	/**
	 * Get Networks of old versions.
	 * Network with isarchive =1 and isarchive =2 are retrived.
	 * @param networkId
	 * @param archiveLevel
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract List<INetworkOldVersion> getNetworkVersions(String networkId, int archiveLevel) throws QuadrigaStorageException;
	
}