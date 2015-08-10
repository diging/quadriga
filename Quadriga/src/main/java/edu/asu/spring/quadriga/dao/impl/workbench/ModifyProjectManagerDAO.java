package edu.asu.spring.quadriga.dao.impl.workbench;

import java.util.Date;
import java.util.Iterator;
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
import edu.asu.spring.quadriga.dto.ProjectCollaboratorDTO;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.ProjectEditorDTO;
import edu.asu.spring.quadriga.dto.ProjectEditorDTOPK;
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
