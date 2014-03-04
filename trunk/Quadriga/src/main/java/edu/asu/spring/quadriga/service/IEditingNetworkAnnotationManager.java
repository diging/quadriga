package edu.asu.spring.quadriga.service;

import java.util.List;

import edu.asu.spring.quadriga.domain.implementation.NetworkAnnotation;
import edu.asu.spring.quadriga.dto.NetworksAnnotationsDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IEditingNetworkAnnotationManager {

	public abstract List<NetworksAnnotationsDTO> getAnnotation(String type, String id, String userid,String networkId)
			throws QuadrigaStorageException;

	public abstract String addAnnotationToNetwork(String networkId,
			String nodeName, String annotationText, String userId,
			String objectType) throws QuadrigaStorageException;

	public abstract String updateAnnotationToNetwork(String annotationId,
			String annotationText) throws QuadrigaStorageException;
	

	List<NetworkAnnotation> getAllAnnotationOfNetwork(String userId,
			String networkId) throws QuadrigaStorageException;

}