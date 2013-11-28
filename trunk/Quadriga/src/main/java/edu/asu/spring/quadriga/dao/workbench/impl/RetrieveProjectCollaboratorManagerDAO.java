package edu.asu.spring.quadriga.dao.workbench.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.dao.workbench.IRetrieveProjectCollaboratorManagerDAO;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.ProjectCollaboratorDTOMapper;
import edu.asu.spring.quadriga.mapper.UserDTOMapper;

public class RetrieveProjectCollaboratorManagerDAO implements IRetrieveProjectCollaboratorManagerDAO
{
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private UserDTOMapper userMapper;
	
	@Autowired
	private ProjectCollaboratorDTOMapper projectCollaboratorMapper;
	
	@Override
	public List<ICollaborator> getProjectCollaborators(String projectId) throws QuadrigaStorageException
	{

		List<ICollaborator> collaborator = null;
		
		ProjectDTO project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, projectId);
		if(project.equals(null))
		{
			throw new QuadrigaStorageException();
		}
		
			collaborator = projectCollaboratorMapper.getProjectCollaboratorList(project);
		
		return collaborator;
	}
	
	@Override
	public List<IUser> getProjectNonCollaborators(String projectid) 
	{
		List<IUser> user = null;
		IUser nonCollaborator = null;
		
		user = new ArrayList<IUser>();
		
		Query query = sessionFactory.getCurrentSession().createQuery(" SELECT user FROM QuadrigaUserDTO user LEFT JOIN ProjectCollaboratorDTO collaborator" +
				" WHERE collaborator.collaboratoruser IS NULL AND collaborator.projectid =:projectid");
		query.setParameter("projectid", projectid);
		
		@SuppressWarnings("unchecked")
		List<QuadrigaUserDTO> collaborator =    query.list();
		
		for(QuadrigaUserDTO tempCollab : collaborator)
		{
			nonCollaborator = userMapper.getUser(tempCollab);
			user.add(nonCollaborator);
		}
		return user;
	}

}
