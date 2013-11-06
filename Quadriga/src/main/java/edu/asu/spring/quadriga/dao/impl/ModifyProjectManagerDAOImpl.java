package edu.asu.spring.quadriga.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.ModifyProjectManagerDAO;
import edu.asu.spring.quadriga.db.sql.DAOConnectionManager;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.dto.ProjectCollaboratorDTO;
import edu.asu.spring.quadriga.dto.ProjectCollaboratorDTOPK;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.ProjectDTOMapper;

@Repository
public class ModifyProjectManagerDAOImpl extends DAOConnectionManager implements ModifyProjectManagerDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private ProjectDTOMapper projectDTOMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(ModifyProjectManagerDAOImpl.class);
	
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
			projectDTO.setProjectowner(getProjectOwner(newOwner));
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
		catch(Exception e)
		{
			logger.error("transferProjectOwnerRequest method :",e);
        	throw new QuadrigaStorageException();
		}
		
	}
	
	/**
	 * This method retrieve the project of the user by his username
	 * @param userName
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	@Override
	public QuadrigaUserDTO getProjectOwner(String userName) throws QuadrigaStorageException
	{
		try
		{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("QuadrigaUserDTO.findByUsername");
			query.setParameter("username", userName);
			return (QuadrigaUserDTO) query.uniqueResult();
		}
		catch(Exception e)
		{
			logger.error("getProjectOwner :",e);
        	throw new QuadrigaStorageException();
		}
	}
	
	
	/**
	 * This method saves a new project
	 * @param project
	 * @return void
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	@Override
	public void addProjectRequest(IProject project) throws QuadrigaStorageException
	{
		project.setInternalid(generateUniqueID());
		ProjectDTO projectDTO = projectDTOMapper.getProjectDTO(project);
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
	 * This method deletes the project and its associations from database.
	 * @param    projectIdList - Project internal id's in a comma 
	 *           separated string.
	 * @author   Karthik Jayaraman
	 */
	@Override
	public void deleteProjectRequest(String projectIdList) throws QuadrigaStorageException
	{
		//List<String> projectIDList = new ArrayList(Arrays.asList(projectIdList.split(",")));
		String[] projectIDList = projectIdList.split(",");
		try
		{
			for(int i=0;i<projectIdList.length();i++)
			{
				sessionFactory.getCurrentSession().delete(new ProjectDTO((String)projectIDList[0]));
			}
		/*	Query query = sessionFactory.getCurrentSession().createQuery("Delete from ProjectDTO proj where proj.projectid in (:projectIDList)");
			query.setParameterList("projectIDList", projectIDList);
			query.executeUpdate();	*/	
		}
		catch(Exception e)
		{
			logger.error("Add project request method :",e);
			throw new QuadrigaStorageException();
		}
	}
}
