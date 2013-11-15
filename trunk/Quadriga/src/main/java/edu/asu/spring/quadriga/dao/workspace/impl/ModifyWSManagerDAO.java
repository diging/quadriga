package edu.asu.spring.quadriga.dao.workspace.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.sql.DAOConnectionManager;
import edu.asu.spring.quadriga.dao.workspace.IModifyWSManagerDAO;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTO;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTOPK;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTO;
import edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTOPK;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.WorkspaceDTOMapper;

@Repository
public class ModifyWSManagerDAO extends DAOConnectionManager implements IModifyWSManagerDAO {

	@Autowired
	WorkspaceDTOMapper workspaceDTOMapper;
	
	@Autowired
	SessionFactory sessionFactory;
	

	private static final Logger logger = LoggerFactory.getLogger(ModifyWSManagerDAO.class);
	
	/**
	 * This adds a workspace record into the database.
	 * @param  workSpace
	 * @return errmsg - blank when success and error message on failure.
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	@Override
	public void addWorkSpaceRequest(IWorkSpace workSpace,String projectId) throws QuadrigaStorageException
	{
		try
		{
			WorkspaceDTO workspaceDTO = workspaceDTOMapper.getWorkspaceDTO(workSpace);
			workspaceDTO.setWorkspaceid(generateUniqueID());
			
			List<ProjectWorkspaceDTO> projectWorkspaceList = workspaceDTO.getProjectWorkspaceDTOList();
			
			ProjectWorkspaceDTO projectWorkspaceDTO = new ProjectWorkspaceDTO();
			projectWorkspaceDTO.setProjectWorkspaceDTOPK(new ProjectWorkspaceDTOPK(projectId, workspaceDTO.getWorkspaceid()));
			projectWorkspaceDTO.setWorkspaceDTO(workspaceDTO);
			projectWorkspaceDTO.setProjectDTO((ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, projectId));
			projectWorkspaceDTO.setCreatedby(workspaceDTO.getCreatedby());
			projectWorkspaceDTO.setCreateddate(new Date());
			projectWorkspaceDTO.setUpdatedby(workspaceDTO.getUpdatedby());
			projectWorkspaceDTO.setUpdateddate(new Date());
			
			if(projectWorkspaceList == null)
			{
				projectWorkspaceList = new ArrayList<ProjectWorkspaceDTO>();
			}
			projectWorkspaceList.add(projectWorkspaceDTO);
			workspaceDTO.setProjectWorkspaceDTOList(projectWorkspaceList);
			sessionFactory.getCurrentSession().save(workspaceDTO);
		}
		catch(Exception e)
		{
			logger.error("addWorkSpaceRequest method :",e);
        	throw new QuadrigaStorageException();
		}
	}
	
	
	@Override
	public void transferWSOwnerRequest(String workspaceId,String oldOwner,String newOwner,String collabRole) throws QuadrigaStorageException
	{
		WorkspaceCollaboratorDTO collaborator = null;
		try
		{
		WorkspaceDTO workspaceDTO = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class,workspaceId);
		//set the new workspace owner
		workspaceDTO.setWorkspaceowner(getUserDTO(newOwner));
		workspaceDTO.setUpdatedby(oldOwner);
		workspaceDTO.setUpdateddate(new Date());
		
		
		//fetch the workspace collaborators
		Iterator<WorkspaceCollaboratorDTO> workspaceCollaborator = workspaceDTO.getWorkspaceCollaboratorDTOList().iterator();
		while(workspaceCollaborator.hasNext())
		{
			collaborator = workspaceCollaborator.next();
			if(collaborator.getQuadrigaUserDTO().getUsername().equals(newOwner))
			{
				workspaceCollaborator.remove();
				break;
			}
		}
		
		//add the current owner as a collaborator
		collaborator = new WorkspaceCollaboratorDTO();
		collaborator.setWorkspaceDTO(workspaceDTO);
		collaborator.setWorkspaceCollaboratorDTOPK(new WorkspaceCollaboratorDTOPK(workspaceId,oldOwner,collabRole));
		collaborator.setQuadrigaUserDTO(getUserDTO(oldOwner));
		collaborator.setCreatedby(oldOwner);
		collaborator.setUpdateddate(new Date());
		collaborator.setUpdateddate(new Date());
		collaborator.setUpdatedby(oldOwner);
		workspaceDTO.getWorkspaceCollaboratorDTOList().add(collaborator);
		
		sessionFactory.getCurrentSession().update(workspaceDTO);
		}
		catch(Exception e)
		{
			logger.error("transferWorkspaceOwnerRequest method :",e);
        	throw new QuadrigaStorageException();
		}
	}
	
	@Override
	public QuadrigaUserDTO getUserDTO(String userName) throws QuadrigaStorageException
	{
		try
		{
		Query query = sessionFactory.getCurrentSession().getNamedQuery("QuadrigaUserDTO.findByUsername");
		query.setParameter("username", userName);
		return (QuadrigaUserDTO) query.uniqueResult();
		}
		catch(Exception e)
		{
			logger.error("getProjectOwner :",e);
        	throw new QuadrigaStorageException();
		}
	}

}
