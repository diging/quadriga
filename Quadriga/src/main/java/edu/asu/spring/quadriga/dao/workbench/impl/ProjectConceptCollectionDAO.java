package edu.asu.spring.quadriga.dao.workbench.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.workbench.IProjectConceptCollectionDAO;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.dto.ConceptCollectionDTO;
import edu.asu.spring.quadriga.dto.ProjectConceptCollectionDTO;
import edu.asu.spring.quadriga.dto.ProjectConceptCollectionDTOPK;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.ConceptCollectionDTOMapper;
import edu.asu.spring.quadriga.mapper.workbench.IProjectBaseMapper;

@Repository
public class ProjectConceptCollectionDAO extends BaseDAO<ProjectConceptCollectionDTO> implements IProjectConceptCollectionDAO 
{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private ConceptCollectionDTOMapper collectionMapper;
	
	@Autowired
	@Qualifier("ProjectBaseMapper")
	private IProjectBaseMapper projectMapper;
	
	@Resource(name = "database_error_msgs")
	private Properties messages;
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectConceptCollectionDAO.class);
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addProjectConceptCollection(String projectId,
			String conceptCollectionId, String userId) throws QuadrigaStorageException
	{
		try
		{
	     List<ProjectConceptCollectionDTO> projectConceptCollectionList;
		  //check if the project id exists
		 ProjectDTO project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, projectId);
		 
		 if(project.equals(null))
		 {
			 throw new QuadrigaStorageException(messages.getProperty("projectId_invalid"));
		 }
		 
		 //check if the concept collection exists
		 ConceptCollectionDTO conceptCollection = (ConceptCollectionDTO) sessionFactory.getCurrentSession().get(ConceptCollectionDTO.class,conceptCollectionId);
		 if(conceptCollection.equals(null))
		 {
			 throw new QuadrigaStorageException(messages.getProperty("conceptCollectionId_invalid"));
		 }
		 
		 //add the concept collection to the project
		 ProjectConceptCollectionDTO projectConceptCollection = projectMapper.getProjectConceptCollection(project, conceptCollection, userId);
		 
		 sessionFactory.getCurrentSession().save(projectConceptCollection);
		 
		 //Adding the project concept collection to a project DTO
		 projectConceptCollectionList = project.getProjectConceptCollectionDTOList();
		 if(projectConceptCollectionList == null)
		 {
			 projectConceptCollectionList = new ArrayList<ProjectConceptCollectionDTO>();
		 }
		 projectConceptCollectionList.add(projectConceptCollection);
		 project.setProjectConceptCollectionDTOList(projectConceptCollectionList);
		 sessionFactory.getCurrentSession().update(project);
		 
		 //Adding the project concept collection mapping to the Concept collection DTO
		 projectConceptCollectionList = conceptCollection.getProjConceptCollectionDTOList();
		 if(projectConceptCollectionList == null)
		 {
			 projectConceptCollectionList = new ArrayList<ProjectConceptCollectionDTO>();
		 }
		 projectConceptCollectionList.add(projectConceptCollection);
		 conceptCollection.setProjConceptCollectionDTOList(projectConceptCollectionList);
		 sessionFactory.getCurrentSession().update(conceptCollection);
		}
		catch(HibernateException ex)
		{
			logger.error("Add concept collection to project method:",ex);
			throw new QuadrigaStorageException();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ProjectConceptCollectionDTO> listProjectConceptCollection(String projectId,
			String userId) throws QuadrigaStorageException
	{
		List<IConceptCollection> conceptCollectionList = null;
		IConceptCollection conceptCollection = null;
		
		//check if the project id is valid
		ProjectDTO project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, projectId);
		
		if(project.equals(null))
		{
			throw new QuadrigaStorageException(messages.getProperty("projectId_invalid"));
		}
		
		conceptCollectionList = new ArrayList<IConceptCollection>();
		
		List<ProjectConceptCollectionDTO> projectConceptCollection = project.getProjectConceptCollectionDTOList();
		
		//retrieve the concept collection details for every concept collection id
//		for(ProjectConceptCollectionDTO tempProjectConceptCollection : projectConceptCollection)
//		{
//			ProjectConceptCollectionDTOPK projectConceptCollectionKey = tempProjectConceptCollection.getProjectConceptcollectionDTOPK();
//			ConceptCollectionDTO collection = (ConceptCollectionDTO) sessionFactory.getCurrentSession().get(ConceptCollectionDTO.class,projectConceptCollectionKey.getConceptcollectionid());
//			conceptCollection = collectionMapper.getConceptCollection(collection);
//			conceptCollectionList.add(conceptCollection);
//		}
		
		return projectConceptCollection;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteProjectConceptCollection(String projectId,
			String userId, String conceptCollectionId) throws QuadrigaStorageException
	{
		try
		{
		//retrieve the row associated with concept collection and project id
		ProjectConceptCollectionDTOPK projectConceptCollectionKey = new ProjectConceptCollectionDTOPK(projectId,conceptCollectionId);
		
		ProjectConceptCollectionDTO projectConceptCollection = (ProjectConceptCollectionDTO) sessionFactory.getCurrentSession().get(ProjectConceptCollectionDTO.class,projectConceptCollectionKey);
      	
		//delete the project concept collection mapping form the project DTO
		ProjectDTO project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, projectId);
		List<ProjectConceptCollectionDTO> projectConceptCollectionList = project.getProjectConceptCollectionDTOList();
		if((projectConceptCollectionList!=null)&&(projectConceptCollectionList.contains(projectConceptCollection)))
		{
			projectConceptCollectionList.remove(projectConceptCollection);
		}
		sessionFactory.getCurrentSession().update(project);
		
		//delete the project concept collection mapping from the concept collection
		ConceptCollectionDTO conceptCollection = (ConceptCollectionDTO) sessionFactory.getCurrentSession().get(ConceptCollectionDTO.class, conceptCollectionId);
		projectConceptCollectionList = conceptCollection.getProjConceptCollectionDTOList();
		if((projectConceptCollectionList!=null)&&(projectConceptCollectionList.contains(projectConceptCollection)))
		{
			projectConceptCollectionList.remove(projectConceptCollection);
		}
		
		sessionFactory.getCurrentSession().update(conceptCollection);
		
		sessionFactory.getCurrentSession().delete(projectConceptCollection);
		
		}
		catch(HibernateException ex)
		{
			logger.error("Delete project and concept collection association :",ex);
			throw new QuadrigaStorageException();
		}
	}

    @Override
    public ProjectConceptCollectionDTO getDTO(String id) {
        return getDTO(ProjectConceptCollectionDTO.class, id);
    }

}
