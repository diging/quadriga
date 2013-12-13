package edu.asu.spring.quadriga.service;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IEditingNetworkAnnotationManager {

	public abstract String[] getAnnotation(String type, String id, String userid)
			throws QuadrigaStorageException;

	public abstract String addAnnotationToNetwork(String networkId,
			String nodeName, String annotationText, String userId,
			String objectType) throws QuadrigaStorageException;

	public abstract String updateAnnotationToNetwork(String annotationId,
			String annotationText) throws QuadrigaStorageException;

}