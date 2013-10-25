package edu.asu.spring.quadriga.dao.impl;

import java.util.Date;
import java.util.Iterator;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.dao.ModifyProjectManagerDAO;
import edu.asu.spring.quadriga.dto.ProjectCollaboratorDTO;
import edu.asu.spring.quadriga.dto.ProjectCollaboratorDTOPK;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public class ModifyProjectManagerDAOImpl implements ModifyProjectManagerDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
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
		projectCollaboratorDTO.setCreatedby(oldOwner);
		projectCollaboratorDTO.setCreateddate(new Date());
		projectCollaboratorDTO.setUpdatedby(oldOwner);
		projectCollaboratorDTO.setUpdateddate(new Date());
		projectDTO.getProjectCollaboratorDTOList().add(projectCollaboratorDTO);
		
		sessionFactory.getCurrentSession().update(projectDTO);
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
		Query query = sessionFactory.getCurrentSession().getNamedQuery("QuadrigaUserDTO.findByUsername");
		query.setParameter("username", userName);
		return (QuadrigaUserDTO) query.uniqueResult();
	}
}
