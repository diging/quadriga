package edu.asu.spring.quadriga.dao.impl.workspace;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.workspace.IListWsDAO;
import edu.asu.spring.quadriga.domain.dspace.IBitStream;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.dto.ConceptCollectionDTO;
import edu.asu.spring.quadriga.dto.NetworksDTO;
import edu.asu.spring.quadriga.dto.WorkspaceConceptcollectionDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.NetworkDTOMapper;
import edu.asu.spring.quadriga.mapper.WorkspaceDTOMapper;
import edu.asu.spring.quadriga.mapper.WorkspaceDspaceDTOMapper;
import edu.asu.spring.quadriga.web.network.INetworkStatus;

@Repository
public class ListWsDAO extends BaseDAO<WorkspaceDTO> implements IListWsDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private WorkspaceDTOMapper workspaceDTOMapper;
	
	@Autowired
	private WorkspaceDspaceDTOMapper workspaceDspaceDTOMapper;
	
	@Autowired
	private NetworkDTOMapper networkDTOMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(ListWsDAO.class);
	
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<IWorkSpace> listWorkspace(String projectid,String username) throws QuadrigaStorageException
	{
		List<IWorkSpace> workspaceList = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("Select projWork.workspaceDTO from ProjectWorkspaceDTO projWork where projWork.projectDTO.projectid =:projectid and projWork.workspaceDTO.workspaceowner.username =:username"); 
			query.setParameter("username", username);
			query.setParameter("projectid", projectid);
			List<WorkspaceDTO> workspaceDTOList = query.list();
			if(workspaceDTOList != null)
			{
				workspaceList = workspaceDTOMapper.getWorkSpaceList(workspaceDTOList); 
			}
		}
		catch(HibernateException e)
		{
			logger.error("List workspace method :",e);
        	throw new QuadrigaStorageException();
		}
		return workspaceList;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WorkspaceDTO> listWorkspaceDTO(String projectid) throws QuadrigaStorageException
	{
		List<WorkspaceDTO> workspaceDTOList = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("Select projWork.workspaceDTO from ProjectWorkspaceDTO projWork where projWork.projectDTO.projectid =:projectid"); 
			query.setParameter("projectid", projectid);
			workspaceDTOList = query.list();

		}
		catch(HibernateException e)
		{
			logger.error("List workspace method :",e);
        	throw new QuadrigaStorageException();
		}
		return workspaceDTOList;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WorkspaceDTO> listWorkspaceDTO(String projectid,String userName) throws QuadrigaStorageException
	{
		List<WorkspaceDTO> workspaceDTOList = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("Select projWork.workspaceDTO from ProjectWorkspaceDTO projWork where projWork.projectDTO.projectid =:projectid and projWork.workspaceDTO.workspaceowner.username =:username");
			query.setParameter("username", userName);
			query.setParameter("projectid", projectid);
			workspaceDTOList = query.list();

		}
		catch(HibernateException e)
		{
			logger.error("List workspace method :",e);
        	throw new QuadrigaStorageException();
		}
		return workspaceDTOList;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<IWorkSpace> listWorkspaceOfCollaborator(String projectid,String username) throws QuadrigaStorageException
	{
		List<IWorkSpace> workspaceList = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("Select projWork.workspaceDTO from ProjectWorkspaceDTO projWork INNER JOIN projWork.workspaceDTO.workspaceCollaboratorDTOList workcollab where workcollab.quadrigaUserDTO.username =:username and projWork.projectDTO.projectid =:projectid"); 
			query.setParameter("username", username);
			query.setParameter("projectid", projectid);
			List<WorkspaceDTO> workspaceDTOList = query.list();
			if(workspaceDTOList != null)
			{
				workspaceList = workspaceDTOMapper.getWorkSpaceList(workspaceDTOList); 
			}
		}
		catch(HibernateException e)
		{
			logger.error("List workspace associated with collaborator  :",e);
        	throw new QuadrigaStorageException();
		}
		return workspaceList;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WorkspaceDTO> listWorkspaceDTOofCollaborator(String projectid,String username) throws QuadrigaStorageException
	{
		List<WorkspaceDTO> workspaceDTOList = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("Select projWork.workspaceDTO from ProjectWorkspaceDTO projWork INNER JOIN projWork.workspaceDTO.workspaceCollaboratorDTOList workcollab where workcollab.quadrigaUserDTO.username =:username and projWork.projectDTO.projectid =:projectid"); 
			query.setParameter("username", username);
			query.setParameter("projectid", projectid);
			workspaceDTOList = query.list();

		}
		catch(HibernateException e)
		{
			logger.error("List workspace associated with collaborator  :",e);
        	throw new QuadrigaStorageException();
		}
		return workspaceDTOList;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<IWorkSpace> listActiveWorkspaceOfOwner(String projectid,String username) throws QuadrigaStorageException
	{
		List<IWorkSpace> workspaceList = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("Select projWork.workspaceDTO from ProjectWorkspaceDTO projWork where projWork.projectDTO.projectid =:projectid and projWork.workspaceDTO.workspaceowner.username =:username and projWork.workspaceDTO.isarchived =:isarchived and projWork.workspaceDTO.isdeactivated =:isdeactivated"); 
			query.setParameter("username", username);
			query.setParameter("projectid", projectid);
			query.setParameter("isdeactivated", false);
			query.setParameter("isarchived", false);
			
			List<WorkspaceDTO> workspaceDTOList = query.list();
			if(workspaceDTOList != null)
			{
				workspaceList = workspaceDTOMapper.getWorkSpaceList(workspaceDTOList); 
			}
		}
		catch(HibernateException e)
		{
			logger.error("Retrieve active workspace method :",e);
        	throw new QuadrigaStorageException();
		}
		return workspaceList;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WorkspaceDTO> listActiveWorkspaceDTOofOwner(String projectid,String username) throws QuadrigaStorageException
	{
		List<WorkspaceDTO> workspaceDTOList = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("Select projWork.workspaceDTO from ProjectWorkspaceDTO projWork where projWork.projectDTO.projectid =:projectid and projWork.workspaceDTO.workspaceowner.username =:username and projWork.workspaceDTO.isarchived =:isarchived and projWork.workspaceDTO.isdeactivated =:isdeactivated"); 
			query.setParameter("username", username);
			query.setParameter("projectid", projectid);
			query.setParameter("isdeactivated", false);
			query.setParameter("isarchived", false);
			
			workspaceDTOList = query.list();
		}
		catch(HibernateException e)
		{
			logger.error("Retrieve active workspace method :",e);
        	throw new QuadrigaStorageException();
		}
		return workspaceDTOList;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<IWorkSpace> listActiveWorkspaceOfCollaborator(String projectid,String username) throws QuadrigaStorageException
	{
		List<IWorkSpace> workspaceList = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("Select projWork.workspaceDTO from ProjectWorkspaceDTO projWork INNER JOIN projWork.workspaceDTO.workspaceCollaboratorDTOList workcollab where workcollab.quadrigaUserDTO.username =:username and projWork.projectDTO.projectid =:projectid and projWork.workspaceDTO.isarchived =:isarchived and projWork.workspaceDTO.isdeactivated =:isdeactivated"); 
			query.setParameter("username", username);
			query.setParameter("projectid", projectid);
			query.setParameter("isdeactivated", false);
			query.setParameter("isarchived", false);
			
			List<WorkspaceDTO> workspaceDTOList = query.list();
			if(workspaceDTOList != null)
			{
				workspaceList = workspaceDTOMapper.getWorkSpaceList(workspaceDTOList); 
			}
		}
		catch(HibernateException e)
		{
			logger.error("retrieve active workspace of collaborator :",e);
        	throw new QuadrigaStorageException();
		}
		return workspaceList;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WorkspaceDTO> listActiveWorkspaceDTOofCollaborator(String projectid,String username) throws QuadrigaStorageException
	{
		List<WorkspaceDTO> workspaceDTOList = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("Select projWork.workspaceDTO from ProjectWorkspaceDTO projWork INNER JOIN projWork.workspaceDTO.workspaceCollaboratorDTOList workcollab where workcollab.quadrigaUserDTO.username =:username and projWork.projectDTO.projectid =:projectid and projWork.workspaceDTO.isarchived =:isarchived and projWork.workspaceDTO.isdeactivated =:isdeactivated"); 
			query.setParameter("username", username);
			query.setParameter("projectid", projectid);
			query.setParameter("isdeactivated", false);
			query.setParameter("isarchived", false);
			
			workspaceDTOList = query.list();
		}
		catch(HibernateException e)
		{
			logger.error("retrieve active workspace of collaborator :",e);
        	throw new QuadrigaStorageException();
		}
		return workspaceDTOList;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<IWorkSpace> listArchivedWorkspace(String projectid,String username) throws QuadrigaStorageException
	{
		List<IWorkSpace> workspaceList = null;
		try
		{
			String value = "SELECT projWork.workspaceDTO from ProjectWorkspaceDTO projWork WHERE projWork.projectWorkspaceDTOPK.projectid =:projectid" +
		            " AND projWork.workspaceDTO.isdeactivated =:isdeactivated  AND projWork.workspaceDTO.isarchived = :isarchived" +
					" AND ((projWork.workspaceDTO.workspaceowner.username = :username) OR (projWork.workspaceDTO.workspaceid IN (" +
					" SELECT wsc.collaboratorDTOPK.workspaceid FROM WorkspaceCollaboratorDTO wsc WHERE wsc.collaboratorDTOPK.collaboratoruser =:username)))";
			Query query = sessionFactory.getCurrentSession().createQuery(value);
			query.setParameter("username", username);
			query.setParameter("projectid", projectid);
			query.setParameter("isdeactivated", false);
			query.setParameter("isarchived", true);
			List<WorkspaceDTO> workspaceDTOList = query.list();
			if(workspaceDTOList != null)
			{
				workspaceList = workspaceDTOMapper.getWorkSpaceList(workspaceDTOList); 
			}
		}
		catch(HibernateException e)
		{
			logger.error("Retrieve archived workspace method :",e);
        	throw new QuadrigaStorageException();
		}
		return workspaceList;
	}
	
	
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WorkspaceDTO> listArchivedWorkspaceDTO(String projectid,String username) throws QuadrigaStorageException
	{
		List<WorkspaceDTO> workspaceDTOList = null;
		try
		{
			String value = "SELECT projWork.workspaceDTO from ProjectWorkspaceDTO projWork WHERE projWork.projectWorkspaceDTOPK.projectid =:projectid" +
		            " AND projWork.workspaceDTO.isdeactivated =:isdeactivated  AND projWork.workspaceDTO.isarchived = :isarchived" +
					" AND ((projWork.workspaceDTO.workspaceowner.username = :username) OR (projWork.workspaceDTO.workspaceid IN (" +
					" SELECT wsc.collaboratorDTOPK.workspaceid FROM WorkspaceCollaboratorDTO wsc WHERE wsc.collaboratorDTOPK.collaboratoruser =:username)))";
			Query query = sessionFactory.getCurrentSession().createQuery(value);
			query.setParameter("username", username);
			query.setParameter("projectid", projectid);
			query.setParameter("isdeactivated", false);
			query.setParameter("isarchived", true);
			workspaceDTOList = query.list();
		}
		catch(HibernateException e)
		{
			logger.error("Retrieve archived workspace method :",e);
        	throw new QuadrigaStorageException();
		}
		return workspaceDTOList;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<IWorkSpace> listDeactivatedWorkspace(String projectid,String username) throws QuadrigaStorageException
	{
		List<IWorkSpace> workspaceList = null;
		try
		{
			String value = "SELECT projWork.workspaceDTO from ProjectWorkspaceDTO projWork WHERE projWork.projectWorkspaceDTOPK.projectid =:projectid" +
		            " AND projWork.workspaceDTO.isdeactivated =:isdeactivated " +
					" AND ((projWork.workspaceDTO.workspaceowner.username = :username) OR (projWork.workspaceDTO.workspaceid IN (" +
					" SELECT wsc.collaboratorDTOPK.workspaceid FROM WorkspaceCollaboratorDTO wsc WHERE wsc.collaboratorDTOPK.collaboratoruser =:username)))";
			Query query = sessionFactory.getCurrentSession().createQuery(value);
			query.setParameter("username", username);
			query.setParameter("projectid", projectid);
			query.setParameter("isdeactivated", true);
			List<WorkspaceDTO> workspaceDTOList = query.list();
			if(workspaceDTOList != null)
			{
				workspaceList = workspaceDTOMapper.getWorkSpaceList(workspaceDTOList); 
			}
		}
		catch(HibernateException e)
		{
			logger.error("retrieve deactive workspace method :",e);
        	throw new QuadrigaStorageException();
		}
		return workspaceList;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WorkspaceDTO> listDeactivatedWorkspaceDTO(String projectid,String username) throws QuadrigaStorageException
	{
		List<WorkspaceDTO> workspaceDTOList = null;
		try
		{
			String value = "SELECT projWork.workspaceDTO from ProjectWorkspaceDTO projWork WHERE projWork.projectWorkspaceDTOPK.projectid =:projectid" +
		            " AND projWork.workspaceDTO.isdeactivated =:isdeactivated " +
					" AND ((projWork.workspaceDTO.workspaceowner.username = :username) OR (projWork.workspaceDTO.workspaceid IN (" +
					" SELECT wsc.collaboratorDTOPK.workspaceid FROM WorkspaceCollaboratorDTO wsc WHERE wsc.collaboratorDTOPK.collaboratoruser =:username)))";
			Query query = sessionFactory.getCurrentSession().createQuery(value);
			query.setParameter("username", username);
			query.setParameter("projectid", projectid);
			query.setParameter("isdeactivated", true);
			workspaceDTOList = query.list();

		}
		catch(HibernateException e)
		{
			logger.error("retrieve deactive workspace method :",e);
        	throw new QuadrigaStorageException();
		}
		return workspaceDTOList;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<IWorkSpace> listActiveWorkspace(String projectid,String username) throws QuadrigaStorageException
	{
		List<IWorkSpace> workspaceList = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("Select projWork.workspaceDTO from ProjectWorkspaceDTO projWork where projWork.projectDTO.projectid =:projectid and projWork.workspaceDTO.workspaceowner.username =:username"); 
			query.setParameter("username", username);
			query.setParameter("projectid", projectid);
			List<WorkspaceDTO> workspaceDTOList = query.list();
			if(workspaceDTOList != null)
			{
				workspaceList = workspaceDTOMapper.getWorkSpaceList(workspaceDTOList); 
			}
		}
		catch(HibernateException e)
		{
			logger.error("listActiveWorkspace method :",e);
        	throw new QuadrigaStorageException();
		}
		return workspaceList;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IWorkSpace getWorkspaceDetails(String workspaceId,String username) throws QuadrigaStorageException
	{
		IWorkSpace workspace = null;
		try
		{
			WorkspaceDTO workspaceDTO = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class, workspaceId);
			if(workspaceDTO != null)
			{
				workspace = workspaceDTOMapper.getWorkSpace(workspaceDTO);
			}
		}
		catch(HibernateException e)
		{
			logger.error("Retrieve workspace details method :",e);
        	throw new QuadrigaStorageException();
		}
		return workspace;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<IBitStream> getBitStreams(String workspaceId, String username) throws QuadrigaAccessException, QuadrigaStorageException
	{
		List<IBitStream> bitstreamList = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("WorkspaceDspaceDTO.findByWorkspaceid");
			query.setParameter("workspaceid", workspaceId);
			List<WorkspaceDspaceDTO> workspaceDspaceDTOList = query.list();
			if(workspaceDspaceDTOList != null)
			{
				bitstreamList = workspaceDspaceDTOMapper.getBitstreamList(workspaceDspaceDTOList);
			}
		}
		catch(HibernateException e)
		{
			logger.error("Retrieve bit streams method :",e);
        	throw new QuadrigaStorageException();
		}
		return bitstreamList;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<INetwork> getWorkspaceNetworkList(String workspaceid) throws QuadrigaStorageException {
		List<INetwork> networkList = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("NetworksDTO.findByWorkspaceid");
			query.setParameter("workspaceid", workspaceid);
			List<NetworksDTO> nwDTOList = query.list();
			if(nwDTOList != null)
			{
				networkList = networkDTOMapper.getNetworkList(nwDTOList);
			}
		}
		catch(HibernateException e)
		{
			logger.error("getWorkspaceNetworkList method :",e);
        	throw new QuadrigaStorageException();
		}
		return networkList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getWorkspaceName(String workspaceId) throws QuadrigaStorageException {
		String workspaceName = "";
		try
		{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("WorkspaceDTO.findByWorkspaceid");
			query.setParameter("workspaceid", workspaceId);
			WorkspaceDTO workspaceDTO = (WorkspaceDTO) query.uniqueResult();
			if(workspaceDTO != null)
			{
				workspaceName = workspaceDTO.getWorkspacename();
			} 
		}
		catch(HibernateException e)
		{
			logger.error("getWorkspaceName method :",e);
        	throw new QuadrigaStorageException();
		}
		return workspaceName;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<INetwork> getWorkspaceRejectedNetworkList(String workspaceid) throws QuadrigaStorageException 
	{
		List<INetwork> networkList = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("from NetworksDTO networks where networks.workspaceid =:workspaceid and networks.status =:status");
			query.setParameter("workspaceid", workspaceid);
			query.setParameter("status", INetworkStatus.REJECTED);
			List<NetworksDTO> networksDTOList = query.list();
			if(networksDTOList != null && networksDTOList.size() > 0)
			{
				networkList = networkDTOMapper.getNetworkList(networksDTOList);
			}
		}
		catch(HibernateException e)
		{
			logger.error("getWorkspaceRejectedNetworkList method :",e);
        	throw new QuadrigaStorageException();
		}
		return networkList;
	}
	
	@Override
	public List<IWorkSpace> getWorkspaceByConceptCollection(String ccId) throws QuadrigaStorageException{
		List<IWorkSpace>  workspaceList = new ArrayList<IWorkSpace>();
		if(ccId ==  null || ccId.equals("")){
			return null;
		}
		try {
			ConceptCollectionDTO conceptCollections = (ConceptCollectionDTO) sessionFactory.getCurrentSession().get(ConceptCollectionDTO.class, ccId);
			List<WorkspaceConceptcollectionDTO> wsConceptCollection = conceptCollections.getWsConceptCollectionDTOList();
			for (WorkspaceConceptcollectionDTO ws: wsConceptCollection ) {
				
				WorkspaceDTO workspaceDTO = ws.getWorkspaceDTO();
				if(workspaceDTO != null) {
					IWorkSpace workspace = workspaceDTOMapper.getWorkSpace(workspaceDTO);
					workspaceList.add (workspace);
				}
			}

		} catch (Exception e) {
			logger.error("Error in fetching workspace id from concept collection: ", e);
			throw new QuadrigaStorageException(e);
		}
		
		return workspaceList;
		
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<INetwork> getWorkspaceApprovedNetworkList(String workspaceid)
			throws QuadrigaStorageException {
		List<INetwork> networkList = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("from NetworksDTO networks where networks.workspaceid =:workspaceid and networks.status =:status");
			query.setParameter("workspaceid", workspaceid);
			query.setParameter("status", INetworkStatus.APPROVED);
			List<NetworksDTO> networksDTOList = query.list();
			if(networksDTOList != null && networksDTOList.size() > 0)
			{
				networkList = networkDTOMapper.getNetworkList(networksDTOList);
			}
		}
		catch(HibernateException e)
		{
			logger.error("getWorkspaceRejectedNetworkList method :",e);
        	throw new QuadrigaStorageException();
		}
		return networkList;
	}


    @Override
    public WorkspaceDTO getDTO(String id) {
        return getDTO(WorkspaceDTO.class, id);
    }
	
	
	
}
