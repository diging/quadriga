package edu.asu.spring.quadriga.dao.impl.workbench;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.workbench.IDBConnectionModifyProjectManager;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.dto.ProjectCollaboratorDTO;
import edu.asu.spring.quadriga.dto.ProjectConceptCollectionDTO;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.ProjectDictionaryDTO;
import edu.asu.spring.quadriga.dto.ProjectEditorDTO;
import edu.asu.spring.quadriga.dto.ProjectEditorDTOPK;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTO;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTOPK;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.ProjectCollaboratorDTOMapper;
import edu.asu.spring.quadriga.mapper.ProjectDTOMapper;

/**
 * This class add/update/delete a project details and 
 * add/delete project editor role.
 * @author kbatna
 *
 */
@Repository
public class ModifyProjectManagerDAO extends BaseDAO implements IDBConnectionModifyProjectManager {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private ProjectDTOMapper projectDTOMapper;
	
	@Autowired
	private ProjectCollaboratorDTOMapper collaboratorMapper;
	
	@Resource(name = "projectconstants")
	private Properties messages;
	
	private static final Logger logger = LoggerFactory.getLogger(ModifyProjectManagerDAO.class);
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addProjectRequest(IProject project, String userName) throws QuadrigaStorageException
	{
		String projectId = messages.getProperty("project_internalid.name") + generateUniqueID();
		project.setProjectId(projectId);
		ProjectDTO projectDTO = projectDTOMapper.getProjectDTO(project,userName);
		
        try
        {
        	sessionFactory.getCurrentSession().save(projectDTO);			
        }
        catch(HibernateException e)
        {
        	logger.error("Add project details :",e);
        	throw new QuadrigaStorageException();
        }
	}
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateProjectRequest(String projID,String projName,String projDesc,String projAccess, String unixName,String userName) throws QuadrigaStorageException
	{
		try
		{
			ProjectDTO projectDTO = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, projID);
			projectDTO.setProjectname(projName);
			projectDTO.setDescription(projDesc);
			projectDTO.setAccessibility(projAccess);
			projectDTO.setUnixname(unixName);
			projectDTO.setUpdatedby(userName);
			projectDTO.setUpdateddate(new Date());
			sessionFactory.getCurrentSession().update(projectDTO);
		}
		catch(HibernateException e)
		{
			logger.error("updateProjectRequest method : "+e);
			throw new QuadrigaStorageException();
		}
		
	}
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteProjectRequest(ArrayList<String> projectIdList) throws QuadrigaStorageException
	{
		try
		{
			for(String projectId : projectIdList)
			{
				ProjectDTO  project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class,projectId);
				//retrieve all the foreign key tables associated with them
				//delete the project workspace DTO
				deleteProjectWorkspaceMapping(projectId);
				deleteProjectDictionaryMapping(projectId);
				deleteProjectConceptCollectionMapping(projectId);
				deleteProjectCollaboratorMapping(projectId);
				deleteProjectEditorMapping(projectId);
				
				sessionFactory.getCurrentSession().delete(project);
			}
		}
		catch(HibernateException e)
		{
			logger.error("Delete project details request method :",e);
			throw new QuadrigaStorageException();
		}
	}
	
	
	@Override
	public void deleteProjectWorkspaceMapping(String projectId)
	{
		ProjectDTO project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, projectId);
		List<ProjectWorkspaceDTO> projectWorkspaceList = project.getProjectWorkspaceDTOList();
		if(projectWorkspaceList != null)
		{
			for(ProjectWorkspaceDTO projectWorkspace : projectWorkspaceList)
			{
//				ProjectWorkspaceDTOPK tempProjectWorkspaceKey = new ProjectWorkspaceDTOPK(projectId,projectWorkspace.getProjectWorkspaceDTOPK().getWorkspaceid());
//				ProjectWorkspaceDTO tempProjectWorkspace = (ProjectWorkspaceDTO) sessionFactory.getCurrentSession().get(ProjectWorkspaceDTO.class,tempProjectWorkspaceKey);
				sessionFactory.getCurrentSession().delete(projectWorkspace);
			}
		}
		project.setProjectWorkspaceDTOList(null);
		sessionFactory.getCurrentSession().update(project);
		
	}
	
	@Override
	public void deleteProjectDictionaryMapping(String projectId)
	{
		ProjectDTO project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, projectId);
		List<ProjectDictionaryDTO> projectDictionaryList = project.getProjectDictionaryDTOList();
		if(projectDictionaryList !=null)
		{
			for(ProjectDictionaryDTO projectDictionary : projectDictionaryList)
			{
				sessionFactory.getCurrentSession().delete(projectDictionary);
			}
		}
		project.setProjectDictionaryDTOList(null);
		sessionFactory.getCurrentSession().update(project);
		
	}
	
	@Override
	public void deleteProjectConceptCollectionMapping(String projectId)
	{
		ProjectDTO project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, projectId);
		List<ProjectConceptCollectionDTO> projectConceptCollectionList = project.getProjectConceptCollectionDTOList();
		if(projectConceptCollectionList != null)
		{
			for(ProjectConceptCollectionDTO projectConceptCollection : projectConceptCollectionList)
			{
				sessionFactory.getCurrentSession().delete(projectConceptCollection);
			}
		}
		project.setProjectConceptCollectionDTOList(null);
		sessionFactory.getCurrentSession().update(project);
	}
	
	@Override
	public void deleteProjectCollaboratorMapping(String projectId)
	{
		ProjectDTO project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, projectId);
		List<ProjectCollaboratorDTO> projectCollaboratorList = project.getProjectCollaboratorDTOList();
		if(projectCollaboratorList != null)
		{
			for(ProjectCollaboratorDTO projectCollaborator : projectCollaboratorList)
			{
               sessionFactory.getCurrentSession().delete(projectCollaborator);				
			}
		}
		project.setProjectConceptCollectionDTOList(null);
		sessionFactory.getCurrentSession().update(project);
	}
	
	@Override
	public void deleteProjectEditorMapping(String projectId)
	{
        ProjectDTO project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, projectId);
		List<ProjectEditorDTO> projectEditorList = project.getProjectEditorDTOList();
		if(projectEditorList !=null)
		{
			for(ProjectEditorDTO projectEditor : projectEditorList)
			{
				sessionFactory.getCurrentSession().delete(projectEditor);
			}
		}
	project.setProjectEditorDTOList(null);
	sessionFactory.getCurrentSession().update(project);
	}
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void transferProjectOwnerRequest(String projectId,String oldOwner,String newOwner,String collabRole) throws QuadrigaStorageException
	{
		try
		{
			ProjectDTO projectDTO = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, projectId);
			projectDTO.setProjectowner(getUserDTO(newOwner));
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
			
			ProjectCollaboratorDTO collaborator = collaboratorMapper.getProjectCollaborator(projectDTO, oldOwner, collabRole);
			
			projectDTO.getProjectCollaboratorDTOList().add(collaborator);
			
			sessionFactory.getCurrentSession().update(projectDTO);
		}
		catch(HibernateException e)
		{
			logger.error("transferProjectOwnerRequest method :",e);
        	throw new QuadrigaStorageException();
		}
		
	}
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void assignProjectOwnerEditor(String projectId,String owner) throws QuadrigaStorageException
	{
		ProjectEditorDTO projectEditor = null;
		ProjectEditorDTOPK projectEditorKey = null;
		ProjectDTO project = null;
		try
		{
			projectEditorKey = new ProjectEditorDTOPK(projectId,owner);
			projectEditor = (ProjectEditorDTO) sessionFactory.getCurrentSession().get(ProjectEditorDTO.class, projectEditorKey);
			
			if( projectEditor == null)
			{
				project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, projectId);
				projectEditor = projectDTOMapper.getProjectEditor(project, owner);
				
				sessionFactory.getCurrentSession().save(projectEditor);
			}
		}
		catch(HibernateException e)
		{
			logger.error("assignProjectOwnerEditor method : "+e);
			throw new QuadrigaStorageException();  
		}
	}
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteProjectOwnerEditor(String projectId,String owner) throws QuadrigaStorageException
	{
		ProjectEditorDTO projectEditor = null;
		ProjectEditorDTOPK projectEditorKey = null;
		
		try
		{
			projectEditorKey = new ProjectEditorDTOPK(projectId,owner);
			projectEditor = (ProjectEditorDTO) sessionFactory.getCurrentSession().get(ProjectEditorDTO.class, projectEditorKey);

			if(projectEditor!=null)
			{
				sessionFactory.getCurrentSession().delete(projectEditor);
			}
		}
		catch(HibernateException e)
		{
			logger.error("deleteProjectOwnerEditor method : "+e);
			throw new QuadrigaStorageException();  
		}
	}
}
