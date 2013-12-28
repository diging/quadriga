package edu.asu.spring.quadriga.dao.workspace;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.DAOConnectionManager;
import edu.asu.spring.quadriga.db.workspace.IDBConnectionListWSManager;
import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.dto.NetworksDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.NetworkDTOMapper;
import edu.asu.spring.quadriga.mapper.WorkspaceDTOMapper;
import edu.asu.spring.quadriga.mapper.WorkspaceDspaceDTOMapper;

@Repository
public class ListWSManagerDAO extends DAOConnectionManager implements IDBConnectionListWSManager {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private WorkspaceDTOMapper workspaceDTOMapper;
	
	@Autowired
	private WorkspaceDspaceDTOMapper workspaceDspaceDTOMapper;
	
	@Autowired
	private NetworkDTOMapper networkDTOMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(ListWSManagerDAO.class);
	
	
	/**
	 * This will list all the active workspaces associated with the project.
	 * @param    projectid
	 * @return   List<IWorkSpace> - list of workspaces associated 
	 *           with the project.
	 * @throws   QuadrigaStorageException
	 * @author   Karthik Jayaraman
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
		catch(Exception e)
		{
			logger.error("getDspaceKeys method :",e);
        	throw new QuadrigaStorageException();
		}
		return workspaceList;
	}
	
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
		catch(Exception e)
		{
			logger.error("getDspaceKeys method :",e);
        	throw new QuadrigaStorageException();
		}
		return workspaceList;
	}
	
	
	/**
	 * This will list all the active workspaces associated with the project.
	 * @param    projectid
	 * @return   List<IWorkSpace> - list of workspaces associated 
	 *           with the project.
	 * @throws   QuadrigaStorageException
	 * @author   Karthik Jayaraman
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
		catch(Exception e)
		{
			logger.error("getDspaceKeys method :",e);
        	throw new QuadrigaStorageException();
		}
		return workspaceList;
	}
	
	/**
	 * This will list all the active workspaces associated with the project.
	 * @param    projectid
	 * @return   List<IWorkSpace> - list of workspaces associated 
	 *           with the project.
	 * @throws   QuadrigaStorageException
	 * @author   Karthik Jayaraman
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
		catch(Exception e)
		{
			logger.error("getDspaceKeys method :",e);
        	throw new QuadrigaStorageException();
		}
		return workspaceList;
	}
	
	
	/**
	 * This will list all the archive workspaces associated with the project.
	 * @param    projectid
	 * @return   List<IWorkSpace> - list of workspaces associated 
	 *           with the project.
	 * @throws   QuadrigaStorageException
	 * @author   Karthik Jayaraman
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<IWorkSpace> listArchivedWorkspace(String projectid,String username) throws QuadrigaStorageException
	{
		List<IWorkSpace> workspaceList = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("Select projWork.workspaceDTO from ProjectWorkspaceDTO projWork INNER JOIN projWork.workspaceDTO.workspaceCollaboratorDTOList workcollab where (workcollab.quadrigaUserDTO.username =:username or projWork.workspaceDTO.workspaceowner.username =:username) and projWork.projectDTO.projectid =:projectid and projWork.workspaceDTO.isarchived =:isarchived and projWork.workspaceDTO.isdeactivated =:isdeactivated"); 
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
		catch(Exception e)
		{
			logger.error("getDspaceKeys method :",e);
        	throw new QuadrigaStorageException();
		}
		return workspaceList;
	}
	
	/**
	 * This will list all the deactivated workspaces associated with the project.
	 * @param    projectid
	 * @return   List<IWorkSpace> - list of workspaces associated 
	 *           with the project.
	 * @throws   QuadrigaStorageException
	 * @author   Karthik Jayaraman
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<IWorkSpace> listDeactivatedWorkspace(String projectid,String username) throws QuadrigaStorageException
	{
		List<IWorkSpace> workspaceList = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("Select projWork.workspaceDTO from ProjectWorkspaceDTO projWork INNER JOIN projWork.workspaceDTO.workspaceCollaboratorDTOList workcollab where (workcollab.quadrigaUserDTO.username =:username or projWork.workspaceDTO.workspaceowner.username =:username) and projWork.projectDTO.projectid =:projectid and projWork.workspaceDTO.isdeactivated =:isdeactivated"); 
			query.setParameter("username", username);
			query.setParameter("projectid", projectid);
			query.setParameter("isdeactivated", true);
			List<WorkspaceDTO> workspaceDTOList = query.list();
			if(workspaceDTOList != null)
			{
				workspaceList = workspaceDTOMapper.getWorkSpaceList(workspaceDTOList); 
			}
		}
		catch(Exception e)
		{
			logger.error("getDspaceKeys method :",e);
        	throw new QuadrigaStorageException();
		}
		return workspaceList;
	}
	
	/**
	 * This will list all the active workspaces associated with the project.
	 * @param    projectid
	 * @return   List<IWorkSpace> - list of workspaces associated 
	 *           with the project.
	 * @throws   QuadrigaStorageException
	 * @author   Karthik Jayaraman
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
		catch(Exception e)
		{
			logger.error("listActiveWorkspace method :",e);
        	throw new QuadrigaStorageException();
		}
		return workspaceList;
	}
	
	/**
	 * This will list the workspace details with workspace ID and username
	 * @param    workspaceId,username
	 * @return   IWorkSpace list of workspaces associated 
	 *           with the workspaceID.
	 * @throws   QuadrigaStorageException
	 * @author   Karthik Jayaraman
	 */
	@Override
	public IWorkSpace getWorkspaceDetails(String workspaceId,String username) throws QuadrigaStorageException
	{
		IWorkSpace workspace = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("Select projWork.workspaceDTO from ProjectWorkspaceDTO projWork where projWork.workspaceDTO.workspaceid =:workspaceid and projWork.workspaceDTO.workspaceowner.username =:username"); 
			query.setParameter("username", username);
			query.setParameter("workspaceid", workspaceId);
			WorkspaceDTO workspaceDTO = (WorkspaceDTO) query.uniqueResult();
			if(workspaceDTO != null)
			{
				workspace = workspaceDTOMapper.getWorkSpace(workspaceDTO);
			}
		}
		catch(Exception e)
		{
			logger.error("getDspaceKeys method :",e);
        	throw new QuadrigaStorageException();
		}
		return workspace;
	}
	
	/**
	 * This will list the bitstreams associated with workspace ID and username
	 * @param    workspaceId,username
	 * @return   IBitStream list of bitstream associated 
	 *           with the workspaceID.
	 * @throws   QuadrigaStorageException
	 * @author   Karthik Jayaraman
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
		catch(Exception e)
		{
			logger.error("getDspaceKeys method :",e);
        	throw new QuadrigaStorageException();
		}
		return bitstreamList;
	}

	/**
	 * This will list the networks associated with workspace ID
	 * @param    workspaceId,username
	 * @return   List of networks
	 * @throws   QuadrigaStorageException
	 * @author   Karthik Jayaraman
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
		catch(Exception e)
		{
			logger.error("getWorkspaceNetworkList method :",e);
        	throw new QuadrigaStorageException();
		}
		return networkList;
	}

	/**
	 * This will get the workspace name of the workspace associated with the workspace ID
	 * @param    workspaceId
	 * @return   workspace name
	 * @throws   QuadrigaStorageException
	 * @author   Karthik Jayaraman
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
		catch(Exception e)
		{
			logger.error("getWorkspaceName method :",e);
        	throw new QuadrigaStorageException();
		}
		return workspaceName;
	}

	/**
	 * This will get the rejected network list associated with the workspace ID
	 * @param    workspaceId
	 * @return   list of rejected networks
	 * @throws   QuadrigaStorageException
	 * @author   Karthik Jayaraman
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
			query.setParameter("status", "REJECTED");
			List<NetworksDTO> networksDTOList = query.list();
			if(networksDTOList != null && networksDTOList.size() > 0)
			{
				networkList = networkDTOMapper.getNetworkList(networksDTOList);
			}
		}
		catch(Exception e)
		{
			logger.error("getWorkspaceRejectedNetworkList method :",e);
        	throw new QuadrigaStorageException();
		}
		return networkList;
	}
	
}
