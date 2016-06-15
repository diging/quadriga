package edu.asu.spring.quadriga.dao.impl.workspace;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.workspace.IWorkspaceAccessDAO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

@Repository
public class WorkspaceAccessDAO extends BaseDAO<WorkspaceDTO> implements IWorkspaceAccessDAO
{
	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean chkWorkspaceOwner(String userName, String workspaceId)
			throws QuadrigaStorageException 
	{
		WorkspaceDTO workspace = null;
		QuadrigaUserDTO workspaceOwner = null;
		boolean isOwner;
		
		try
		{
			isOwner = false;
			workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class, workspaceId);
			
			workspaceOwner = workspace.getWorkspaceowner();
			
			if(workspaceOwner.getUsername().equals(userName))
			{
				isOwner = true;
			}
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			throw new QuadrigaStorageException();
		}
		return isOwner;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean chkWorkspaceOwnerEditorRole(String userName,
			String workspaceId) throws QuadrigaStorageException 
	{
		
	    int count;
	    boolean isEditor;
	    isEditor = false;
	    Query query = null;
	    
	    //check if he is a project editor
		query = sessionFactory.getCurrentSession().createQuery("SELECT COUNT(p.projectEditorDTOPK.projectid) FROM ProjectEditorDTO p WHERE p.projectEditorDTOPK.projectid IN" +
				" (SELECT ws.projectWorkspaceDTOPK.projectid FROM ProjectWorkspaceDTO ws WHERE ws.projectWorkspaceDTOPK.workspaceid =:workspaceid) " +
				" AND p.quadrigaUserDTO.username =:username");
		query.setParameter("username", userName);
		query.setParameter("workspaceid", workspaceId);
		count = ((Number) query.iterate().next()).intValue();
		if(count > 0)
		{
			isEditor = true;
		}
		
		//check if the user is workspace editor
		if(!isEditor)
		{
			query = sessionFactory.getCurrentSession().createQuery("SELECT COUNT(ws.workspaceEditorDTOPK.workspaceid) FROM WorkspaceEditorDTO ws " +
					" WHERE ws.workspaceEditorDTOPK.workspaceid =:workspaceid AND ws.quadrigaUserDTO.username =:username");
			query.setParameter("username",userName);
			query.setParameter("workspaceid", workspaceId);
			count = ((Number)query.iterate().next()).intValue();
			if(count > 0)
			{
				isEditor = true;
			}
		}
		
		return isEditor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean chkWorkspaceProjectInheritOwnerEditorRole(String userName,
			String workspaceId) throws QuadrigaStorageException
	{
		boolean isEditor;
		int count;
		
		isEditor = false;
		 //check if he is a workspace editor
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT COUNT(p.projectEditorDTOPK.projectid) FROM ProjectEditorDTO p WHERE p.projectEditorDTOPK.projectid IN" +
				" (SELECT ws.projectWorkspaceDTOPK.projectid FROM ProjectWorkspaceDTO ws WHERE ws.projectWorkspaceDTOPK.workspaceid =:workspaceid) " +
				" AND p.quadrigaUserDTO.username =:username");
		query.setParameter("username", userName);
		query.setParameter("workspaceid", workspaceId);
		count = ((Number) query.iterate().next()).intValue();
		if(count > 0)
		{
			isEditor = true;
		}
		return isEditor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean chkWorkspaceExists(String workspaceId)
			throws QuadrigaStorageException 
	{
		boolean isExists;
		WorkspaceDTO workspace = null;
		try
		{
			isExists = false;
			workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class, workspaceId);
			if( workspace != null)
			{
				isExists = true;
			}
		}
		catch(Exception ex)
		{
			throw new QuadrigaStorageException();
		}
		return isExists;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean chkIsWorkspaceAssocaited(String userName)
			throws QuadrigaStorageException
	{
		int count;
		boolean isAssociated;
		try
		{
			isAssociated = false;
			
			Query query = sessionFactory.getCurrentSession().createQuery("SELECT COUNT(ws.workspaceid) FROM WorkspaceDTO ws WHERE ws.workspaceowner.username =:username");
			query.setParameter("username",userName);
			count = ((Long) query.iterate().next()).intValue();
			if(count > 0)
			{
				isAssociated = true;
			}
			else
			{
				isAssociated = false;
			}
			
		}
		catch(Exception ex)
		{
			throw new QuadrigaStorageException();
		}
		
		return isAssociated;
	}

	/**
	 * {@inheritDoc}
	 * @throws QuadrigaAccessException 
	 */
	@Override
	public boolean chkIsCollaboratorWorkspaceAssociated(String userName,
			String role) throws QuadrigaStorageException 
	{
		int count;
		boolean isAssociated;
		try
		{
			isAssociated = false;
			Query query = sessionFactory.getCurrentSession().createQuery("SELECT COUNT(w.collaboratorDTOPK.workspaceid) FROM WorkspaceCollaboratorDTO w" +
					" WHERE w.collaboratorDTOPK.collaboratoruser = :collaboratoruser AND " +
					" w.collaboratorDTOPK.collaboratorrole = :collaboratorrole");
			query.setParameter("collaboratoruser", userName);
			query.setParameter("collaboratorrole", role);
			count = ((Long)query.iterate().next()).intValue();
			if(count > 0)
			{
				isAssociated = true;
			}
			else
			{
				isAssociated = false;
			}
		}
		catch(Exception ex)
		{
			throw new QuadrigaStorageException();
		}
		return isAssociated;
	}

    @Override
    public WorkspaceDTO getDTO(String id) {
        return getDTO(WorkspaceDTO.class, id);
    }

}
