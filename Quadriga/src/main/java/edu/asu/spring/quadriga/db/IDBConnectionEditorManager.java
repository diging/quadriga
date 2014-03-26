package edu.asu.spring.quadriga.db;

import java.util.List;

import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.dto.NetworksAnnotationsDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.web.network.INetworkStatus;

/**
 * This interface has all the DB related functionality for Network Editor Management.
 *   
 * @author Lohith Dwaraka
 *
 */
public interface IDBConnectionEditorManager {

	/**
	 * This method should help in getting {@link List} of {@link INetwork} which is available for editing for a {@link IUser}.
	 * @param user									{@link IUser} object is usually the login user
	 * @return										returns {@link List} of {@link INetwork}
	 * @throws QuadrigaStorageException				Throws Storage exception when there is a issue while getting any data from database
	 */
	public abstract List<INetwork> getEditorNetworkList(IUser user)
			throws QuadrigaStorageException;

	/**
	 * This method should help in assigning a network to a {@link IUser} based on {@link INetwork} ID, {@link INetwork} Name and {@link INetwork} version. 
	 * @param networkId										{@link INetwork} ID of type {@link String}.
	 * @param user											{@link IUser} object
	 * @param networkName									{@link INetwork} name of type {@link String}
	 * @param version										{@link INetwork} version of type {@link String}
	 * @return												Returns success message.
	 * @throws QuadrigaStorageException						Throws Storage exception when there is a issue while getting any data from database
	 */
	public abstract String assignNetworkToUser(String networkId, IUser user,String networkName,int version)
			throws QuadrigaStorageException;


	/**
	 * This method should help in changing the status of the network in the tbl_network table. 
	 * @param networkId										ID of the {@link INetwork}
	 * @param status										Status can be REJECT/APPEND/APPROVE/PENDING. Please use {@link INetworkStatus} to assign status value.
	 * @return												Returns the success/failure status of the method
	 * @throws QuadrigaStorageException						Throws Storage exception when there is a issue while getting any data from database
	 */
	public abstract String updateNetworkStatus(String networkId, String status)
			throws QuadrigaStorageException;

	/**
	 * This method is used to change the status of the network in the tbl_network_assigned table. 
	 * @param networkId										{@link INetwork} ID of type String
	 * @param status										Status can be REJECT/APPEND/APPROVE/PENDING. Please use {@link INetworkStatus} to assign status value.
	 * @return												Returns the success/failure status of the method
	 * @throws QuadrigaStorageException						Throws Storage exception when there is a issue while getting any data from database
	 */
	public abstract String updateAssignedNetworkStatus(String networkId, String status,int latestVersion)
			throws QuadrigaStorageException;

	/**
	 * This method would help editors to add annotation to a network.
	 * @param annotationType 			Type of annotation , added to edge/node.
	 * @param networkId					ID of the {@link INetwork} editor requests for
	 * @param nodeId					ID of the node.
	 * @param edgeId					ID of the edge.
	 * @param nodeName					Name of the selected node by the editor.
	 * @param annotationText			Annotation text entered by the editor
	 * @param userId					{@link IUser} id of the editor
	 * @param objectType				Object type of selected node by editor. Type can be derived from Subject, Object or Predicate.
	 * @return							Returns the success/failure status of the method
	 * @throws QuadrigaStorageException
	 */
	public abstract String addAnnotationToNetwork(String annotatonType,String networkId, String nodeId,String edgeId,String nodeName,
			String annotationText, String userId,String objectType)
			throws QuadrigaStorageException;
	
	/**
	 * This methods should helps in getting all annotations of the network based on the node type, node id, user id and network id
	 * @param type											Type can be derived from Subject, Object or Predicate.
	 * @param id											Target Node ID  
	 * @param userid										{@link IUser} id of type String
	 * @return												Returns a {@link List} of {@link NetworksAnnotationsDTO} 
	 * @throws QuadrigaStorageException						Throws Storage exception when there is a issue while getting any data from database
	 */
	public abstract List<NetworksAnnotationsDTO> getAnnotationByNodeType(String type, String id,String userid,String networkId) throws QuadrigaStorageException;
	
	/**
	 * This methods should helps in getting all annotations of the network based on the edge Id.
	 * @param type						Type can be derived from Subject, Object or Predicate.
	 * @param id						ID can be edge id in the network.
	 * @param userid					{@link IUser} id of the editor
	 * @return							Returns a {@link List} of {@link NetworksAnnotationsDTO} 
	 * @throws QuadrigaStorageException
	 */
	public abstract List<NetworksAnnotationsDTO> getAnnotationByEdgeId(String id,String userid,String networkId) throws QuadrigaStorageException;
	
	/**
	 * This method should help in updating annotation of node based on the annotation ID.
	 * 
	 * @param annotationId									Annotation ID of the annotation belonging to some node in the network
	 * @param annotationText								Changed Annotation text of type String
	 * @return												Returns the success/failure status of the method
	 * @throws QuadrigaStorageException						Throws Storage exception when there is a issue while getting any data from database
	 */
	public abstract String updateAnnotationToNetwork(String annotationId,String annotationText ) throws QuadrigaStorageException;

	/**
	 * This method should get all the {@link INetwork} of the {@link IUser} based on the status of the network.
	 * 
	 * @param user											{@link IUser} object
	 * @param networkStatus									Status can be REJECT/APPEND/APPROVE/PENDING. Please use {@link INetworkStatus} to assign status value.
	 * @return												Returns the {@link List} of {@link INetwork}
	 * @throws QuadrigaStorageException						Throws Storage exception when there is a issue while getting any data from database
	 */
	public abstract List<INetwork> getNetworksOfUser(IUser user, String networkStatus) throws QuadrigaStorageException;

	/**
	 * This method should get all the {@link INetwork} which is being assigned  of the other users based on {@link List} of status of the network.
	 * 
	 * @param user											{@link IUser} object
	 * @param networkStatus									{@link List} of {@link INetwork} status, status can be REJECT/APPEND/APPROVE/PENDING. Please use {@link INetworkStatus} to assign status value.
	 * @return												Returns the {@link List} of {@link INetwork}	
	 * @throws QuadrigaStorageException						Throws Storage exception when there is a issue while getting any data from database
	 */
	public abstract List<INetwork> getNetworkListOfOtherEditors(IUser user,	List<String> networkStatus) throws QuadrigaStorageException;

	/**
	 * This methods should help in getting all annotations of the network based {@link INetwork} ID
	 * @param userId										{@link IUser} id of the editor of type String
	 * @param networkId										ID of the {@link INetwork} of String
	 * @return												Returns {@link List} of {@link NetworksAnnotationsDTO}
	 * @throws QuadrigaStorageException						Throws Storage exception when there is a issue while getting any data from database
	 */
	List<NetworksAnnotationsDTO> getAllAnnotationOfNetwork(String userId,
			String networkId) throws QuadrigaStorageException;


}