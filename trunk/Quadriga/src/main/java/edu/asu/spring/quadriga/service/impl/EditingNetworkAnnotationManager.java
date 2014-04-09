package edu.asu.spring.quadriga.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.db.IDBConnectionEditorManager;
import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.INetworkAnnotation;
import edu.asu.spring.quadriga.domain.implementation.NetworkAnnotation;
import edu.asu.spring.quadriga.dto.NetworkAnnotationsDTO;
import edu.asu.spring.quadriga.dto.NetworkEdgeAnnotationsDTO;
import edu.asu.spring.quadriga.dto.NetworkNodeAnnotationsDTO;
import edu.asu.spring.quadriga.dto.NetworkRelationAnnotationsDTO;
import edu.asu.spring.quadriga.dto.NetworksAnnotationsDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.NetworkDTOMapper;
import edu.asu.spring.quadriga.service.IEditingNetworkAnnotationManager;

@Service
public class EditingNetworkAnnotationManager implements IEditingNetworkAnnotationManager {

	@Autowired
	IDBConnectionEditorManager dbConnectionEditManager;
	
	@Autowired
	private NetworkDTOMapper networkMapper;
	

	/**
	 * This method retrieves the annotation entered by the user for the network node.
	 * @param type : specifies it is a relation or an appellation
	 * @param id : it is the relation event id or the appelation id.
	 * @param userid : logged in user
	 * @param networkId : network id
	 * @return String[] - array of string containing the annotations entered by the user
	 */
	@Override
	@Transactional
	public List<NetworkAnnotationsDTO> getAnnotation(String type, String id,String userid,String networkId) throws QuadrigaStorageException{
		List<NetworkNodeAnnotationsDTO> networkNodeAnnotationsDTOList = null;
		networkNodeAnnotationsDTOList = dbConnectionEditManager.getAnnotationByNodeType(type, id, userid, networkId);
		List<NetworkAnnotationsDTO> networkAnnotationsDTOs = new ArrayList<NetworkAnnotationsDTO>();
		for(NetworkNodeAnnotationsDTO networkEdgeAnnotationsDTO : networkNodeAnnotationsDTOList){
			NetworkAnnotationsDTO annotationsDTO = networkEdgeAnnotationsDTO.getAnnotationNodes();
			networkAnnotationsDTOs.add(annotationsDTO);
		}
		return networkAnnotationsDTOs;
	}
	
	/**
	 * This method adds annotation to the node of a network
	 * @param annotationType - type of annotation ,added to edge/node
	 * @param networkId - network id
	 * @param nodeId - node id
	 * @param edgeId - edge id
	 * @param nodeName - node in the network for which annotation needs to be added
	 * @param annotationText - annotation text value
	 * @param userId - logged in user
	 * @param objectType
	 * @return String - message after adding the annotation to a node of network.
	 * @throws QuadrigaStorageException
	 */
	
	@Override
	@Transactional
	public String addAnnotationToNetwork(String networkId, String nodeId,String nodeName,
			String annotationText, String userId,String objectType)
			throws QuadrigaStorageException{
		String msg = dbConnectionEditManager.addAnnotationToNetwork(networkId, nodeId,nodeName,
				annotationText, userId,objectType);
		return msg;
	}
	

	/**
	 * This method updates the annotation associated to a node of network
	 * @param annotationId - annotation id
	 * @param annotationText - annotated script
	 * @return String - message after updating annotation to a node if network.
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public String updateAnnotationToNetwork(String annotationId,String annotationText) throws QuadrigaStorageException{
		String msg = dbConnectionEditManager.updateAnnotationToNetwork(annotationId, annotationText);
		return msg;
	}
	

	/**
	 * This method gets all the annotation of the network.
	 * @param networkId				Network id to fetch all the annotation related to that network
	 * returns 						{@link List} of {@link NetworksAnnotationsDTO} which contains all the Network Annotaions of {@link INetwork}
	 */
	@Override
	@Transactional
	public List<NetworkAnnotationsDTO> getAllAnnotationOfNetwork(String username, String networkId) throws QuadrigaStorageException {
		
		List<NetworkAnnotationsDTO> networkAnnoDTOList = dbConnectionEditManager.getAllAnnotationOfNetwork(username,networkId);
		
		
//		List<NetworkAnnotation> networkAnnoList = null;
//		
//		for(NetworkAnnotationsDTO dto :networkAnnoDTOList){
//			NetworkAnnotation n = new NetworkAnnotation();
//			n.setAnnotationId(dto.getAnnotationId());
//			n.setAnnotationText(dto.getAnnotationText());
//			n.setNodeId(dto.getNodeid());
//			n.setNodeName(dto.getNodename());
//			n.setUserId(dto.getCreatedby());
//			if(networkAnnoList==null){
//				networkAnnoList=new ArrayList<NetworkAnnotation>();
//			}
//			networkAnnoList.add(n);
//		}
		List<INetworkAnnotation> networkAnnotationsList = new ArrayList<INetworkAnnotation>();
		for(NetworkAnnotationsDTO networkAnnotation : networkAnnoDTOList){
			if(networkAnnotation.getObjectType().equals("node")){
				//INetworkAnnotation nodeAnnotation = 
			}
			if(networkAnnotation.getObjectType().equals("edge")){
			}
			if(networkAnnotation.getObjectType().equals("relation")){
			}
		}

		
		return networkAnnoDTOList;
	}

	/**
	 * This method retrieves the annotation entered by the user for the network edge
	 * @param type : specifies it is a relation or an appellation
	 * @param id : it is the id of the edge
	 * @param userid : logged in user
	 * @param networkId : network id
	 * @return String[] - array of string containing the annotations entered by the user
	 */
	@Override
	@Transactional
	public List<NetworkAnnotationsDTO> getAnnotationOfEdge(String sourceId,String targetId,
			String userId, String networkId) throws QuadrigaStorageException {
		List<NetworkEdgeAnnotationsDTO> networkEdgeAnnotationsDTOList = null;
		networkEdgeAnnotationsDTOList = dbConnectionEditManager.getAnnotationByEdgeId(sourceId ,targetId, userId,networkId);
		List<NetworkAnnotationsDTO> networkAnnotationsDTOs = new ArrayList<NetworkAnnotationsDTO>();
		for(NetworkEdgeAnnotationsDTO networkEdgeAnnotationsDTO : networkEdgeAnnotationsDTOList){
			NetworkAnnotationsDTO annotationsDTO = networkEdgeAnnotationsDTO.getAnnotationEdges();
			networkAnnotationsDTOs.add(annotationsDTO);
		}
		return networkAnnotationsDTOs;
	}
	/**
	 * This method adds annotation to the node of a network
	 * @param annotationType - type of annotation ,added to edge/node
	 * @param networkId - network id
	 * @param nodeId - node id
	 * @param edgeId - edge id
	 * @param nodeName - node in the network for which annotation needs to be added
	 * @param annotationText - annotation text value
	 * @param userId - logged in user
	 * @param objectType
	 * @return String - message after adding the annotation to a node of network.
	 * @throws QuadrigaStorageException
	 */
	
	@Override
	@Transactional
	public String addAnnotationToEdge(String networkId, String sourceId,String targetId,String sourceName,
			String targetName,String annotationText, String userId,String objectType,String targetType)
			throws QuadrigaStorageException{
		String msg = dbConnectionEditManager.addAnnotationToEdge(networkId, sourceId,targetId,sourceName,
				targetName,annotationText, userId,objectType,targetType);
		return msg;
	}

	@Override
	public List<NetworksAnnotationsDTO> getAnnotationOfEdge(String id,
			String userid, String networkId) throws QuadrigaStorageException {
		return null;
	}
	
	
	/**
	 * This method calls {@link NetworkManagerDAO} to add an annotation to a relation in network.
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
	@Override
	@Transactional
	public void addAnnotationToRelation(String annotationText,String networkId,String predicateId,String predicateName,String subjectId,String subjectName,
			String objectId, String objectName,String userName,String annotedObjectType) throws QuadrigaStorageException
	{
		dbConnectionEditManager.addAnnotationToRelation(annotationText, networkId, predicateId, predicateName, subjectId, subjectName, objectId, objectName, userName, annotedObjectType);
		
	}
	
	
	/**
	 * This method calls the {@link NetworkManagerDAO} layer method  to retrieve
	 * the annotations associated with the relation.
	 * @param subjectId     Id of the subject in the annotated relation
	 * @param objectId      Id of the object in the annotated relation.
	 * @param predicateId   Id of the predicate in the annotated relation.
	 * @param userName      name of the editor.
	 * @return List<NetworkRelationAnnotationsDTO> List of {@link NetworkRelationAnnotations} objects containing the annotations for the given relation 
	 * @throws QuadrigaStorageException Any database exception is redirected to the custom defined database error exception message.
	 */
	@Override
	@Transactional
	public List<NetworkAnnotationsDTO> getAnnotationToRelation(String networkId,String subjectId,String objectId, String predicateId,String userName) throws QuadrigaStorageException
	{
		List<NetworkAnnotationsDTO> networkAnnotations = null;
		List<NetworkRelationAnnotationsDTO> networkRelationAnnotations = null;
		networkRelationAnnotations = dbConnectionEditManager.getAnnotationToRelation(networkId,subjectId, objectId, predicateId, userName);
		networkAnnotations = networkMapper.getMappedRelationAnnotations(networkRelationAnnotations);
		return networkAnnotations;
	}
	
	

	
	
}
