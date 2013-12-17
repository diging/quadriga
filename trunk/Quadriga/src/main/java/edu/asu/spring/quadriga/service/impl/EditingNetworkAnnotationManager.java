package edu.asu.spring.quadriga.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.db.IDBConnectionEditorManager;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IEditingNetworkAnnotationManager;

@Service
public class EditingNetworkAnnotationManager implements IEditingNetworkAnnotationManager {

	@Autowired
	IDBConnectionEditorManager dbConnectionEditManager;
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.impl.IEditingNetworkAnnotationManager#getAnnotation(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public String[] getAnnotation(String type, String id,String userid) throws QuadrigaStorageException{
		String arr[] = null;
		arr = dbConnectionEditManager.getAnnotation(type,id , userid);
		return arr;
	}
	
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.impl.IEditingNetworkAnnotationManager#addAnnotationToNetwork(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public String addAnnotationToNetwork(String networkId, String nodeName,
			String annotationText, String userId,String objectType)
			throws QuadrigaStorageException{
		String msg = dbConnectionEditManager.addAnnotationToNetwork(networkId, nodeName,
				annotationText, userId,objectType);
		return msg;
	}
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.impl.IEditingNetworkAnnotationManager#updateAnnotationToNetwork(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public String updateAnnotationToNetwork(String annotationId,String annotationText ) throws QuadrigaStorageException{
		String msg = dbConnectionEditManager.updateAnnotationToNetwork(annotationId, annotationText);
		return msg;
	}
	
	
}
