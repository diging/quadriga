package edu.asu.spring.quadriga.dao.impl.workspace;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.workspace.IDBConnectionWorkspaceCC;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.dto.ConceptCollectionDTO;
import edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTO;
import edu.asu.spring.quadriga.dto.WorkspaceConceptcollectionDTO;
import edu.asu.spring.quadriga.dto.WorkspaceConceptcollectionDTOPK;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.ConceptCollectionDTOMapper;
import edu.asu.spring.quadriga.service.conceptcollection.mapper.IConceptCollectionShallowMapper;

@Repository
public class WorkspaceCCDAO extends BaseDAO<WorkspaceConceptcollectionDTO> implements IDBConnectionWorkspaceCC {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private ConceptCollectionDTOMapper collectionMapper;
	
	@Autowired
	private IConceptCollectionShallowMapper ccShallowMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(WorkspaceCCDAO.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String addWorkspaceCC(String workspaceId, String CCId, String userId) throws QuadrigaStorageException {
		String errMsg = "";
		ConceptCollectionDTO conceptCollection = null;
		WorkspaceDTO workspace = null;
		List<WorkspaceConceptcollectionDTO> workspaceConceptCollectionList = null;
		try
		{
			workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class, workspaceId);
			if(workspace == null)
			{
				throw new QuadrigaStorageException("Invalid workspace id");
			}
			
			conceptCollection = (ConceptCollectionDTO) sessionFactory.getCurrentSession().get(ConceptCollectionDTO.class, CCId);
			if(conceptCollection == null)
			{
				throw new QuadrigaStorageException("Invalid concept collection");
			}
			
			WorkspaceConceptcollectionDTOPK wrkspaceCCDTOKey = new WorkspaceConceptcollectionDTOPK(workspaceId,CCId);
			WorkspaceConceptcollectionDTO wrkspaceCCDTO  = (WorkspaceConceptcollectionDTO) sessionFactory.getCurrentSession().get(WorkspaceConceptcollectionDTO.class, wrkspaceCCDTOKey);
			if(wrkspaceCCDTO!=null)
			{
				throw new QuadrigaStorageException("Workspace is already associated with the concept collection");
			}
			
			Date date = new Date();
			wrkspaceCCDTO = new WorkspaceConceptcollectionDTO();
			wrkspaceCCDTO.setWorkspaceConceptcollectionDTOPK(wrkspaceCCDTOKey);
			wrkspaceCCDTO.setCreatedby(userId);
			wrkspaceCCDTO.setCreateddate(date);
			wrkspaceCCDTO.setUpdatedby(userId);
			wrkspaceCCDTO.setUpdateddate(date);
			wrkspaceCCDTO.setConceptCollectionDTO(conceptCollection);
			wrkspaceCCDTO.setWorkspaceDTO(workspace);
			sessionFactory.getCurrentSession().save(wrkspaceCCDTO);
			
			//add the workspace concept collection mapping to workspace DTO
			workspaceConceptCollectionList = workspace.getWorkspaceConceptCollectionDTOList();
			if(workspaceConceptCollectionList == null)
			{
				workspaceConceptCollectionList = new ArrayList<WorkspaceConceptcollectionDTO>();
			}
			workspaceConceptCollectionList.add(wrkspaceCCDTO);
			sessionFactory.getCurrentSession().update(workspace);
			
			//add the workspace concept collection mapping to the concept collection DTO
			workspaceConceptCollectionList = conceptCollection.getWsConceptCollectionDTOList();
			if(workspaceConceptCollectionList == null)
			{
				workspaceConceptCollectionList = new ArrayList<WorkspaceConceptcollectionDTO>();
			}
			workspaceConceptCollectionList.add(wrkspaceCCDTO);
			sessionFactory.getCurrentSession().update(conceptCollection);
		}
		catch(Exception e)
		{
			errMsg = "Oops! Error from DB";
			logger.error("addWorkspaceCC method :",e);
        	throw new QuadrigaStorageException();
		}
		return errMsg;
	}

//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public List<IConceptCollection> listWorkspaceCC(String workspaceId, String userId) throws QuadrigaStorageException {
//		List<IConceptCollection> conceptCollList = new ArrayList<IConceptCollection>();
//		try
//		{
//			WorkspaceDTO workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class, workspaceId);
//			if(workspace == null)
//			{
//				return null;
//			}
//			List<WorkspaceConceptcollectionDTO> workspaceConceptCollection = workspace.getWorkspaceConceptCollectionDTOList();
//			
//			if(workspaceConceptCollection == null)
//			{
//				return null;
//			}
//			
//			for(WorkspaceConceptcollectionDTO tempWSConceptCollection : workspaceConceptCollection)
//			{
//				ConceptCollectionDTO conceptCollectionDTO = tempWSConceptCollection.getConceptCollectionDTO();
//				IConceptCollection conceptCollection = collectionMapper.getConceptCollection(conceptCollectionDTO);
//				conceptCollList.add(conceptCollection);
//			}
//		}
//		catch(Exception e)
//		{
//			logger.error("listWorkspaceCC method :",e);
//        	throw new QuadrigaStorageException();
//		}
//		return conceptCollList;
//	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public WorkspaceDTO listWorkspaceCC(String workspaceId, String userId) throws QuadrigaStorageException {
		List<IConceptCollection> conceptCollList = new ArrayList<IConceptCollection>();
		WorkspaceDTO workspace = null;
		try
		{
			 workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class, workspaceId);
//			if(workspace == null)
//			{
//				return null;
//			}
//			List<WorkspaceConceptcollectionDTO> workspaceConceptCollection = workspace.getWorkspaceConceptCollectionDTOList();
//			
//			if(workspaceConceptCollection == null)
//			{
//				return null;
//			}
//			
//			for(WorkspaceConceptcollectionDTO tempWSConceptCollection : workspaceConceptCollection)
//			{
//				ConceptCollectionDTO conceptCollectionDTO = tempWSConceptCollection.getConceptCollectionDTO();
//				IConceptCollection conceptCollection = collectionMapper.getConceptCollection(conceptCollectionDTO);
//				conceptCollList.add(conceptCollection);
//			}
		}
		catch(Exception e)
		{
			logger.error("listWorkspaceCC method :",e);
        	throw new QuadrigaStorageException();
		}
		return workspace;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<IConceptCollection> getNonAssociatedWorkspaceConcepts(String workspaceId, String userId) throws QuadrigaStorageException
	{
		WorkspaceDTO workspace = null;
		IConceptCollection conceptCollection = null;
		List<IConceptCollection> conceptCollectionList = new ArrayList<IConceptCollection>();
        List<ConceptCollectionDTO> conceptCollectionDTOList = new ArrayList<ConceptCollectionDTO>();
		try
		{
			workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class, workspaceId);
			
			if(!workspace.getWorkspaceowner().getUsername().equals(userId))
			{
				throw new QuadrigaStorageException();
			}
			
			Query query = sessionFactory.getCurrentSession().createQuery("FROM ConceptCollectionDTO cc WHERE cc.conceptCollectionid NOT IN(" +
					"SELECT w.workspaceConceptcollectionDTOPK.conceptcollectionid FROM WorkspaceConceptcollectionDTO w WHERE w.workspaceConceptcollectionDTOPK.workspaceid = :workspaceid)");
			query.setParameter("workspaceid", workspaceId);
			conceptCollectionDTOList = query.list();
			
			for(ConceptCollectionDTO conceptCollectionDTO : conceptCollectionDTOList)
			{
				conceptCollection = ccShallowMapper.getConceptCollectionDetails(conceptCollectionDTO);
				conceptCollectionList.add(conceptCollection);
			}
			
		}
		catch(Exception ex)
		{
			throw new QuadrigaStorageException();
		}
		return conceptCollectionList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteWorkspaceCC(String workspaceId, String userId, String CCId) throws QuadrigaStorageException 
	{
		WorkspaceDTO workspace = null;
		List<WorkspaceConceptcollectionDTO> workspaceConceptCollectionList = null;
		try
		{
		
		workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class, workspaceId);
		
		if(workspace == null)
		{
			throw new QuadrigaStorageException("workspaceid is invalid");
		}
		
		ConceptCollectionDTO conceptCollection = (ConceptCollectionDTO) sessionFactory.getCurrentSession().get(ConceptCollectionDTO.class, CCId);
		
		if(conceptCollection == null)
		{
			throw new QuadrigaStorageException("ConceptCollection id is invalid");
		}
		
		//retrieve the concept collection associated with the workspace
		WorkspaceConceptcollectionDTOPK workspaceConceptCollectionKey = new WorkspaceConceptcollectionDTOPK(workspaceId,CCId);
		
		WorkspaceConceptcollectionDTO workspaceConceptCollection = (WorkspaceConceptcollectionDTO) sessionFactory.getCurrentSession().get(WorkspaceConceptcollectionDTO.class,workspaceConceptCollectionKey);
		
		if(workspaceConceptCollection == null)
		{
			throw new QuadrigaStorageException("Workspace is not mapped to the given concept collection");
		}
		
		sessionFactory.getCurrentSession().delete(workspaceConceptCollection);
		
		
		//remove the mapping from the workspace DTO object
		workspaceConceptCollectionList = workspace.getWorkspaceConceptCollectionDTOList();
		if((workspaceConceptCollectionList !=null)&&(workspaceConceptCollectionList.contains(workspaceConceptCollection)))
		{
			workspaceConceptCollectionList.remove(workspaceConceptCollection);
		}
		sessionFactory.getCurrentSession().update(workspace);
		
		//remove the mapping from the Concept Collection DTO object
		workspaceConceptCollectionList = conceptCollection.getWsConceptCollectionDTOList();
		if((workspaceConceptCollectionList !=null)&&(workspaceConceptCollectionList.contains(workspaceConceptCollection)))
		{
			workspaceConceptCollectionList.remove(workspaceConceptCollection);
		}
		sessionFactory.getCurrentSession().update(conceptCollection);
		
		}
		catch(Exception ex)
		{
			throw new QuadrigaStorageException();
		}
	}

    @Override
    public WorkspaceConceptcollectionDTO getDTO(String id) {
        return getDTO(WorkspaceConceptcollectionDTO.class, id);
    }
	
	
}
