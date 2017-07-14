package edu.asu.spring.quadriga.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.IEditorDAO;
import edu.asu.spring.quadriga.dao.INetworkDAO;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.network.INetworkNodeInfo;
import edu.asu.spring.quadriga.domain.workspace.IWorkspace;
import edu.asu.spring.quadriga.dto.NetworkAnnotationsDTO;
import edu.asu.spring.quadriga.dto.NetworkAssignedDTO;
import edu.asu.spring.quadriga.dto.NetworkAssignedDTOPK;
import edu.asu.spring.quadriga.dto.NetworkEdgeAnnotationsDTO;
import edu.asu.spring.quadriga.dto.NetworkNodeAnnotationsDTO;
import edu.asu.spring.quadriga.dto.NetworkRelationAnnotationsDTO;
import edu.asu.spring.quadriga.dto.NetworkStatementsDTO;
import edu.asu.spring.quadriga.dto.NetworkWorkspaceDTO;
import edu.asu.spring.quadriga.dto.NetworkWorkspaceDTOPK;
import edu.asu.spring.quadriga.dto.NetworksDTO;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.NetworkDTOMapper;
import edu.asu.spring.quadriga.mapper.WorkspaceDTOMapper;
import edu.asu.spring.quadriga.web.network.INetworkStatus;

/**
 * This class is responsible for Querying the MySQL database and fetch the class
 * objects related to Networks module
 * 
 * @author Ram Kumar Kumaresan
 * 
 */
@Repository
public class NetworkDAO extends BaseDAO<NetworksDTO> implements INetworkDAO, IEditorDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private NetworkDTOMapper networkMapper;

    @Autowired
    private WorkspaceDTOMapper workspaceMapper;

    /**
     * 
     * {@inheritDoc}
     * 
     * Uses Hibernate to get {@link NetworkDTO} of a {@link INetwork} ID.
     */
    @Override
    public NetworksDTO getNetworksDTO(String networkId) throws QuadrigaStorageException {
        NetworksDTO networksDTO = null;
        try {
            networksDTO = (NetworksDTO) sessionFactory.getCurrentSession().get(NetworksDTO.class, networkId);
            return networksDTO;
        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }
    }

    /**
     * Add a new network into a workspace. Creates a unique Network ID and
     * assigns the user as owner to the network object. It then adds the network
     * into the Workspace. The method uses Hibernate Framework to perform the
     * database operations.
     * 
     * @param networkName
     *            Name of the newly added network.
     * @param user
     *            Owner of the network.
     * @param workspaceid
     *            Workspace id to which the network would be assigned.
     * @return Network id if the operation was successful. Exception for all
     *         other cases.
     * @throws QuadrigaStorageException
     *             Exception will be thrown when the input parameters do not
     *             satisfy the system/database constraints or due to database
     *             connection troubles.
     */
    @Override
    public String addNetwork(String networkName, IUser user, String workspaceid, String networkStatus,
            String externalUserId) throws QuadrigaStorageException {

        if (networkName == null || user == null || workspaceid == null)
            throw new QuadrigaStorageException("Error in adding a network");

        String networkid = generateUniqueID();

        NetworksDTO networksDTO = new NetworksDTO(networkid, networkName, user.getUserName(), networkStatus,
                user.getUserName(), new Date(), user.getUserName(), new Date());
        networksDTO.setExternalUserId(externalUserId);
        String userName = user.getUserName();
        Date date = new Date();

        NetworkWorkspaceDTOPK networkWorkspaceDTOPK = new NetworkWorkspaceDTOPK(networkid, workspaceid);
        NetworkWorkspaceDTO networkWorkspaceDTO = new NetworkWorkspaceDTO();
        networkWorkspaceDTO.setNetworkWorkspaceDTOPK(networkWorkspaceDTOPK);
        networkWorkspaceDTO.setNetworksDTO(networksDTO);
        networkWorkspaceDTO.setCreateddate(date);
        networkWorkspaceDTO.setUpdateddate(date);
        networkWorkspaceDTO.setCreatedby(userName);
        networkWorkspaceDTO.setUpdatedby(userName);

        WorkspaceDTO workspaceDTO = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class,
                workspaceid);
        networkWorkspaceDTO.setWorkspaceDTO(workspaceDTO);
        networksDTO.setNetworkWorkspace(networkWorkspaceDTO);
        try {
            sessionFactory.getCurrentSession().save(networksDTO);
            return networkid;
        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }

    }

    /**
     * Add Network statements (like AE, RE ) associated to networks. This method
     * would also add more info of the network statement like top node, type of
     * node. Also adds all the statements in the XML for further use cases. The
     * method uses Hibernate Framework to perform the database operations.
     * 
     * @param networkId
     *            ID of network.
     * @param id
     *            ID of network statement.
     * @param type
     *            Type of network statement, AE - Appellation event, RE -
     *            Relation event.
     * @param isTop
     *            {@link Boolean} To whether the network statement is in
     *            starting point xml.
     * @param user
     *            Owner of the network.
     * @return Null if the operation was successful. Exception for all other
     *         cases.
     * @throws QuadrigaStorageException
     *             Exception will be thrown when the input parameters do not
     *             satisfy the system/database constraints or due to database
     *             connection troubles.
     */
    @Override
    public String addNetworkStatement(String rowid, String networkId, String id, String type, int isTop, IUser user,
            int version) throws QuadrigaStorageException {

        NetworkStatementsDTO networkStatementsDTO = new NetworkStatementsDTO(rowid, networkId, id, isTop, version, type,
                user.getUserName(), new Date(), user.getUserName(), new Date());

        try {
            sessionFactory.getCurrentSession().save(networkStatementsDTO);
            return null;
        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }
    }

    /**
     * Get {@link INetwork} object for which the user is an owner, using the
     * network ID. The method uses Hibernate Framework to perform the database
     * operations.
     * 
     * @param networkid
     *            ID of network.
     * @param user
     *            Owner of the network.
     * @return return {@link INetwork} object associated to the networkid, user.
     *         Null if the there are no networks for the input constraints.
     * @throws QuadrigaStorageException
     *             Exception will be thrown when the input parameters do not
     *             satisfy the system/database constraints or due to database
     *             connection troubles.
     */
    /*
     * @Override public INetwork getNetwork(String networkId) throws
     * QuadrigaStorageException { INetwork network = null; try { Query query =
     * sessionFactory.getCurrentSession().createQuery(
     * " from NetworksDTO network where network.networkid = :networkid");
     * query.setParameter("networkid", networkId);
     * 
     * NetworksDTO networksDTO = (NetworksDTO) query.uniqueResult(); if
     * (networksDTO != null) { network = networkMapper.getNetwork(networksDTO);
     * 
     * // Get the project id associated with the workspace id query =
     * sessionFactory .getCurrentSession().getNamedQuery(
     * "ProjectWorkspaceDTO.findByWorkspaceid" );
     * query.setParameter("workspaceid", networksDTO.getWorkspaceid());
     * ProjectWorkspaceDTO projectWorkspaceDTO = (ProjectWorkspaceDTO)
     * query.uniqueResult();
     * 
     * if (projectWorkspaceDTO != null) { // Get the project details
     * if(projectWorkspaceDTO.getProjectDTO() != null)
     * network.setProjectWorkspace
     * (projectMapper.getProject(projectWorkspaceDTO.getProjectDTO()));
     * 
     * // Get the workspace details if(projectWorkspaceDTO.getWorkspaceDTO() !=
     * null)
     * network.setWorkspace(workspaceMapper.getWorkSpace(projectWorkspaceDTO
     * .getWorkspaceDTO()));
     * 
     * } }else{ logger.info(" networksDTO is null "); }
     * 
     * return network; } catch (Exception e) { logger.error(
     * "Error in fetching a network status: ", e); throw new
     * QuadrigaStorageException(e); } }
     */

    /**
     * This would give the list of {@link INetwork} belonging to the
     * {@link IUser}. The method uses Hibernate Framework to perform the
     * database operations.
     * 
     * @param user
     *            Owner of the network.
     * @return returns {@link List} of {@link INetwork} belonging to owner. The
     *         list will be empty (not null) if there are no networks matching
     *         the input constraints.
     * @throws QuadrigaStorageException
     *             Exception will be thrown when the input parameters do not
     *             satisfy the system/database constraints or due to database
     *             connection troubles.
     */
    @Override
    public List<NetworksDTO> getNetworkList(IUser user) throws QuadrigaStorageException {
        try {
            Query query = sessionFactory.getCurrentSession().getNamedQuery("NetworksDTO.findByNetworkowner");
            query.setParameter("networkowner", user.getUserName());

            @SuppressWarnings("unchecked")
            List<NetworksDTO> listNetworksDTO = query.list();

            return listNetworksDTO;
        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }
    }

    /**
     * This would give the list of {@link INetwork} that are approved
     */
    @Override
    public List<NetworksDTO> getApprovedNetworkList() throws QuadrigaStorageException {
        try {
            Query query = sessionFactory.getCurrentSession()
                    .createSQLQuery(
                            "select n.* from tbl_project p, tbl_project_workspace pw, tbl_network_workspace nw, tbl_networks n where p.projectid = pw.projectid and pw.workspaceid = nw.workspaceid and nw.networkid = n.networkid and p.accessibility = 'PUBLIC' and n.status = 'APPROVED'")
                    .setResultTransformer(Transformers.aliasToBean(NetworksDTO.class));
            ;

            @SuppressWarnings("unchecked")
            List<NetworksDTO> listNetworksDTO = query.list();

            return listNetworksDTO;
        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }
    }

    /**
     * Get all the network statements {@link INetworkNodeInfo} for a network.
     * Function can get Network statements with isTop =1 and not the archived
     * versions. The method uses Hibernate Framework to perform the database
     * operations.
     * 
     * @param networkId
     *            ID of network.
     * @return returns {@link List} of {@link INetworkNodeInfo}. The list will
     *         be empty (not null) if there are no network statements matching
     *         the input constraints.
     * @throws QuadrigaStorageException
     *             Exception will be thrown when the input parameters do not
     *             satisfy the system/database constraints or due to database
     *             connection troubles.
     */
    @Override
    public List<NetworkStatementsDTO> getNetworkNodes(String networkId, int versionId) throws QuadrigaStorageException {
        int isTop = 1;

        try {
            Query query = sessionFactory.getCurrentSession()
                    .createQuery(" from NetworkStatementsDTO n WHERE n.networkid = :networkid"
                            + " AND n.version = :versionId AND n.istop= :isTop ");
            query.setParameter("networkid", networkId);
            query.setParameter("versionId", versionId);
            query.setParameter("isTop", isTop);

            @SuppressWarnings("unchecked")
            List<NetworkStatementsDTO> listNetworkStatementsDTO = query.list();
            /*
             * if (listNetworkStatementsDTO != null) { networkNodeList =
             * networkMapper.getListOfNetworkNodeInfo(listNetworkStatementsDTO);
             * }
             */
            return listNetworkStatementsDTO;
        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }
    }

    /**
     * Update the network name for an existing network This method could be
     * called if User's network has been rejected and user prefers to store the
     * network with an alternative name (Like version name). The method uses
     * Hibernate Framework to perform the database operations.
     * 
     * @param networkId
     *            ID of an existing network.
     * @param networkName
     *            New name for the network.
     * @return "success" string if the operation was successful. Exception for
     *         all other cases.
     * @throws QuadrigaStorageException
     *             Exception will be thrown when the input parameters do not
     *             satisfy the system/database constraints or due to database
     *             connection troubles.
     */
    @Override
    public String updateNetworkName(String networkId, String networkName) throws QuadrigaStorageException {
        try {
            Query query = sessionFactory.getCurrentSession().getNamedQuery("NetworksDTO.findByNetworkid");
            query.setParameter("networkid", networkId);

            NetworksDTO networksDTO = (NetworksDTO) query.uniqueResult();
            networksDTO.setNetworkname(networkName);

            sessionFactory.getCurrentSession().update(networksDTO);

            return "success";
        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }
    }

    /**
     * Get the list of networks associated with a project id. If the project id
     * is null or the project does not contain any workspaces or networks then
     * the return will be null. The method uses Hibernate Framework to perform
     * the database operations.
     * 
     * @param projectid
     *            The id of the project in which you need to find the list of
     *            networks.
     * @return List of networks belonging to the given project id. The list will
     *         be empty (not null) if there are no networks matching the input
     *         constraints.
     * @throws QuadrigaStorageException
     *             Exception will be thrown when the input paramets do not
     *             satisfy the system/database constraints or due to database
     *             connection troubles.
     */
    @Override
    public List<NetworksDTO> getNetworkDTOList(String projectid) throws QuadrigaStorageException {
        if (projectid == null || projectid.equals(""))
            return null;

        // Create a query to get all projects
        Query query = sessionFactory.getCurrentSession().getNamedQuery("ProjectWorkspaceDTO.findByProjectid");
        query.setParameter("projectid", projectid);

        @SuppressWarnings("unchecked")
        List<ProjectWorkspaceDTO> projectWorkspaceDTOList = query.list();

        List<NetworksDTO> networksDTOList = new ArrayList<NetworksDTO>();

        // If there are a list of projects, get all the networks using the
        // workspace ids

        for (ProjectWorkspaceDTO projectWorkspaceDTO : projectWorkspaceDTOList) {
            if (projectWorkspaceDTO != null) {

                String workspaceid1 = projectWorkspaceDTO.getProjectWorkspaceDTOPK().getWorkspaceid();
                Query queryNetworks = sessionFactory.getCurrentSession()
                        .getNamedQuery("NetworkWorkspaceDTO.findByWorkspaceid");
                queryNetworks.setParameter("workspaceid", workspaceid1);

                @SuppressWarnings("unchecked")
                List<NetworkWorkspaceDTO> networkWorkspaceDTOList = queryNetworks.list();
                for (NetworkWorkspaceDTO networkWorkspaceDTO : networkWorkspaceDTOList) {
                    networksDTOList.add(networkWorkspaceDTO.getNetworksDTO());
                }
            }
        }
        return networksDTOList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<INetwork> getAllNetworkVersions(String networkId) throws QuadrigaStorageException {
        if (networkId == null || networkId.equals(""))
            return null;

        List<INetwork> networkList = new ArrayList<INetwork>();

        Query query = sessionFactory.getCurrentSession().getNamedQuery("NetworkAssignedDTO.findByNetworkid");
        query.setParameter("networkid", networkId);
        List<NetworkAssignedDTO> networkAssignedDTOList = query.list();

        if (networkAssignedDTOList != null) {
            networkList.addAll(networkMapper.getNetworkVersions(networkAssignedDTOList));
        }

        return networkList;
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
    public List<NetworksDTO> getEditorNetworkList(IUser user) throws QuadrigaStorageException {
        // List<INetwork> networkList = new ArrayList<INetwork>();

        try {
            Query query;

            String query1 = "Select n from NetworksDTO n where n.status = 'PENDING' and";
            query1 += "((n.networkWorkspace.workspaceDTO.workspaceid in  ";
            query1 += "(select distinct wc.collaboratorDTOPK.workspaceid from WorkspaceCollaboratorDTO wc "
                    + "where wc.collaboratorDTOPK.collaboratoruser = :username and "
                    + "wc.collaboratorDTOPK.collaboratorrole in ('wscollab_role2','wscollab_role1'))) OR ";
            query1 += "(n.networkWorkspace.workspaceDTO.workspaceid in  ";
            query1 += "(select pw.projectWorkspaceDTOPK.workspaceid from ProjectWorkspaceDTO pw "
                    + "where pw.projectWorkspaceDTOPK.projectid in "
                    + "(select distinct pc.collaboratorDTOPK.projectid from ProjectCollaboratorDTO pc "
                    + "where pc.collaboratorDTOPK.collaboratoruser = :username and pc.collaboratorDTOPK.collaboratorrole in "
                    + "('collaborator_role4')))) OR ";
            query1 += "(n.networkWorkspace.workspaceDTO.workspaceid in  ";
            query1 += "(select pw.projectWorkspaceDTOPK.workspaceid from ProjectWorkspaceDTO pw "
                    + "where pw.projectWorkspaceDTOPK.projectid in "
                    + "(select pe.projectEditorDTOPK.projectid from ProjectEditorDTO pe where pe.projectEditorDTOPK.editor = :username))) "
                    + "or n.networkWorkspace.workspaceDTO.workspaceid in (select distinct we.workspaceEditorDTOPK.workspaceid from WorkspaceEditorDTO we "
                    + "where we.workspaceEditorDTOPK.editor = :username)))))" + "";
            System.out.println("Testing query :" + query1);

            query = sessionFactory.getCurrentSession().createQuery(query1);

            query.setParameter("username", user.getUserName());

            @SuppressWarnings("unchecked")
            List<NetworksDTO> listNetworksDTO = query.list();

            return listNetworksDTO;
        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Integer> getLatestVersionOfNetwork(String networkID) throws QuadrigaStorageException {
        String query1 = "select max(ns.version) from NetworkStatementsDTO ns where ns.networkid= :networkId";
        Query query = sessionFactory.getCurrentSession().createQuery(query1);
        query.setParameter("networkId", networkID);
        @SuppressWarnings("unchecked")
        List<Integer> latestVersion = query.list();
        if (latestVersion == null) {
            throw new QuadrigaStorageException();
        }
        return latestVersion;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String assignNetworkToUser(String networkId, IUser user, String networkName, int version)
            throws QuadrigaStorageException {

        NetworkAssignedDTO networkAssignedDTO = new NetworkAssignedDTO();
        Date date = new Date();
        String assignedUsername = user.getUserName();
        networkAssignedDTO.setNetworkAssignedDTOPK(new NetworkAssignedDTOPK(networkId, assignedUsername, date));
        networkAssignedDTO.setStatus(INetworkStatus.ASSIGNED);
        networkAssignedDTO.setCreatedby(assignedUsername);
        networkAssignedDTO.setUpdatedby(assignedUsername);
        networkAssignedDTO.setUpdateddate(date);
        networkAssignedDTO.setNetworkname(networkName);
        networkAssignedDTO.setVersion(version);

        /*
         * NetworkAssignedDTO newtorkAssignedDTO = networkMapper
         * .getNetworkAssignedDTOWithNetworkName(networkId, user.getUserName(),
         * INetworkStatus.ASSIGNED, version, networkName);
         */

        try {
            sessionFactory.getCurrentSession().save(networkAssignedDTO);
            return "";
        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String updateNetworkStatus(String networkId, String status) throws QuadrigaStorageException {
        try {
            Query query = sessionFactory.getCurrentSession().getNamedQuery("NetworksDTO.findByNetworkid");
            query.setParameter("networkid", networkId);

            NetworksDTO networksDTO = (NetworksDTO) query.uniqueResult();
            networksDTO.setStatus(status);

            sessionFactory.getCurrentSession().update(networksDTO);

            return "";
        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String updateAssignedNetworkStatus(String networkId, String status, int latestVersion)
            throws QuadrigaStorageException {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery(
                    "FROM NetworkAssignedDTO n WHERE n.networkAssignedDTOPK.networkid = :networkid and n.version = :version");
            query.setParameter("networkid", networkId);
            query.setParameter("version", latestVersion);

            NetworkAssignedDTO networkAssignedDTO = (NetworkAssignedDTO) query.uniqueResult();
            networkAssignedDTO.setStatus(status);

            sessionFactory.getCurrentSession().update(networkAssignedDTO);

            return "";
        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String addAnnotationToNetwork(String networkId, String nodeId, String nodeName, String annotationText,
            String userName, String objectType) throws QuadrigaStorageException {
        try {
            String annotationId = generateUniqueID();
            NetworkAnnotationsDTO networkAnnotation = networkMapper.getNetworkAnnotationDTO(networkId, annotationId,
                    annotationText, objectType, userName);

            NetworkNodeAnnotationsDTO networkNodeAnnotation = networkMapper.getNetworkNodeAnnationDTO(networkAnnotation,
                    userName, nodeId, nodeName);
            networkAnnotation.setNetworkNodeAnnotation(networkNodeAnnotation);

            sessionFactory.getCurrentSession().save(networkAnnotation);
            sessionFactory.getCurrentSession().save(networkNodeAnnotation);

        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }
        return "";
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<NetworkNodeAnnotationsDTO> getAnnotationByNodeType(String type, String nodeId, String networkId)
            throws QuadrigaStorageException {
        try {
            List<NetworkNodeAnnotationsDTO> networkNodeAnnotationsDTOList = new ArrayList<NetworkNodeAnnotationsDTO>();
            Query query = sessionFactory.getCurrentSession().createQuery(
                    "from NetworkNodeAnnotationsDTO n where n.nodeId = :nodeid and n.annotationNodes.networkId =:networkid");
            query.setParameter("nodeid", nodeId);
            query.setParameter("networkid", networkId);

            networkNodeAnnotationsDTOList = query.list();
            return networkNodeAnnotationsDTOList;

        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<NetworkEdgeAnnotationsDTO> getAnnotationByEdgeId(String sourceId, String targetId, String userId,
            String networkId) throws QuadrigaStorageException {
        try {
            List<NetworkEdgeAnnotationsDTO> networkEdgeAnnotationsDTOList = new ArrayList<NetworkEdgeAnnotationsDTO>();
            Query query = sessionFactory.getCurrentSession().createQuery(
                    "from NetworkEdgeAnnotationsDTO n where n.sourceId = :sourceid and n.targetId = :targetid  and n.annotationEdges.networkId =:networkid");
            query.setParameter("sourceid", sourceId);
            query.setParameter("targetid", targetId);
            query.setParameter("networkid", networkId);

            networkEdgeAnnotationsDTOList = query.list();
            return networkEdgeAnnotationsDTOList;

        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<NetworkAnnotationsDTO> getAllAnnotationOfNetwork(String userId, String networkId)
            throws QuadrigaStorageException {
        try {
            List<NetworkAnnotationsDTO> networkAnnotationsDTOList = new ArrayList<NetworkAnnotationsDTO>();
            Query query = sessionFactory.getCurrentSession()
                    .createQuery("from NetworkAnnotationsDTO n where username = :username and networkid =:networkid");
            query.setParameter("username", userId);
            query.setParameter("networkid", networkId);

            networkAnnotationsDTOList = query.list();
            return networkAnnotationsDTOList;

        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @throws QuadrigaStorageException
     */
    @Override
    public List<NetworksDTO> getNetworksOfUserWithStatus(IUser user, String networkStatus)
            throws QuadrigaStorageException {

        try {
            Query query = sessionFactory.getCurrentSession().createQuery(
                    "from NetworksDTO n where n.networkid in (Select na.networkAssignedDTOPK.networkid from NetworkAssignedDTO na where na.networkAssignedDTOPK.assigneduser = :assigneduser ) and status = :status");
            query.setParameter("assigneduser", user.getUserName());
            query.setParameter("status", networkStatus);

            @SuppressWarnings("unchecked")
            List<NetworksDTO> listNetworksDTO = query.list();

            return listNetworksDTO;
        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<INetwork> getNetworkListOfOtherEditors(IUser user, List<String> networkStatus)
            throws QuadrigaStorageException {
        List<INetwork> networkList = new ArrayList<INetwork>();

        try {

            // Create the query to get the list of networks
            Query query = sessionFactory.getCurrentSession().createQuery(
                    "from NetworksDTO n where n.networkid in (Select na.networkAssignedDTOPK.networkid from NetworkAssignedDTO na where na.networkAssignedDTOPK.assigneduser <> :assigneduser) and status in (:status)");
            query.setParameter("assigneduser", user.getUserName());
            query.setParameterList("status", networkStatus);

            List<NetworksDTO> listNetworksDTO = query.list();
            for (NetworksDTO networkDto : listNetworksDTO) {
                INetwork network = networkMapper.getNetwork(networkDto);
                NetworkWorkspaceDTO nwDto = networkDto.getNetworkWorkspace();
                WorkspaceDTO wsDto = nwDto.getWorkspaceDTO();
                IWorkspace workspace = workspaceMapper.getWorkSpace(wsDto);
                network.setWorkspace(workspace);
                networkList.add(network);
            }

            // Update project name and workspace name of the network
            for (INetwork network : networkList) {
                // Get the assigned user name for the network
                query = sessionFactory.getCurrentSession().createQuery(
                        "from NetworkAssignedDTO na where na.networkAssignedDTOPK.networkid = :networkid and status = :status");
                query.setParameter("networkid", network.getNetworkId());
                query.setParameter("status", INetworkStatus.ASSIGNED);
                NetworkAssignedDTO networkAssignedDTO = (NetworkAssignedDTO) query.uniqueResult();
                if (networkAssignedDTO != null) {
                    network.setAssignedUser(networkAssignedDTO.getNetworkAssignedDTOPK().getAssigneduser());
                }
            }

            return networkList;
        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }
    }

    @Override
    public String addAnnotationToEdge(String networkId, String sourceId, String targetId, String sourceName,
            String targetName, String annotationText, String userId, String objectType, String targetType)
                    throws QuadrigaStorageException {
        NetworkEdgeAnnotationsDTO networkEdgeAnnotationsDTO = getNetworkEdgeAnnotationDTO(networkId, sourceId, targetId,
                sourceName, targetName, annotationText, "ANNOT_" + generateUniqueID(), userId, objectType, targetType);

        try {
            sessionFactory.getCurrentSession().save(networkEdgeAnnotationsDTO);
            return "";
        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }

    }

    /**
     * This method adds an annotation to a relation in network.
     * 
     * @param annotationText
     *            The annotated text
     * @param networkId
     *            Id of the network for which the relations are annotated.
     * @param predicateId
     *            Id of predicate of a relation
     * @param predicateName
     *            Name of predicate of a relation
     * @param subjectId
     *            Id of subject of a relation
     * @param subjectName
     *            Name of subject of a relation
     * @param objectId
     *            Id of object of a relation
     * @param objectName
     *            Name of object of a relation
     * @param userName
     *            Name of user who annotated the relation
     * @param annotedObjectType
     *            The type of object that is annotated by editor.
     * @throws QuadrigaStorageException
     *             Any database exception
     * @author kiran batna
     */
    @Override
    public void addAnnotationToRelation(String annotationText, String networkId, String predicateId,
            String predicateName, String subjectId, String subjectName, String objectId, String objectName,
            String userName, String annotedObjectType) throws QuadrigaStorageException {
        try {
            String annotationId = generateUniqueID();
            NetworkAnnotationsDTO networkAnnotation = networkMapper.getNetworkAnnotationDTO(networkId, annotationId,
                    annotationText, annotedObjectType, userName);
            NetworkRelationAnnotationsDTO networkRelationAnnotation = networkMapper.getNetworkRelationAnnationDTO(
                    networkAnnotation, userName, predicateId, predicateName, subjectId, subjectName, objectId,
                    objectName);
            networkAnnotation.setNetworkRelationAnnotation(networkRelationAnnotation);

            // networkRelationAnnotationList =
            // networkAnnotation.getNetworkRelationAnnotationList();
            // if(networkRelationAnnotationList == null)
            // {
            // networkRelationAnnotationList = new
            // ArrayList<NetworkRelationAnnotationsDTO>();
            // }
            // networkRelationAnnotationList.add(networkRelationAnnotation);
            // networkAnnotation.setNetworkRelationAnnotationList(networkRelationAnnotationList);

            sessionFactory.getCurrentSession().save(networkAnnotation);
            sessionFactory.getCurrentSession().save(networkRelationAnnotation);

        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }

    }

    /**
     * This method adds annotation to the given node in a network.
     * 
     * @param annotationText
     *            Annotation text for the given node in the network.
     * @param networkId
     *            Id of the network.
     * @param nodeId
     *            Id of the node which is annotated.
     * @param nodeName
     *            Name of the node which is annotated.
     * @param userName
     *            Logged in user name.
     * @param annotedObjectType
     *            Type of the object(node/relation)
     * @throws QuadrigaStorageException
     *             Any database exception is redirected to custom defined
     *             exception.
     * @author Sowjanya Amabati
     */
    @Override
    public void addAnnotationToNode(String annotationText, String networkId, String nodeId, String nodeName,
            String userName, String annotedObjectType) throws QuadrigaStorageException {
        try {
            String annotationId = "ANNOT_" + generateUniqueID();
            NetworkAnnotationsDTO networkAnnotation = networkMapper.getNetworkAnnotationDTO(networkId, annotationId,
                    annotationText, annotedObjectType, userName);
            NetworkNodeAnnotationsDTO networkNodeAnnotation = networkMapper.getNetworkNodeAnnationDTO(networkAnnotation,
                    userName, nodeId, nodeName);
            networkAnnotation.setNetworkNodeAnnotation(networkNodeAnnotation);

            sessionFactory.getCurrentSession().save(networkAnnotation);
            sessionFactory.getCurrentSession().save(networkNodeAnnotation);

        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }

    }

    /**
     * This method retrieves the annotations associated with the relation.
     * 
     * @param networkId
     *            Id of the network for which the relation belongs.
     * @param subjectId
     *            Id of the subject in the annotated relation
     * @param objectId
     *            Id of the object in the annotated relation.
     * @param predicateId
     *            Id of the predicate in the annotated relation.
     * @param userName
     *            name of the editor.
     * @return List<NetworkRelationAnnotationsDTO> List of
     *         {@link NetworkRelationAnnotations} objects containing the
     *         annotations for the given relation
     * @throws QuadrigaStorageException
     *             Any database exception is redirected to the custom defined
     *             database error exception message.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<NetworkRelationAnnotationsDTO> getAnnotationToRelation(String networkId, String subjectId,
            String objectId, String predicateId, String userName) throws QuadrigaStorageException {
        List<NetworkRelationAnnotationsDTO> networkRelationAnnotations = null;
        try {
            String queryString = "from NetworkRelationAnnotationsDTO n WHERE n.subjectId = :subjectid AND n.objectId = :objectid AND n.predicateId = :predicateid AND n.annotationRelation.networkId =:networkid";
            Query query = sessionFactory.getCurrentSession().createQuery(queryString);
            query.setParameter("subjectid", subjectId);
            query.setParameter("objectid", objectId);
            query.setParameter("predicateid", predicateId);
            query.setParameter("networkid", networkId);
            networkRelationAnnotations = query.list();
        } catch (HibernateException ex) {
            throw new QuadrigaStorageException(ex);
        }

        return networkRelationAnnotations;

    }
    
    public NetworkEdgeAnnotationsDTO getNetworkEdgeAnnotationDTO(String networkId, String sourceId, String targetId,
            String sourceName, String targetName, String annotationText, String annotationId, String userName,
            String objectType, String targetNodeType) {
        NetworkAnnotationsDTO networkAnnotationsDTO = new NetworkAnnotationsDTO(annotationId, annotationText, networkId,
                userName, objectType, userName, new Date(), userName, new Date());
        NetworkEdgeAnnotationsDTO networkEdgeAnnotationsDTO = new NetworkEdgeAnnotationsDTO();
        networkEdgeAnnotationsDTO.setSourceId(sourceId);
        networkEdgeAnnotationsDTO.setTargetId(targetId);
        networkEdgeAnnotationsDTO.setSourceName(sourceName);
        networkEdgeAnnotationsDTO.setTargetName(targetName);
        networkEdgeAnnotationsDTO.setTargetNodeType(targetNodeType);
        networkEdgeAnnotationsDTO.setCreatedBy(userName);
        networkEdgeAnnotationsDTO.setCreatedDate(new Date());
        networkEdgeAnnotationsDTO.setUpdatedBy(userName);
        networkEdgeAnnotationsDTO.setUpdatedDate(new Date());
        networkEdgeAnnotationsDTO.setAnnotationEdges(networkAnnotationsDTO);
        return networkEdgeAnnotationsDTO;
    }

    @Override
    public String getIdPrefix() {
        return messages.getProperty("network_id.prefix");
    }

    @Override
    public NetworksDTO getDTO(String id) {
        return getDTO(NetworksDTO.class, id);
    }

}
