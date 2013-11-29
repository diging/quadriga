package edu.asu.spring.quadriga.dao.sql.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.hibernate.CacheMode;
import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.sql.DAOConnectionManager;
import edu.asu.spring.quadriga.db.IDBConnectionNetworkManager;
import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.INetworkNodeInfo;
import edu.asu.spring.quadriga.domain.INetworkOldVersion;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.impl.NetworkFactory;
import edu.asu.spring.quadriga.dto.NetworkAssignedDTO;
import edu.asu.spring.quadriga.dto.NetworkStatementsDTO;
import edu.asu.spring.quadriga.dto.NetworksDTO;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.NetworkDTOMapper;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;
import edu.asu.spring.quadriga.web.network.INetworkStatus;

/**
 * This class is responsible for Querying the MySQL database
 * and fetch the class objects
 * 
 * @author Ram Kumar Kumaresan
 *
 */
@Repository
public class NetworkManagerDAO extends DAOConnectionManager implements IDBConnectionNetworkManager
{

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private NetworkDTOMapper networkMapper;

	@Resource(name = "database_error_msgs")
	private Properties messages;

	@Autowired
	private IRetrieveProjectManager retrieveProjectDetails;

	@Autowired
	private IListWSManager wsManager;

	@Autowired
	private IUserManager userManager;

	private static final Logger logger = LoggerFactory.getLogger(NetworkManagerDAO.class);

	@Autowired
	private NetworkFactory networkFactory;

	@Override
	public String addNetworkRequest(String networkName, IUser user, String workspaceid) throws QuadrigaStorageException {

		String networkid = generateUniqueID();
		NetworksDTO networksDTO = networkMapper.getNetworksDTO(networkid, networkName, user.getUserName(), workspaceid);

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

		NetworkStatementsDTO networkStatementsDTO = networkMapper.getNetworkStatementsDTO(generateUniqueID(), networkId, id, type, isTop, user.getUserName());

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
		INetwork network = networkFactory.createNetworkObject();
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery(" from NetworksDTO network where network.networkowner = :networkowner and network.networkid = :networkid");
			query.setParameter("networkowner", user.getUserName());
			query.setParameter("networkid", networkId);

			NetworksDTO networksDTO = (NetworksDTO) query.uniqueResult();
			if(networksDTO != null)
			{
				network = networkMapper.getNetwork(networksDTO);

				//Get the project details from the workspace id
				query = sessionFactory.getCurrentSession().getNamedQuery("ProjectWorkspaceDTO.findByWorkspaceid");
				query.setParameter("workspaceid", networksDTO.getWorkspaceid());
				ProjectWorkspaceDTO projectWorkspaceDTO = (ProjectWorkspaceDTO) query.uniqueResult();

				if(projectWorkspaceDTO!= null)
				{
					IProject project = retrieveProjectDetails.getProjectDetails(projectWorkspaceDTO.getProjectWorkspaceDTOPK().getProjectid());
					network.setProjectName(project.getName());

					//Get the workspace name
					String workspaceName=wsManager.getWorkspaceName(network.getWorkspaceid());
					network.setWorkspaceName(workspaceName);
				}
			}
			return network;
		}
		catch(Exception e)
		{
			logger.error("Error in fetching a network status: ",e);
			throw new QuadrigaStorageException(e);
		}
	}

	@Override
	public List<INetwork> getNetworkList(IUser user) throws QuadrigaStorageException {
		List<INetwork> networkList = new ArrayList<INetwork>();
		try
		{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("NetworksDTO.findByNetworkowner");
			query.setParameter("networkowner", user.getUserName());

			List<NetworksDTO> listNetworksDTO = query.list();
			networkList = networkMapper.getListOfNetworks(listNetworksDTO);

			//Update project and workspace name for the network
			for(INetwork network : networkList)
			{
				//Get the project details from the workspace id
				query = sessionFactory.getCurrentSession().getNamedQuery("ProjectWorkspaceDTO.findByWorkspaceid");
				query.setParameter("workspaceid", network.getWorkspaceid());
				ProjectWorkspaceDTO projectWorkspaceDTO = (ProjectWorkspaceDTO) query.uniqueResult();

				if(projectWorkspaceDTO!= null)
				{
					IProject project = retrieveProjectDetails.getProjectDetails(projectWorkspaceDTO.getProjectWorkspaceDTOPK().getProjectid());
					network.setProjectName(project.getName());

					//Get the workspace name
					String workspaceName=wsManager.getWorkspaceName(network.getWorkspaceid());
					network.setWorkspaceName(workspaceName);
				}else{
					logger.info("projectWorkspaceDTO is null beta");
				}
			}

			return networkList;
		}
		catch(Exception e)
		{
			logger.error("Error in fetching a network status: ",e);
			throw new QuadrigaStorageException(e);
		}
	}

	@Override
	public String getProjectIdForWorkspaceId(String workspaceid) throws QuadrigaStorageException {
		try
		{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("ProjectWorkspaceDTO.findByWorkspaceid");
			query.setParameter("workspaceid", workspaceid);
			ProjectWorkspaceDTO projectWorkspaceDTO = (ProjectWorkspaceDTO) query.uniqueResult();

			if(projectWorkspaceDTO != null)
				return projectWorkspaceDTO.getProjectWorkspaceDTOPK().getProjectid();
			else 
				return null;
		}
		catch(Exception e)
		{
			logger.error("Error in fetching project id from workspace: ",e);
			throw new QuadrigaStorageException(e);
		}
	}

	@Override
	public boolean hasNetworkName(String networkName, IUser user) throws QuadrigaStorageException {

		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery(" from NetworksDTO network where network.networkowner = :networkowner and network.networkname = :networkname");
			query.setParameter("networkowner", user.getUserName());
			query.setParameter("networkname", networkName);
			List<NetworksDTO> networksDTO = query.list();

			if(networksDTO != null)
			{
				if(networksDTO.size() > 0)
					return true;
			}
			return false;
		}
		catch(Exception e)
		{
			logger.error("Error in network name check: ",e);
			throw new QuadrigaStorageException(e);
		}

	}

	@Override
	public List<INetworkNodeInfo> getNetworkTopNodes(String networkId) throws QuadrigaStorageException {
		List<INetworkNodeInfo> networkNodeList = new ArrayList<INetworkNodeInfo>();
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery(" from NetworkStatementsDTO network where network.networkid = :networkid and network.istop = 1 and isarchived = 0");
			query.setParameter("networkid", networkId);
			List<NetworkStatementsDTO> networkStatementsDTOList = query.list();

			if(networkStatementsDTOList != null)
				networkNodeList = networkMapper.getListOfNetworkNodeInfo(networkStatementsDTOList);
			return networkNodeList;
		}
		catch(Exception e)
		{
			logger.error("Error in fetching a network top nodes: ",e);
			throw new QuadrigaStorageException(e);
		}
	}

	//TODO: Add the admin username
	@Override
	public String archiveNetworkStatement(String networkId, String id) throws QuadrigaStorageException {

		Transaction transaction = null;
		try
		{
			//Obtain a stateless session to overcome OutOfMemory Exception
			StatelessSession  session = sessionFactory.openStatelessSession();
			transaction = session.beginTransaction();

			//Select only the rows matching the network id and obtain a scrollable list
			ScrollableResults scrollableDTO = session.createQuery("  FROM NetworkStatementsDTO n WHERE n.networkid = :networkid and n.id = :id")
					.setParameter("networkid", networkId)
					.setParameter("id", id)
					.setCacheMode(CacheMode.IGNORE)
					.scroll(ScrollMode.FORWARD_ONLY);

			while(scrollableDTO.next())
			{
				//Update the rows with archive id 1 or 0
				NetworkStatementsDTO networkStatementDTO = (NetworkStatementsDTO) scrollableDTO.get(0);
				if(networkStatementDTO.getIsarchived() == INetworkStatus.NOT_ARCHIVED)
				{
					networkStatementDTO.setIsarchived(INetworkStatus.ARCHIVE_LEVEL_ONE);
					session.update(networkStatementDTO);
				}
				else if(networkStatementDTO.getIsarchived() == INetworkStatus.ARCHIVE_LEVEL_ONE)
				{
					networkStatementDTO.setIsarchived(INetworkStatus.NOT_REACHABLE_ARCHIVE);
					session.update(networkStatementDTO);
				}
			}

			return "";
		}
		catch(Exception e)
		{
			logger.error("Error in archiving a network statement: ",e);
			throw new QuadrigaStorageException(e);
		}
	}

	@Override
	public List<INetworkNodeInfo> getAllNetworkNodes(String networkId) throws QuadrigaStorageException {

		List<INetworkNodeInfo> networkNodeList = new ArrayList<INetworkNodeInfo>();

		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery(" from NetworkStatementsDTO n WHERE n.networkid = :networkid and n.isarchived = 0");
			query.setParameter("networkid", networkId);

			List<NetworkStatementsDTO> listNetworkStatementsDTO = query.list();
			if(listNetworkStatementsDTO != null)
			{
				networkNodeList = networkMapper.getListOfNetworkNodeInfo(listNetworkStatementsDTO);
			}
			return networkNodeList;
		}
		catch(Exception e)
		{
			logger.error("Error in fetching network nodes: ",e);
			throw new QuadrigaStorageException(e);
		}
	}

	@Override
	public String archiveNetwork(String networkId) throws QuadrigaStorageException {
		Transaction transaction = null;
		try
		{
			//Obtain a stateless session to overcome OutOfMemory Exception
			StatelessSession  session = sessionFactory.openStatelessSession();
			transaction = session.beginTransaction();

			//Select only the rows matching the network id and obtain a scrollable list
			ScrollableResults scrollableDTO = session.getNamedQuery("NetworkStatementsDTO.findByNetworkid").setParameter("networkid", networkId).setCacheMode(CacheMode.IGNORE).scroll(ScrollMode.FORWARD_ONLY);

			while(scrollableDTO.next())
			{
				//Update the rows with archive id 1 or 0
				NetworkStatementsDTO networkStatementDTO = (NetworkStatementsDTO) scrollableDTO.get(0);
				if(networkStatementDTO.getIsarchived() == INetworkStatus.NOT_ARCHIVED)
				{
					networkStatementDTO.setIsarchived(INetworkStatus.ARCHIVE_LEVEL_ONE);
					session.update(networkStatementDTO);
				}
				else if(networkStatementDTO.getIsarchived() == INetworkStatus.ARCHIVE_LEVEL_ONE)
				{
					networkStatementDTO.setIsarchived(INetworkStatus.NOT_REACHABLE_ARCHIVE);
					session.update(networkStatementDTO);
				}
			}

			scrollableDTO = session.getNamedQuery("NetworkAssignedDTO.findByNetworkid").setParameter("networkid", networkId).setCacheMode(CacheMode.IGNORE).scroll(ScrollMode.FORWARD_ONLY);
			while(scrollableDTO.next())
			{
				//Update the rows with archive id 1 or 0
				NetworkAssignedDTO networkAssignedDTO = (NetworkAssignedDTO) scrollableDTO.get(0);
				if(networkAssignedDTO.getIsarchived() == INetworkStatus.NOT_ARCHIVED)
				{
					networkAssignedDTO.setIsarchived(INetworkStatus.ARCHIVE_LEVEL_ONE);
					session.update(networkAssignedDTO);
				}
				else if(networkAssignedDTO.getIsarchived() == INetworkStatus.ARCHIVE_LEVEL_ONE)
				{
					networkAssignedDTO.setIsarchived(INetworkStatus.NOT_REACHABLE_ARCHIVE);
					session.update(networkAssignedDTO);
				}
			}

			transaction.commit();
			session.close();

			return "";
		}
		catch(Exception e)
		{
			if(transaction != null)
				transaction.rollback();

			logger.error("Error in archiving a network: ",e);
			throw new QuadrigaStorageException(e);
		}

	}

	@Override
	public INetworkOldVersion getNetworkOldVersionDetails(String networkId)	throws QuadrigaStorageException {
		try
		{
			INetworkOldVersion networkOldVersion =null;
			Query query = sessionFactory.getCurrentSession().getNamedQuery("NetworkAssignedDTO.findByNetworkid");
			query.setParameter("networkid", networkId);

			NetworkAssignedDTO networkAssignedDTO = (NetworkAssignedDTO) query.uniqueResult();

			if(networkAssignedDTO != null)
			{
				networkOldVersion = networkMapper.getNetworkOldVersion(networkAssignedDTO);
			}
			return networkOldVersion;

		}
		catch(Exception e)
		{
			logger.error("Error in fetching	 old version details: ",e);
			throw new QuadrigaStorageException(e);
		}
	}

	@Override
	public List<INetworkNodeInfo> getNetworkOldVersionTopNodes(String networkId) throws QuadrigaStorageException {
		List<INetworkNodeInfo> networkNodeList = new ArrayList<INetworkNodeInfo>();
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery(" from NetworkStatementsDTO n WHERE n.networkid = :networkid and istop = 1 and n.isarchived = 1");
			query.setParameter("networkid", networkId);

			List<NetworkStatementsDTO> listNetworkStatementsDTO = query.list();
			if(listNetworkStatementsDTO != null)
			{
				networkNodeList = networkMapper.getListOfNetworkNodeInfo(listNetworkStatementsDTO);
			}
			return networkNodeList;

		}
		catch(Exception e)
		{
			logger.error("Error in fetching	 old version details: ",e);
			throw new QuadrigaStorageException(e);
		}
	}

	@Override
	public INetwork getNetworkDetails(String networkId)	throws QuadrigaStorageException {
		INetwork network=networkFactory.createNetworkObject();
		try
		{
			NetworksDTO networkDTO = (NetworksDTO) sessionFactory.getCurrentSession().get(NetworksDTO.class, networkId);
			if(networkDTO != null)
			{
				network = networkMapper.getNetwork(networkDTO);
				network.setCreator(userManager.getUserDetails(networkDTO.getNetworkowner()));

				//Get the project details from the workspace id
				Query query = sessionFactory.getCurrentSession().getNamedQuery("ProjectWorkspaceDTO.findByWorkspaceid");
				query.setParameter("workspaceid", networkDTO.getWorkspaceid());
				ProjectWorkspaceDTO projectWorkspaceDTO = (ProjectWorkspaceDTO) query.uniqueResult();

				if(projectWorkspaceDTO!= null)
				{
					IProject project = retrieveProjectDetails.getProjectDetails(projectWorkspaceDTO.getProjectWorkspaceDTOPK().getProjectid());
					network.setProjectName(project.getName());

					//Get the workspace name
					String workspaceName=wsManager.getWorkspaceName(network.getWorkspaceid());
					network.setWorkspaceName(workspaceName);
				}
			}			
			return network;
		}
		catch(Exception e)
		{
			logger.error("Error in fetching	 network details: ",e);
			throw new QuadrigaStorageException(e);
		}
	}


}
