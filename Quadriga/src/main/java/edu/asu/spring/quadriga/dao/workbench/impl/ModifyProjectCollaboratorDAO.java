package edu.asu.spring.quadriga.dao.workbench.impl;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import java.util.Iterator;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.sql.DAOConnectionManager;
import edu.asu.spring.quadriga.dao.workbench.IModifyProjectCollaboratorDAO;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.dto.ProjectCollaboratorDTO;
import edu.asu.spring.quadriga.dto.ProjectCollaboratorDTOPK;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.ProjectCollaboratorDTOMapper;

@Repository
public class ModifyProjectCollaboratorDAO extends DAOConnectionManager implements
		IModifyProjectCollaboratorDAO 
{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private ProjectCollaboratorDTOMapper projectMapper;
	
	@Resource(name = "database_error_msgs")
	private Properties messages;
	
	private static final Logger logger = LoggerFactory.getLogger(ModifyProjectCollaboratorDAO.class);
	
	@Override
	public void addCollaboratorRequest(ICollaborator collaborator, String projectid,String userName) throws QuadrigaStorageException
	{
		List<ProjectCollaboratorDTO> projectCollaborator;
		try
		{
			ProjectDTO projectDTO = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, projectid);
			
			projectCollaborator = projectDTO.getProjectCollaboratorDTOList();
			
			if(!projectDTO.equals(null))
			{
				
				projectMapper.getProjectCollaboratorDAO(projectCollaborator,collaborator, projectid, userName);
				
				projectDTO.setProjectCollaboratorDTOList(projectCollaborator);
				
				sessionFactory.getCurrentSession().update(projectDTO);
				
			}
		}
		catch(Exception ex)
		{
			logger.error("Adding project collaborator",ex);
			throw new QuadrigaStorageException();
		}
	}
	
	@Override
	public void deleteColloratorRequest(String userName, String projectid) throws QuadrigaStorageException
	{
		List<ProjectCollaboratorDTO> projectCollaborator = null;
		List<String> collaborators = null;
		try
		{
		   ProjectDTO project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, projectid);
		   projectCollaborator = project.getProjectCollaboratorDTOList();
		   collaborators = getList(userName);
		   if(collaborators.size() == 0)
		   {
			   throw new QuadrigaStorageException(messages.getProperty("project_collaborator_invalid"));
		   }
		   if(!project.equals(null))
		   {
			   Iterator<ProjectCollaboratorDTO> iterator = projectCollaborator.iterator();
			   
			   while(iterator.hasNext())
			   {
				   String collaborator = iterator.next().getQuadrigaUserDTO().getUsername();
				   if(collaborators.contains(collaborator))
				   {
					   iterator.remove();
				   }
			   }
			   project.setProjectCollaboratorDTOList(projectCollaborator);
			   
			   sessionFactory.getCurrentSession().update(project);
		   }
		   else
		   {
			   throw new QuadrigaStorageException(messages.getProperty("projectId_invalid"));
		   }
		
		}
		catch(Exception ex)
		{
			logger.error("Error in deleting project collaborators",ex);
			throw new QuadrigaStorageException();
		}
	}
	
	@Override
	public void updateCollaboratorRequest(String projectid,String collabUser,String collaboratorRole,String username) throws QuadrigaStorageException 
	{
		ProjectDTO project = null;
		ProjectCollaboratorDTO projectCollaborator = null;
		ProjectCollaboratorDTOPK collaboratorKey = null;
		List<ProjectCollaboratorDTO> collaboratorList = null;
		List<String> roles = null;
		QuadrigaUserDTO user = null;
		String collaborator;
		Date date = null;
		try
		{
			project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, projectid);
			collaboratorList = project.getProjectCollaboratorDTOList();
			roles = getList(collaboratorRole);
			
			if(!project.equals(null))
			{
				//delete all the roles associated with the user
				Iterator<ProjectCollaboratorDTO> iterator = collaboratorList.iterator();
				while(iterator.hasNext())
				{
					collaborator = iterator.next().getQuadrigaUserDTO().getUsername();
					if(collaborator.equals(collabUser))
					{
						iterator.remove();
					}
				}
				
				project.setProjectCollaboratorDTOList(collaboratorList);
				
				sessionFactory.getCurrentSession().update(project);
				
				user = getUserDTO(collabUser);
				
				//add the roles to the collaborator user
				for(String role : roles)
				{
					date = new Date();
					projectCollaborator = new ProjectCollaboratorDTO();
					collaboratorKey = new ProjectCollaboratorDTOPK(projectid,collabUser,role);
					projectCollaborator.setProjectDTO(project);
					projectCollaborator.setProjectCollaboratorDTOPK(collaboratorKey);
					projectCollaborator.setQuadrigaUserDTO(user);
					projectCollaborator.setCreatedby(username);
					projectCollaborator.setCreateddate(date);
					projectCollaborator.setUpdatedby(username);
					projectCollaborator.setUpdateddate(date);
					collaboratorList.add(projectCollaborator);
				}
				
				project.setProjectCollaboratorDTOList(collaboratorList);
				sessionFactory.getCurrentSession().update(project);
			}
			else
			{
				 throw new QuadrigaStorageException(messages.getProperty("projectId_invalid"));
			}
		}
		catch(Exception ex)
		{
			logger.error("Error while updating project collaborators",ex);
			throw new QuadrigaStorageException();
		}
		
	}

}
