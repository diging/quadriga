package edu.asu.spring.quadriga.db;

import java.util.List;

import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionEditorManager {

	public abstract List<INetwork> getEditorNetworkList(IUser user)
			throws QuadrigaStorageException;

	public abstract String assignNetworkToUser(String networkId, IUser user)
			throws QuadrigaStorageException;

	public abstract List<INetwork> getAssignNetworkOfUser(IUser user)
			throws QuadrigaStorageException;

	public abstract String updateNetworkStatus(String networkId, String status)
			throws QuadrigaStorageException;

	public abstract List<INetwork> getRejectedNetworkOfUser(IUser user)
			throws QuadrigaStorageException;

	public abstract List<INetwork> getApprovedNetworkOfUser(IUser user)
			throws QuadrigaStorageException;

	public abstract List<INetwork> getfinishedNetworkListOfOtherEditors(IUser user)
			throws QuadrigaStorageException;

	public abstract List<INetwork> getAssignedNetworkListOfOtherEditors(IUser user)
			throws QuadrigaStorageException;

	public abstract String updateAssignedNetworkStatus(String networkId, String status)
			throws QuadrigaStorageException;

	public abstract String addAnnotationToNetwork(String networkId, String nodeName,
			String annotationText, String userId,String objectType)
			throws QuadrigaStorageException;
	public abstract String[] getAnnotation(String type, String id,String userid) throws QuadrigaStorageException;
	public abstract String updateAnnotationToNetwork(String annotationId,String annotationText ) throws QuadrigaStorageException;


}