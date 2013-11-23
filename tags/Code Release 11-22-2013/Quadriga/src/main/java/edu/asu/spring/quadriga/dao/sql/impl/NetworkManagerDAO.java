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
import edu.asu.spring.quadriga.dto.NetworkStatementsDTO;
import edu.asu.spring.quadriga.dto.NetworksDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDeniedDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserRequestsDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.NetworkDTOMapper;
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
	private NetworkDTOMapper networkMapper;

	@Resource(name = "database_error_msgs")
	private Properties messages;

	private static final Logger logger = LoggerFactory.getLogger(NetworkManagerDAO.class);

	@Override
	public String addNetworkRequest(String networkName, IUser user, String workspaceid) throws QuadrigaStorageException {

		String networkid = generateUniqueID();
		NetworksDTO networksDTO = networkMapper.getNetworksDTO(networkid, networkName, user.getName(), workspaceid);

		try
		{
			sessionFactory.getCurrentSession().save(networksDTO);	
			return networkid;
		}
		catch(Exception e)
		{
			logger.error("Error in adding a network request: ",e);
			throw new QuadrigaStorageException(e);
		}
	}

	@Override
	public String addNetworkStatement(String networkId, String id, String type, String isTop, IUser user) throws QuadrigaStorageException {

		NetworkStatementsDTO networkStatementsDTO = networkMapper.getNetworkStatementsDTO(networkId, id, type, isTop, user.getName());

		try
		{
			sessionFactory.getCurrentSession().save(networkStatementsDTO);	
			return null;
		}
		catch(Exception e)
		{
			logger.error("Error in adding a network request: ",e);
			throw new QuadrigaStorageException(e);
		}
	}

	@Override
	public INetwork getNetworkStatus(String networkId, IUser user) throws QuadrigaStorageException {
		Query query = sessionFactory.getCurrentSession().createQuery(" from NetworksDTO network where network.networkowner = :networkowner and network.networkid = :networkid");
		query.setParameter("networkowner", user.getName());
		query.setParameter("networkid", networkId);

		try
		{
			NetworksDTO networksDTO = (NetworksDTO) query.uniqueResult();

			//TODO: Talk with karthik on fetching the workspace and project id in one call to the database. Need to change the class structure
		}
		catch(Exception e)
		{
			logger.error("Error in fetching a network status: ",e);
			throw new QuadrigaStorageException(e);
		}

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
		Query query = sessionFactory.getCurrentSession().createQuery(" from NetworksDTO network where network.networkowner = :networkowner and network.networkname = :networkname");
		query.setParameter("networkowner", user.getName());
		query.setParameter("networkname", networkName);

		try
		{
			List<NetworksDTO> networksDTO = query.list();

			if(networksDTO.size() > 0)
				return true;
			else 
				return false;
		}
		catch(Exception e)
		{
			logger.error("Error in fetching a network status: ",e);
			throw new QuadrigaStorageException(e);
		}

	}

	@Override
	public List<INetworkNodeInfo> getNetworkTopNodes(String networkId) throws QuadrigaStorageException {
		Query query = sessionFactory.getCurrentSession().createQuery(" from NetworkStatementsDTO network where network.networkid = :networkid and network.istop = 1 and isarchived = 0");
		query.setParameter("networkid", networkId);

		try
		{
			List<NetworkStatementsDTO> networkStatementsDTOList = query.list();
			return networkMapper.getListOfNetworkNodeInfo(networkStatementsDTOList);
		}

		catch(Exception e)
		{
			logger.error("Error in fetching a network status: ",e);
			throw new QuadrigaStorageException(e);
		}
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
