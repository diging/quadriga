package edu.asu.spring.quadriga.dao.workbench;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

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
import edu.asu.spring.quadriga.mapper.ProjectDTOMapper;

@Repository
public class ProjectDictionaryDAO implements IDBConnectionProjectDictionary 
{
	@Resource(name = "database_error_msgs")
	private Properties messages;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private DictionaryDTOMapper dictionaryMapper;
	
	@Autowired
	private ProjectDTOMapper projectMapper;
	
	/**
	 * {@inheritDoc}
	 */
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
		ProjectDictionaryDTO projectDictionary = projectMapper.getProjectDictionary(project, dictionary, userId);
		
		sessionFactory.getCurrentSession().save(projectDictionary);
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<IDictionary> listProjectDictionary(String projectId,String userId) throws QuadrigaStorageException
	{
		List<IDictionary> dictionaryList = null;
		List<ProjectDictionaryDTO> projectDictionaryDTOList;
		DictionaryDTO dictionaryDTO = null;
		IDictionary dictionary = null;
		
		dictionaryList = new ArrayList<IDictionary>();
		
		//verify project id
		ProjectDTO project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, projectId);
		
		if(project.equals(null))
		{
			throw new QuadrigaStorageException(messages.getProperty("projectId_invalid"));
		}
		
		projectDictionaryDTOList = project.getProjectDictionaryDTOList();
		
	    for(ProjectDictionaryDTO projectDictionary: projectDictionaryDTOList)	
	    {
	    	dictionaryDTO = projectDictionary.getDictionary();
	    	dictionary = dictionaryMapper.getDictionary(dictionaryDTO);
	    	dictionaryList.add(dictionary);
	    }
		
		return dictionaryList;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteProjectDictionary(String projectId,String userId,String dictionaryId)
	{
		
		ProjectDictionaryDTOPK projectDictionaryKey = new ProjectDictionaryDTOPK(projectId,dictionaryId);
		
		ProjectDictionaryDTO projectDcitionary = (ProjectDictionaryDTO) sessionFactory.getCurrentSession().get(ProjectDictionaryDTO.class,projectDictionaryKey); 
		
		sessionFactory.getCurrentSession().delete(projectDcitionary);
	}

}
