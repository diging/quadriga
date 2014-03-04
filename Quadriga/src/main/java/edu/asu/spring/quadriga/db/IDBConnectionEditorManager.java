package edu.asu.spring.quadriga.db;

import java.util.List;

import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.dto.NetworksAnnotationsDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionEditorManager {

	/**
	 * Get networks which editor can assign to him/her 
	 * @param user
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract List<INetwork> getEditorNetworkList(IUser user)
			throws QuadrigaStorageException;

	/**
	 * Assign a network to an editor
	 * @param networkId
	 * @param user
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract String assignNetworkToUser(String networkId, IUser user)
			throws QuadrigaStorageException;
	/**
	 * Update the network status based on REJECT/APPEND/APPROVE
	 * @param networkId
	 * @param status
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract String updateNetworkStatus(String networkId, String status)
			throws QuadrigaStorageException;

	/**
	 * update the network status 
	 * @param networkId
	 * @param status
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract String updateAssignedNetworkStatus(String networkId, String status)
			throws QuadrigaStorageException;

	/**
	 * Add annotation to a network
	 * @param networkId
	 * @param nodeName
	 * @param annotationText
	 * @param userId
	 * @param objectType
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract String addAnnotationToNetwork(String networkId, String nodeName,
			String annotationText, String userId,String objectType)
			throws QuadrigaStorageException;
	
	/**
	 * Get all annotations of the network 
	 * @param type
	 * @param id
	 * @param userid
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract List<NetworksAnnotationsDTO> getAnnotation(String type, String id,String userid,String networkId) throws QuadrigaStorageException;
	
	/**
	 * Update annotation of networks
	 * @param annotationId
	 * @param annotationText
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract String updateAnnotationToNetwork(String annotationId,String annotationText ) throws QuadrigaStorageException;

	public abstract List<INetwork> getNetworksOfUser(IUser user, String networkStatus) throws QuadrigaStorageException;

	public abstract List<INetwork> getNetworkListOfOtherEditors(IUser user,	List<String> networkStatus) throws QuadrigaStorageException;

	List<NetworksAnnotationsDTO> getAllAnnotationOfNetwork(String userId,
			String networkId) throws QuadrigaStorageException;


}