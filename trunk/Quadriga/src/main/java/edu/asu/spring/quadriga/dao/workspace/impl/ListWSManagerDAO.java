package edu.asu.spring.quadriga.dao.workspace.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.sql.DAOConnectionManager;
import edu.asu.spring.quadriga.dao.workspace.IListWSManagerDAO;
import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.WorkspaceDTOMapper;
import edu.asu.spring.quadriga.mapper.WorkspaceDspaceDTOMapper;

@Repository
public class ListWSManagerDAO extends DAOConnectionManager  implements IListWSManagerDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private WorkspaceDTOMapper workspaceDTOMapper;
	
	@Autowired
	private WorkspaceDspaceDTOMapper workspaceDspaceDTOMapper;
	
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
			logger.error("getDspaceKeys method :",e);
        	throw new QuadrigaStorageException();
		}
		return workspaceList;
	}
	
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
	
}
