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
	 * Add a new network into a workspace. Creates a unique Network ID  and assigns the user as owner to the network object. 
	 * It then adds the network into the Workspace. The method uses Hibernate Framework to perform the database operations.
	 *  
	 * @param networkName 					Name of the newly added network.
	 * @param user							Owner of the network.
	 * @param workspaceid					Workspace id to which the network would be assigned.
	 * @return								Network id if the operation was successful. Exception for all other cases.
	 * @throws QuadrigaStorageException		Exception will be thrown when the input parameters do not satisfy the system/database constraints or due to database connection troubles.
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
	 * Add Network statements (like AE, RE ) associated to networks. This method would also add more info of the network statement like top node, type of node.
	 * Also adds all the statements in the XML for further use cases.
	 * The method uses Hibernate Framework to perform the database operations.
	 * 
	 * @param networkId						ID of network.
	 * @param id							ID of network statement.
	 * @param type							Type of network statement, AE - Appellation event, RE - Relation event.
	 * @param isTop							{@link Boolean} To whether the network statement is in starting point xml. 
	 * @param user							Owner of the network.
	 * @return								Null if the operation was successful. Exception for all other cases.
	 * @throws QuadrigaStorageException		Exception will be thrown when the input parameters do not satisfy the system/database constraints or due to database connection troubles.
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
	 * Get {@link INetwork} object for which the user is an owner, using the network ID. 
	 * The method uses Hibernate Framework to perform the database operations.
	 * 
	 * @param networkid							ID of network.
	 * @param user								Owner of the network.
	 * @return									return {@link INetwork} object associated to the networkid, user. Null if the there are no networks for the input constraints.
	 * @throws QuadrigaStorageException			Exception will be thrown when the input parameters do not satisfy the system/database constraints or due to database connection troubles.
	 */
	@Override
	public INetwork getNetwork(String networkId) throws QuadrigaStorageException {
		INetwork network = null;
		try {
			Query query = sessionFactory.getCurrentSession().createQuery(" from NetworksDTO network where network.networkid = :networkid");
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
	 * This would give the list of {@link INetwork} belonging to the {@link IUser}.
	 * The method uses Hibernate Framework to perform the database operations.
	 *  
	 * @param user							Owner of the network.
	 * @return								returns {@link List} of {@link INetwork} belonging to owner. The list will be empty (not null) if there are no networks matching the input constraints.
	 * @throws QuadrigaStorageException		Exception will be thrown when the input parameters do not satisfy the system/database constraints or due to database connection troubles.
	 */
	@Override
	public List<INetwork> getNetworkList(IUser user)
			throws QuadrigaStorageException {
		List<INetwork> networkList = new ArrayList<INetwork>();
		try {
			Query query = sessionFactory.getCurrentSession().getNamedQuery(
					"NetworksDTO.findByNetworkowner");
			query.setParameter("networkowner", user.getUserName());

			@SuppressWarnings("unchecked")
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
	 * Check if the network name is already used.
	 * The method uses Hibernate Framework to perform the database operations.
	 * 
	 * @param networkName					Name for the network.
	 * @param user							{@link IUser} object for searching network name.
	 * @return								TRUE - If the network name is already in use. FALSE - If the network name is not already taken.
	 * @throws QuadrigaStorageException		Exception will be thrown when the input parameters do not satisfy the system/database constraints or due to database connection troubles.
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
			@SuppressWarnings("unchecked")
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
	 * Get all the network statements {@link INetworkNodeInfo} for a network.
	 * Function can get Network statements with isTop =1 and not the archived versions.
	 * The method uses Hibernate Framework to perform the database operations.
	 * 
	 * @param networkId						ID of network.
	 * @return								returns {@link List} of {@link INetworkNodeInfo}. The list will be empty (not null) if there are no network statements matching the input constraints.
	 * @throws QuadrigaStorageException		Exception will be thrown when the input parameters do not satisfy the system/database constraints or due to database connection troubles.
	 */
	@Override
	public List<INetworkNodeInfo> getNetworkNodes(String networkId,int versionId)
			throws QuadrigaStorageException {

		List<INetworkNodeInfo> networkNodeList = new ArrayList<INetworkNodeInfo>();
		
		int isTop = 1;
		
		try {
			Query query = sessionFactory.getCurrentSession().createQuery(" from NetworkStatementsDTO n WHERE n.networkid = :networkid" +
					" AND n.isarchived = :versionId AND n.istop= :isTop ");
			query.setParameter("networkid", networkId);
			query.setParameter("versionId",versionId);
			query.setParameter("isTop",isTop);
			
			@SuppressWarnings("unchecked")
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
	 * This method should be called when network is reuploaded after admin has rejected the network.
	 * Archive the network, would mark the network statements with isarchived >0 
	 * isarchived = 0 the network is active.
	 * isarchived > 0 the network is archived with the version number (0 or 1 or 2 ..) when the same network
	 * is uploaded after admin has rejected the previous ones.
	 * The method uses Hibernate Framework to perform the database operations.
	 * 
	 * @param networkId						ID of network.
	 * @return								Empty string if the operation was successful. Exception for all other cases.
	 * @throws QuadrigaStorageException		Exception will be thrown when the input parameters do not satisfy the system/database constraints or due to database connection troubles.
	 */
	@Override
	public String archiveNetwork(String networkId)
			throws QuadrigaStorageException {
		Transaction transaction = null;
		try {
			// Obtain a stateless session to overcome OutOfMemory Exception
			StatelessSession session = sessionFactory.openStatelessSession();
			transaction = session.beginTransaction();
			
			int archiveNumber = 0;

			// Select only the rows matching the network id and obtain a
			// scrollable list
			Query query = session.getNamedQuery("NetworkStatementsDTO.findByNetworkid");
			query.setParameter("networkid", networkId);
			ScrollableResults scrollableDTO = query.scroll(ScrollMode.FORWARD_ONLY);

			while (scrollableDTO.next()) {
				// Update the network statements
				NetworkStatementsDTO networkStatementDTO = (NetworkStatementsDTO) scrollableDTO.get(0);
				archiveNumber = networkStatementDTO.getIsarchived();
				networkStatementDTO.setIsarchived(archiveNumber+1);
				session.update(networkStatementDTO);
			}

			scrollableDTO = session.getNamedQuery("NetworkAssignedDTO.findByNetworkid").setParameter("networkid", networkId).scroll(ScrollMode.FORWARD_ONLY);
			while (scrollableDTO.next()) {
				// update the network assigned rows
				NetworkAssignedDTO networkAssignedDTO = (NetworkAssignedDTO) scrollableDTO.get(0);
				archiveNumber = networkAssignedDTO.getIsarchived();
				networkAssignedDTO.setIsarchived(archiveNumber+1);
				session.update(networkAssignedDTO);
			}

			transaction.commit();
			session.close();

			return "";
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();

			logger.error("Error in archiving a network: ", e);
			throw new QuadrigaStorageException(e);
		}

	}
	
	
	/**
	 * Update the network name for an existing network
	 * This method could be called if User's network has been rejected and user prefers to store the network with an alternative name (Like version name).
	 * The method uses Hibernate Framework to perform the database operations.
	 *  
	 * @param networkId						ID of an existing network.
	 * @param networkName					New name for the network.
	 * @return								"success" string if the operation was successful. Exception for all other cases.
	 * @throws QuadrigaStorageException		Exception will be thrown when the input parameters do not satisfy the system/database constraints or due to database connection troubles.
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
	 * Get the list of networks associated with a project id. If the project id is null or the project does not contain any
	 * workspaces or networks then the return will be null.
	 * The method uses Hibernate Framework to perform the database operations.
	 * 
	 * @param projectid						The id of the project in which you need to find the list of networks.
	 * @return								List of networks belonging to the given project id. The list will be empty (not null) if there are no networks matching the input constraints.
	 * @throws QuadrigaStorageException		Exception will be thrown when the input paramets do not satisfy the system/database constraints or due to database connection troubles.
	 */
	@Override
	public List<INetwork> getNetworks(String projectid)	throws QuadrigaStorageException {
		if(projectid == null || projectid.equals(""))
			return null;

		//Create a query to get all projects
		Query query = sessionFactory.getCurrentSession().getNamedQuery("ProjectWorkspaceDTO.findByProjectid");
		query.setParameter("projectid", projectid);

		@SuppressWarnings("unchecked")
		List<ProjectWorkspaceDTO> projectWorkspaceDTOList = query.list();


		List<INetwork> networkList = new ArrayList<INetwork>();

		//If there are a list of projects, get all the networks using the workspace ids

		for(ProjectWorkspaceDTO projectWorkspaceDTO : projectWorkspaceDTOList){
			if (projectWorkspaceDTO != null) {

				String workspaceid1 = projectWorkspaceDTO.getProjectWorkspaceDTOPK().getWorkspaceid();
				Query queryNetworks = sessionFactory.getCurrentSession().getNamedQuery("NetworksDTO.findByWorkspaceid");
				queryNetworks.setParameter("workspaceid", workspaceid1);

				@SuppressWarnings("unchecked")
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
	 * Get the old versions of the network that were archived.
	 * The method uses Hibernate Framework to perform the database operations.
	 * 
	 * @param networkId						ID of network.
	 * @param archiveLevel					Archive level would be from 0 or 1 or 2 - levels of old versions.
	 * @return								returns {@link List} of {@link INetworkOldVersion}. Null if there are no networks matching the input constraints.
	 * @throws QuadrigaStorageException		Exception will be thrown when the input parameters do not satisfy the system/database constraints or due to database connection troubles.
	 */
	@Override
	public List<INetworkOldVersion> getNetworkVersions(String networkId, int archiveLevel) throws QuadrigaStorageException {
		try {
			List<INetworkOldVersion> networkOldVersionsList = null;
			Query query = sessionFactory.getCurrentSession().createQuery(" from NetworkAssignedDTO n WHERE n.networkAssignedDTOPK.networkid = :networkid and n.isarchived= :isarchived");
			query.setParameter("networkid", networkId);
			query.setParameter("isarchived", archiveLevel);

			@SuppressWarnings("unchecked")
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

			@SuppressWarnings("unchecked")
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
	public String assignNetworkToUser(String networkId, IUser user, String networkName)
			throws QuadrigaStorageException {
		NetworkAssignedDTO newtorkAssignedDTO = networkMapper
				.getNetworkAssignedDTOWithNetworkName(networkId, user.getUserName(),
						INetworkStatus.PENDING, INetworkStatus.NOT_ARCHIVED, networkName);

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
	@SuppressWarnings("unchecked")
	@Override
	public List<NetworksAnnotationsDTO> getAnnotationByNodeType(String type, String id, String userId,
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
	@SuppressWarnings("unchecked")
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
	public List<INetwork> getNetworksOfUser(IUser user, String networkStatus) throws QuadrigaStorageException {
		List<INetwork> networkList = null;

		try {
			Query query = sessionFactory.getCurrentSession().createQuery("from NetworksDTO n where n.networkid in (Select na.networkAssignedDTOPK.networkid from NetworkAssignedDTO na where na.networkAssignedDTOPK.assigneduser = :assigneduser ) and status = :status");
			query.setParameter("assigneduser", user.getUserName());
			query.setParameter("status", networkStatus);

			@SuppressWarnings("unchecked")
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

			@SuppressWarnings("unchecked")
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
