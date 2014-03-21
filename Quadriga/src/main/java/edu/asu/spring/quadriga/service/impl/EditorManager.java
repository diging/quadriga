package edu.asu.spring.quadriga.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.db.IDBConnectionEditorManager;
import edu.asu.spring.quadriga.db.IDBConnectionNetworkManager;
import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.INetworkFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IEditorManager;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.web.network.INetworkStatus;

/**
 * This class acts as a Network manager which handles the networks object
 * 
 * @author : Lohith Dwaraka
 */
@Service
public class EditorManager implements IEditorManager {

	private static final Logger logger = LoggerFactory
			.getLogger(EditorManager.class);


	@Autowired
	private INetworkFactory networkFactory;
	
	@Autowired
	private INetworkManager networkManager;

	@Autowired
	private IDBConnectionEditorManager dbConnect;

	@Autowired
	private IDBConnectionNetworkManager dbnetworkManager;



	/**
	 * This method retrieves the list of networks associated with the user.
	 * @param IUser - logged in user object
	 * @return List<INetwork> - list of networks associated with the user.
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public List<INetwork> getEditorNetworkList(IUser user) throws QuadrigaStorageException{
		List<INetwork> networkList = new ArrayList<INetwork>();

		try{
			networkList=dbConnect.getEditorNetworkList(user);
		}catch(QuadrigaStorageException e){
			logger.error("Something went wrong in DB",e);
		}
		return networkList;
	}

	/**
	 * This method assigns a network to the user
	 * @param networkId - networkId
	 * @param user - logged in user object
	 * @return String - message after assigning the network to the user
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public String assignNetworkToUser(String networkId, IUser user) throws QuadrigaStorageException{
		String msg = "";
		INetwork network= networkManager.getNetwork(networkId);
		try{
			msg = dbConnect.assignNetworkToUser(networkId, user,network.getName());
		}catch(QuadrigaStorageException e){
			logger.error("Something went wrong in DB",e);
		}
		return msg;
	}

	/**
	 * This method retrieve the assigned networks of the user
	 * @param user - logged in user object
	 * @return List<INetwork> - list of assigned networks
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public  List<INetwork> getAssignNetworkOfUser(IUser user)
			throws QuadrigaStorageException{
		List<INetwork> networkList = null;

		try{
			networkList=dbConnect.getNetworksOfUser(user, INetworkStatus.ASSIGNED);
		}catch(QuadrigaStorageException e){
			logger.error("Something went wrong in DB",e);
		}
		return networkList;
	}

	/**
	 * This method retrieves the networks of all other editors
	 * @param IUser - logged in user object
	 * @return List<INetwork> - list of networks assigned to other editors
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public List<INetwork> getfinishedNetworkListOfOtherEditors(IUser user) throws QuadrigaStorageException{

		List<INetwork> networkList = null;

		try{

			List<String> networkStatus = new ArrayList<String>();
			networkStatus.add(INetworkStatus.APPROVED);

			networkList = dbConnect.getNetworkListOfOtherEditors(user, networkStatus);
		}catch(QuadrigaStorageException e){
			logger.error("Something went wrong in DB",e);
		}
		return networkList;
	}

	/**
	 * This method retrieves the assigned list of networks for other editors
	 * @param IUser - logged in user object
	 * @return List<INetwork> - list of assigned networks of other editors
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public List<INetwork> getAssignedNetworkListOfOtherEditors(IUser user) throws QuadrigaStorageException{

		List<INetwork> networkList = null;

		try{

			List<String> networkStatus = new ArrayList<String>();
			networkStatus.add(INetworkStatus.ASSIGNED);

			networkList = dbConnect.getNetworkListOfOtherEditors(user, networkStatus);

		}catch(QuadrigaStorageException e){
			logger.error("Something went wrong in DB",e);
		}
		return networkList;
	}

	/**
	 * This method retrieves the list of reject networks for the user
	 * @param IUser - logged in user object
	 * @return List<INetwork> - list of rejected networks for the user
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public  List<INetwork> getRejectedNetworkOfUser(IUser user)
			throws QuadrigaStorageException{
		List<INetwork> networkList = null;

		try{
			networkList=dbConnect.getNetworksOfUser(user, INetworkStatus.REJECTED);
		}catch(QuadrigaStorageException e){
			logger.error("Something went wrong in DB",e);
		}
		return networkList;
	}

	/**
	 * This method retrieves the approved networks of the user
	 * @param IUser - logged in user object
	 * @return List<INetwork> - list of approved networks for the user.
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public  List<INetwork> getApprovedNetworkOfUser(IUser user)
			throws QuadrigaStorageException{
		List<INetwork> networkList = new ArrayList<INetwork>();

		try{
			networkList=dbConnect.getNetworksOfUser(user, INetworkStatus.APPROVED);
		}catch(QuadrigaStorageException e){
			logger.error("Something went wrong in DB",e);
		}
		return networkList;
	}

	/**
	 * This method update the status of the network
	 * @param networkId - network id
	 * @param status - status of the network
	 * @throws QuadrigaStorageException
	 * @return String - message after updating the network status
	 */
	@Override
	@Transactional
	public String updateNetworkStatus(String networkId, String status) throws QuadrigaStorageException {
		String msg = "";
		logger.info("Changing to status : "+status);
		try{
			msg = dbConnect.updateNetworkStatus(networkId, status);
		}catch(QuadrigaStorageException e){
			logger.error("Something went wrong in DB",e);
		}
		return msg;
	}

	/**
	 * This method updates the assigned network status
	 * @param networkId - network id of assigned network
	 * @param status - status of the network
	 * @throws QuadrigaStorageException
	 * @return String - message after updating the assigned network status
	 */
	@Override
	@Transactional
	public String updateAssignedNetworkStatus(String networkId, String status) throws QuadrigaStorageException {
		String msg = "";
		try{
			msg = dbConnect.updateAssignedNetworkStatus(networkId, status);
		}catch(QuadrigaStorageException e){
			logger.error("Something went wrong in DB",e);
		}
		return msg;
	}


}
