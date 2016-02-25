package edu.asu.spring.quadriga.dao.impl.workbench;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.workbench.IRetrieveProjectDAO;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.ProjectCollaboratorDTOMapper;
import edu.asu.spring.quadriga.mapper.ProjectDTOMapper;

@Repository
public class RetrieveProjectDAO extends BaseDAO<ProjectDTO> implements IRetrieveProjectDAO 
{
	@Autowired
	private ProjectDTOMapper projectDTOMapper;

	@Autowired
	private ProjectCollaboratorDTOMapper collaboratorDTOMapper;

	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = LoggerFactory.getLogger(RetrieveProjectDAO.class);


	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * Uses Hibernate to get {@link ProjectDTO} of a {@link IProject} ID. 
	 */
	@Override
	public ProjectDTO getProjectDTO(String projectId) throws QuadrigaStorageException 
	{
		ProjectDTO projectDTO = null;
		try
		{
			projectDTO = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, projectId);
			return projectDTO;
		}
		catch(Exception e)
		{
			logger.info("Retrieve project details method :"+e.getMessage());	
			throw new QuadrigaStorageException(e);
		}
	}
	
	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * Uses Hibernate to get {@link ProjectDTO} of a {@link IProject} ID. 
	 */
	@Override
	public ProjectDTO getProjectDTO(String projectId,String userId) throws QuadrigaStorageException 
	{
		ProjectDTO projectDTO = null;
		List<ProjectDTO> projectDTOList = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery(" from ProjectDTO project where project.projectowner.username =:username and projectid=:projectid");
			//projectDTO = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, projectId);
			query.setParameter("username", userId);
			query.setParameter("projectid", projectId);
			
			projectDTOList =  query.list();
			if(projectDTOList != null && projectDTOList.size() > 0){
			projectDTO = projectDTOList.get(0);
			}
			return projectDTO;
		}
		catch(Exception e)
		{
			logger.info("Retrieve project details method :"+e.getMessage());	
			throw new QuadrigaStorageException(e);
		}
	}


	/**
	 * Meghana
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectDTO> getAllProjectsDTOByAccessibility(String accessibility) throws QuadrigaStorageException 
	{
		List<ProjectDTO> projectDTOList = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery(" from ProjectDTO project where project.accessibility = :accessibility");
			query.setParameter("accessibility", accessibility);

			projectDTOList =  query.list();

			return projectDTOList;
		}
		catch(Exception e)
		{
			logger.info("Retrieve project details method :"+e.getMessage());	
			throw new QuadrigaStorageException(e);
		}
	}


	@Override
	@SuppressWarnings("unchecked")
	public List<ProjectDTO> getProjectDTOList(String sUserName) throws QuadrigaStorageException
	{
		List<ProjectDTO> projectDTOList = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery(" from ProjectDTO project where project.projectowner.username =:username");
			query.setParameter("username", sUserName);
			projectDTOList = query.list();
		}
		catch(Exception e)
		{
			logger.info("getProjectList details method :"+e.getMessage());	
			throw new QuadrigaStorageException(e);
		}
		return projectDTOList;
	}
	
	/**
	 * 
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectDTO> getCollaboratorProjectDTOListOfUser(String sUserName) throws QuadrigaStorageException
	{
		List<ProjectDTO> projectDTOList = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("Select distinct projectCollab.projectDTO from ProjectCollaboratorDTO projectCollab where projectCollab.quadrigaUserDTO.username =:username");
			query.setParameter("username", sUserName);
			projectDTOList = query.list();
		}
		catch(Exception e)
		{
			logger.info("getCollaboratorProjectList method :"+e.getMessage());	
			throw new QuadrigaStorageException(e);
		}
		return projectDTOList;
	}

//	/**
//	 * This method fetches the list of projects where current logged in user is the collaborator.
//	 * @returns  List of projects
//	 * @throws	 QuadrigaStorageException 
//	 * @author   Karthik Jayaraman
//	 */
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<IProject> getProjectListAsWorkspaceOwner(String sUserName) throws QuadrigaStorageException
//	{
//		List<IProject> projectList = new ArrayList<IProject>();
//		try
//		{
//			Query query = sessionFactory.getCurrentSession().createQuery("Select projWork.projectDTO from ProjectWorkspaceDTO projWork where projWork.workspaceDTO.workspaceowner.username = :username group by projWork.projectDTO.projectid");
//			query.setParameter("username", sUserName);
//			List<ProjectDTO> projectDTOList = query.list();
//
//			if(projectDTOList != null && projectDTOList.size() >0)
//			{
//				Iterator<ProjectDTO> projectIterator = projectDTOList.iterator();
//				while(projectIterator.hasNext())
//				{
//					ProjectDTO projectDTO = projectIterator.next();
//					IProject project = projectDTOMapper.getProject(projectDTO);
//					project.setCollaborators(collaboratorDTOMapper.getProjectCollaboratorList(projectDTO));
//					projectList.add(project);
//				}
//			}
//		}
//		catch(Exception e)
//		{
//			logger.info("getCollaboratorProjectList method :"+e.getMessage());	
//			throw new QuadrigaStorageException(e);
//		}
//		return projectList;
//	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectDTO> getProjectDTOListAsWorkspaceOwner(String sUserName) throws QuadrigaStorageException
	{
		List<ProjectDTO> projectDTOList = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("Select projWork.projectDTO from ProjectWorkspaceDTO projWork where projWork.workspaceDTO.workspaceowner.username = :username group by projWork.projectDTO.projectid");
			query.setParameter("username", sUserName);
			projectDTOList = query.list();
		}
		catch(Exception e)
		{
			logger.info("getCollaboratorProjectList method :"+e.getMessage());	
			throw new QuadrigaStorageException(e);
		}
		return projectDTOList;
	}
	
//	/**
//	 * {@inheritDoc}
//	 */
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<IProject> getProjectListAsWorkspaceCollaborator(String sUserName) throws QuadrigaStorageException
//	{
//		List<IProject> projectList = new ArrayList<IProject>();
//		try
//		{
//			Query query = sessionFactory.getCurrentSession().createQuery("Select projWork.projectDTO from ProjectWorkspaceDTO projWork where projWork.workspaceDTO in (Select wcDTO.workspaceDTO from WorkspaceCollaboratorDTO wcDTO where wcDTO.collaboratorDTOPK.collaboratoruser = :collaboratoruser)");
//			query.setParameter("collaboratoruser", sUserName);
//			List<ProjectDTO> projectDTOList = query.list();
//
//			if(projectDTOList != null && projectDTOList.size() >0)
//			{
//				Iterator<ProjectDTO> projectIterator = projectDTOList.iterator();
//				while(projectIterator.hasNext())
//				{
//					ProjectDTO projectDTO = projectIterator.next();
//					IProject project = projectDTOMapper.getProject(projectDTO);
//					project.setCollaborators(collaboratorDTOMapper.getProjectCollaboratorList(projectDTO));
//					projectList.add(project);
//				}
//			}
//		}
//		catch(Exception e)
//		{
//			logger.info("getProjectListAsWorkspaceCollaborator method :"+e.getMessage());	
//			throw new QuadrigaStorageException(e);
//		}
//		return projectList;
//	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectDTO> getProjectDTOListAsWorkspaceCollaborator(String sUserName) throws QuadrigaStorageException
	{
		List<ProjectDTO> projectDTOList = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("Select projWork.projectDTO from ProjectWorkspaceDTO projWork where projWork.workspaceDTO in (Select wcDTO.workspaceDTO from WorkspaceCollaboratorDTO wcDTO where wcDTO.collaboratorDTOPK.collaboratoruser = :collaboratoruser)");
			query.setParameter("collaboratoruser", sUserName);
			projectDTOList = query.list();

		}
		catch(Exception e)
		{
			logger.info("getProjectListAsWorkspaceCollaborator method :"+e.getMessage());	
			throw new QuadrigaStorageException(e);
		}
		return projectDTOList;
	}
	
//	/**
//	 * {@inheritDoc}
//	 */
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<IProject> getProjectListByCollaboratorRole(String sUserName,String collaboratorRole) throws QuadrigaStorageException
//	{
//		List<IProject> projectList = new ArrayList<IProject>();
//		try
//		{
//			Query query = sessionFactory.getCurrentSession().createQuery("Select projWork.projectDTO from ProjectWorkspaceDTO projWork where projWork.workspaceDTO in (Select wcDTO.workspaceDTO from WorkspaceCollaboratorDTO wcDTO where wcDTO.quadrigaUserDTO.username = :username )");
//			query.setParameter("username", sUserName);
//			List<ProjectDTO> projectDTOList = query.list();
//
//			if(projectDTOList != null && projectDTOList.size() >0)
//			{
//				Iterator<ProjectDTO> projectIterator = projectDTOList.iterator();
//				while(projectIterator.hasNext())
//				{
//					ProjectDTO projectDTO = projectIterator.next();
//					IProject project = projectDTOMapper.getProject(projectDTO);
//					project.setCollaborators(collaboratorDTOMapper.getProjectCollaboratorList(projectDTO));
//					projectList.add(project);
//				}
//			}
//		}
//		catch(Exception e)
//		{
//			logger.info("getCollaboratorProjectList method :"+e.getMessage());	
//			throw new QuadrigaStorageException(e);
//		}
//		return projectList;
//	}

	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectDTO> getProjectDTOListByCollaboratorRole(String sUserName,String collaboratorRole) throws QuadrigaStorageException
	{
		List<ProjectDTO> projectDTOList = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("Select projWork.projectDTO from ProjectWorkspaceDTO projWork where projWork.workspaceDTO in (Select wcDTO.workspaceDTO from WorkspaceCollaboratorDTO wcDTO where wcDTO.quadrigaUserDTO.username = :username )");
			query.setParameter("username", sUserName);
			projectDTOList = query.list();

		}
		catch(Exception e)
		{
			logger.info("getCollaboratorProjectList method :"+e.getMessage());	
			throw new QuadrigaStorageException(e);
		}
		return projectDTOList;
	}
	
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public IProject getProjectDetailsByUnixName(String unixName) throws QuadrigaStorageException {
//
//		Query query = sessionFactory.getCurrentSession().createQuery(" from ProjectDTO project where project.unixname = :unixname");
//		query.setParameter("unixname", unixName);
//		ProjectDTO projectDTO = (ProjectDTO) query.uniqueResult();
//		IProject project = null;
//		
//		if(projectDTO!=null){
//			project = projectDTOMapper.getProject(projectDTO);
//			project.setCollaborators(collaboratorDTOMapper.getProjectCollaboratorList(projectDTO));
//		}
//		
//		return project;
//	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProjectDTO getProjectDTOByUnixName(String unixName) throws QuadrigaStorageException {

		Query query = sessionFactory.getCurrentSession().createQuery(" from ProjectDTO project where project.unixname = :unixname");
		query.setParameter("unixname", unixName);
		ProjectDTO projectDTO = (ProjectDTO) query.uniqueResult();
		
		return projectDTO;
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public IProject getProject(String workspaceid) throws QuadrigaStorageException {
		if(workspaceid == null || workspaceid.equals(""))
			return null;
		
		try {
			//Find the project id from the workspace
			Query query = sessionFactory.getCurrentSession().createQuery("Select pw.projectDTO FROM ProjectWorkspaceDTO pw where pw.workspaceDTO.workspaceid =:workspaceid");
			query.setParameter("workspaceid", workspaceid);
			ProjectDTO projectDTO = (ProjectDTO) query.uniqueResult();
			if(projectDTO != null)
				return projectDTOMapper.getProject(projectDTO);

		} catch (Exception e) {
			logger.error("Error in fetching project id from workspace: ", e);
			throw new QuadrigaStorageException(e);
		}

		return null;
	}

    @Override
    public ProjectDTO getDTO(String id) {
        return getDTO(ProjectDTO.class, id);
    }
	
	

}
