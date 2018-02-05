package edu.asu.spring.quadriga.dao;

import java.util.List;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.network.INetworkNodeInfo;
import edu.asu.spring.quadriga.dto.NetworkStatementsDTO;
import edu.asu.spring.quadriga.dto.NetworksDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface INetworkDAO {


	/**
	 * Add a new network into workspace
	 * Creates a new Network ID  and assigns owner to network object, Adds a network into the Workspace. 
	 * So Vogon can access this network from its Workspace
	 * @param networkName 		Name for the newly added network
	 * @param user				Owner of the network
	 * @param workspaceid		Workspace the network would be assigned
	 * @return					return success/error message
	 * @throws QuadrigaStorageException
	 */
	public abstract String addNetwork(String networkName, IUser user, String workspaceid, String networkStatus, String externalUserId)
			throws QuadrigaStorageException;
	
	/**
	 * Add Network statements (like AE, RE ) associated to networks.
	 * This method would also add more info of the network statement like top node, type of node.
	 * Also adds all the statements in the XML for further use cases.
	 * At present only the statements with isTop = 1 are been used for displaying networks on UI.
	 * @param networkId			ID of network
	 * @param id				ID of network statement
	 * @param type				Type of network statement, AE - Appellation event, RE - Relation event
	 * @param isTop				{@link Boolean} To whether the network statement is in starting point xml. 
	 * @param user				Owner of the network
	 * @return					return success/error message
	 * @throws QuadrigaStorageException
	 */
	public abstract String addNetworkStatement(String rowid,String networkId, String id, String type,
			int isTop, IUser user, int version) throws QuadrigaStorageException;


	/**
	 * Get {@link INetwork} object of the User using the network ID.
	 * This object would have info of project and workspace the network belongs to.
	 * Used for showing the network in the UI.
	 * @param networkid			ID of network
	 * @param user				Owner of the network
	 * @return					return {@link INetwork} object associated to the networkid, user
	 * @throws QuadrigaStorageException
	 */
	//public abstract INetwork getNetwork(String networkid) throws QuadrigaStorageException;

	/**
	 * This would give the list of {@link INetwork} belonging the {@link IUser}.
	 * Mainly used to display list of Networks and project and workspace it belong to, in the UI. 
	 * @param user				Owner of the network
	 * @return					returns {@link List} of {@link INetwork} belonging to owner
	 * @throws QuadrigaStorageException
	 */
	public abstract List<NetworksDTO> getNetworkList(IUser user) throws QuadrigaStorageException;
	
	
	/**
	 * This would give the list of {@link INetwork} that are approved.
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract List<NetworksDTO> getApprovedNetworkList() throws QuadrigaStorageException;


	/**
	 * Get all the network statements {@link INetworkNodeInfo} for a network.
	 * Function can get Network statements with isTop =1, 
	 * means only the top node ID so we can fetch the whole Node from QStore.
	 * Used to access Relation event or Appellation events from QStore.
	 * We can use this method for visualize network or edit network purpose.
	 * 
	 * @param networkId			ID of network
	 * @return					returns {@link List} of {@link INetworkNodeInfo}	
	 * @throws QuadrigaStorageException
	 */
	public abstract List<NetworkStatementsDTO> getNetworkNodes(String networkId,int versionId)
			throws QuadrigaStorageException;
	
	/**
	 * Update network name, when vogon tries to re-upload the network with a different name
	 * This method could be called if User's network has been rejected and user prefers to store the network with an alternative name (Like version name) 
	 * @param networkId			ID of network
	 * @param networkName		New name for the network
	 * @return					returns success/error messages
	 * @throws QuadrigaStorageException
	 */
	public abstract String updateNetworkName(String networkId,String networkName) throws QuadrigaStorageException;

	/**
	 * Get Networks of old versions.
	 * @param networkId					ID of network
	 * @return							returns {@link List} of {@link INetwork}
	 * @throws QuadrigaStorageException
	 */

	List<INetwork> getAllNetworkVersions(String networkId) 
			throws QuadrigaStorageException;

	List<Integer> getLatestVersionOfNetwork(String networkID)
			throws QuadrigaStorageException;
	
	/*List<INetwork> getNetworkOfOwner(IUser user) 
			throws QuadrigaStorageException;*/

	NetworksDTO getNetworksDTO(String networkId)
			throws QuadrigaStorageException;

	List<NetworksDTO> getNetworkDTOList(String projectid)
			throws QuadrigaStorageException;
	
	public abstract List<NetworksDTO> getNetworkWithStatement(List<String> statementIds) throws QuadrigaStorageException;

    public abstract List<NetworkStatementsDTO> getNetworkNodes(List<String> statementIds) throws QuadrigaStorageException;

}