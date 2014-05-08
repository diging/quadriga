package edu.asu.spring.quadriga.dao.workspace;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.DAOConnectionManager;
import edu.asu.spring.quadriga.db.workspace.IDBConnectionModifyWSManager;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.dto.ConceptCollectionDTO;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.dto.NetworkWorkspaceDTO;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTO;
import edu.asu.spring.quadriga.dto.WorkspaceConceptcollectionDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDictionaryDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDspaceDTO;
import edu.asu.spring.quadriga.dto.WorkspaceEditorDTO;
import edu.asu.spring.quadriga.dto.WorkspaceEditorDTOPK;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.ProjectDTOMapper;
import edu.asu.spring.quadriga.mapper.WorkspaceCollaboratorDTOMapper;
import edu.asu.spring.quadriga.mapper.WorkspaceDTOMapper;

@Repository
public class ModifyWSManagerDAO extends DAOConnectionManager implements IDBConnectionModifyWSManager {

	@Autowired
	WorkspaceDTOMapper workspaceDTOMapper;

	@Autowired
	ProjectDTOMapper projectMapper;

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	WorkspaceCollaboratorDTOMapper collaboratorMapper;

	private static final Logger logger = LoggerFactory.getLogger(ModifyWSManagerDAO.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addWorkSpaceRequest(IWorkSpace workSpace,String projectId) throws QuadrigaStorageException
	{
		try
		{
			ProjectDTO project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class,projectId);
			WorkspaceDTO workspaceDTO = workspaceDTOMapper.getWorkspaceDTO(workSpace);
			workspaceDTO.setWorkspaceid(generateUniqueID());


			ProjectWorkspaceDTO projectWorkspaceDTO = projectMapper.getProjectWorkspace(project, workspaceDTO);

			workspaceDTO.setProjectWorkspaceDTO(projectWorkspaceDTO);
			sessionFactory.getCurrentSession().save(workspaceDTO);
			sessionFactory.getCurrentSession().update(project);
		}
		catch(HibernateException e)
		{
			logger.error("addWorkSpaceRequest method :",e);
			throw new QuadrigaStorageException();
		}
	}

	/**
	 * {@inheritDoc}
	 */
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
			collaborator = collaboratorMapper.getWorkspaceCollaboratorDTO(workspaceDTO, oldOwner, collabRole);
			workspaceDTO.getWorkspaceCollaboratorDTOList().add(collaborator);

			sessionFactory.getCurrentSession().update(workspaceDTO);
		}
		catch(Exception e)
		{
			logger.error("transferWorkspaceOwnerRequest method :",e);
			throw new QuadrigaStorageException();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void assignWorkspaceOwnerEditor(String workspaceId,String owner) throws QuadrigaStorageException
	{
		try
		{
			WorkspaceDTO workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class,workspaceId);
			WorkspaceEditorDTO workspaceEditorDTO = workspaceDTOMapper.getWorkspaceEditor(workspace, owner);
			sessionFactory.getCurrentSession().save(workspaceEditorDTO);
		}
		catch(Exception e)
		{
			logger.error("assignWorkspaceOwnerEditor method :",e);
			throw new QuadrigaStorageException();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String deleteWorkspaceOwnerEditor(String workspaceId,String owner) throws QuadrigaStorageException
	{
		String result = "";
		try
		{
			WorkspaceEditorDTO workspaceEditorDTO = (WorkspaceEditorDTO) sessionFactory.getCurrentSession().get(WorkspaceEditorDTO.class, new WorkspaceEditorDTOPK(workspaceId, owner));
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

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String deleteWorkspaceRequest(String workspaceIdList) throws QuadrigaStorageException
	{
		String errMsg = "";
		try
		{
			deleteProjectWorkspace(workspaceIdList);
			deleteWorkspaceConceptcollection(workspaceIdList);
			deleteWorkspaceDictionary(workspaceIdList);
			deleteWorkspaceDspace(workspaceIdList);
			deleteWorkspaceEditor(workspaceIdList);
			deleteWorkspaceNetwork(workspaceIdList);

			//Delete the workspace ids
			String[] workspaceIds = workspaceIdList.split(",");
			for(String workspaceId : workspaceIds)
			{
				WorkspaceDTO workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class, workspaceId);
				sessionFactory.getCurrentSession().delete(workspace);
			}
//			Query query = sessionFactory.getCurrentSession().createQuery("Delete from WorkspaceDTO workspace where workspace.workspaceid IN (:workspaceIdList)");
//			query.setParameter("workspaceIdList", workspaceIdList);
//			query.executeUpdate();
		}
		catch(Exception e)
		{
			logger.error("deleteWorkspaceRequest :",e);
			errMsg = "Exception in Database";
			throw new QuadrigaStorageException();
		}
		return errMsg;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String updateWorkspaceRequest(IWorkSpace workspace) throws QuadrigaStorageException {
		String errMsg = "";
		try
		{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("WorkspaceDTO.findByWorkspaceid");
			query.setParameter("workspaceid", workspace.getWorkspaceId());
			WorkspaceDTO workspaceDTO = (WorkspaceDTO) query.uniqueResult();
			if(workspaceDTO != null)
			{
				workspaceDTO.setWorkspacename(workspace.getWorkspaceName());
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteProjectWorkspace(String workspaceIdList) throws QuadrigaStorageException
	{
		try
		{
			String[] workspaceIds = workspaceIdList.split(",");
			for(String workspaceId : workspaceIds)
			{
				WorkspaceDTO workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class, workspaceId);
				if(workspace == null)
				{
					throw new QuadrigaStorageException(); 
				}
				ProjectWorkspaceDTO projectWorkspaceDTO = workspace.getProjectWorkspaceDTO();

				ProjectDTO project = projectWorkspaceDTO.getProjectDTO();
				List<ProjectWorkspaceDTO> tempProjectWorkspaceList = project.getProjectWorkspaceDTOList();
				if(tempProjectWorkspaceList != null && tempProjectWorkspaceList.contains(projectWorkspaceDTO))
				{
					tempProjectWorkspaceList.remove(projectWorkspaceDTO);
					project.setProjectWorkspaceDTOList(tempProjectWorkspaceList);
					sessionFactory.getCurrentSession().update(project);
				}
				sessionFactory.getCurrentSession().delete(projectWorkspaceDTO);
				//assigning the workspace project mapping to null for workspace object
				workspace.setProjectWorkspaceDTO(null);
				sessionFactory.getCurrentSession().update(workspace);
			}
		}
		catch(Exception ex)
		{
			throw new QuadrigaStorageException();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteWorkspaceDictionary(String workspaceIdList) throws QuadrigaStorageException
	{
		try
		{
			String[] workspaceIds = workspaceIdList.split(",");
			for(String workspaceId : workspaceIds)
			{
				WorkspaceDTO workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class, workspaceId);
				if(workspace == null)
				{
					throw new QuadrigaStorageException(); 
				}
				List<WorkspaceDictionaryDTO> workspaceDictionaryList = workspace.getWorkspaceDictionaryDTOList();
				for(WorkspaceDictionaryDTO workspaceDictionary : workspaceDictionaryList)
				{
					DictionaryDTO dictionary = workspaceDictionary.getDictionaryDTO();
					List<WorkspaceDictionaryDTO> tempWorkspaceDictionary = dictionary.getWsDictionaryDTOList();
					if(tempWorkspaceDictionary != null && tempWorkspaceDictionary.contains(workspaceDictionary))
					{
						tempWorkspaceDictionary.remove(workspaceDictionary);
						dictionary.setWsDictionaryDTOList(tempWorkspaceDictionary);
						sessionFactory.getCurrentSession().update(tempWorkspaceDictionary);
					}
					sessionFactory.getCurrentSession().delete(workspaceDictionary);
				}

				//assigning the dictionary workspace mapping for workspace object to null
				workspace.setWorkspaceDictionaryDTOList(null);
				sessionFactory.getCurrentSession().update(workspace);
			}
		}
		catch(Exception ex)
		{
			throw new QuadrigaStorageException();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteWorkspaceConceptcollection(String workspaceIdList)
			throws QuadrigaStorageException 
			{
		try
		{
			String[] workspaceIds = workspaceIdList.split(",");
			for(String workspaceId : workspaceIds)
			{
				WorkspaceDTO workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class, workspaceId);
				if(workspace == null)
				{
					throw new QuadrigaStorageException(); 
				}
				List<WorkspaceConceptcollectionDTO> workspaceConceptCollectionList = workspace.getWorkspaceConceptCollectionDTOList();
				for(WorkspaceConceptcollectionDTO workspaceConceptCollection : workspaceConceptCollectionList)
				{
					ConceptCollectionDTO conceptCollection = workspaceConceptCollection.getConceptCollectionDTO();
					List<WorkspaceConceptcollectionDTO> tempWorkspaceConceptCollectionList = conceptCollection.getWsConceptCollectionDTOList();
					if(tempWorkspaceConceptCollectionList !=null && tempWorkspaceConceptCollectionList.contains(conceptCollection))
					{
						tempWorkspaceConceptCollectionList.remove(workspaceConceptCollection);
						conceptCollection.setWsConceptCollectionDTOList(tempWorkspaceConceptCollectionList);
						sessionFactory.getCurrentSession().update(conceptCollection);
					}
					sessionFactory.getCurrentSession().delete(workspaceConceptCollection);
				}

				//set the workspace concept collection mapping to null for workspace object
				workspace.setWorkspaceConceptCollectionDTOList(null);
				sessionFactory.getCurrentSession().update(workspace);
			}
		}
		catch(Exception ex)
		{
			throw new QuadrigaStorageException();
		}
			}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteWorkspaceDspace(String workspaceIdList)
			throws QuadrigaStorageException
			{
		try
		{
			String[] workspaceIds = workspaceIdList.split(",");
			for(String workspaceId : workspaceIds)
			{
				WorkspaceDTO workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class, workspaceId);
				if(workspace == null)
				{
					throw new QuadrigaStorageException(); 
				}
				List<WorkspaceDspaceDTO> workspaceDspaceList = workspace.getWorkspaceDspaceDTOList();
				for(WorkspaceDspaceDTO workspaceDspace : workspaceDspaceList)
				{
					sessionFactory.getCurrentSession().delete(workspaceDspace);
				}
				//set the workspace dspace mapping null in workspace object
				workspace.setWorkspaceDspaceDTOList(null);
				sessionFactory.getCurrentSession().update(workspace);
			}
		}
		catch(Exception ex)
		{
			throw new QuadrigaStorageException();
		}
			}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteWorkspaceEditor(String workspaceIdList)
			throws QuadrigaStorageException {

		try
		{
			String[] workspaceIds = workspaceIdList.split(",");
			for(String workspaceId : workspaceIds)
			{
				WorkspaceDTO workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class, workspaceId);
				if(workspace == null)
				{
					throw new QuadrigaStorageException(); 
				}
				List<WorkspaceEditorDTO> workspaceEditorList = workspace.getWorkspaceEditorDTOList();
				for(WorkspaceEditorDTO workspaceEditor : workspaceEditorList)
				{
					sessionFactory.getCurrentSession().delete(workspaceEditor);
				}

				//set the editor workspace mapping to null in workspace object
				workspace.setWorkspaceEditorDTOList(null);
				sessionFactory.getCurrentSession().update(workspace);
			}
		}
		catch(Exception ex)
		{
			throw new QuadrigaStorageException();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteWorkspaceNetwork(String workspaceIdList)
			throws QuadrigaStorageException 
			{
		try
		{
			String[] workspaceIds = workspaceIdList.split(",");
			for(String workspaceId : workspaceIds)
			{
				WorkspaceDTO workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class, workspaceId);
				if(workspace == null)
				{
					throw new QuadrigaStorageException(); 
				}
				List<NetworkWorkspaceDTO> workspaceNetworksList = workspace.getWorkspaceNetworkDTOList();
				for(NetworkWorkspaceDTO workspaceNetwork : workspaceNetworksList)
				{
					sessionFactory.getCurrentSession().delete(workspaceNetwork);
				}
				//set the workspace network mapping to null in workspace object
				workspace.setWorkspaceNetworkDTOList(null);
				sessionFactory.getCurrentSession().update(workspace);

			}
		}
		catch(Exception ex)
		{
			throw new QuadrigaStorageException();
		}
			}

}
