package edu.asu.spring.quadriga.dao;

import java.util.List;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.dto.NetworkAnnotationsDTO;
import edu.asu.spring.quadriga.dto.NetworkEdgeAnnotationsDTO;
import edu.asu.spring.quadriga.dto.NetworkNodeAnnotationsDTO;
import edu.asu.spring.quadriga.dto.NetworkRelationAnnotationsDTO;
import edu.asu.spring.quadriga.dto.NetworksDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.web.network.INetworkStatus;

/**
 * This interface has all the DB related functionality for Network Editor Management.
 *   
 * @author Lohith Dwaraka
 *
 */
public interface IEditorDAO {

	/**
	 * This method should help in getting {@link List} of {@link INetwork} which is available for editing for a {@link IUser}.
	 * @param user									{@link IUser} object is usually the login user
	 * @return										returns {@link List} of {@link INetwork}
	 * @throws QuadrigaStorageException				Throws Storage exception when there is a issue while getting any data from database
	 */
	public abstract List<NetworksDTO> getEditorNetworkList(IUser user)
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
	public abstract String addAnnotationToNetwork(String networkId,String nodeId,String nodeName,
			String annotationText, String objectType,String userId)
			throws QuadrigaStorageException;
	
	/**
	 * This methods should helps in getting all annotations of the network based on the node type, node id, user id and network id
	 * @param type											Type can be derived from Subject, Object or Predicate.
	 * @param id											Target Node ID  
	 * @param userid										{@link IUser} id of type String
	 * @return												Returns a {@link List} of {@link NetworksAnnotationsDTO} 
	 * @throws QuadrigaStorageException						Throws Storage exception when there is a issue while getting any data from database
	 */
	public abstract List<NetworkNodeAnnotationsDTO> getAnnotationByNodeType(String type, String id,String networkId) throws QuadrigaStorageException;
	
	/**
	 * This methods should helps in getting all annotations of the network based on the edge Id.
	 * @param type						Type can be derived from Subject, Object or Predicate.
	 * @param id						ID can be edge id in the network.
	 * @param userid					{@link IUser} id of the editor
	 * @return							Returns a {@link List} of {@link NetworksAnnotationsDTO} 
	 * @throws QuadrigaStorageException
	 */
	public abstract List<NetworkEdgeAnnotationsDTO> getAnnotationByEdgeId(String sourceId,String targetId,String userid,String networkId) throws QuadrigaStorageException;
	
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
	//public abstract List<INetwork> getNetworksOfUser(IUser user, String networkStatus) throws QuadrigaStorageException;

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
	public abstract List<NetworkAnnotationsDTO> getAllAnnotationOfNetwork(String userId,
			String networkId) throws QuadrigaStorageException;
             
	public abstract String addAnnotationToEdge(String networkId, String sourceId, String targetId,
			String sourceName, String targetName, String annotationText,String userId, String objectType, String targetType) throws QuadrigaStorageException;

	/**
	 * This method adds an annotation to a relation in network.
	 * @param annotationText     The annotated text
	 * @param networkId          Id of the network for which the relations are annotated.
	 * @param predicateId        Id of predicate of a relation
	 * @param predicateName      Name of predicate of a relation
	 * @param subjectId          Id of subject of a relation
	 * @param subjectName        Name of subject of a relation
	 * @param objectId           Id of object of a relation
	 * @param objectName         Name of object of a relation
	 * @param userName           Name of user who annotated the relation
	 * @param annotedObjectType  The type of object that is annotated by editor.
	 * @throws QuadrigaStorageException  Any database exception
	 * @author kiran batna
	 */
	public abstract void addAnnotationToRelation(String annotationText, String networkId,
			String predicateId, String predicateName, String subjectId, String subjectName, String objectId,
			String objectName, String userName, String annotedObjectType)
			throws QuadrigaStorageException;

	/**
	 * This method retrieves the annotations associated with the relation.
	 * @param networkId     Id of the network the relation belongs.
	 * @param subjectId     Id of the subject in the annotated relation
	 * @param objectId      Id of the object in the annotated relation.
	 * @param predicateId   Id of the predicate in the annotated relation.
	 * @param userName      name of the editor.
	 * @return List<NetworkRelationAnnotationsDTO> List of {@link NetworkRelationAnnotations} objects containing the annotations for the given relation 
	 * @throws QuadrigaStorageException Any database exception is redirected to the custom defined database error exception message.
	 */
	public abstract List<NetworkRelationAnnotationsDTO> getAnnotationToRelation(String networkId,String subjectId, String objectId,
			String predicateId, String userName) throws QuadrigaStorageException;

	public abstract void addAnnotationToNode(String annotationText, String networkId,
			String nodeId, String nodeName, String userName, String annotedObjectType)
			throws QuadrigaStorageException;

	List<NetworksDTO> getNetworksOfUserWithStatus(IUser user, String networkStatus)
			throws QuadrigaStorageException;


}