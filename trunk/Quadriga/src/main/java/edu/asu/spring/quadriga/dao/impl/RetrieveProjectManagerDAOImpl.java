package edu.asu.spring.quadriga.dao.impl;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.RetrieveProjectManagerDAO;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.ProjectCollaboratorDTOMapper;
import edu.asu.spring.quadriga.mapper.ProjectDTOMapper;

@Repository
public class RetrieveProjectManagerDAOImpl implements RetrieveProjectManagerDAO {

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

	private static final Logger logger = LoggerFactory.getLogger(RetrieveProjectManagerDAOImpl.class);
	
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
}
