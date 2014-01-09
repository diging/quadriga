package edu.asu.spring.quadriga.dao.workspace;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.DAOConnectionManager;
import edu.asu.spring.quadriga.db.workspace.IDBConnectionWorkspaceCC;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.dto.ConceptCollectionDTO;
import edu.asu.spring.quadriga.dto.WorkspaceConceptcollectionDTO;
import edu.asu.spring.quadriga.dto.WorkspaceConceptcollectionDTOPK;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.ConceptCollectionDTOMapper;

@Repository
public class WorkspaceCCDAO extends DAOConnectionManager implements IDBConnectionWorkspaceCC {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private ConceptCollectionDTOMapper collectionMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(WorkspaceCCDAO.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String addWorkspaceCC(String workspaceId, String CCId, String userId) throws QuadrigaStorageException {
		String errMsg = "";
		ConceptCollectionDTO conceptCollection = null;
		WorkspaceDTO workspace = null;
		List<WorkspaceConceptcollectionDTO> workspaceConceptCollectionDTOList = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("from WorkspaceConceptcollectionDTO wrkConceptColl where wrkConceptColl.workspaceConceptcollectionDTOPK.workspaceid =:workspaceid and wrkConceptColl.workspaceConceptcollectionDTOPK.conceptcollectionid =:conceptcollectionid");
			query.setParameter("workspaceid", workspaceId);
			query.setParameter("conceptcollectionid", CCId);
			if(query.list()!= null)
			{
				errMsg = "Concept Collection already exists";
			}
			
			else
			{
				workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class, workspaceId);
				conceptCollection = (ConceptCollectionDTO) sessionFactory.getCurrentSession().get(ConceptCollectionDTO.class, CCId);
				
				workspaceConceptCollectionDTOList = workspace.getWorkspaceConceptCollectionDTOList();
				
				WorkspaceConceptcollectionDTO wrkspaceCCDTO  = new WorkspaceConceptcollectionDTO(new WorkspaceConceptcollectionDTOPK(workspaceId,CCId));
				wrkspaceCCDTO.setCreatedby(userId);
				wrkspaceCCDTO.setCreateddate(new Date());
				wrkspaceCCDTO.setUpdatedby(userId);
				wrkspaceCCDTO.setUpdateddate(new Date());
				wrkspaceCCDTO.setConceptCollectionDTO(conceptCollection);
				wrkspaceCCDTO.setWorkspaceDTO(workspace);
				
				workspaceConceptCollectionDTOList.add(wrkspaceCCDTO);
				
				sessionFactory.getCurrentSession().save(wrkspaceCCDTO);
				sessionFactory.getCurrentSession().update(workspace);
			}
		}
		catch(Exception e)
		{
			errMsg = "OOPS ! Error from DB";
			logger.error("addWorkspaceCC method :",e);
        	throw new QuadrigaStorageException();
		}
		return errMsg;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<IConceptCollection> listWorkspaceCC(String workspaceId, String userId) throws QuadrigaStorageException {
		List<IConceptCollection> conceptCollList = new ArrayList<IConceptCollection>();
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("Select wrkConceptColl.conceptCollectionDTO from WorkspaceConceptcollectionDTO wrkConceptColl where wrkConceptColl.workspaceConceptcollectionDTOPK.workspaceid =:workspaceid");
			query.setParameter("workspaceid", workspaceId);
			List<ConceptCollectionDTO> conceptCollDTOList = query.list();
				for(ConceptCollectionDTO conceptCollectionDTO : conceptCollDTOList)
				{
					IConceptCollection conceptCollection = collectionMapper.getConceptCollection(conceptCollectionDTO);
					conceptCollList.add(conceptCollection);
				}
		}
		catch(Exception e)
		{
			logger.error("listWorkspaceCC method :",e);
        	throw new QuadrigaStorageException();
		}
		return conceptCollList;
	}
	
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
				conceptCollection = collectionMapper.getConceptCollection(conceptCollectionDTO);
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
		List<WorkspaceConceptcollectionDTO> workspaceConceptCollectionDTOList = null;
		try
		{
		
		workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class, workspaceId);
		
		workspaceConceptCollectionDTOList = workspace.getWorkspaceConceptCollectionDTOList();
		
		//retrieve the concept collection associated with the workspace
		WorkspaceConceptcollectionDTOPK workspaceConceptCollectionKey = new WorkspaceConceptcollectionDTOPK(workspaceId,CCId);
		
		WorkspaceConceptcollectionDTO workspaceConceptCollection = (WorkspaceConceptcollectionDTO) sessionFactory.getCurrentSession().get(WorkspaceConceptcollectionDTO.class,workspaceConceptCollectionKey);
		
		if(workspaceConceptCollectionDTOList.contains(workspaceConceptCollection))
		{
			workspaceConceptCollectionDTOList.remove(workspaceConceptCollection);
		}
		
		workspace.setWorkspaceConceptCollectionDTOList(workspaceConceptCollectionDTOList);
		
		sessionFactory.getCurrentSession().delete(workspaceConceptCollection);
		sessionFactory.getCurrentSession().update(workspace);
		}
		catch(Exception ex)
		{
			throw new QuadrigaStorageException();
		}
	}
	
	
}
