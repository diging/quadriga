package edu.asu.spring.quadriga.dao.workbench.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.sql.DAOConnectionManager;
import edu.asu.spring.quadriga.dao.workbench.IProjectAccessManagerDAO;
import edu.asu.spring.quadriga.dto.ProjectDTO;

@Repository
public class ProjectAccessManagerDAO extends DAOConnectionManager implements  IProjectAccessManagerDAO 
{
	@Autowired
	private SessionFactory sessionFactory;
	
	public static final Logger logger = LoggerFactory.getLogger(ProjectAccessManagerDAO.class);
	
	@Override
	public boolean chkProjectOwner(String userName,String projectId)
	{
		boolean isProjectOwner;
		
		isProjectOwner = false;
		
		ProjectDTO project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, projectId);
		
		String owner = project.getProjectowner().getUsername();
		
		if(owner.equals(userName))
		{
			isProjectOwner = true;
		}
		else
		{
			isProjectOwner = false;
		}
		
		return isProjectOwner;
	}
	
	@Override
	public boolean chkIsProjectAssociated(String userName)
	{
		boolean isUserAssociated;
		
		int count;
		isUserAssociated = false;
		
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT count(p.projectid) FROM ProjectDTO p WHERE p.projectowner =:userName");
		query.setParameter("userName", userName);
		count = ((Integer) query.iterate().next()).intValue();
		if(count > 0)
		{
			isUserAssociated = true;
		}
		else
		{
			isUserAssociated = false;
		}
		return isUserAssociated;
	}
	
	@Override
	public boolean chkProjectCollaborator(String userName,String collaboratorRole,String projectId)
	{
		boolean isCollaborator;
		
		isCollaborator = false;
		int count;
		
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT count(pc.projectid) FROM ProjectCollaboratorDTO pc WHERE pc.collaboratoruser =:userName AND pc.collaboratorrole =:collaboratorRole AND pc.projectid =:projectId");
		query.setParameter("userName", userName);
		query.setParameter("collaboratorRole", collaboratorRole);
		query.setParameter("projectId",projectId);
		count = ((Integer) query.iterate().next()).intValue();
		if(count > 0)
		{
			isCollaborator = true;
		}
		else
		{
			isCollaborator = false;
		}
		
		return isCollaborator;
	}
	
	@Override
	public boolean chkDuplicateProjUnixName(String unixName,String projectId)
	{
		boolean isDuplicate;
		isDuplicate = false;
		
		Query query = sessionFactory.getCurrentSession().getNamedQuery("ProjectDTO.findByUnixname");
		query.setString("unixname", unixName);
		
		ProjectDTO project = (ProjectDTO) query.uniqueResult();
		
		if(!project.equals(null))
		{
			if(project.getProjectid().equals(projectId))
			{
				isDuplicate = false;
			}
			else
			{
				isDuplicate = true;
			}
		}
		return isDuplicate;
	}
	
	@Override
	public boolean chkProjectOwnerEditorRole(String userName,String projectId)
	{
		boolean isEditor;
		isEditor = false;
		int count;
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT count(pc.projectEditorDTOPK.projectid) FROM ProjectEditorDTO pc WHERE pc.projectEditorDTOPK.projectid =:projectId AND pc.projectEditorDTOPK.owner =:userName");
		query.setParameter("userName", userName);
		query.setParameter("projectId",projectId);
		
		count = ((Number) query.uniqueResult()).intValue();
		if(count > 0)
		{
			isEditor = true;
		}
		else
		{
			isEditor = false;
		}
		
		return isEditor;
	}

}
