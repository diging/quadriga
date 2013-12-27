package edu.asu.spring.quadriga.dao.workspace;

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
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

@Repository
public class WorkspaceCCDAO extends DAOConnectionManager implements IDBConnectionWorkspaceCC {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(WorkspaceCCDAO.class);

	/**
	 *  Method add a Concept collection to a workspace        
	 * @param			Workspace ID, Concept collection ID, user id           
	 * @returns         path of list workspace Concept collection page
	 * @throws			QuadrigaStorageException
	 * @author          Karthik Jayaraman
	 */
	@Override
	public String addWorkspaceCC(String workspaceId, String CCId, String userId) throws QuadrigaStorageException {
		String errMsg = "";
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
				WorkspaceConceptcollectionDTO wrkspaceCCDTO  = new WorkspaceConceptcollectionDTO(new WorkspaceConceptcollectionDTOPK(workspaceId,CCId));
				wrkspaceCCDTO.setCreatedby(userId);
				wrkspaceCCDTO.setCreateddate(new Date());
				wrkspaceCCDTO.setUpdatedby(userId);
				wrkspaceCCDTO.setUpdateddate(new Date());
				sessionFactory.getCurrentSession().save(wrkspaceCCDTO);
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
	 * Method to list the Concept collection in workspace
	 * @param workspaceId
	 * @param userId
	 * @return
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	@Override
	public List<IConceptCollection> listWorkspaceCC(String workspaceId, String userId) throws QuadrigaStorageException {
		List<IConceptCollection> conceptCollList = null;
		try
		{
			Query query = sessionFactory.getCurrentSession().createQuery("Select wrkConceptColl.conceptCollectionDTO from WorkspaceConceptcollectionDTO wrkConceptColl where wrkConceptColl.workspaceConceptcollectionDTOPK.workspaceid =:workspaceid");
			query.setParameter("workspaceid", workspaceId);
			List<ConceptCollectionDTO> conceptCollDTOList = query.list();
			if(conceptCollDTOList != null && conceptCollDTOList.size()> 0)
			{
			}
			
			else
			{
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
	public void deleteWorkspaceCC(String workspaceId, String userId, String CCId) throws QuadrigaStorageException 
	{
		try
		{
		//retrieve the concept collection associated with the workspace
		WorkspaceConceptcollectionDTOPK workspaceConceptCollectionKey = new WorkspaceConceptcollectionDTOPK(workspaceId,CCId);
		
		WorkspaceConceptcollectionDTO workspaceConceptCollection = (WorkspaceConceptcollectionDTO) sessionFactory.getCurrentSession().get(WorkspaceConceptcollectionDTO.class,workspaceConceptCollectionKey);
		
		sessionFactory.getCurrentSession().delete(workspaceConceptCollection);
		}
		catch(Exception ex)
		{
			throw new QuadrigaStorageException();
		}
	}
	
	
}
