package edu.asu.spring.quadriga.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.db.IDBConnectionEditorManager;
import edu.asu.spring.quadriga.dto.NetworksAnnotationsDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IEditingNetworkAnnotationManager;

@Service
public class EditingNetworkAnnotationManager implements IEditingNetworkAnnotationManager {

	@Autowired
	IDBConnectionEditorManager dbConnectionEditManager;
	

	/**
	 * This method retrieves the annotation entered by the user for the network node.
	 * @param type : specifies it is a relation or an appellation
	 * @param id : it is the relation event id or the appelation id.
	 * @param userid : logged in user
	 * @param networkId : network id
	 * @return String[] - array of string containing the annotations entered by the user
	 */
	@SuppressWarnings("null")
	@Override
	@Transactional
	public List<NetworksAnnotationsDTO> getAnnotation(String type, String id,String userid,String networkId) throws QuadrigaStorageException{
		String[] arr = null;
		List<NetworksAnnotationsDTO> list = null;
	//	arr = dbConnectionEditManager.getAnnotation(type,id , userid,networkId);
		list = dbConnectionEditManager.getAnnotation(type,id , userid,networkId);
//		if(list != null && list.size() > 0){
//			arr = new String[list.size()];
//			for (int i = 0; i < list.size(); i++) {
//				NetworksAnnotationsDTO networksAnnotationsDTO = list.get(i);
//				arr[i] = networksAnnotationsDTO.getAnnotationtext();
//			}
//		}
		return list;
	}
	
	/**
	 * This method adds annotation to the node of a network
	 * @param networkId - network id
	 * @param nodeName - node in the network for which annotation needs to be added
	 * @param annotationText - annotation text value
	 * @param userId - logged in user
	 * @param objectType
	 * @return String - message after adding the annotation to a node of network.
	 * @throws QuadrigaStorageException
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
	
	
}
