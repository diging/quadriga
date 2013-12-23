package edu.asu.spring.quadriga.dao.workbench.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.db.workbench.IDBConnectionProjectDictionary;
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.ProjectDictionaryDTO;
import edu.asu.spring.quadriga.dto.ProjectDictionaryDTOPK;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.DictionaryDTOMapper;

@Repository
public class ProjectDictionaryDAO implements IDBConnectionProjectDictionary 
{
	@Resource(name = "database_error_msgs")
	private Properties messages;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private DictionaryDTOMapper dictionaryMapper;
	
	@Override
	public void addProjectDictionary(String projectId, String dictionaryId, String userId) throws QuadrigaStorageException
	{
		//check if the projectId
		ProjectDTO project= (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class,projectId);
		
		if(project.equals(null))
		{
			throw new QuadrigaStorageException(messages.getProperty("projectId_invalid"));
		}
		
		//check the dictionaryId
		DictionaryDTO dictionary = (DictionaryDTO) sessionFactory.getCurrentSession().get(DictionaryDTO.class, dictionaryId);
		
		if(dictionary.equals(null))
		{
			throw new QuadrigaStorageException(messages.getProperty("dictionaryId_invalid"));
		}
		
		//create a new ProjectDictionaryDTO object
		ProjectDictionaryDTO projectDictionary = new ProjectDictionaryDTO();
		Date date = new Date();
		ProjectDictionaryDTOPK projectDictionaryKey = new ProjectDictionaryDTOPK(projectId,dictionaryId);
		projectDictionary.setProjectDictionaryDTOPK(projectDictionaryKey);
		projectDictionary.setCreatedby(userId);
		projectDictionary.setCreateddate(date);
		projectDictionary.setUpdatedby(userId);
		projectDictionary.setUpdatedby(userId);
		projectDictionary.setUpdateddate(date);
		
		sessionFactory.getCurrentSession().save(projectDictionary);
		
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<IDictionary> listProjectDictionary(String projectId,String userId) throws QuadrigaStorageException
	{
		List<DictionaryDTO> dictionaryDTOList = null;
		List<IDictionary> dictionaryList = null;
		IDictionary dictionary = null;
		
		dictionaryList = new ArrayList<IDictionary>();
		
		//verify project id
		ProjectDTO project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, projectId);
		
		if(project.equals(null))
		{
			throw new QuadrigaStorageException(messages.getProperty("projectId_invalid"));
		}
		
		Query query = sessionFactory.getCurrentSession().getNamedQuery("ProjectDictionaryDTO.findByProjectid");
		query.setParameter("projectid", projectId);
		
		dictionaryDTOList = query.list();
		
	    for(DictionaryDTO tempDictionary: dictionaryDTOList)	
	    {
	    	dictionary = dictionaryMapper.getDictionary(tempDictionary);
	    	dictionaryList.add(dictionary);
	    }
		
		return dictionaryList;
	}
	
	@Override
	public void deleteProjectDictionary(String projectId,String userId,String dictionaryId)
	{
		
		ProjectDictionaryDTOPK projectDictionaryKey = new ProjectDictionaryDTOPK(projectId,dictionaryId);
		
		ProjectDictionaryDTO projectDcitionary = (ProjectDictionaryDTO) sessionFactory.getCurrentSession().get(ProjectDictionaryDTO.class,projectDictionaryKey); 
		
		sessionFactory.getCurrentSession().delete(projectDcitionary);
	}

}
