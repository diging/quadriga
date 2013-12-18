package edu.asu.spring.quadriga.db;

import java.util.List;

import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionEditorManager {

	/**
	 * Get networks which editor can assign to him/her 
	 * @param user
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract List<INetwork> getEditorNetworkList(IUser user)
			throws QuadrigaStorageException;

	/**
	 * Assign a network to an editor
	 * @param networkId
	 * @param user
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract String assignNetworkToUser(String networkId, IUser user)
			throws QuadrigaStorageException;

	/**
	 * Get all the assigned networks of the editor
	 * @param user
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract List<INetwork> getAssignNetworkOfUser(IUser user)
			throws QuadrigaStorageException;

	/**
	 * Update the network status based on REJECT/APPEND/APPROVE
	 * @param networkId
	 * @param status
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract String updateNetworkStatus(String networkId, String status)
			throws QuadrigaStorageException;

	/**
	 * Get all rejected networks of the user
	 * @param user
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract List<INetwork> getRejectedNetworkOfUser(IUser user)
			throws QuadrigaStorageException;

	/**
	 * Get all approved networks of the user
	 * @param user
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract List<INetwork> getApprovedNetworkOfUser(IUser user)
			throws QuadrigaStorageException;

	/**
	 * Get network list of other editors which has status finalized
	 * @param user
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract List<INetwork> getfinishedNetworkListOfOtherEditors(IUser user)
			throws QuadrigaStorageException;

	/**
	 * Get assigned networks of other editors
	 * @param user
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract List<INetwork> getAssignedNetworkListOfOtherEditors(IUser user)
			throws QuadrigaStorageException;

	/**
	 * update the network status 
	 * @param networkId
	 * @param status
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract String updateAssignedNetworkStatus(String networkId, String status)
			throws QuadrigaStorageException;

	/**
	 * Add annotation to a network
	 * @param networkId
	 * @param nodeName
	 * @param annotationText
	 * @param userId
	 * @param objectType
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract String addAnnotationToNetwork(String networkId, String nodeName,
			String annotationText, String userId,String objectType)
			throws QuadrigaStorageException;
	
	/**
	 * Get all annotations of the network 
	 * @param type
	 * @param id
	 * @param userid
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract String[] getAnnotation(String type, String id,String userid,String networkId) throws QuadrigaStorageException;
	
	/**
	 * Update annotation of networks
	 * @param annotationId
	 * @param annotationText
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract String updateAnnotationToNetwork(String annotationId,String annotationText ) throws QuadrigaStorageException;


}