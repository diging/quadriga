package edu.asu.spring.quadriga.dao.workspace.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.sql.DAOConnectionManager;
import edu.asu.spring.quadriga.dao.workspace.IWorkspaceCollaboratorManagerDAO;
import edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTO;
import edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTOPK;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

@Repository
public class WorkspaceCollaboratorManagerDAO  extends DAOConnectionManager implements
		IWorkspaceCollaboratorManagerDAO 
{
	
	private static final Logger logger = LoggerFactory.getLogger(WorkspaceCollaboratorManagerDAO.class);
	
	@Autowired
	private SessionFactory   sessionFactory;
	
	@Resource(name = "database_error_msgs")
	private Properties messages;
	
	
	@Override
	public void addWorkspaceCollaborator(String collaborator,String collabRoleList,String workspaceid,String userName) throws QuadrigaStorageException 
	{
		WorkspaceDTO workspace = null;
		WorkspaceCollaboratorDTO workspaceCollaborator = null;
		List<WorkspaceCollaboratorDTO> collaboratorList = null;
		WorkspaceCollaboratorDTOPK collaboratorPK = null;
		
		String[] collaboratorRoles;
		try
		{
			collaboratorList = new ArrayList<WorkspaceCollaboratorDTO>();
			//fetch the workspaceDTO details
			workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class, workspaceid);
			
			if(!workspace.equals(null))
			{
				//create a collaboratorDTO with the given details
				collaboratorRoles = collabRoleList.split(",");
				for(int i=0;i<collaboratorRoles.length;i++)
				{
					workspaceCollaborator = new WorkspaceCollaboratorDTO();
					collaboratorPK = new WorkspaceCollaboratorDTOPK(workspaceid,collaborator,collaboratorRoles[i]);
					workspaceCollaborator.setWorkspaceDTO(workspace);
					workspaceCollaborator.setWorkspaceCollaboratorDTOPK(collaboratorPK);
					workspaceCollaborator.setQuadrigaUserDTO(getUserDTO(collaborator));
					workspaceCollaborator.setCreatedby(userName);
					workspaceCollaborator.setCreateddate(new Date());
					workspaceCollaborator.setUpdatedby(userName);
					workspaceCollaborator.setUpdateddate(new Date());
					collaboratorList.add(workspaceCollaborator);
				}
				
				workspace.setWorkspaceCollaboratorDTOList(collaboratorList);
				sessionFactory.getCurrentSession().update(workspace);
			}
			else
			{
				throw new QuadrigaStorageException(messages.getProperty("workspaceId_invalid"));
			}
			
		}
		catch(QuadrigaStorageException ex)
		{
			logger.error("Error while adding workspace collaborators:",ex);
			throw new QuadrigaStorageException();
		}
	}
	
	@Override
	public void deleteWorkspaceCollaborator(String collaborator,String workspaceid) throws QuadrigaStorageException
	{
		WorkspaceDTO workspace = null;
		List<WorkspaceCollaboratorDTO> collaboratorList = null;
		List<String> collaborators = null;
		
		try
		{
			workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class, workspaceid);
			collaboratorList = workspace.getWorkspaceCollaboratorDTOList();
			collaborators = getList(collaborator);
			if(collaborators.size() == 0)
			{
				throw new QuadrigaStorageException(messages.getProperty("workspace_collaborator_invalid"));
			}
			if(!workspace.equals(null))
			{
				Iterator<WorkspaceCollaboratorDTO> iterator = collaboratorList.iterator();
				while(iterator.hasNext())
				{
					String userName = iterator.next().getWorkspaceCollaboratorDTOPK().getUsername();
					if(collaborators.contains(userName))
					{
						iterator.remove();
					}
				}
				
				workspace.setWorkspaceCollaboratorDTOList(collaboratorList);
				
				sessionFactory.getCurrentSession().update(workspace);
			}
			else
			{
				throw new QuadrigaStorageException(messages.getProperty("workspaceId_invalid"));
			}
		}
		catch(Exception ex)
		{
			logger.error("Error in deleting workspace collaborators",ex);
			throw new QuadrigaStorageException();
		}
	}
	
	@Override
	public void updateWorkspaceCollaborator(String workspaceId,String collabUser,String collaboratorRole,String userName) throws QuadrigaStorageException
	{
		WorkspaceDTO workspace = null;
		WorkspaceCollaboratorDTO collaborator = null;
		WorkspaceCollaboratorDTOPK collaboratorPK = null;
		List<WorkspaceCollaboratorDTO> collaboratorList = null;
		ArrayList<String> collaboratorRoles;
		String wsCollaborator;
		
		try
		{
			workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class, workspaceId);
			collaboratorList = workspace.getWorkspaceCollaboratorDTOList();
			collaboratorRoles = getList(collaboratorRole);
			if(!workspace.equals(null))
			{
				//delete all the roles associated with the user
				Iterator<WorkspaceCollaboratorDTO> iterator = collaboratorList.iterator();
				while(iterator.hasNext())
				{
					wsCollaborator = iterator.next().getQuadrigaUserDTO().getUsername();
					if(wsCollaborator.equals(collabUser))
					{
						iterator.remove();
					}
				}
				
				workspace.setWorkspaceCollaboratorDTOList(collaboratorList);
				//add the user with new roles
				//create a collaboratorDTO with the given details
				for(String value : collaboratorRoles)
				{
					collaborator = new WorkspaceCollaboratorDTO();
					collaboratorPK = new WorkspaceCollaboratorDTOPK(workspaceId,collabUser,value);
					collaborator.setWorkspaceDTO(workspace);
					collaborator.setWorkspaceCollaboratorDTOPK(collaboratorPK);
					collaborator.setQuadrigaUserDTO(getUserDTO(collabUser));
					collaborator.setCreatedby(userName);
					collaborator.setCreateddate(new Date());
					collaborator.setUpdatedby(userName);
					collaborator.setUpdateddate(new Date());
					collaboratorList.add(collaborator);
				}
				
				workspace.setWorkspaceCollaboratorDTOList(collaboratorList);
				
				sessionFactory.getCurrentSession().update(workspace);
			}
			else
			{
				throw new QuadrigaStorageException(messages.getProperty("workspaceId_invalid"));
			}
		}
		catch(Exception ex)
		{
			throw new QuadrigaStorageException();
		}
		
	}

}
