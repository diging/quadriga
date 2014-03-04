package edu.asu.spring.quadriga.dao;

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

import edu.asu.spring.quadriga.db.IDBConnectionEditorManager;
import edu.asu.spring.quadriga.db.IDBConnectionNetworkManager;
import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.INetworkNodeInfo;
import edu.asu.spring.quadriga.domain.INetworkOldVersion;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.dto.NetworkAssignedDTO;
import edu.asu.spring.quadriga.dto.NetworkStatementsDTO;
import edu.asu.spring.quadriga.dto.NetworksAnnotationsDTO;
import edu.asu.spring.quadriga.dto.NetworksDTO;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.NetworkDTOMapper;
import edu.asu.spring.quadriga.mapper.ProjectDTOMapper;
import edu.asu.spring.quadriga.mapper.WorkspaceDTOMapper;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;
import edu.asu.spring.quadriga.web.network.INetworkStatus;

/**
 * This class is responsible for Querying the MySQL database and fetch the class
 * objects related to Networks module
 * 
 * @author Ram Kumar Kumaresan
 * 
 */
@Repository
public class NetworkManagerDAO extends DAOConnectionManager implements IDBConnectionNetworkManager, IDBConnectionEditorManager {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private NetworkDTOMapper networkMapper;

	@Resource(name = "database_error_msgs")
	private Properties messages;

	@Autowired
	private ProjectDTOMapper projectMapper;

	@Autowired
	private WorkspaceDTOMapper workspaceMapper;

	@Autowired
	private IRetrieveProjectManager retrieveProjectDetails;

	@Autowired
	private IListWSManager wsManager;

	private static final Logger logger = LoggerFactory
			.getLogger(NetworkManagerDAO.class);



	/**
	 * {@inheritDoc}
	 */
	@Override
	public String addNetworkRequest(String networkName, IUser user,	String workspaceid) throws QuadrigaStorageException {

		if(networkName == null || user == null || workspaceid == null)
			throw new QuadrigaStorageException("Error in adding a network");

		String networkid = generateUniqueID();
		NetworksDTO networksDTO = networkMapper.getNetworksDTO(networkid, networkName, user.getUserName(), INetworkStatus.PENDING, workspaceid);

		try {
			sessionFactory.getCurrentSession().save(networksDTO);
			return networkid;
		} catch (Exception e) {
			logger.error("Error in adding a network request: ", e);
			throw new QuadrigaStorageException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String addNetworkStatement(String rowid,String networkId, String id, String type,
			String isTop, IUser user) throws QuadrigaStorageException {

		NetworkStatementsDTO networkStatementsDTO = networkMapper
				.getNetworkStatementsDTO(rowid,networkId, id, type, isTop,
						user.getUserName());

		try {
			sessionFactory.getCurrentSession().save(networkStatementsDTO);
			return null;
		} catch (Exception e) {
			logger.error("Error in adding a network request: ", e);
			throw new QuadrigaStorageException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public INetwork getNetwork(String networkId, IUser user) throws QuadrigaStorageException {
		INetwork network = null;
		try {
			Query query = sessionFactory.getCurrentSession().createQuery(" from NetworksDTO network where network.networkowner = :networkowner and network.networkid = :networkid");
			query.setParameter("networkowner", user.getUserName());
			query.setParameter("networkid", networkId);

			NetworksDTO networksDTO = (NetworksDTO) query.uniqueResult();
			if (networksDTO != null) {
				network = networkMapper.getNetwork(networksDTO);

				// Get the project id associated with the workspace id
				query = sessionFactory.getCurrentSession().getNamedQuery("ProjectWorkspaceDTO.findByWorkspaceid");
				query.setParameter("workspaceid", networksDTO.getWorkspaceid());
				ProjectWorkspaceDTO projectWorkspaceDTO = (ProjectWorkspaceDTO) query.uniqueResult();

				if (projectWorkspaceDTO != null) {
					// Get the project details
					if(projectWorkspaceDTO.getProjectDTO() != null)
						network.setProject(projectMapper.getProject(projectWorkspaceDTO.getProjectDTO()));

					// Get the workspace details
					if(projectWorkspaceDTO.getWorkspaceDTO() != null)
						network.setWorkspace(workspaceMapper.getWorkSpace(projectWorkspaceDTO.getWorkspaceDTO()));

				}
			}else{
				logger.info(" networksDTO is null ");
			}

			return network;
		} catch (Exception e) {
			logger.error("Error in fetching a network status: ", e);
			throw new QuadrigaStorageException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<INetwork> getNetworkList(IUser user)
			throws QuadrigaStorageException {
		List<INetwork> networkList = new ArrayList<INetwork>();
		try {
			Query query = sessionFactory.getCurrentSession().getNamedQuery(
					"NetworksDTO.findByNetworkowner");
			query.setParameter("networkowner", user.getUserName());

			List<NetworksDTO> listNetworksDTO = query.list();
			networkList = networkMapper.getListOfNetworks(listNetworksDTO);

			// Update project and workspace name for the network
			for (INetwork network : networkList) {
				// Get the project id associated with the workspace id
				query = sessionFactory.getCurrentSession().getNamedQuery(
						"ProjectWorkspaceDTO.findByWorkspaceid");
				query.setParameter("workspaceid", network.getWorkspaceid());
				ProjectWorkspaceDTO projectWorkspaceDTO = (ProjectWorkspaceDTO) query
						.uniqueResult();

				if (projectWorkspaceDTO != null) {
					// Get the project details
					if(projectWorkspaceDTO.getProjectDTO() != null)
						network.setProject(projectMapper.getProject(projectWorkspaceDTO.getProjectDTO()));

					// Get the workspace details
					if(projectWorkspaceDTO.getWorkspaceDTO() != null)
						network.setWorkspace(workspaceMapper.getWorkSpace(projectWorkspaceDTO.getWorkspaceDTO()));

				}
			}

			return networkList;
		} catch (Exception e) {
			logger.error("Error in fetching a network status: ", e);
			throw new QuadrigaStorageException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasNetworkName(String networkName, IUser user)
			throws QuadrigaStorageException {

		try {
			Query query = sessionFactory
					.getCurrentSession()
					.createQuery(
							" from NetworksDTO network where network.networkowner = :networkowner and network.networkname = :networkname");
			query.setParameter("networkowner", user.getUserName());
			query.setParameter("networkname", networkName);
			List<NetworksDTO> networksDTO = query.list();

			if (networksDTO != null) {
				if (networksDTO.size() > 0)
					return true;
			}
			return false;
		} catch (Exception e) {
			logger.error("Error in network name check: ", e);
			throw new QuadrigaStorageException(e);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String archiveNetworkStatement(String networkId, String id)
			throws QuadrigaStorageException {

		Transaction transaction = null;
		try {
			// Obtain a stateless session to overcome OutOfMemory Exception
			StatelessSession session = sessionFactory.openStatelessSession();
			transaction = session.beginTransaction();

			// Select only the rows matching the network id and obtain a
			// scrollable list
			ScrollableResults scrollableDTO = session
					.createQuery(
							"  FROM NetworkStatementsDTO n WHERE n.networkid = :networkid and n.id = :id")
							.setParameter("networkid", networkId)
							.setParameter("id", id).scroll(ScrollMode.FORWARD_ONLY);

			while (scrollableDTO.next()) {
				// Update the rows with archive id 1 or 0
				NetworkStatementsDTO networkStatementDTO = (NetworkStatementsDTO) scrollableDTO
						.get(0);
				if (networkStatementDTO.getIsarchived() == INetworkStatus.NOT_ARCHIVED) {
					networkStatementDTO
					.setIsarchived(INetworkStatus.ARCHIVE_LEVEL_ONE);
					session.update(networkStatementDTO);
				} else if (networkStatementDTO.getIsarchived() == INetworkStatus.ARCHIVE_LEVEL_ONE) {
					networkStatementDTO
					.setIsarchived(INetworkStatus.NOT_REACHABLE_ARCHIVE);
					session.update(networkStatementDTO);
				}
			}

			return "";
		} catch (Exception e) {
			logger.error("Error in archiving a network statement: ", e);
			throw new QuadrigaStorageException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<INetworkNodeInfo> getNetworkNodes(String networkId)
			throws QuadrigaStorageException {

		List<INetworkNodeInfo> networkNodeList = new ArrayList<INetworkNodeInfo>();

		try {
			Query query = sessionFactory.getCurrentSession().createQuery(" from NetworkStatementsDTO n WHERE n.networkid = :networkid");
			query.setParameter("networkid", networkId);

			List<NetworkStatementsDTO> listNetworkStatementsDTO = query.list();
			if (listNetworkStatementsDTO != null) {
				networkNodeList = networkMapper.getListOfNetworkNodeInfo(listNetworkStatementsDTO);
			}
			return networkNodeList;
		} catch (Exception e) {
			logger.error("Error in fetching network nodes: ", e);
			throw new QuadrigaStorageException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String archiveNetwork(String networkId)
			throws QuadrigaStorageException {
		Transaction transaction = null;
		try {
			// Obtain a stateless session to overcome OutOfMemory Exception
			StatelessSession session = sessionFactory.openStatelessSession();
			transaction = session.beginTransaction();

			// Select only the rows matching the network id and obtain a
			// scrollable list
			Query query = session.getNamedQuery("NetworkStatementsDTO.findByNetworkid");
			query.setParameter("networkid", networkId);
			ScrollableResults scrollableDTO = query.scroll(ScrollMode.FORWARD_ONLY);

			while (scrollableDTO.next()) {
				// Update the rows with archive id 1 or 0
				NetworkStatementsDTO networkStatementDTO = (NetworkStatementsDTO) scrollableDTO.get(0);
				if (networkStatementDTO.getIsarchived() == INetworkStatus.NOT_ARCHIVED) {
					networkStatementDTO.setIsarchived(INetworkStatus.ARCHIVE_LEVEL_ONE);
					session.update(networkStatementDTO);
				} else if (networkStatementDTO.getIsarchived() == INetworkStatus.ARCHIVE_LEVEL_ONE) {
					networkStatementDTO.setIsarchived(INetworkStatus.NOT_REACHABLE_ARCHIVE);
					session.update(networkStatementDTO);
				}
			}

			scrollableDTO = session.getNamedQuery("NetworkAssignedDTO.findByNetworkid").setParameter("networkid", networkId).scroll(ScrollMode.FORWARD_ONLY);
			while (scrollableDTO.next()) {
				// Update the rows with archive id 1 or 0
				NetworkAssignedDTO networkAssignedDTO = (NetworkAssignedDTO) scrollableDTO.get(0);
				if (networkAssignedDTO.getIsarchived() == INetworkStatus.NOT_ARCHIVED) {
					networkAssignedDTO.setIsarchived(INetworkStatus.ARCHIVE_LEVEL_ONE);
					session.update(networkAssignedDTO);

				} else if (networkAssignedDTO.getIsarchived() == INetworkStatus.ARCHIVE_LEVEL_ONE) {
					networkAssignedDTO.setIsarchived(INetworkStatus.NOT_REACHABLE_ARCHIVE);
					session.update(networkAssignedDTO);
				}
			}

			transaction.commit();
			session.close();

			return "";
		} catch (Exception e) {
			System.out
			.println("Exception occurred.....................................");
			if (transaction != null)
				transaction.rollback();

			logger.error("Error in archiving a network: ", e);
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
	public List<INetwork> getEditorNetworkList(IUser user)
			throws QuadrigaStorageException {
		List<INetwork> networkList = new ArrayList<INetwork>();

		try {

			String query1 = "Select n from NetworksDTO n where n.networkid not in (select na.networkAssignedDTOPK.networkid from " +
					"NetworkAssignedDTO na where na.isarchived=0) and";
			query1 += "((n.workspaceid in  " ;
			query1 += "(select distinct wc.workspaceCollaboratorDTOPK.workspaceid from WorkspaceCollaboratorDTO wc " +
					"where wc.workspaceCollaboratorDTOPK.collaboratoruser = :username and " +
					"wc.workspaceCollaboratorDTOPK.collaboratorrole in ('wscollab_role2','wscollab_role1'))) OR ";
			query1 += "(n.workspaceid in  " ;
			query1 += "(select pw.projectWorkspaceDTOPK.workspaceid from ProjectWorkspaceDTO pw " +
					"where pw.projectWorkspaceDTOPK.projectid in " +
					"(select distinct pc.projectCollaboratorDTOPK.projectid from ProjectCollaboratorDTO pc " +
					"where pc.projectCollaboratorDTOPK.collaboratoruser = :username and pc.projectCollaboratorDTOPK.collaboratorrole in " +
					"('collaborator_role4')))) OR ";
			query1 += "(n.workspaceid in  " ;
			query1 += "(select pw.projectWorkspaceDTOPK.workspaceid from ProjectWorkspaceDTO pw " +
					"where pw.projectWorkspaceDTOPK.projectid in " +
					"(select pe.projectEditorDTOPK.projectid from ProjectEditorDTO pe where pe.projectEditorDTOPK.editor = :username))) " +
					"or n.workspaceid in (select distinct we.workspaceEditorDTOPK.workspaceid from WorkspaceEditorDTO we " +
					"where we.workspaceEditorDTOPK.editor = :username)))))"
					+ "";
			Query query = sessionFactory
					.getCurrentSession()
					.createQuery(query1);

			query.setParameter("username", user.getUserName());

			List<NetworksDTO> listNetworksDTO = query.list();
			networkList = networkMapper.getListOfNetworks(listNetworksDTO);

			// Update project name and workspace name of the network
			for (INetwork network : networkList) {
				// Get the project id associated with the workspace id
				query = sessionFactory.getCurrentSession().getNamedQuery(
						"ProjectWorkspaceDTO.findByWorkspaceid");
				query.setParameter("workspaceid", network.getWorkspaceid());
				ProjectWorkspaceDTO projectWorkspaceDTO = (ProjectWorkspaceDTO) query
						.uniqueResult();

				if (projectWorkspaceDTO != null) {
					// Get the project details
					if(projectWorkspaceDTO.getProjectDTO() != null)
						network.setProject(projectMapper.getProject(projectWorkspaceDTO.getProjectDTO()));

					// Get the workspace details
					if(projectWorkspaceDTO.getWorkspaceDTO() != null)
						network.setWorkspace(workspaceMapper.getWorkSpace(projectWorkspaceDTO.getWorkspaceDTO()));

				}
			}

			return networkList;
		} catch (Exception e) {
			logger.error("Error in fetching network list of editors: ", e);
			throw new QuadrigaStorageException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String assignNetworkToUser(String networkId, IUser user)
			throws QuadrigaStorageException {
		NetworkAssignedDTO newtorkAssignedDTO = networkMapper
				.getNetworkAssignedDTO(networkId, user.getUserName(),
						INetworkStatus.PENDING, INetworkStatus.NOT_ARCHIVED);

		try {
			sessionFactory.getCurrentSession().save(newtorkAssignedDTO);
			return "";
		} catch (Exception e) {
			logger.error("Error in assigning network to user: ", e);
			throw new QuadrigaStorageException(e);
		}
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public String updateNetworkStatus(String networkId, String status)
			throws QuadrigaStorageException {
		try {
			Query query = sessionFactory.getCurrentSession().getNamedQuery(
					"NetworksDTO.findByNetworkid");
			query.setParameter("networkid", networkId);

			NetworksDTO networksDTO = (NetworksDTO) query.uniqueResult();
			networksDTO.setStatus(status);

			sessionFactory.getCurrentSession().update(networksDTO);

			return "";
		} catch (Exception e) {
			logger.error("Error in changing network status: ", e);
			throw new QuadrigaStorageException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String updateAssignedNetworkStatus(String networkId, String status)
			throws QuadrigaStorageException {
		try {
			Query query = sessionFactory
					.getCurrentSession()
					.createQuery(
							"FROM NetworkAssignedDTO n WHERE n.networkAssignedDTOPK.networkid = :networkid and n.isarchived = :isarchived");
			query.setParameter("networkid", networkId);
			query.setParameter("isarchived", INetworkStatus.NOT_ARCHIVED);

			NetworkAssignedDTO networkAssignedDTO = (NetworkAssignedDTO) query
					.uniqueResult();
			networkAssignedDTO.setStatus(status);

			sessionFactory.getCurrentSession().update(networkAssignedDTO);

			return "";
		} catch (Exception e) {
			logger.error("Error in changing network status: ", e);
			throw new QuadrigaStorageException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String addAnnotationToNetwork(String networkId, String id,
			String annotationText, String userId, String objectType)
					throws QuadrigaStorageException {
		NetworksAnnotationsDTO networkAnnotationsDTO = networkMapper
				.getNetworkAnnotationDTO(networkId, id, annotationText,
						"ANNOT_" + generateUniqueID(), userId, objectType);

		try {
			sessionFactory.getCurrentSession().save(networkAnnotationsDTO);
			return "";
		} catch (Exception e) {
			logger.error("Error in assigning network to user: ", e);
			throw new QuadrigaStorageException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override

	public List<NetworksAnnotationsDTO> getAnnotation(String type, String id, String userId,
			String networkId) throws QuadrigaStorageException {
		try {
			List<NetworksAnnotationsDTO> networkAnnotationsDTOList = new ArrayList<NetworksAnnotationsDTO>();
			Query query = sessionFactory
					.getCurrentSession()
					.createQuery(
							"from NetworksAnnotationsDTO n where n.objectid = :id and username = :username and networkid =:networkid and objecttype = :objecttype");
			query.setParameter("id", id);
			query.setParameter("username", userId);
			query.setParameter("objecttype", type);
			query.setParameter("networkid", networkId);

			networkAnnotationsDTOList =  query.list();
			return networkAnnotationsDTOList;

		} catch (Exception e) {
			logger.error("Error in fetching annotation: ", e);
			throw new QuadrigaStorageException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<NetworksAnnotationsDTO> getAllAnnotationOfNetwork(String userId,
			String networkId) throws QuadrigaStorageException {
		try {
			List<NetworksAnnotationsDTO> networkAnnotationsDTOList = new ArrayList<NetworksAnnotationsDTO>();
			Query query = sessionFactory
					.getCurrentSession()
					.createQuery(
							"from NetworksAnnotationsDTO n where username = :username and networkid =:networkid");
			query.setParameter("username", userId);
			query.setParameter("networkid", networkId);

			networkAnnotationsDTOList =  query.list();
			return networkAnnotationsDTOList;

		} catch (Exception e) {
			logger.error("Error in fetching annotation: ", e);
			throw new QuadrigaStorageException(e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String updateAnnotationToNetwork(String annotationId,
			String annotationText) throws QuadrigaStorageException {
		try {
			Query query = sessionFactory.getCurrentSession().getNamedQuery(
					"NetworksAnnotationsDTO.findByAnnotationId");
			query.setParameter("annotationid", annotationId);
			NetworksAnnotationsDTO annotation = (NetworksAnnotationsDTO) query
					.uniqueResult();
			annotation.setAnnotationtext(annotationText);
			sessionFactory.getCurrentSession().update(annotation);
			return "";
		} catch (Exception e) {
			logger.error("Error in fetching annotation: ", e);
			throw new QuadrigaStorageException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String updateNetworkName(String networkId, String networkName)
			throws QuadrigaStorageException {
		try {
			Query query = sessionFactory.getCurrentSession().getNamedQuery(
					"NetworksDTO.findByNetworkid");
			query.setParameter("networkid", networkId);

			NetworksDTO networksDTO = (NetworksDTO) query.uniqueResult();
			networksDTO.setNetworkname(networkName);

			sessionFactory.getCurrentSession().update(networksDTO);

			return "success";
		} catch (Exception e) {
			logger.error("Error in changing network status: ", e);
			throw new QuadrigaStorageException(e);
		}
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<INetwork> getNetworks(String projectid)	throws QuadrigaStorageException {
		if(projectid == null || projectid.equals(""))
			return null;

		//Create a query to get all projects
		Query query = sessionFactory.getCurrentSession().getNamedQuery("ProjectWorkspaceDTO.findByProjectid");
		query.setParameter("projectid", projectid);

		List<ProjectWorkspaceDTO> projectWorkspaceDTOList = query.list();
		for(ProjectWorkspaceDTO projectWorkspaceDTO : projectWorkspaceDTOList){
			System.out.println(projectWorkspaceDTO.getProjectDTO().getProjectname());
		}

		List<INetwork> networkList = new ArrayList<INetwork>();

		//If there are a list of projects, get all the networks using the workspace ids

		for(ProjectWorkspaceDTO projectWorkspaceDTO : projectWorkspaceDTOList){
			if (projectWorkspaceDTO != null) {
				//String workspaceid1 = projectWorkspaceDTO.getWorkspaceDTO().getWorkspaceid();
				//System.out.println(workspaceid1);
				String workspaceid1 = projectWorkspaceDTO.getProjectWorkspaceDTOPK().getWorkspaceid();
				Query queryNetworks = sessionFactory.getCurrentSession().getNamedQuery("NetworksDTO.findByWorkspaceid");
				queryNetworks.setParameter("workspaceid", workspaceid1);

				List<NetworksDTO> networksDTOList = queryNetworks.list();

				//Add the networks to the list
				if(networksDTOList != null)
				{
					//networkList = new ArrayList<INetwork>();
					networkList.addAll(networkMapper.getListOfNetworks(networksDTOList));
				}
			}
		}

		for (INetwork network : networkList) {
			// Get the project id associated with the workspace id
			query = sessionFactory.getCurrentSession().getNamedQuery(
					"ProjectWorkspaceDTO.findByWorkspaceid");
			query.setParameter("workspaceid", network.getWorkspaceid());
			ProjectWorkspaceDTO projectWorkspaceDTO = (ProjectWorkspaceDTO) query
					.uniqueResult();

			if (projectWorkspaceDTO != null) {
				// Get the project details
				if(projectWorkspaceDTO.getProjectDTO() != null)
					network.setProject(projectMapper.getProject(projectWorkspaceDTO.getProjectDTO()));

				// Get the workspace details
				if(projectWorkspaceDTO.getWorkspaceDTO() != null)
					network.setWorkspace(workspaceMapper.getWorkSpace(projectWorkspaceDTO.getWorkspaceDTO()));

			}
		}


		return networkList;


	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<INetworkOldVersion> getNetworkVersions(String networkId, int archiveLevel) throws QuadrigaStorageException {
		try {
			List<INetworkOldVersion> networkOldVersionsList = null;
			Query query = sessionFactory.getCurrentSession().createQuery(" from NetworkAssignedDTO n WHERE n.networkAssignedDTOPK.networkid = :networkid and n.isarchived= :isarchived");
			query.setParameter("networkid", networkId);
			query.setParameter("isarchived", archiveLevel);

			List<NetworkAssignedDTO> networkAssignedDTOList = query.list();

			if(networkAssignedDTOList != null)
			{
				networkOldVersionsList = new ArrayList<INetworkOldVersion>();
				for(NetworkAssignedDTO networkAssignedDTO : networkAssignedDTOList)
				{
					networkOldVersionsList.add(networkMapper.getNetworkOldVersion(networkAssignedDTO));
				}
			}

			return networkOldVersionsList;
		} catch (Exception e) {
			logger.error("Error in fetching	 old version details: ", e);
			throw new QuadrigaStorageException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<INetwork> getNetworksOfUser(IUser user, String networkStatus) throws QuadrigaStorageException {
		List<INetwork> networkList = null;

		try {
			Query query = sessionFactory.getCurrentSession().createQuery("from NetworksDTO n where n.networkid in (Select na.networkAssignedDTOPK.networkid from NetworkAssignedDTO na where na.networkAssignedDTOPK.assigneduser = :assigneduser) and status = :status");
			query.setParameter("assigneduser", user.getUserName());
			query.setParameter("status", networkStatus);

			List<NetworksDTO> listNetworksDTO = query.list();
			networkList = networkMapper.getListOfNetworks(listNetworksDTO);

			// Update project name and workspace name of the network
			for (INetwork network : networkList) {
				// Get the project id associated with the workspace id
				query = sessionFactory.getCurrentSession().getNamedQuery("ProjectWorkspaceDTO.findByWorkspaceid");
				query.setParameter("workspaceid", network.getWorkspaceid());
				ProjectWorkspaceDTO projectWorkspaceDTO = (ProjectWorkspaceDTO) query.uniqueResult();

				if (projectWorkspaceDTO != null) {
					// Get the project details
					if(projectWorkspaceDTO.getProjectDTO() != null)
						network.setProject(projectMapper.getProject(projectWorkspaceDTO.getProjectDTO()));

					// Get the workspace details
					if(projectWorkspaceDTO.getWorkspaceDTO() != null)
						network.setWorkspace(workspaceMapper.getWorkSpace(projectWorkspaceDTO.getWorkspaceDTO()));

				}
			}

			return networkList;
		} catch (Exception e) {
			logger.error("Error in fetching network of user: ", e);
			throw new QuadrigaStorageException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<INetwork> getNetworkListOfOtherEditors(IUser user, List<String> networkStatus)	throws QuadrigaStorageException {
		List<INetwork> networkList = new ArrayList<INetwork>();

		try {

			// Create the query to get the list of networks
			Query query = sessionFactory.getCurrentSession().createQuery(
					"from NetworksDTO n where n.networkid in (Select na.networkAssignedDTOPK.networkid from NetworkAssignedDTO na where na.networkAssignedDTOPK.assigneduser <> :assigneduser) and status in (:status)");
			query.setParameter("assigneduser", user.getUserName());
			query.setParameterList("status", networkStatus);

			List<NetworksDTO> listNetworksDTO = query.list();
			networkList = networkMapper.getListOfNetworks(listNetworksDTO);

			// Update project name and workspace name of the network
			for (INetwork network : networkList) {
				// Get the assigned user name for the network
				query = sessionFactory.getCurrentSession().createQuery("from NetworkAssignedDTO na where na.networkAssignedDTOPK.networkid = :networkid and status = :status");
				query.setParameter("networkid", network.getId());
				query.setParameter("status", INetworkStatus.ASSIGNED);
				NetworkAssignedDTO networkAssignedDTO = (NetworkAssignedDTO) query.uniqueResult();
				if(networkAssignedDTO != null)
					network.setAssignedUser(networkAssignedDTO.getNetworkAssignedDTOPK().getAssigneduser());


				// Get the project id associated with the workspace id
				query = sessionFactory.getCurrentSession().getNamedQuery("ProjectWorkspaceDTO.findByWorkspaceid");
				query.setParameter("workspaceid", network.getWorkspaceid());
				ProjectWorkspaceDTO projectWorkspaceDTO = (ProjectWorkspaceDTO) query.uniqueResult();

				if (projectWorkspaceDTO != null) {
					// Get the project details
					if(projectWorkspaceDTO.getProjectDTO() != null)
						network.setProject(projectMapper.getProject(projectWorkspaceDTO.getProjectDTO()));

					// Get the workspace details
					if(projectWorkspaceDTO.getWorkspaceDTO() != null)
						network.setWorkspace(workspaceMapper.getWorkSpace(projectWorkspaceDTO.getWorkspaceDTO()));

				}
			}

			return networkList;
		} catch (Exception e) {
			logger.error("Error in fetching network of user: ", e);
			throw new QuadrigaStorageException(e);
		}
	}

	/**
	 * 
	 * @param network
	 * @param username
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public int updateNetwork(INetwork network, String username) throws QuadrigaStorageException
	{
		if(network == null || username == null || username.equals(""))
			return FAILURE;

		try
		{
			NetworksDTO networksDTO = networkMapper.getNetworksDTO(network.getId(), network.getName(), username, network.getStatus(), network.getWorkspaceid());
			sessionFactory.getCurrentSession().saveOrUpdate(networksDTO);
			return SUCCESS;
		}
		catch(Exception e)
		{
			logger.error("update network method :",e);
			throw new QuadrigaStorageException(e);
		}
	}

}
