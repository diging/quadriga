package edu.asu.spring.quadriga.service;

import java.util.List;

import edu.asu.spring.quadriga.domain.implementation.NetworkAnnotation;
import edu.asu.spring.quadriga.dto.NetworksAnnotationsDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IEditingNetworkAnnotationManager {

	public abstract List<NetworksAnnotationsDTO> getAnnotation(String type, String id, String userid,String networkId)
			throws QuadrigaStorageException;
	public abstract List<NetworksAnnotationsDTO> getAnnotationOfEdge(String id, String userid,String networkId)
			throws QuadrigaStorageException;

	public abstract String addAnnotationToNetwork(String annotationtype,String networkId,String nodeId,
			String edgeId,String nodeName, String annotationText, String userId,
			String objectType) throws QuadrigaStorageException;

	public abstract String updateAnnotationToNetwork(String annotationId,
			String annotationText) throws QuadrigaStorageException;
	

	public abstract List<NetworkAnnotation> getAllAnnotationOfNetwork(String userId,
			String networkId) throws QuadrigaStorageException;
	public abstract String addAnnotationToEdge(String annotationType, String networkId,
			String sourceId, String targetId, String sourceName,
			String targetName, String annotationText, String userId,
			String objectType, String targetType)
			throws QuadrigaStorageException;
	public abstract List<NetworksAnnotationsDTO> getAnnotationOfEdge(String sourceId,
			String targetId, String userId, String networkId)
			throws QuadrigaStorageException;

}