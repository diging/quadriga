package edu.asu.spring.quadriga.dao.workbench.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.sql.DAOConnectionManager;
import edu.asu.spring.quadriga.db.workbench.IDBConnectionRetrieveProjectManager;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.ProjectCollaboratorDTOMapper;
import edu.asu.spring.quadriga.mapper.ProjectDTOMapper;

@Repository
public class RetrieveProjectManagerDAO extends DAOConnectionManager implements IDBConnectionRetrieveProjectManager 
{
	@Autowired
	private ProjectDTOMapper projectDTOMapper;
	
	@Autowired
	private ProjectCollaboratorDTOMapper collaboratorDTOMapper;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private static final Logger logger = LoggerFactory.getLogger(RetrieveProjectManagerDAO.class);
	
	/**
	 * This retrieves the project details for the supplied project internal id.
	 * @param projectId
	 * @return IProject - project object containing details.
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	@Override
	public IProject getProjectDetails(String projectId) throws QuadrigaStorageException 
	{
		IProject project = null;
		try
		{
			ProjectDTO projectDTO = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, projectId);
			project = projectDTOMapper.getProject(projectDTO);
			project.setCollaborators(collaboratorDTOMapper.getProjectCollaboratorList(projectDTO));
			return project;
		}
		catch(Exception e)
		{
			logger.info("Retrieve project details method :"+e.getMessage());	
			throw new QuadrigaStorageException(e);
		}
	}
	
	/**
	 * This method fetches the list of projects where current logged in user is the owner.
	 * If the logged in user is quadriga admin all the projects are retrieved.                
	 * @returns  List of projects
	 * @throws	 QuadrigaStorageException 
	 * @author   Karthik Jayaraman
     */
	@Override
	@SuppressWarnings("unchecked")
	public List<IProject> getProjectList(String sUserName) throws QuadrigaStorageException
	{
		List<IProject> projectList = new ArrayList<IProject>();
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery(" from ProjectDTO project where project.projectowner.username =:username");
			query.setParameter("username", sUserName);
			List<ProjectDTO> projectDTOList = query.list();
			
			if(projectDTOList != null && projectDTOList.size() >0)
			{
				Iterator<ProjectDTO> projectIterator = projectDTOList.iterator();
				while(projectIterator.hasNext())
				{
					ProjectDTO projectDTO = projectIterator.next();
					IProject project = projectDTOMapper.getProject(projectDTO);
					project.setCollaborators(collaboratorDTOMapper.getProjectCollaboratorList(projectDTO));
					projectList.add(project);
				}
			}
		}
		catch(Exception e)
		{
			logger.info("getProjectList details method :"+e.getMessage());	
			throw new QuadrigaStorageException(e);
		}
		return projectList;
	}	
	
	/**
	 * This method fetches the list of projects where current logged in user is the collaborator.
	 * @returns  List of projects
	 * @throws	 QuadrigaStorageException 
	 * @author   Karthik Jayaraman
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<IProject> getCollaboratorProjectList(String sUserName) throws QuadrigaStorageException
	{
		List<IProject> projectList = new ArrayList<IProject>();
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("Select distinct projectCollab.projectDTO from ProjectCollaboratorDTO projectCollab where projectCollab.quadrigaUserDTO.username =:username");
			query.setParameter("username", sUserName);
			List<ProjectDTO> projectDTOList = query.list();
			
			if(projectDTOList != null && projectDTOList.size() >0)
			{
				Iterator<ProjectDTO> projectIterator = projectDTOList.iterator();
				while(projectIterator.hasNext())
				{
					ProjectDTO projectDTO = projectIterator.next();
					IProject project = projectDTOMapper.getProject(projectDTO);
					project.setCollaborators(collaboratorDTOMapper.getProjectCollaboratorList(projectDTO));
					projectList.add(project);
				}
			}
		}
		catch(Exception e)
		{
			logger.info("getCollaboratorProjectList method :"+e.getMessage());	
			throw new QuadrigaStorageException(e);
		}
		return projectList;
	}
	
	/**
	 * This method fetches the list of projects where current logged in user is the collaborator.
	 * @returns  List of projects
	 * @throws	 QuadrigaStorageException 
	 * @author   Karthik Jayaraman
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<IProject> getProjectListAsWorkspaceOwner(String sUserName) throws QuadrigaStorageException
	{
		List<IProject> projectList = new ArrayList<IProject>();
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("Select projWork.projectDTO from ProjectWorkspaceDTO projWork where projWork.workspaceDTO.workspaceowner.username = :username group by projWork.projectDTO.projectid");
			query.setParameter("username", sUserName);
			List<ProjectDTO> projectDTOList = query.list();
			
			if(projectDTOList != null && projectDTOList.size() >0)
			{
				Iterator<ProjectDTO> projectIterator = projectDTOList.iterator();
				while(projectIterator.hasNext())
				{
					ProjectDTO projectDTO = projectIterator.next();
					IProject project = projectDTOMapper.getProject(projectDTO);
					project.setCollaborators(collaboratorDTOMapper.getProjectCollaboratorList(projectDTO));
					projectList.add(project);
				}
			}
		}
		catch(Exception e)
		{
			logger.info("getCollaboratorProjectList method :"+e.getMessage());	
			throw new QuadrigaStorageException(e);
		}
		return projectList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<IProject> getProjectListAsWorkspaceCollaborator(String sUserName) throws QuadrigaStorageException
	{
		List<IProject> projectList = new ArrayList<IProject>();
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("Select projWork.projectDTO from ProjectWorkspaceDTO projWork where projWork.workspaceDTO in (Select wcDTO.workspaceDTO from WorkspaceCollaboratorDTO wcDTO where wcDTO.workspaceCollaboratorDTOPK.collaboratoruser = :collaboratoruser)");
			query.setParameter("collaboratoruser", sUserName);
			List<ProjectDTO> projectDTOList = query.list();
			
			if(projectDTOList != null && projectDTOList.size() >0)
			{
				Iterator<ProjectDTO> projectIterator = projectDTOList.iterator();
				while(projectIterator.hasNext())
				{
					ProjectDTO projectDTO = projectIterator.next();
					IProject project = projectDTOMapper.getProject(projectDTO);
					project.setCollaborators(collaboratorDTOMapper.getProjectCollaboratorList(projectDTO));
					projectList.add(project);
				}
			}
		}
		catch(Exception e)
		{
			logger.info("getProjectListAsWorkspaceCollaborator method :"+e.getMessage());	
			throw new QuadrigaStorageException(e);
		}
		return projectList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<IProject> getProjectListByCollaboratorRole(String sUserName,String collaboratorRole) throws QuadrigaStorageException
	{
		List<IProject> projectList = new ArrayList<IProject>();
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("Select projWork.projectDTO from ProjectWorkspaceDTO projWork where projWork.workspaceDTO in (Select wcDTO.workspaceDTO from WorkspaceCollaboratorDTO wcDTO where wcDTO.quadrigaUserDTO.username = :username )");
			query.setParameter("username", sUserName);
			List<ProjectDTO> projectDTOList = query.list();
			
			if(projectDTOList != null && projectDTOList.size() >0)
			{
				Iterator<ProjectDTO> projectIterator = projectDTOList.iterator();
				while(projectIterator.hasNext())
				{
					ProjectDTO projectDTO = projectIterator.next();
					IProject project = projectDTOMapper.getProject(projectDTO);
					project.setCollaborators(collaboratorDTOMapper.getProjectCollaboratorList(projectDTO));
					projectList.add(project);
				}
			}
		}
		catch(Exception e)
		{
			logger.info("getCollaboratorProjectList method :"+e.getMessage());	
			throw new QuadrigaStorageException(e);
		}
		return projectList;
	}

	@Override
	public IProject getProjectDetailsByUnixName(String unixName) throws QuadrigaStorageException {
				
			Query query = sessionFactory.getCurrentSession().createQuery(" from ProjectDTO project where project.unixname = :unixname");
			query.setParameter("unixname", unixName);
			//Query query = sessionFactory.getCurrentSession().getNamedQuery("ProjectDTO.findByUnixname");
			ProjectDTO projectDTO = (ProjectDTO) query.uniqueResult();
			if(projectDTO!=null){
				IProject project = projectDTOMapper.getProject(projectDTO);
				project.setCollaborators(collaboratorDTOMapper.getProjectCollaboratorList(projectDTO));
				return project;
			}
			else
				return null;
		
	}
}
