package edu.asu.spring.quadriga.dao.workbench.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.dao.sql.DAOConnectionManager;
import edu.asu.spring.quadriga.dao.workbench.IModifyProjectCollaboratorDAO;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.dto.ProjectCollaboratorDTO;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.ProjectCollaboratorDTOMapper;

public class ModifyProjectCollaboratorDAO extends DAOConnectionManager implements
		IModifyProjectCollaboratorDAO 
{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	ProjectCollaboratorDTOMapper projectMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(ModifyProjectCollaboratorDAO.class);
	
	public void addCollaboratorRequest(ICollaborator collaborator, String projectid,String userName) throws QuadrigaStorageException
	{
		List<ProjectCollaboratorDTO> projectCollaborator;
		try
		{
			ProjectDTO projectDTO = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, projectid);
			
			if(!projectDTO.equals(null))
			{
				
				projectCollaborator = projectMapper.getProjectCollaboratorDAO(collaborator, projectid, userName);
				
				
				
			}
		}
		catch(Exception ex)
		{
			logger.error("Adding project collaborator",ex);
			throw new QuadrigaStorageException();
		}
	}

}
