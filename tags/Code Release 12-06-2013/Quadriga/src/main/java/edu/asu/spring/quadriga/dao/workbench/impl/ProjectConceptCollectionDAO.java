package edu.asu.spring.quadriga.dao.workbench.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.sql.DAOConnectionManager;
import edu.asu.spring.quadriga.dao.workbench.IProjectConceptCollectionDAO;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.dto.ConceptcollectionsDTO;
import edu.asu.spring.quadriga.dto.ProjectConceptcollectionDTO;
import edu.asu.spring.quadriga.dto.ProjectConceptcollectionDTOPK;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.ConceptCollectionDTOMapper;

@Repository
public class ProjectConceptCollectionDAO extends DAOConnectionManager implements
		IProjectConceptCollectionDAO 
{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private ConceptCollectionDTOMapper collectionMapper;
	
	@Resource(name = "database_error_msgs")
	private Properties messages;
	
	@Override
	public void addProjectConceptCollection(String projectId,
			String conceptCollectionId, String userId) throws QuadrigaStorageException
	{
		try
		{
		  //check if the project id exists
		 ProjectDTO project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, projectId);
		 
		 if(project.equals(null))
		 {
			 throw new QuadrigaStorageException(messages.getProperty("projectId_invalid"));
		 }
		  	 
		 //check if the concept collection exists
		 ConceptcollectionsDTO conceptCollection = (ConceptcollectionsDTO) sessionFactory.getCurrentSession().get(ConceptcollectionsDTO.class,conceptCollectionId);
		 if(conceptCollection.equals(null))
		 {
			 throw new QuadrigaStorageException(messages.getProperty("conceptCollectionId_invalid"));
		 }
		 
		 //add the concept collection to the project
		 Date date = new Date();
		 ProjectConceptcollectionDTO projectConceptCollection = new ProjectConceptcollectionDTO();
		 ProjectConceptcollectionDTOPK projectConceptCollectionKey = new ProjectConceptcollectionDTOPK(projectId,conceptCollectionId);
		 projectConceptCollection.setProjectConceptcollectionDTOPK(projectConceptCollectionKey);
		 projectConceptCollection.setCreatedby(userId);
		 projectConceptCollection.setCreateddate(date);
		 projectConceptCollection.setUpdatedby(userId);
		 projectConceptCollection.setUpdateddate(date);
		 
		 sessionFactory.getCurrentSession().save(projectConceptCollection);
		}
		catch(HibernateException ex)
		{
			throw new QuadrigaStorageException();
		}
	}
	
	
	@Override
	public List<IConceptCollection> listProjectConceptCollection(String projectId,
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
		
		//retrieve the concept collection id for the given project id
		Query query = sessionFactory.getCurrentSession().getNamedQuery("ProjectConceptcollectionDTO.findByProjectid");
		query.setParameter("projectid", projectId);

		@SuppressWarnings("unchecked")
		List<ProjectConceptcollectionDTO> projectConceptCollection = query.list();
		
		//retrieve the concept collection details for every concept collection id
		for(ProjectConceptcollectionDTO tempProjectConceptCollection : projectConceptCollection)
		{
			ProjectConceptcollectionDTOPK projectConceptCollectionKey = tempProjectConceptCollection.getProjectConceptcollectionDTOPK();
			ConceptcollectionsDTO collection = (ConceptcollectionsDTO) sessionFactory.getCurrentSession().get(ConceptcollectionsDTO.class,projectConceptCollectionKey.getConceptcollectionid());
			conceptCollection = collectionMapper.getConceptCollection(collection);
			conceptCollectionList.add(conceptCollection);
		}
		
		return conceptCollectionList;
	}
	
	@Override
	public void deleteProjectConceptCollection(String projectId,
			String userId, String conceptCollectionId) throws QuadrigaStorageException
	{
		try
		{
		//retrieve the row associated with concept collection and project id
		ProjectConceptcollectionDTOPK projectConceptCollectionKey = new ProjectConceptcollectionDTOPK(projectId,conceptCollectionId);
		
		ProjectConceptcollectionDTO projectConceptCollection = (ProjectConceptcollectionDTO) sessionFactory.getCurrentSession().get(ProjectConceptcollectionDTO.class,projectConceptCollectionKey);
      	
		sessionFactory.getCurrentSession().delete(projectConceptCollection);
		}
		catch(HibernateException ex)
		{
			throw new QuadrigaStorageException();
		}
	}

}