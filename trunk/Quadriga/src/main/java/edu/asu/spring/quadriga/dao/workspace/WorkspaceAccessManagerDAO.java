package edu.asu.spring.quadriga.dao.workspace;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.DAOConnectionManager;
import edu.asu.spring.quadriga.db.workspace.IDBConnectionWSAccessManager;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

@Repository
public class WorkspaceAccessManagerDAO extends DAOConnectionManager implements IDBConnectionWSAccessManager
{
	@Autowired
	private SessionFactory sessionFactory;
	
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

	@Override
	public boolean chkWorkspaceOwnerEditorRole(String userName,
			String workspaceId) throws QuadrigaStorageException 
	{
		//TODO : Require clarification on the logic
		throw new NotYetImplementedException();
	}

	@Override
	public boolean chkWorkspaceProjectInheritOwnerEditorRole(String userName,
			String workspaceId) throws QuadrigaStorageException
	{
		//TODO : Require clarification on the logic
		throw new NotYetImplementedException();
	}

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
			count = ((Integer) query.iterate().next()).intValue();
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
	public boolean chkIsCollaboratorWorkspaceAssociated(String userName,
			String role) throws QuadrigaStorageException 
	{
		int count;
		boolean isAssociated;
		try
		{
			isAssociated = false;
			Query query = sessionFactory.getCurrentSession().createQuery("SELECT COUNT(w.workspaceid) FROM WorkspaceCollaboratorDTO w" +
					" WHERE w.workspaceCollaboratorDTOPK.collaboratoruser = :collaboratoruser AND " +
					" w.workspaceCollaboratorDTOPK.collaboratorrole = :collaboratorrole");
			count = ((Integer)query.iterate().next()).intValue();
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

}
