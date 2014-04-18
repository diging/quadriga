package edu.asu.spring.quadriga.dao.editor;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.DAOConnectionManager;
import edu.asu.spring.quadriga.db.editor.IDBConnectionEditorAccessManager;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

@Repository
public class EditorAccessManagerDAO extends DAOConnectionManager implements IDBConnectionEditorAccessManager
{
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public boolean chkIsEditor(String userName) throws QuadrigaStorageException 
	{
		boolean isEditor;
		int count;
		Query query = null;
		isEditor = false;
		
		//check if he is a project editor
		query = sessionFactory.getCurrentSession().createQuery("SELECT COUNT(p.projectEditorDTOPK.projectid) FROM ProjectEditorDTO p WHERE p.projectEditorDTOPK.editor =:userName");
		query.setParameter("userName", userName);
		count = ((Number)query.iterate().next()).intValue();
		if(count > 0)
		{
			isEditor = true;
		}
		
		//check if he is workspace editor
		if(!isEditor)
		{
			query = sessionFactory.getCurrentSession().createQuery("SELECT COUNT(ws.workspaceEditorDTOPK.workspaceid) FROM WorkspaceEditorDTO ws WHERE ws.workspaceEditorDTOPK.editor =:userName");
			query.setParameter("username", userName);
			count = ((Number)query.iterate().next()).intValue();
			if(count > 0)
			{
				isEditor = true;
			}
		}
		
		
		return isEditor;
	}

	@Override
	public boolean chkIsNetworkEditor(String networkId, String userName)
			throws QuadrigaStorageException 
	{
		boolean isEditor;
		Query query = null;
		int count;
		
		isEditor = false;
		
		//check if he is workspace editor for given network
		query = sessionFactory.getCurrentSession().createQuery("SELECT COUNT(n.networkid) FROM NetworksDTO n WHERE n.networkid =:networkId" +
				" AND n.workspaceid IN (SELECT ws.workspaceid FROM WorkspaceEditorDTO ws WHERE ws.editor =:username)");
		query.setParameter("networkid", networkId);
		query.setParameter("username", userName);
		count = ((Number)query.iterate().next()).intValue();
		if(count > 0)
		{
			isEditor = true;
		}
		
		//check if the user is project editor for given network
		if(!isEditor)
		{
			query = sessionFactory.getCurrentSession().createQuery("SELECT COUNT(n.networkid) FROM NetworksDTO n WHERE n.networkid" +
					" AND n.workspaceid IN (SELECT pws.workspaceid FROM ProjectWorkspaceDTO pws WHERE pws.projectWorkspaceDTOPK.projectid IN " +
					" (SELECT p.projectid FROM ProjectEditorDTO p WHERE p.projectEditorDTOPK.editor =:username))");
			query.setParameter("networkid", networkId);
            query.setParameter("username", userName);
            count = ((Number)query.iterate().next()).intValue();
    		if(count > 0)
    		{
    			isEditor = true;
    		}
		}
		
		return isEditor;
	}

}