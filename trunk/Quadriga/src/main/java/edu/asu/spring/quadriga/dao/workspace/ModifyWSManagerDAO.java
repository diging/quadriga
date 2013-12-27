package edu.asu.spring.quadriga.dao.workspace;

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

import edu.asu.spring.quadriga.dao.DAOConnectionManager;
import edu.asu.spring.quadriga.db.workspace.IDBConnectionModifyWSManager;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTO;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTOPK;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTO;
import edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTOPK;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.dto.WorkspaceEditorDTO;
import edu.asu.spring.quadriga.dto.WorkspaceEditorDTOPK;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.WorkspaceDTOMapper;

@Repository
public class ModifyWSManagerDAO extends DAOConnectionManager implements IDBConnectionModifyWSManager {

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
			/*projectWorkspaceDTO.setWorkspaceDTO(workspaceDTO);
			projectWorkspaceDTO.setProjectDTO((ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, projectId));*/
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
	public void assignWorkspaceOwnerEditor(String workspaceId,String owner) throws QuadrigaStorageException
	{
		try
		{
			WorkspaceEditorDTO workspaceEditorDTO = new WorkspaceEditorDTO();
			workspaceEditorDTO.setWorkspaceEditorDTOPK(new WorkspaceEditorDTOPK(workspaceId, owner));
			workspaceEditorDTO.setCreatedby(owner);
			workspaceEditorDTO.setCreateddate(new Date());
			workspaceEditorDTO.setUpdatedby(owner);
			workspaceEditorDTO.setUpdateddate(new Date());
			sessionFactory.getCurrentSession().save(workspaceEditorDTO);
		}
		catch(Exception e)
		{
			logger.error("assignWorkspaceOwnerEditor method :",e);
        	throw new QuadrigaStorageException();
		}
	}
	
	@Override
	public String deleteWorkspaceOwnerEditor(String workspaceId,String owner) throws QuadrigaStorageException
	{
		String result = null;
		try
		{
			WorkspaceEditorDTO workspaceEditorDTO = (WorkspaceEditorDTO) sessionFactory.getCurrentSession().get(WorkspaceEditorDTO.class, new WorkspaceEditorDTO(workspaceId, owner));
			if(workspaceEditorDTO != null)
			{
				sessionFactory.getCurrentSession().delete(workspaceEditorDTO);
			}
			else
			{
				result = "Owner don't exist";
			}
		}
		catch(Exception e)
		{
			logger.error("deleteWorkspaceOwnerEditor method :",e);
        	throw new QuadrigaStorageException();
		}
		return result;
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
			logger.error("getUserDTO :",e);
        	throw new QuadrigaStorageException();
		}
	}

	@Override
	public String deleteWorkspaceRequest(String workspaceIdList) throws QuadrigaStorageException
	{
		String errMsg = "";
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("Delete from WorkspaceDTO workspace where workspace.workspaceid IN (:workspaceIdList)");
			query.setParameter("workspaceIdList", workspaceIdList);
			int updatedRecordCount = query.executeUpdate();
			if(! (updatedRecordCount > 0))
			{
				errMsg = "Error in deleting workspaces";
			}
		}
		catch(Exception e)
		{
			logger.error("deleteWorkspaceRequest :",e);
			errMsg = "Exception in Database";
        	throw new QuadrigaStorageException();
		}
		return errMsg;
	}

	@Override
	public String updateWorkspaceRequest(IWorkSpace workspace) throws QuadrigaStorageException {
		String errMsg = "";
		try
		{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("WorkspaceDTO.findByWorkspaceid");
			query.setParameter("workspaceid", workspace.getId());
			WorkspaceDTO workspaceDTO = (WorkspaceDTO) query.uniqueResult();
			if(workspaceDTO != null)
			{
				workspaceDTO.setWorkspacename(workspace.getName());
				workspaceDTO.setDescription(workspace.getDescription());
				workspaceDTO.setUpdateddate(new Date());
				workspaceDTO.setUpdatedby(workspace.getOwner().getName());
				
				sessionFactory.getCurrentSession().update(workspaceDTO);
			}
			else
			{
				errMsg = "Workspace ID is invalid";
			}
		}
		catch(Exception e)
		{
			logger.error("deleteWorkspaceRequest :",e);
			errMsg = "Exception in Database";
        	throw new QuadrigaStorageException();
		}
		return errMsg;
	}

}
