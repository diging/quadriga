package edu.asu.spring.quadriga.service;

import java.util.List;

import org.codehaus.jettison.json.JSONException;

import edu.asu.spring.quadriga.dao.impl.NetworkDAO;
import edu.asu.spring.quadriga.domain.network.INetworkAnnotation;
import edu.asu.spring.quadriga.dto.NetworkAnnotationsDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IEditingNetworkAnnotationManager {

	public abstract List<NetworkAnnotationsDTO> getAnnotation(String type, String id,String networkId)
			throws QuadrigaStorageException;
	public abstract List<NetworkAnnotationsDTO> getAnnotationOfEdge(String id, String userid,String networkId)
			throws QuadrigaStorageException;

	public abstract String addAnnotationToNetwork(String networkId,String nodeId,
			String nodeName, String annotationText, String userId,
			String objectType) throws QuadrigaStorageException;

	public abstract List<INetworkAnnotation> getAllAnnotationOfNetwork(String userId,
			String networkId) throws QuadrigaStorageException;
	public abstract String addAnnotationToEdge( String networkId,
			String sourceId, String targetId, String sourceName,
			String targetName, String annotationText, String userId,
			String objectType, String targetType)
			throws QuadrigaStorageException;
	public abstract List<NetworkAnnotationsDTO> getAnnotationOfEdge(String sourceId,
			String targetId, String userId, String networkId)
			throws QuadrigaStorageException;
	
	/**
	 * This method calls {@link NetworkDAO} to add an annotation to a relation in network.
	 * @param annotationText       Annotated text submitted by editor.
	 * @param networkId            Id of the network for which the relation contains.
	 * @param predicateId          Id of the predicate in the annotated relation.
	 * @param predicateName        Name of the predicate in the annotated relation
	 * @param subjectId            Id of subject in the annotated relation
	 * @param subjectName          Name of the subject in the annotated relation.
	 * @param objectId             Id of object in the annotated relation.
	 * @param objectName           Name of the object in the annotated relation.
	 * @param userName             Name of the editor who annotated the relation.
	 * @param annotedObjectType    Type of annotated object. Here it is relation type.
	 * @throws QuadrigaStorageException  Any database exception.
	 */
	public abstract void addAnnotationToRelation(String annotationText, String networkId,
			String predicateId, String predicateName, String subjectId, String subjectName, String objectId,
			String objectName, String userName, String annotedObjectType)
			throws QuadrigaStorageException;
	
	/**
	 * This method calls the {@link NetworkDAO} layer method  to retrieve
	 * the annotations associated with the relation.
	 * @param subjectId     Id of the subject in the annotated relation
	 * @param objectId      Id of the object in the annotated relation.
	 * @param predicateId   Id of the predicate in the annotated relation.
	 * @param userName      name of the editor.
	 * @return List<NetworkRelationAnnotationsDTO> List of {@link NetworkRelationAnnotations} objects containing the annotations for the given relation 
	 * @throws QuadrigaStorageException Any database exception is redirected to the custom defined database error exception message.
	 */
	public abstract List<NetworkAnnotationsDTO> getAnnotationToRelation(String networkId,String subjectId, String objectId,
			String predicateId, String userName) throws QuadrigaStorageException;
	
	public abstract String getAllAnnotationOfNetworkAsJson(String username, String networkId)
			throws QuadrigaStorageException, JSONException;

}