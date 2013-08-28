package edu.asu.spring.quadriga.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.IDBConnectionEditorManager;
import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.INetworkFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IEditorManager;

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
	@Qualifier("DBConnectionEditorManagerBean")
	private IDBConnectionEditorManager dbConnect;



	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.impl.IEditorManager#getEditorNetworkList(edu.asu.spring.quadriga.domain.IUser)
	 */
	@Override
	public List<INetwork> getEditorNetworkList(IUser user) throws QuadrigaStorageException{
		List<INetwork> networkList = new ArrayList<INetwork>();

		try{
			networkList=dbConnect.getEditorNetworkList(user);
		}catch(QuadrigaStorageException e){
			logger.error("Something went wrong in DB",e);
		}
		return networkList;
	}

	@Override
	public String assignNetworkToUser(String networkId, IUser user) throws QuadrigaStorageException{
		String msg = "";
		try{
			msg = dbConnect.assignNetworkToUser(networkId, user);
		}catch(QuadrigaStorageException e){
			logger.error("Something went wrong in DB",e);
		}
		return msg;
	}
	
	@Override
	public  List<INetwork> getAssignNetworkOfUser(IUser user)
			throws QuadrigaStorageException{
		List<INetwork> networkList = new ArrayList<INetwork>();

		try{
			networkList=dbConnect.getAssignNetworkOfUser(user);
		}catch(QuadrigaStorageException e){
			logger.error("Something went wrong in DB",e);
		}
		return networkList;
	}

}
