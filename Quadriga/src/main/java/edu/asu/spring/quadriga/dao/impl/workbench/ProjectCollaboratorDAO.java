package edu.asu.spring.quadriga.dao.impl.workbench;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.workbench.IProjectCollaboratorDAO;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.dto.ProjectCollaboratorDTO;
import edu.asu.spring.quadriga.dto.ProjectCollaboratorDTOPK;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.ProjectCollaboratorDTOMapper;
import edu.asu.spring.quadriga.mapper.UserDTOMapper;

@Repository
public class ProjectCollaboratorDAO extends BaseDAO<ProjectCollaboratorDTO> implements
		IProjectCollaboratorDAO 
{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private ProjectCollaboratorDTOMapper projectMapper;
	
	@Autowired
    private UserDTOMapper userMapper;
    
	@Resource(name = "database_error_msgs")
	private Properties messages;
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectCollaboratorDAO.class);
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addCollaboratorRequest(ICollaborator collaborator, String projectid,String userName) throws QuadrigaStorageException
	{
		try
		{
			ProjectDTO projectDTO = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, projectid);
			
			if(projectDTO != null) {
				projectMapper.addCollaboratorToProjectDTO(projectDTO,collaborator,userName);
				sessionFactory.getCurrentSession().update(projectDTO);
			}
		}
		catch(HibernateException ex)
		{
			logger.error("Adding project collaborator :",ex);
			throw new QuadrigaStorageException();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteColloratorRequest(String userName, String projectid) throws QuadrigaStorageException
	{
		try
		{
			ProjectDTO project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, projectid);
			if (project == null) {
			    throw new QuadrigaStorageException(messages.getProperty("projectId_invalid"));
			}
			
		    List<ProjectCollaboratorDTO> projectCollaborators = project.getProjectCollaboratorDTOList();
			if(projectCollaborators != null)
			{
				Iterator<ProjectCollaboratorDTO> iterator = projectCollaborators.iterator();
				while(iterator.hasNext())
				{
					ProjectCollaboratorDTO projCollaborator = iterator.next();
					String collaboratorUsername = projCollaborator.getQuadrigaUserDTO().getUsername();
					if(userName.equals(collaboratorUsername))
					{
						iterator.remove();
						sessionFactory.getCurrentSession().delete(projCollaborator);
					}
				}
				sessionFactory.getCurrentSession().update(project);
			}
			
		}
		catch(Exception ex)
		{
			logger.error("Error in deleting project collaborators",ex);
			throw new QuadrigaStorageException();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateCollaboratorRequest(String projectid,String collabUser,String collaboratorRole,String username) throws QuadrigaStorageException 
	{
		ProjectDTO project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, projectid);
	    if(project == null) {
            throw new QuadrigaStorageException(messages.getProperty("projectId_invalid"));
        }
	    
	    List<ProjectCollaboratorDTO> collaboratorList = project.getProjectCollaboratorDTOList();
	    List<String> newCollaboratorRoles = getList(collaboratorRole);
	    List<String> existingRoles = new ArrayList<String>();
		
		//remove the user roles which are not associated with the input selection
		Iterator<ProjectCollaboratorDTO> iterator = collaboratorList.iterator();
		while(iterator.hasNext())
		{
		    ProjectCollaboratorDTO projectCollaborator = iterator.next();
		    ProjectCollaboratorDTOPK collaboratorKey = projectCollaborator.getCollaboratorDTOPK();
		    String collaborator = projectCollaborator.getQuadrigaUserDTO().getUsername();
		    String collabRole = collaboratorKey.getCollaboratorrole();
			if(collaborator.equals(collabUser))
			{
				if(!newCollaboratorRoles.contains(collabRole))
				{
					iterator.remove();
				}
				else
				{
					existingRoles.add(collabRole);
				}
			}
		}
		
		//add the new roles to the collaborator
		QuadrigaUserDTO user = getUserDTO(collabUser);
		
		for(String role : newCollaboratorRoles)
		{
			if(!existingRoles.contains(role))
			{
				Date date = new Date();
				ProjectCollaboratorDTO projectCollaborator = new ProjectCollaboratorDTO();
				ProjectCollaboratorDTOPK collaboratorKey = new ProjectCollaboratorDTOPK(projectid,collabUser,role);
				projectCollaborator.setProjectDTO(project);
				projectCollaborator.setCollaboratorDTOPK(collaboratorKey);
				projectCollaborator.setQuadrigaUserDTO(user);
				projectCollaborator.setCreatedby(username);
				projectCollaborator.setCreateddate(date);
				projectCollaborator.setUpdatedby(username);
				projectCollaborator.setUpdateddate(date);
				collaboratorList.add(projectCollaborator);
			}
		}
		
		project.setProjectCollaboratorDTOList(collaboratorList);
		try {
			sessionFactory.getCurrentSession().update(project);
		}
		catch(Exception ex)
		{
			logger.error("Error while updating project collaborators",ex);
			throw new QuadrigaStorageException();
		}
	}
	
	/**
     * {@inheritDoc}
     */
    @Override
    public List<ICollaborator> getProjectCollaborators(String projectId) throws QuadrigaStorageException
    {

        List<ICollaborator> collaborator = null;
        
        ProjectDTO project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, projectId);
        if(project.equals(null))
        {
            throw new QuadrigaStorageException();
        }
        
            collaborator = projectMapper.getProjectCollaboratorList(project);
        
        return collaborator;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<IUser> getProjectNonCollaborators(String projectid) 
    {
        List<IUser> user = null;
        IUser nonCollaborator = null;
        
        ProjectDTO project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, projectid);
        
        user = new ArrayList<IUser>();
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT user FROM QuadrigaUserDTO user WHERE user.username NOT IN " +
                "(SELECT collaborator.collaboratorDTOPK.collaboratoruser FROM ProjectCollaboratorDTO collaborator " +
                "  WHERE collaborator.collaboratorDTOPK.projectid =:projectid)");
        query.setParameter("projectid", projectid);
        
        @SuppressWarnings("unchecked")
        List<QuadrigaUserDTO> collaborator = query.list();
        
        for(QuadrigaUserDTO tempCollab : collaborator)
        {
            if(!project.getProjectowner().getUsername().equals(tempCollab.getUsername()))
            {
                nonCollaborator = userMapper.getUser(tempCollab);
                user.add(nonCollaborator);
            }
        
        }
        return user;
    }

}
