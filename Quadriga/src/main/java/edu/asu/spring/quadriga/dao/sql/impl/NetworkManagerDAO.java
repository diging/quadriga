package edu.asu.spring.quadriga.dao.sql.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

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
import edu.asu.spring.quadriga.db.IDBConnectionEditorManager;
import edu.asu.spring.quadriga.db.IDBConnectionNetworkManager;
import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.INetworkNodeInfo;
import edu.asu.spring.quadriga.domain.INetworkOldVersion;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.impl.NetworkFactory;
import edu.asu.spring.quadriga.dto.NetworkAssignedDTO;
import edu.asu.spring.quadriga.dto.NetworkStatementsDTO;
import edu.asu.spring.quadriga.dto.NetworksAnnotationsDTO;
import edu.asu.spring.quadriga.dto.NetworksDTO;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.NetworkDTOMapper;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;
import edu.asu.spring.quadriga.web.network.INetworkStatus;

/**
 * This class is responsible for Querying the MySQL database
 * and fetch the class objects related to Networks module
 * 
 * @author Ram Kumar Kumaresan
 *
 */
@Repository
public class NetworkManagerDAO extends DAOConnectionManager implements IDBConnectionNetworkManager,IDBConnectionEditorManager
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

	private static final Logger logger = LoggerFactory.getLogger(NetworkManagerDAO.class);

	@Autowired
	private NetworkFactory networkFactory;

	/**
	 * {@inheritDoc} 
	 */
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

	/**
	 * {@inheritDoc} 
	 */
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

	/**
	 * {@inheritDoc} 
	 */
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

				//Get the project id associated with the workspace id
				query = sessionFactory.getCurrentSession().getNamedQuery("ProjectWorkspaceDTO.findByWorkspaceid");
				query.setParameter("workspaceid", networksDTO.getWorkspaceid());
				ProjectWorkspaceDTO projectWorkspaceDTO = (ProjectWorkspaceDTO) query.uniqueResult();

				if(projectWorkspaceDTO!= null)
				{
					//Get the project details
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

	/**
	 * {@inheritDoc} 
	 */
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
				//Get the project id associated with the workspace id
				query = sessionFactory.getCurrentSession().getNamedQuery("ProjectWorkspaceDTO.findByWorkspaceid");
				query.setParameter("workspaceid", network.getWorkspaceid());
				ProjectWorkspaceDTO projectWorkspaceDTO = (ProjectWorkspaceDTO) query.uniqueResult();

				if(projectWorkspaceDTO!= null)
				{
					//Get the project details
					IProject project = retrieveProjectDetails.getProjectDetails(projectWorkspaceDTO.getProjectWorkspaceDTOPK().getProjectid());
					network.setProjectName(project.getName());

					//Get the workspace name
					String workspaceName=wsManager.getWorkspaceName(network.getWorkspaceid());
					network.setWorkspaceName(workspaceName);
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

	/**
	 * {@inheritDoc} 
	 */
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
	public List<String> getNetworksForProjectId(String projectid) throws QuadrigaStorageException{
		Query query = sessionFactory.getCurrentSession().getNamedQuery("ProjectWorkspaceDTO.findByProjectid");
		query.setParameter("projectid", projectid);
		List<ProjectWorkspaceDTO> projectWorkspaceDTOList = query.list();
		List<String> NetworkList = null;
		if(projectWorkspaceDTOList!=null){
				for(ProjectWorkspaceDTO projectWorkspaceDTO:projectWorkspaceDTOList)
				{
					String wId = projectWorkspaceDTO.getProjectWorkspaceDTOPK().getWorkspaceid();
					Query query1 = sessionFactory.getCurrentSession().getNamedQuery("NetworksDTO.findByWorkspaceid");
					query.setParameter("workspaceid", wId);
					List<NetworksDTO> networksDTOList = query1.list();
					NetworkList = new ArrayList<String>();
					for(NetworksDTO networksDTO:networksDTOList){
						String network = networksDTO.getNetworkname();
						NetworkList.add(network);
					}
				}	
		}
		
		return NetworkList;
	}
	
	
	/**
	 * {@inheritDoc} 
	 */
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

	/**
	 * {@inheritDoc} 
	 */
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

	/**
	 * {@inheritDoc} 
	 */
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

	/**
	 * {@inheritDoc} 
	 */
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

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public String archiveNetwork(String networkId) throws QuadrigaStorageException {
		Transaction transaction = null;
		try
		{
			//Obtain a stateless session to overcome OutOfMemory Exception
			StatelessSession  session = sessionFactory.openStatelessSession();
			transaction = session.beginTransaction();

			//Select only the rows matching the network id and obtain a scrollable list
			Query query = session.getNamedQuery("NetworkStatementsDTO.findByNetworkid");
			query.setParameter("networkid", networkId);
			ScrollableResults scrollableDTO = query.scroll(ScrollMode.FORWARD_ONLY);

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

			scrollableDTO = session.getNamedQuery("NetworkAssignedDTO.findByNetworkid").setParameter("networkid", networkId).scroll(ScrollMode.FORWARD_ONLY);
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
			System.out.println("Exception occurred.....................................");
			if(transaction != null)
				transaction.rollback();

			logger.error("Error in archiving a network: ",e);
			throw new QuadrigaStorageException(e);
		}

	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public INetworkOldVersion getNetworkOldVersionDetails(String networkId)	throws QuadrigaStorageException {
		try
		{
			INetworkOldVersion networkOldVersion =null;
			Query query = sessionFactory.getCurrentSession().createQuery(" from NetworkAssignedDTO n WHERE n.networkAssignedDTOPK.networkid = :networkid and n.isarchived= :isarchived");
			query.setParameter("networkid", networkId);
			query.setParameter("isarchived", INetworkStatus.ARCHIVE_LEVEL_ONE);

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

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public List<INetworkNodeInfo> getNetworkOldVersionTopNodes(String networkId) throws QuadrigaStorageException {
		List<INetworkNodeInfo> networkNodeList = new ArrayList<INetworkNodeInfo>();
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery(" from NetworkStatementsDTO n WHERE n.networkid = :networkid and istop = 1 and n.isarchived = :isarchived");
			query.setParameter("networkid", networkId);
			query.setParameter("isarchived", INetworkStatus.ARCHIVE_LEVEL_ONE);

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

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public INetwork getNetworkDetails(String networkId)	throws QuadrigaStorageException {
		INetwork network=networkFactory.createNetworkObject();
		try
		{
			NetworksDTO networkDTO = (NetworksDTO) sessionFactory.getCurrentSession().get(NetworksDTO.class, networkId);
			if(networkDTO != null)
			{
				network = networkMapper.getNetwork(networkDTO);

				//Get the project id associated with the workspace id
				Query query = sessionFactory.getCurrentSession().getNamedQuery("ProjectWorkspaceDTO.findByWorkspaceid");
				query.setParameter("workspaceid", networkDTO.getWorkspaceid());
				ProjectWorkspaceDTO projectWorkspaceDTO = (ProjectWorkspaceDTO) query.uniqueResult();

				if(projectWorkspaceDTO!= null)
				{
					//Get the project details
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

	/******************************************************************************************************
	 * 
	 * The following methods deal with editor section of the network module
	 * 
	 *******************************************************************************************************/
	
	/**
	 * {@inheritDoc} 
	 */
	@Override
	public List<INetwork> getEditorNetworkList(IUser user) throws QuadrigaStorageException{
		List<INetwork> networkList = new ArrayList<INetwork>();

		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("Select n from NetworksDTO n where n.networkid not in (select na.networkAssignedDTOPK.networkid from NetworkAssignedDTO na where na.isarchived='0') and (n.workspaceid in ( select distinct wc.workspaceCollaboratorDTOPK.workspaceid from WorkspaceCollaboratorDTO wc where wc.workspaceCollaboratorDTOPK.username = :username and wc.workspaceCollaboratorDTOPK.collaboratorrole in ('wscollab_role2','wscollab_role1') and wc.workspaceCollaboratorDTOPK.workspaceid in (select pw.projectWorkspaceDTOPK.workspaceid from ProjectWorkspaceDTO pw where pw.projectWorkspaceDTOPK.projectid in (select distinct pc.projectCollaboratorDTOPK.projectid from ProjectCollaboratorDTO pc where pc.projectCollaboratorDTOPK.collaboratoruser = :username and pc.projectCollaboratorDTOPK.collaboratorrole in ('collaborator_role4')) or pw.projectWorkspaceDTOPK.projectid in (select pe.projectEditorDTOPK.projectid from ProjectEditorDTO pe where pe.projectEditorDTOPK.owner = :username))) or n.workspaceid in (select distinct we.workspaceEditorDTOPK.workspaceid from WorkspaceEditorDTO we where we.workspaceEditorDTOPK.owner = :username)))");
			query.setParameter("username", user.getUserName());
			
			List<NetworksDTO> listNetworksDTO = query.list();
			networkList = networkMapper.getListOfNetworks(listNetworksDTO);

			//Update project name and workspace name of the network
			for(INetwork network : networkList)
			{				
				//Get the project id associated with the workspace id
				query = sessionFactory.getCurrentSession().getNamedQuery("ProjectWorkspaceDTO.findByWorkspaceid");
				query.setParameter("workspaceid", network.getWorkspaceid());
				ProjectWorkspaceDTO projectWorkspaceDTO = (ProjectWorkspaceDTO) query.uniqueResult();

				if(projectWorkspaceDTO!= null)
				{
					//Get the project details
					IProject project = retrieveProjectDetails.getProjectDetails(projectWorkspaceDTO.getProjectWorkspaceDTOPK().getProjectid());
					network.setProjectName(project.getName());

					//Get the workspace name
					String workspaceName=wsManager.getWorkspaceName(network.getWorkspaceid());
					network.setWorkspaceName(workspaceName);
				}
			}

			return networkList;
		}
		catch(Exception e)
		{
			logger.error("Error in fetching network list of editors: ",e);
			throw new QuadrigaStorageException(e);
		}
	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public String assignNetworkToUser(String networkId, IUser user) throws QuadrigaStorageException{
		NetworkAssignedDTO newtorkAssignedDTO = networkMapper.getNetworkAssignedDTO(networkId, user.getUserName(), INetworkStatus.PENDING, INetworkStatus.NOT_ARCHIVED);

		try
		{
			sessionFactory.getCurrentSession().save(newtorkAssignedDTO);	
			return "";
		}
		catch(Exception e)
		{
			logger.error("Error in assigning network to user: ",e);
			throw new QuadrigaStorageException(e);
		}	
	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public List<INetwork> getAssignedNetworkListOfOtherEditors(IUser user) throws QuadrigaStorageException{
		List<INetwork> networkList = new ArrayList<INetwork>();

		try
		{
			//Create the query to get the list of networks that are not for this user and are not assigned/pending
			Query query = sessionFactory.getCurrentSession().createQuery("from NetworksDTO n where n.networkid in (Select na.networkAssignedDTOPK.networkid from NetworkAssignedDTO na where na.networkAssignedDTOPK.assigneduser <> :assigneduser) and status = :status");
			query.setParameter("assigneduser", user.getUserName());
			query.setParameter("status", INetworkStatus.ASSIGNED);

			List<NetworksDTO> listNetworksDTO = query.list();
			networkList = networkMapper.getListOfNetworks(listNetworksDTO);

			//Update project name, workspace name and assigned username of the network
			for(INetwork network : networkList)
			{				
				//Get the assigned user name for the network
				query = sessionFactory.getCurrentSession().createQuery("from NetworkAssignedDTO na where na.networkAssignedDTOPK.networkid = :networkid and status = :status");
				query.setParameter("networkid", network.getId());
				query.setParameter("status", INetworkStatus.ASSIGNED);
				NetworkAssignedDTO networkAssignedDTO = (NetworkAssignedDTO) query.uniqueResult();
				network.setAssignedUser(networkAssignedDTO.getNetworkAssignedDTOPK().getAssigneduser());

				//Get the project id associated with the workspace id
				query = sessionFactory.getCurrentSession().getNamedQuery("ProjectWorkspaceDTO.findByWorkspaceid");
				query.setParameter("workspaceid", network.getWorkspaceid());
				ProjectWorkspaceDTO projectWorkspaceDTO = (ProjectWorkspaceDTO) query.uniqueResult();

				if(projectWorkspaceDTO!= null)
				{
					//Get the project details
					IProject project = retrieveProjectDetails.getProjectDetails(projectWorkspaceDTO.getProjectWorkspaceDTOPK().getProjectid());
					network.setProjectName(project.getName());

					//Get the workspace name
					String workspaceName=wsManager.getWorkspaceName(network.getWorkspaceid());
					network.setWorkspaceName(workspaceName);
				}
			}

			return networkList;
		}
		catch(Exception e)
		{
			logger.error("Error in fetching network of user: ",e);
			throw new QuadrigaStorageException(e);
		}
	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public List<INetwork> getfinishedNetworkListOfOtherEditors(IUser user) throws QuadrigaStorageException{
		List<INetwork> networkList = new ArrayList<INetwork>();

		try
		{
			//Set up the roles to be used in the HQL query
			List<String> roles = new ArrayList<String>();
			roles.add(INetworkStatus.ASSIGNED);
			roles.add(INetworkStatus.PENDING);

			//Create the query to get the list of networks that are not for this user and are not assigned/pending
			Query query = sessionFactory.getCurrentSession().createQuery("from NetworksDTO n where n.networkid in (Select na.networkid from NetworkAssignedDTO na where na.networkAssignedDTOPK.assigneduser <> :assigneduser) and status not in (:status)");
			query.setParameter("assigneduser", user.getUserName());
			query.setParameterList("status", roles);

			List<NetworksDTO> listNetworksDTO = query.list();
			networkList = networkMapper.getListOfNetworks(listNetworksDTO);

			//Update project name and workspace name of the network
			for(INetwork network : networkList)
			{				
				//Get the project id associated with the workspace id
				query = sessionFactory.getCurrentSession().getNamedQuery("ProjectWorkspaceDTO.findByWorkspaceid");
				query.setParameter("workspaceid", network.getWorkspaceid());
				ProjectWorkspaceDTO projectWorkspaceDTO = (ProjectWorkspaceDTO) query.uniqueResult();

				if(projectWorkspaceDTO!= null)
				{
					//Get the project details
					IProject project = retrieveProjectDetails.getProjectDetails(projectWorkspaceDTO.getProjectWorkspaceDTOPK().getProjectid());
					network.setProjectName(project.getName());

					//Get the workspace name
					String workspaceName=wsManager.getWorkspaceName(network.getWorkspaceid());
					network.setWorkspaceName(workspaceName);
				}
			}

			return networkList;
		}
		catch(Exception e)
		{
			logger.error("Error in fetching network of user: ",e);
			throw new QuadrigaStorageException(e);
		}
	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public List<INetwork> getAssignNetworkOfUser(IUser user) throws QuadrigaStorageException{
		List<INetwork> networkList = new ArrayList<INetwork>();

		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("from NetworksDTO n where n.networkid in (Select na.networkAssignedDTOPK.networkid from NetworkAssignedDTO na where na.networkAssignedDTOPK.assigneduser = :assigneduser) and status = :status");
			query.setParameter("assigneduser", user.getUserName());
			query.setParameter("status", INetworkStatus.ASSIGNED);

			List<NetworksDTO> listNetworksDTO = query.list();
			networkList = networkMapper.getListOfNetworks(listNetworksDTO);

			//Update project name workspace name and old version of the network
			for(INetwork network : networkList)
			{
				//Get the old version of the network
				INetworkOldVersion networkOldVersion = this.getNetworkOldVersionDetails(network.getId());
				network.setNetworkOldVersion(networkOldVersion);

				//Get the project id associated with the workspace id
				query = sessionFactory.getCurrentSession().getNamedQuery("ProjectWorkspaceDTO.findByWorkspaceid");
				query.setParameter("workspaceid", network.getWorkspaceid());
				ProjectWorkspaceDTO projectWorkspaceDTO = (ProjectWorkspaceDTO) query.uniqueResult();

				if(projectWorkspaceDTO!= null)
				{
					//Get the project details
					IProject project = retrieveProjectDetails.getProjectDetails(projectWorkspaceDTO.getProjectWorkspaceDTOPK().getProjectid());
					network.setProjectName(project.getName());

					//Get the workspace name
					String workspaceName=wsManager.getWorkspaceName(network.getWorkspaceid());
					network.setWorkspaceName(workspaceName);
				}
			}

			return networkList;
		}
		catch(Exception e)
		{
			logger.error("Error in fetching network of user: ",e);
			throw new QuadrigaStorageException(e);
		}
	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public List<INetwork> getApprovedNetworkOfUser(IUser user) throws QuadrigaStorageException{
		List<INetwork> networkList = new ArrayList<INetwork>();

		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("from NetworksDTO n where n.networkid in (Select na.networkAssignedDTOPK.networkid from NetworkAssignedDTO na where na.networkAssignedDTOPK.assigneduser = :assigneduser) and status = :status");
			query.setParameter("assigneduser", user.getUserName());
			query.setParameter("status", INetworkStatus.APPROVED);

			List<NetworksDTO> listNetworksDTO = query.list();
			networkList = networkMapper.getListOfNetworks(listNetworksDTO);

			//Update project name and workspace name of the network
			for(INetwork network : networkList)
			{				
				//Get the project id associated with the workspace id
				query = sessionFactory.getCurrentSession().getNamedQuery("ProjectWorkspaceDTO.findByWorkspaceid");
				query.setParameter("workspaceid", network.getWorkspaceid());
				ProjectWorkspaceDTO projectWorkspaceDTO = (ProjectWorkspaceDTO) query.uniqueResult();

				if(projectWorkspaceDTO!= null)
				{
					//Get the project details
					IProject project = retrieveProjectDetails.getProjectDetails(projectWorkspaceDTO.getProjectWorkspaceDTOPK().getProjectid());
					network.setProjectName(project.getName());

					//Get the workspace name
					String workspaceName=wsManager.getWorkspaceName(network.getWorkspaceid());
					network.setWorkspaceName(workspaceName);
				}
			}

			return networkList;
		}
		catch(Exception e)
		{
			logger.error("Error in fetching network of user: ",e);
			throw new QuadrigaStorageException(e);
		}
	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public List<INetwork> getRejectedNetworkOfUser(IUser user) throws QuadrigaStorageException{
		List<INetwork> networkList = new ArrayList<INetwork>();

		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("from NetworksDTO n where n.networkid in (Select na.networkAssignedDTOPK.networkid from NetworkAssignedDTO na where na.networkAssignedDTOPK.assigneduser = :assigneduser) and status = :status");
			query.setParameter("assigneduser", user.getUserName());
			query.setParameter("status", INetworkStatus.REJECTED);

			List<NetworksDTO> listNetworksDTO = query.list();
			networkList = networkMapper.getListOfNetworks(listNetworksDTO);

			//Update project name and workspace name of the network
			for(INetwork network : networkList)
			{				
				//Get the project id associated with the workspace id
				query = sessionFactory.getCurrentSession().getNamedQuery("ProjectWorkspaceDTO.findByWorkspaceid");
				query.setParameter("workspaceid", network.getWorkspaceid());
				ProjectWorkspaceDTO projectWorkspaceDTO = (ProjectWorkspaceDTO) query.uniqueResult();

				if(projectWorkspaceDTO!= null)
				{
					//Get the project details
					IProject project = retrieveProjectDetails.getProjectDetails(projectWorkspaceDTO.getProjectWorkspaceDTOPK().getProjectid());
					network.setProjectName(project.getName());

					//Get the workspace name
					String workspaceName=wsManager.getWorkspaceName(network.getWorkspaceid());
					network.setWorkspaceName(workspaceName);
				}
			}

			return networkList;
		}
		catch(Exception e)
		{
			logger.error("Error in fetching network of user: ",e);
			throw new QuadrigaStorageException(e);
		}
	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public String updateNetworkStatus(String networkId, String status) throws QuadrigaStorageException {
		try
		{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("NetworksDTO.findByNetworkid");
			query.setParameter("networkid", networkId);

			NetworksDTO networksDTO = (NetworksDTO) query.uniqueResult();
			networksDTO.setStatus(status);

			sessionFactory.getCurrentSession().update(networksDTO);

			return "";
		}
		catch(Exception e)
		{
			logger.error("Error in changing network status: ",e);
			throw new QuadrigaStorageException(e);
		}
	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public String updateAssignedNetworkStatus(String networkId, String status) throws QuadrigaStorageException {
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("FROM NetworkAssignedDTO n WHERE n.networkAssignedDTOPK.networkid = :networkid and n.isarchived = :isarchived");
			query.setParameter("networkid", networkId);
			query.setParameter("isarchived", INetworkStatus.NOT_ARCHIVED);

			NetworkAssignedDTO networkAssignedDTO = (NetworkAssignedDTO) query.uniqueResult();
			networkAssignedDTO.setStatus(status);

			sessionFactory.getCurrentSession().update(networkAssignedDTO);

			return "";
		}
		catch(Exception e)
		{
			logger.error("Error in changing network status: ",e);
			throw new QuadrigaStorageException(e);
		}
	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public String addAnnotationToNetwork(String networkId, String id, String annotationText, String userId,String objectType) throws QuadrigaStorageException {
		NetworksAnnotationsDTO networkAnnotationsDTO = networkMapper.getNetworkAnnotationDTO(networkId, id, annotationText, "ANNOT_"+generateUniqueID(), userId, objectType);

		try
		{
			sessionFactory.getCurrentSession().save(networkAnnotationsDTO);	
			return "";
		}
		catch(Exception e)
		{
			logger.error("Error in assigning network to user: ",e);
			throw new QuadrigaStorageException(e);
		}	
	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public String[] getAnnotation(String type, String id,String userId,String networkId) throws QuadrigaStorageException{
		String[] annotationArray = new String[2];
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("from NetworksAnnotationsDTO n where n.id = :id and username = :username and networkid =:networkid and objecttype = :objecttype");
			query.setParameter("id", id);
			query.setParameter("username", userId);
			query.setParameter("objecttype", type);
			query.setParameter("networkid", networkId);

			NetworksAnnotationsDTO networkAnnotationsDTO = (NetworksAnnotationsDTO) query.uniqueResult();
			if(networkAnnotationsDTO !=null){
				annotationArray[0] = networkAnnotationsDTO.getAnnotationtext();
				annotationArray[1] = networkAnnotationsDTO.getAnnotationid();
			}
			return annotationArray;
			
		}
		catch(Exception e)
		{
			logger.error("Error in fetching annotation: ",e);
			throw new QuadrigaStorageException(e);
		}
	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public String updateAnnotationToNetwork(String annotationId,String annotationText ) throws QuadrigaStorageException {
		try
		{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("NetworksAnnotationsDTO.findByAnnotationId");
			query.setParameter("annotationid", annotationId);
			NetworksAnnotationsDTO annotation = (NetworksAnnotationsDTO) query.uniqueResult();
			annotation.setAnnotationtext(annotationText);
			sessionFactory.getCurrentSession().update(annotation);
			return "";
		}
		catch(Exception e)
		{
			logger.error("Error in fetching annotation: ",e);
			throw new QuadrigaStorageException(e);
		}
	}

	@Override
	public String updateNetworkName(String networkId,String networkName) throws QuadrigaStorageException {
			try
			{
				Query query = sessionFactory.getCurrentSession().getNamedQuery("NetworksDTO.findByNetworkid");
				query.setParameter("networkid", networkId);

				NetworksDTO networksDTO = (NetworksDTO) query.uniqueResult();
				networksDTO.setNetworkname(networkName);

				sessionFactory.getCurrentSession().update(networksDTO);

				return "success";
			}
			catch(Exception e)
			{
				logger.error("Error in changing network status: ",e);
				throw new QuadrigaStorageException(e);
			}
	}

}
