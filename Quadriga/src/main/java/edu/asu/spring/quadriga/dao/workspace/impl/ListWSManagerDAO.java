package edu.asu.spring.quadriga.dao.workspace.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.workspace.IListWSManagerDAO;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.WorkspaceDTOMapper;

@Repository
public class ListWSManagerDAO implements IListWSManagerDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private WorkspaceDTOMapper workspaceDTOMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(ListWSManagerDAO.class);
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
			Query query = sessionFactory.getCurrentSession().createQuery("Select projWork.workspaceDTO from ProjectWorkspaceDTO projWork INNER JOIN projWork.workspaceDTO.workspaceCollaboratorDTOList workcollab where (workcollab.quadrigaUserDTO.username =:username or projWork.workspaceDTO.workspaceowner.username =:username) and projWork.projectDTO.projectid =:projectid"); 
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
	
	
	
}
