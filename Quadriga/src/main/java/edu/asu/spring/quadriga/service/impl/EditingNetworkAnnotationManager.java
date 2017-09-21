package edu.asu.spring.quadriga.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.IEditorDAO;
import edu.asu.spring.quadriga.dao.impl.NetworkDAO;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.network.INetworkAnnotation;
import edu.asu.spring.quadriga.domain.network.INetworkEdgeAnnotation;
import edu.asu.spring.quadriga.domain.network.INetworkNodeAnnotation;
import edu.asu.spring.quadriga.domain.network.INetworkRelationAnnotation;
import edu.asu.spring.quadriga.domain.network.impl.NetworkEdgeAnnotation;
import edu.asu.spring.quadriga.domain.network.impl.NetworkNodeAnnotation;
import edu.asu.spring.quadriga.domain.network.impl.NetworkRelationAnnotation;
import edu.asu.spring.quadriga.dto.NetworkAnnotationsDTO;
import edu.asu.spring.quadriga.dto.NetworkEdgeAnnotationsDTO;
import edu.asu.spring.quadriga.dto.NetworkNodeAnnotationsDTO;
import edu.asu.spring.quadriga.dto.NetworkRelationAnnotationsDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.NetworkDTOMapper;
import edu.asu.spring.quadriga.service.IAnnotationObjectTypes;
import edu.asu.spring.quadriga.service.IEditingNetworkAnnotationManager;
import edu.asu.spring.quadriga.service.network.factory.INetworkEdgeAnnotationFactory;
import edu.asu.spring.quadriga.service.network.factory.INetworkNodeAnnotationFactory;
import edu.asu.spring.quadriga.service.network.factory.INetworkRelationAnnotationFactory;

@Service
public class EditingNetworkAnnotationManager implements IEditingNetworkAnnotationManager {

	@Autowired
	IEditorDAO dbConnectionEditManager;
	
	@Autowired
	private NetworkDTOMapper networkMapper;
	
	@Autowired
	private INetworkEdgeAnnotationFactory networkEdgeAnnotationFactory;
	
	@Autowired
	private INetworkNodeAnnotationFactory networkNodeAnnotationFactory;
	
	@Autowired
	private INetworkRelationAnnotationFactory networkRelationAnnotationFactory;
	

	public INetworkEdgeAnnotationFactory getNetworkEdgeAnnotationFactory() {
		return networkEdgeAnnotationFactory;
	}

	public void setNetworkEdgeAnnotationFactory(
			INetworkEdgeAnnotationFactory networkEdgeAnnotationFactory) {
		this.networkEdgeAnnotationFactory = networkEdgeAnnotationFactory;
	}

	public INetworkNodeAnnotationFactory getNetworkNodeAnnotationFactory() {
		return networkNodeAnnotationFactory;
	}

	public void setNetworkNodeAnnotationFactory(
			INetworkNodeAnnotationFactory networkNodeAnnotationFactory) {
		this.networkNodeAnnotationFactory = networkNodeAnnotationFactory;
	}

	public INetworkRelationAnnotationFactory getNetworkRelationAnnotationFactory() {
		return networkRelationAnnotationFactory;
	}

	public void setNetworkRelationAnnotationFactory(
			INetworkRelationAnnotationFactory networkRelationAnnotationFactory) {
		this.networkRelationAnnotationFactory = networkRelationAnnotationFactory;
	}

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
	public List<NetworkAnnotationsDTO> getAnnotation(String type, String id,String networkId) throws QuadrigaStorageException{
		List<NetworkNodeAnnotationsDTO> networkNodeAnnotationsDTOList = null;
		networkNodeAnnotationsDTOList = dbConnectionEditManager.getAnnotationByNodeType(type, id, networkId);
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
	 * This method gets all the annotation of the network.
	 * @param networkId				Network id to fetch all the annotation related to that network
	 * returns 						{@link List} of {@link NetworksAnnotationsDTO} which contains all the Network Annotaions of {@link INetwork}
	 */
	@Override
	@Transactional
	public List<INetworkAnnotation> getAllAnnotationOfNetwork(String username, String networkId) throws QuadrigaStorageException {
		
		List<NetworkAnnotationsDTO> networkAnnoDTOList = dbConnectionEditManager.getAllAnnotationOfNetwork(username,networkId);
		List<INetworkAnnotation> networkAnnotationsList = new ArrayList<INetworkAnnotation>();
		for(NetworkAnnotationsDTO networkAnnotation : networkAnnoDTOList){
			if(networkAnnotation.getObjectType().equals(IAnnotationObjectTypes.NODE)){
				INetworkNodeAnnotation networkNodeAnnotation =  (NetworkNodeAnnotation) networkNodeAnnotationFactory.createNetworkNodeAnnotationObject();
				
				networkNodeAnnotation.setAnnotationId(networkAnnotation.getAnnotationId());
				networkNodeAnnotation.setAnnotationText(networkAnnotation.getAnnotationText());
				networkNodeAnnotation.setNetworkId(networkAnnotation.getNetworkId());
				networkNodeAnnotation.setNodeId(networkAnnotation.getNetworkNodeAnnotation().getNodeId());
				networkNodeAnnotation.setNodeName(networkAnnotation.getNetworkNodeAnnotation().getNodeName());
				networkNodeAnnotation.setUserName(networkAnnotation.getUserName());
				networkNodeAnnotation.setObjectType(networkAnnotation.getObjectType());
				
			    networkAnnotationsList.add(networkNodeAnnotation);
			}
			if(networkAnnotation.getObjectType().equals(IAnnotationObjectTypes.EDGE)){
				INetworkEdgeAnnotation networkEdgeAnnotation = (NetworkEdgeAnnotation) networkEdgeAnnotationFactory.createNetworkEdgeAnnotationObject();
				networkEdgeAnnotation.setAnnotationId(networkAnnotation.getAnnotationId());
				networkEdgeAnnotation.setAnnotationText(networkAnnotation.getAnnotationText());
				networkEdgeAnnotation.setNetworkId(networkAnnotation.getNetworkId());
				networkEdgeAnnotation.setUserName(networkAnnotation.getUserName());
				networkEdgeAnnotation.setSourceId(networkAnnotation.getNetworkEdgeAnnotation().getSourceId());
				networkEdgeAnnotation.setSourceName(networkAnnotation.getNetworkEdgeAnnotation().getSourceName());
				networkEdgeAnnotation.setTargetId(networkAnnotation.getNetworkEdgeAnnotation().getTargetId());
				networkEdgeAnnotation.setTargetName(networkAnnotation.getNetworkEdgeAnnotation().getTargetName());
				networkEdgeAnnotation.setTargetNodeType(networkAnnotation.getNetworkEdgeAnnotation().getTargetNodeType());
				networkEdgeAnnotation.setObjectType(networkAnnotation.getObjectType());
				
				networkAnnotationsList.add(networkEdgeAnnotation);
			}
			if(networkAnnotation.getObjectType().equals(IAnnotationObjectTypes.RELATION)){
				INetworkRelationAnnotation networkRelationAnnotation = (NetworkRelationAnnotation) networkRelationAnnotationFactory.createNetworkRelationAnnotationObject();
				networkRelationAnnotation.setAnnotationId(networkAnnotation.getAnnotationId());
				networkRelationAnnotation.setAnnotationText(networkAnnotation.getAnnotationText());
				networkRelationAnnotation.setNetworkId(networkAnnotation.getNetworkId());
				networkRelationAnnotation.setUserName(networkAnnotation.getUserName());
				if (networkAnnotation.getNetworkRelationAnnotation() != null) {
    				networkRelationAnnotation.setPredicateId(networkAnnotation.getNetworkRelationAnnotation().getPredicateId());
    				networkRelationAnnotation.setPredicateName(networkAnnotation.getNetworkRelationAnnotation().getPredicateName());
    				networkRelationAnnotation.setSubjectId(networkAnnotation.getNetworkRelationAnnotation().getSubjectId());
    				networkRelationAnnotation.setSubjectName(networkAnnotation.getNetworkRelationAnnotation().getSubjectName());
    				networkRelationAnnotation.setObjectId(networkAnnotation.getNetworkRelationAnnotation().getObjectId());
    				networkRelationAnnotation.setObjectName(networkAnnotation.getNetworkRelationAnnotation().getObjectName());
				} else {
				    networkRelationAnnotation.setPredicateName(networkAnnotation.getNetworkNodeAnnotation().getNodeName());
				    networkRelationAnnotation.setPredicateId(networkAnnotation.getNetworkNodeAnnotation().getNodeId());
	                
				}
				networkRelationAnnotation.setObjectType(networkAnnotation.getObjectType());
				
				networkAnnotationsList.add(networkRelationAnnotation);
			}
		}

		
		return networkAnnotationsList;
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
	public List<NetworkAnnotationsDTO> getAnnotationOfEdge(String id,
			String userid, String networkId) throws QuadrigaStorageException {
		return null;
	}
	
	
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
	@Override
	@Transactional
	public void addAnnotationToRelation(String annotationText,String networkId,String predicateId,String predicateName,String subjectId,String subjectName,
			String objectId, String objectName,String userName,String annotedObjectType) throws QuadrigaStorageException
	{
		dbConnectionEditManager.addAnnotationToRelation(annotationText, networkId, predicateId, predicateName, subjectId, subjectName, objectId, objectName, userName, annotedObjectType);
		
	}
	
	
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
	
	@Override
	@Transactional
	public String getAllAnnotationOfNetworkAsJson(String username, String networkId) throws QuadrigaStorageException, JSONException {
		List<INetworkAnnotation> networkAnnotationsList = getAllAnnotationOfNetwork(username, networkId);
		String jsonAnnotations = "";
		JSONArray ja = new JSONArray();
		for(INetworkAnnotation networkAnnotation : networkAnnotationsList){
			JSONObject j = new JSONObject();
			if(networkAnnotation instanceof NetworkNodeAnnotation) {
				j.put("name", ((NetworkNodeAnnotation) networkAnnotation).getNodeName());
				j.put("text", ((NetworkNodeAnnotation) networkAnnotation).getAnnotationText());
				j.put("objecttype", ((NetworkNodeAnnotation) networkAnnotation).getObjectType());
				ja.put(j);
			} 
			if(networkAnnotation instanceof NetworkEdgeAnnotation) {
				String name = ((NetworkEdgeAnnotation) networkAnnotation).getSourceName() + "-" + ((NetworkEdgeAnnotation) networkAnnotation).getTargetName();
				j.put("name", name);
				j.put("text", ((NetworkEdgeAnnotation) networkAnnotation).getAnnotationText());
				j.put("objecttype", ((NetworkEdgeAnnotation) networkAnnotation).getObjectType());
				ja.put(j);
			}
			if(networkAnnotation instanceof NetworkRelationAnnotation) {
				String name = ((NetworkRelationAnnotation) networkAnnotation).getPredicateName();
				j.put("name", name);
				j.put("text", ((NetworkRelationAnnotation) networkAnnotation).getAnnotationText());
				j.put("objecttype", ((NetworkRelationAnnotation) networkAnnotation).getObjectType());
				ja.put(j);
			} 
			
			
		}
		//j1.put("text", ja);
		//jsonAnnotations = j1.toString();
		jsonAnnotations = ja.toString();
		return jsonAnnotations;
		
	}

	
	
}
