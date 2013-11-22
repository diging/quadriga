package edu.asu.spring.quadriga.dao.sql.impl;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.NotYetImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.sql.DAOConnectionManager;
import edu.asu.spring.quadriga.dao.sql.INetworkManagerDAO;
import edu.asu.spring.quadriga.dao.sql.IUserManagerDAO;
import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.INetworkNodeInfo;
import edu.asu.spring.quadriga.domain.INetworkOldVersion;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDeniedDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserRequestsDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.UserDTOMapper;

/**
 * This class is responsible for Querying the MySQL database
 * and fetch the class objects
 * 
 * @author Ram Kumar Kumaresan
 *
 */
@Repository
public class NetworkManagerDAO extends DAOConnectionManager implements INetworkManagerDAO
{

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private UserDTOMapper userMapper;
	
	@Resource(name = "database_error_msgs")
	private Properties messages;

	private static final Logger logger = LoggerFactory.getLogger(NetworkManagerDAO.class);

	@Override
	public String addNetworkRequest(String networkName, IUser user, String workspaceid) throws QuadrigaStorageException {
		
		throw new NotYetImplementedException("Yet to be implemeted");
	}

	@Override
	public String addNetworkStatement(String networkId, String id, String type, String isTop, IUser user) throws QuadrigaStorageException {
		throw new NotYetImplementedException("Yet to be implemeted");
	}

	@Override
	public INetwork getNetworkStatus(String networkName, IUser user)
			throws QuadrigaStorageException {
		throw new NotYetImplementedException("Yet to be implemeted");
	}

	@Override
	public List<INetwork> getNetworkList(IUser user) throws QuadrigaStorageException {
		throw new NotYetImplementedException("Yet to be implemeted");
	}

	@Override
	public String getProjectIdForWorkspaceId(String workspaceid) throws QuadrigaStorageException {
		throw new NotYetImplementedException("Yet to be implemeted");
	}

	@Override
	public boolean hasNetworkName(String networkName, IUser user) throws QuadrigaStorageException {
		throw new NotYetImplementedException("Yet to be implemeted");
	}

	@Override
	public List<INetworkNodeInfo> getNetworkTopNodes(String networkId) throws QuadrigaStorageException {
		throw new NotYetImplementedException("Yet to be implemeted");
	}

	@Override
	public String archiveNetworkStatement(String networkId, String id) throws QuadrigaStorageException {
		throw new NotYetImplementedException("Yet to be implemeted");
	}

	@Override
	public List<INetworkNodeInfo> getAllNetworkNodes(String networkId) throws QuadrigaStorageException {
		throw new NotYetImplementedException("Yet to be implemeted");
	}

	@Override
	public String archiveNetwork(String networkId) throws QuadrigaStorageException {
		throw new NotYetImplementedException("Yet to be implemeted");
	}

	@Override
	public INetworkOldVersion getNetworkOldVersionDetails(String networkId)	throws QuadrigaStorageException {
		throw new NotYetImplementedException("Yet to be implemeted");
	}

	@Override
	public List<INetworkNodeInfo> getNetworkOldVersionTopNodes(String networkId) throws QuadrigaStorageException {
		throw new NotYetImplementedException("Yet to be implemeted");
	}

	@Override
	public INetwork getNetworkDetails(String networkId)	throws QuadrigaStorageException {
		throw new NotYetImplementedException("Yet to be implemeted");
	}


}
