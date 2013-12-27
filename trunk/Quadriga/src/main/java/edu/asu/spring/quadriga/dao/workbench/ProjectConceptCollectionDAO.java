package edu.asu.spring.quadriga.dao.workbench;

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

import edu.asu.spring.quadriga.dao.DAOConnectionManager;
import edu.asu.spring.quadriga.db.workbench.IDBConnectionProjectConceptColleciton;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.dto.ConceptCollectionDTO;
import edu.asu.spring.quadriga.dto.ProjectConceptCollectionDTO;
import edu.asu.spring.quadriga.dto.ProjectConceptCollectionDTOPK;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.ConceptCollectionDTOMapper;

@Repository
public class ProjectConceptCollectionDAO extends DAOConnectionManager implements
IDBConnectionProjectConceptColleciton 
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
		 ConceptCollectionDTO conceptCollection = (ConceptCollectionDTO) sessionFactory.getCurrentSession().get(ConceptCollectionDTO.class,conceptCollectionId);
		 if(conceptCollection.equals(null))
		 {
			 throw new QuadrigaStorageException(messages.getProperty("conceptCollectionId_invalid"));
		 }
		 
		 //add the concept collection to the project
		 Date date = new Date();
		 ProjectConceptCollectionDTO projectConceptCollection = new ProjectConceptCollectionDTO();
		 ProjectConceptCollectionDTOPK projectConceptCollectionKey = new ProjectConceptCollectionDTOPK(projectId,conceptCollectionId);
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
		List<ProjectConceptCollectionDTO> projectConceptCollection = query.list();
		
		//retrieve the concept collection details for every concept collection id
		for(ProjectConceptCollectionDTO tempProjectConceptCollection : projectConceptCollection)
		{
			ProjectConceptCollectionDTOPK projectConceptCollectionKey = tempProjectConceptCollection.getProjectConceptcollectionDTOPK();
			ConceptCollectionDTO collection = (ConceptCollectionDTO) sessionFactory.getCurrentSession().get(ConceptCollectionDTO.class,projectConceptCollectionKey.getConceptcollectionid());
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
		ProjectConceptCollectionDTOPK projectConceptCollectionKey = new ProjectConceptCollectionDTOPK(projectId,conceptCollectionId);
		
		ProjectConceptCollectionDTO projectConceptCollection = (ProjectConceptCollectionDTO) sessionFactory.getCurrentSession().get(ProjectConceptCollectionDTO.class,projectConceptCollectionKey);
      	
		sessionFactory.getCurrentSession().delete(projectConceptCollection);
		}
		catch(HibernateException ex)
		{
			throw new QuadrigaStorageException();
		}
	}

}
