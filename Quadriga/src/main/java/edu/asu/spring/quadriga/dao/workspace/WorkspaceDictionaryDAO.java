package edu.asu.spring.quadriga.dao.workspace;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.DAOConnectionManager;
import edu.asu.spring.quadriga.db.workspace.IDBConnectionWorkspaceDictionary;
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDictionaryDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDictionaryDTOPK;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.DictionaryDTOMapper;

@Repository
public class WorkspaceDictionaryDAO extends DAOConnectionManager implements IDBConnectionWorkspaceDictionary 
{
	
	@Autowired
	DictionaryDTOMapper dictionaryMapper;
	
	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addWorkspaceDictionary(String workspaceId,
			String dictionaryId, String userId) throws QuadrigaStorageException 
	{
		WorkspaceDTO workspace = null;
		List<WorkspaceDictionaryDTO> workspaceDictionaryList = null;
		
		//check if the dictionary is already associated to the workspace
		try
		{
			workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class, workspaceId);
			
			workspaceDictionaryList = workspace.getWorkspaceDictionaryDTOList();
			
			WorkspaceDictionaryDTOPK workspaceDictionaryKey = new WorkspaceDictionaryDTOPK(workspaceId,dictionaryId);
			WorkspaceDictionaryDTO workspaceDictionary = new WorkspaceDictionaryDTO(workspaceDictionaryKey);
			Date date = new Date();
			workspaceDictionary.setCreatedby(userId);
			workspaceDictionary.setCreateddate(date);
			workspaceDictionary.setUpdatedby(userId);
			workspaceDictionary.setUpdateddate(date);
			
			if(workspaceDictionaryList.contains(workspaceDictionary))
			{
				throw new QuadrigaStorageException();
			}
			else
			{
			   workspaceDictionaryList.add(workspaceDictionary);
			   workspace.setWorkspaceDictionaryDTOList(workspaceDictionaryList);
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
	public List<IDictionary> listWorkspaceDictionary(String workspaceId,
			String userId) throws QuadrigaStorageException 
	{
		WorkspaceDTO workspace = null;
		List<WorkspaceDictionaryDTO> workspaceDictionaryList = null;
		IDictionary dictionary = null;
		DictionaryDTO dictionaryDTO = null;
		List<IDictionary> dictionaryList = new ArrayList<IDictionary>();
		
		try
		{
			workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class, workspaceId);
			workspaceDictionaryList = workspace.getWorkspaceDictionaryDTOList();
			for(WorkspaceDictionaryDTO workspaceDictionary : workspaceDictionaryList)
			{
				dictionaryDTO = workspaceDictionary.getDictionaryDTO();
				dictionary = dictionaryMapper.getDictionary(dictionaryDTO);
				dictionaryList.add(dictionary);
			}
		}
		catch(Exception ex)
		{
			throw new QuadrigaStorageException();
		}
		
		return dictionaryList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteWorkspaceDictionary(String workspaceId, String userId,
			String dictionaryId) throws QuadrigaStorageException 
	{
		try
		{
			WorkspaceDictionaryDTOPK workspaceDictionaryKey = new WorkspaceDictionaryDTOPK(workspaceId,dictionaryId);
			
			WorkspaceDictionaryDTO workspaceDictionary = new WorkspaceDictionaryDTO(workspaceDictionaryKey);
			
			sessionFactory.getCurrentSession().delete(workspaceDictionary);
		}
		catch(Exception ex)
		{
			throw new QuadrigaStorageException();
		}
		
	}

}
