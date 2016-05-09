package edu.asu.spring.quadriga.dao.impl.workbench;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.workbench.IProjectDictionaryDAO;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.impl.workbench.ProjectCollaborator;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IProjectCollaborator;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.ProjectDictionaryDTO;
import edu.asu.spring.quadriga.dto.ProjectDictionaryDTOPK;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.DictionaryDTOMapper;
import edu.asu.spring.quadriga.mapper.ProjectCollaboratorDTOMapper;
import edu.asu.spring.quadriga.mapper.workbench.IProjectDeepMapper;

@Repository
public class ProjectDictionaryDAO extends BaseDAO<ProjectDictionaryDTO> implements IProjectDictionaryDAO 
{
	@Resource(name = "database_error_msgs")
	private Properties messages;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private DictionaryDTOMapper dictionaryMapper;
	
	@Autowired
	private IProjectDeepMapper projectMapper;
	
	@Autowired
	private ProjectCollaboratorDTOMapper collaboratorDTOMapper;
	
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
		
		//add the project dictionary to the Project DTO
		List<ProjectDictionaryDTO> projectDictionaryList;
		projectDictionaryList = project.getProjectDictionaryDTOList();
		if(projectDictionaryList == null)
		{
			projectDictionaryList = new ArrayList<ProjectDictionaryDTO>();
		}
		projectDictionaryList.add(projectDictionary);
		project.setProjectDictionaryDTOList(projectDictionaryList);
		sessionFactory.getCurrentSession().update(project);
		
		//add the project dictionary mapping to the dictionary object
		projectDictionaryList = dictionary.getProjectDictionaryDTOList();
		if(projectDictionaryList == null)
		{
			projectDictionaryList = new ArrayList<ProjectDictionaryDTO>();
		}
		projectDictionaryList.add(projectDictionary);
		dictionary.setProjectDictionaryDTOList(projectDictionaryList);
		sessionFactory.getCurrentSession().update(dictionary);
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteProjectDictionary(String projectId,String userId,String dictionaryId)
	{
		List<ProjectDictionaryDTO> projectDictionaryList;
		
		ProjectDictionaryDTOPK projectDictionaryKey = new ProjectDictionaryDTOPK(projectId,dictionaryId);
		
		ProjectDictionaryDTO projectDcitionary = (ProjectDictionaryDTO) sessionFactory.getCurrentSession().get(ProjectDictionaryDTO.class,projectDictionaryKey); 
		
		//delete the project dictionary mapping from the project DTO
		ProjectDTO project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class,projectId);
		projectDictionaryList = project.getProjectDictionaryDTOList();
		if((projectDictionaryList != null)&&(projectDictionaryList.contains(projectDcitionary)))
		{
			projectDictionaryList.remove(projectDcitionary);
		}
        project.setProjectDictionaryDTOList(projectDictionaryList);
        sessionFactory.getCurrentSession().update(project);
        
        //delete the project dictionary mapping from the dictionary DTO
        DictionaryDTO dictionary = (DictionaryDTO) sessionFactory.getCurrentSession().get(DictionaryDTO.class, dictionaryId);
		projectDictionaryList = dictionary.getProjectDictionaryDTOList();
		if((projectDictionaryList != null)&&(projectDictionaryList.contains(projectDcitionary)))
		{
			projectDictionaryList.remove(projectDcitionary);
		}
		dictionary.setProjectDictionaryDTOList(projectDictionaryList);
		sessionFactory.getCurrentSession().update(dictionary);
        
		sessionFactory.getCurrentSession().delete(projectDcitionary);
	}

	@Override
	public List<IProject> getprojectsByDictId(String dictionaryId)
			throws QuadrigaStorageException {
		
		List<IProject> projects = new ArrayList<IProject>();
				
		try
		{
	        DictionaryDTO dictionary = (DictionaryDTO) sessionFactory.getCurrentSession().get(DictionaryDTO.class, dictionaryId);
	        List<ProjectDictionaryDTO> projectDictionaryDTOList = dictionary.getProjectDictionaryDTOList();
	        
	        for(ProjectDictionaryDTO projectDictionaryDTO : projectDictionaryDTOList)
	        {
	        	ProjectDTO projectDTO = projectDictionaryDTO.getProject();
	        	
	        	if(projectDTO != null)
	        	{
	        		IProject project = projectMapper.getProject(projectDTO);
	        		List<IProjectCollaborator> projectCollaboratorList = new ArrayList<IProjectCollaborator>();
	        		
	        		for(ICollaborator collaborator : collaboratorDTOMapper.getProjectCollaboratorList(projectDTO))
	        		{
	        			IProjectCollaborator projectCollaborator = new ProjectCollaborator();
	        			projectCollaborator.setCollaborator(collaborator);
	        			projectCollaborator.setProject(project);
	        			projectCollaboratorList.add(projectCollaborator);
	        		}
	        		project.setProjectCollaborators(projectCollaboratorList);
	        		projects.add(project);
	        	}
	        	
	        }
		}
		catch(Exception e)
		{
			throw new QuadrigaStorageException(e);
		}
		
		return projects;
	}

    @Override
    public ProjectDictionaryDTO getDTO(String id) {
        return getDTO(ProjectDictionaryDTO.class, id);
    }

}
