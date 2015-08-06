package edu.asu.spring.quadriga.dao.impl.workspace;

import java.util.Date;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.workspace.IDBConnectionArchiveWSManager;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;

@Repository
public class ArchiveWorkspaceManagerDAO extends BaseDAO implements IDBConnectionArchiveWSManager 
{
	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void archiveWorkspace(String workspaceIdList,boolean archive,String wsUser)
	{
		String[] workspaceId = workspaceIdList.split(",");
		
		for(int i =0 ; i<workspaceId.length;i++)
		{
			WorkspaceDTO workspaceDTO = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class,workspaceId[i]);
			workspaceDTO.setIsarchived(archive);
			workspaceDTO.setUpdatedby(wsUser);
			workspaceDTO.setUpdateddate(new Date());
			sessionFactory.getCurrentSession().update(workspaceDTO);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deactivateWorkspace(String workspaceIdList,boolean deactivate,String wsUser)
	{
		String[] workspaceId = workspaceIdList.split(",");
		for(int i=0;i<workspaceId.length;i++)
		{
			WorkspaceDTO workspaceDTO = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class, workspaceId[i]);
			workspaceDTO.setIsdeactivated(deactivate);
			workspaceDTO.setUpdatedby(wsUser);
			workspaceDTO.setUpdateddate(new Date());
			sessionFactory.getCurrentSession().update(workspaceDTO);
		}
	}

}
