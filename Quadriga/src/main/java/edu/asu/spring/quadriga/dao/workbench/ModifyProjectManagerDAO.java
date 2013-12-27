package edu.asu.spring.quadriga.dao.workbench;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.DAOConnectionManager;
import edu.asu.spring.quadriga.db.workbench.IDBConnectionModifyProjectManager;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.dto.ProjectCollaboratorDTO;
import edu.asu.spring.quadriga.dto.ProjectCollaboratorDTOPK;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.ProjectEditorDTO;
import edu.asu.spring.quadriga.dto.ProjectEditorDTOPK;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.ProjectDTOMapper;

@Repository
public class ModifyProjectManagerDAO extends DAOConnectionManager implements IDBConnectionModifyProjectManager {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private ProjectDTOMapper projectDTOMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(ModifyProjectManagerDAO.class);
	
	
	/**
	 * This method saves a new project
	 * @param project
	 * @return void
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	@Override
	public void addProjectRequest(IProject project, String userName) throws QuadrigaStorageException
	{
		project.setInternalid(generateUniqueID());
		ProjectDTO projectDTO = projectDTOMapper.getProjectDTO(project,userName);
		projectDTO.setCreatedby(userName);
		
        try
        {
        	sessionFactory.getCurrentSession().save(projectDTO);			
        }
        catch(Exception e)
        {
        	logger.error("getProjectOwner :",e);
        	throw new QuadrigaStorageException();
        }
	}
	
	/**
	 * This method Updates the project information
	 * @param projID Project ID of the project
	 * @param projName Project Name
	 * @param projDesc Project Description
	 * @param projAccess Project Access
	 * @param projID Project unix name
	 * @param userName - Username of the user updating the project.
	 * @author Karthik Jayaraman
	 */
	@Override
	public void updateProjectRequest(String projID,String projName,String projDesc,String projAccess, String unixName,String userName) throws QuadrigaStorageException
	{
		try
		{
			ProjectDTO projectDTO = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, projID);
			projectDTO.setProjectname(projName);
			projectDTO.setDescription(projDesc);
			projectDTO.setAccessibility(projAccess);
			projectDTO.setUnixname(unixName);
			projectDTO.setUpdatedby(userName);
			projectDTO.setUpdateddate(new Date());
			sessionFactory.getCurrentSession().update(projectDTO);
		}
		catch(HibernateException e)
		{
			logger.error("updateProjectRequest method : "+e);
			throw new QuadrigaStorageException();
		}
		
	}
	
	/**
	 * This method deletes the project and its associations from database.
	 * @param    projectIdList - Project internal id's in a comma 
	 *           separated string.
	 * @author   Karthik Jayaraman
	 */
	@Override
	public void deleteProjectRequest(ArrayList<String> projectIdList) throws QuadrigaStorageException
	{
		try
		{
			for(String projectId : projectIdList)
			{
				ProjectDTO  projectDTO = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class,projectId);
				sessionFactory.getCurrentSession().delete(projectDTO);
			}
		}
		catch(HibernateException e)
		{
			logger.error("Add project request method :",e);
			throw new QuadrigaStorageException();
		}
	}
	
	/**
	 * This method transfers the project ownership to another user
	 * @param projectId
	 * @param oldOwner
	 * @param newOwner
	 * @param collabRole
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	@Override
	public void transferProjectOwnerRequest(String projectId,String oldOwner,String newOwner,String collabRole) throws QuadrigaStorageException
	{
		try
		{
			ProjectDTO projectDTO = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, projectId);
			projectDTO.setProjectowner(getUserDTO(newOwner));
			projectDTO.setUpdatedby(oldOwner);
			projectDTO.setUpdateddate(new Date());
			
			Iterator<ProjectCollaboratorDTO> iterator = projectDTO.getProjectCollaboratorDTOList().iterator();
			while(iterator.hasNext())
			{
				ProjectCollaboratorDTO projectCollaboratorDTO = iterator.next();
				if(projectCollaboratorDTO.getQuadrigaUserDTO().getUsername().equals(newOwner))
				{
					iterator.remove();
				}
			}
			
			ProjectCollaboratorDTO projectCollaboratorDTO = new ProjectCollaboratorDTO();
			projectCollaboratorDTO.setProjectDTO(projectDTO);
			projectCollaboratorDTO.setProjectCollaboratorDTOPK(new ProjectCollaboratorDTOPK(projectId,oldOwner,collabRole));
			projectCollaboratorDTO.setQuadrigaUserDTO(new QuadrigaUserDTO(oldOwner));
			projectCollaboratorDTO.setCreatedby(oldOwner);
			projectCollaboratorDTO.setCreateddate(new Date());
			projectCollaboratorDTO.setUpdatedby(oldOwner);
			projectCollaboratorDTO.setUpdateddate(new Date());
			projectDTO.getProjectCollaboratorDTOList().add(projectCollaboratorDTO);
			
			sessionFactory.getCurrentSession().update(projectDTO);
		}
		catch(HibernateException e)
		{
			logger.error("transferProjectOwnerRequest method :",e);
        	throw new QuadrigaStorageException();
		}
		
	}
	
	/**
	 * This method assigns editor role to the owner
	 * @param projID Project ID of the project
	 * @param owner Project Owner
	 * @author Karthik Jayaraman
	 */
	@Override
	public void assignProjectOwnerEditor(String projectId,String owner) throws QuadrigaStorageException
	{
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("from ProjectEditorDTO projEditorDTO where projEditorDTO.projectEditorDTOPK.projectid =:projectid and projEditorDTO.projectEditorDTOPK.editor =:owner");
			query.setParameter("projectid", projectId);
			query.setParameter("owner", owner);
			
			ProjectEditorDTO projectEditorDTO = (ProjectEditorDTO) query.uniqueResult();
			
			if( projectEditorDTO == null)
			{
				projectEditorDTO = new ProjectEditorDTO();
				projectEditorDTO.setProjectEditorDTOPK(new ProjectEditorDTOPK(projectId, owner));
				projectEditorDTO.setUpdatedby(owner);
				projectEditorDTO.setUpdateddate(new Date());
				projectEditorDTO.setCreatedby(owner);
				projectEditorDTO.setCreateddate(new Date());
				sessionFactory.getCurrentSession().save(projectEditorDTO);
			}
		}
		catch(HibernateException e)
		{
			logger.error("assignProjectOwnerEditor method : "+e);
			throw new QuadrigaStorageException();  
		}
	}
	
	/**
	 * This method deletes the editor role from the owner
	 * @param projID Project ID of the project
	 * @param owner Project Owner
	 * @author Karthik Jayaraman
	 */
	@Override
	public void deleteProjectOwnerEditor(String projectId,String owner) throws QuadrigaStorageException
	{
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("from ProjectEditorDTO projEditorDTO where projEditorDTO.projectEditorDTOPK.projectid =:projectid and  projEditorDTO.projectEditorDTOPK.owner =:owner");
			query.setParameter("projectid", projectId);
			query.setParameter("owner", owner);
			
			ProjectEditorDTO projectEditorDTO = (ProjectEditorDTO) query.uniqueResult();
			if(!projectEditorDTO.equals(null))
			{
				sessionFactory.getCurrentSession().delete(projectEditorDTO);
			}
		}
		catch(Exception e)
		{
			logger.error("deleteProjectOwnerEditor method : "+e);
			throw new QuadrigaStorageException();  
		}
	}
}
