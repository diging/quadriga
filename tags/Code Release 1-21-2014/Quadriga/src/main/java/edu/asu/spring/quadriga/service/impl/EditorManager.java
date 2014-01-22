package edu.asu.spring.quadriga.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.db.IDBConnectionEditorManager;
import edu.asu.spring.quadriga.db.IDBConnectionNetworkManager;
import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.INetworkOldVersion;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.INetworkFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IEditorManager;
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
	private IDBConnectionEditorManager dbConnect;

	@Autowired
	private IDBConnectionNetworkManager dbnetworkManager;



	/**
	 * Get network list for a editor
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
	 * Assign a network to user
	 */
	@Override
	@Transactional
	public String assignNetworkToUser(String networkId, IUser user) throws QuadrigaStorageException{
		String msg = "";
		try{
			msg = dbConnect.assignNetworkToUser(networkId, user);
		}catch(QuadrigaStorageException e){
			logger.error("Something went wrong in DB",e);
		}
		return msg;
	}

	/**
	 * Get assigned network list of the user
	 */
	@Override
	@Transactional
	public  List<INetwork> getAssignNetworkOfUser(IUser user)
			throws QuadrigaStorageException{
		List<INetwork> networkList = null;

		try{
			networkList=dbConnect.getNetworksOfUser(user, INetworkStatus.ASSIGNED);

			//Try to get the old network version(Archive level one) for each network in the list returned.
			for(INetwork network: networkList)
			{
				List<INetworkOldVersion> networkOldVersion = dbnetworkManager.getNetworkVersions(network.getId(), INetworkStatus.ARCHIVE_LEVEL_ONE);

				if(networkOldVersion != null && networkOldVersion.size() > 0){
					//Get the first index as according to business logic only one object will exist at Archive level one
					network.setNetworkOldVersion(networkOldVersion.get(0));
				}
			}

		}catch(QuadrigaStorageException e){
			logger.error("Something went wrong in DB",e);
		}
		return networkList;
	}

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
	 * Get Rejected network list of user
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
	 * Get Approved network list of a user
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
	 * Update the status of the network
	 * PENDING / ASSIGNED / REJECTED / APPROVED
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
	 * Update the status of the assigned network
	 * PENDING / ASSIGNED / REJECTED / APPROVED
	 */
	@Override
	@Transactional
	public String updateAssignedNetworkStatus(String networkId, String status) throws QuadrigaStorageException {
		String msg = "";
		try{
			msg = dbConnect.updateAssignedNetworkStatus(networkId, status);
			//TODO: Get the network owner details based on network id
			//TODO: Send email to owner
		}catch(QuadrigaStorageException e){
			logger.error("Something went wrong in DB",e);
		}
		return msg;
	}

}
