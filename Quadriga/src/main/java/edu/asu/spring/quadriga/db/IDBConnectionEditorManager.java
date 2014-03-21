package edu.asu.spring.quadriga.db;

import java.util.List;

import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.dto.NetworksAnnotationsDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.web.network.INetworkStatus;

/**
 * This interface has all the DB related functionality for Network Editor Management.
 * We can have functionalities covering different state of network when the Editor has assigned the network himself.
 *   
 * @author Lohith Dwaraka
 *
 */
public interface IDBConnectionEditorManager {

	/**
	 * This method should help in getting list of networks which is available to editor for editing.
	 * We need to consider the editor role of the {@link IUser} with respect any project or workspace. 
	 * Based on the roles {@link IUser} has possessed, we need to get all the networks belonging to through resource ( {@link IProject} / {@link IWorkspace} 
	 * @param user				{@link IUser} is usually the logged in user
	 * @return					{@link List} of {@link INetwork} as the output
	 * @throws QuadrigaStorageException
	 */
	public abstract List<INetwork> getEditorNetworkList(IUser user)
			throws QuadrigaStorageException;

	/**
	 * This methods is used when Editor requests to assign the {@link INetwork} to him to work on verify and edit the Network if necessary. 
	 * Check for Editor permission during implementation.
	 * 
	 * @param networkId				ID of the {@link INetwork} editor requests for
	 * @param user					{@link IUser} object of the logged in Editor
	 * @return						Returns the success/failure status of the method
	 * @throws QuadrigaStorageException
	 */
	public abstract String assignNetworkToUser(String networkId, IUser user,String networkName)
			throws QuadrigaStorageException;
	/**
	 * This method is used to change the status of the network. 
	 * Different states of the network can have value -- REJECT/APPEND/APPROVE.
	 * 
	 * @param networkId					ID of the {@link INetwork} editor requests for
	 * @param status					Status can be REJECT/APPEND/APPROVE. Please use {@link INetworkStatus} to assign status value.
	 * @return							Returns the success/failure status of the method
	 * @throws QuadrigaStorageException
	 */
	public abstract String updateNetworkStatus(String networkId, String status)
			throws QuadrigaStorageException;

	/**
	 * This method is used to change the status of the network. 
	 * Different states of the network can have value -- REJECT/APPEND/APPROVE.
	 * Works on mostly the Archived network
	 * 
	 * @param networkId					ID of the {@link INetwork} editor requests for
	 * @param status					Status can be REJECT/APPEND/APPROVE. Please use {@link INetworkStatus} to assign status value.
	 * @return							Returns the success/failure status of the method
	 * @throws QuadrigaStorageException
	 */
	public abstract String updateAssignedNetworkStatus(String networkId, String status)
			throws QuadrigaStorageException;

	/**
	 * This method would help editors to add annotation to a network.
	 * @param networkId					ID of the {@link INetwork} editor requests for
	 * @param nodeName					Name of the selected node by the editor.
	 * @param annotationText			Annotation text entered by the editor
	 * @param userId					{@link IUser} id of the editor
	 * @param objectType				Object type of selected node by editor. Type can be derived from Subject, Object or Predicate.
	 * @return							Returns the success/failure status of the method
	 * @throws QuadrigaStorageException
	 */
	public abstract String addAnnotationToNetwork(String networkId, String nodeId,String nodeName,
			String annotationText, String userId,String objectType)
			throws QuadrigaStorageException;
	
	/**
	 * This methods should helps in getting all annotations of the network based on the object type
	 * @param type						Type can be derived from Subject, Object or Predicate.
	 * @param id						ID can be node id in the network.
	 * @param userid					{@link IUser} id of the editor
	 * @return							Returns a {@link List} of {@link NetworksAnnotationsDTO} 
	 * @throws QuadrigaStorageException
	 */
	public abstract List<NetworksAnnotationsDTO> getAnnotationByNodeType(String type, String id,String userid,String networkId) throws QuadrigaStorageException;
	
	/**
	 * This methods help in updating annotation of networks based on the annotation ID.
	 * 
	 * @param annotationId				Annotation ID of the annotation belonging some node in the network
	 * @param annotationText			Changed Annotation text 
	 * @return							Returns the success/failure status of the method
	 * @throws QuadrigaStorageException
	 */
	public abstract String updateAnnotationToNetwork(String annotationId,String annotationText ) throws QuadrigaStorageException;

	/**
	 * This method should get all the {@link INetwork} of the {@link IUser} based on the status of the network.
	 * Status can be REJECT/APPEND/APPROVE. Please use {@link INetworkStatus} to assign status value.
	 * 
	 * @param user						{@link IUser} id of the owner
	 * @param networkStatus				Status can be REJECT/APPEND/APPROVE. Please use {@link INetworkStatus} to assign status value.
	 * @return							Returns the {@link List} of {@link INetwork}
	 * @throws QuadrigaStorageException
	 */
	public abstract List<INetwork> getNetworksOfUser(IUser user, String networkStatus) throws QuadrigaStorageException;

	/**
	 * This method should get all the {@link INetwork} of the other users( Assigned to other {@link IUser} based on {@link List} of status of the network.
	 * 
	 * 
	 * @param user						{@link IUser} object of the logged in user
	 * @param networkStatus				List of INetwork status, status can be REJECT/APPEND/APPROVE. Please use {@link INetworkStatus} to assign status value.
	 * @return							Returns the {@link List} of {@link INetwork}	
	 * @throws QuadrigaStorageException
	 */
	public abstract List<INetwork> getNetworkListOfOtherEditors(IUser user,	List<String> networkStatus) throws QuadrigaStorageException;

	/**
	 * This methods should helps in getting all annotations of the network based {@link INetwork} ID
	 * @param userId					{@link IUser} id of the editor
	 * @param networkId					ID of the {@link INetwork} editor requests for
	 * @return
	 * @throws QuadrigaStorageException
	 */
	List<NetworksAnnotationsDTO> getAllAnnotationOfNetwork(String userId,
			String networkId) throws QuadrigaStorageException;


}