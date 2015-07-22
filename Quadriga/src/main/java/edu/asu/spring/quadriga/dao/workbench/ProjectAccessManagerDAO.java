package edu.asu.spring.quadriga.dao.workbench;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.DAOConnectionManager;
import edu.asu.spring.quadriga.db.workbench.IProjectAccessManager;
import edu.asu.spring.quadriga.dto.ProjectDTO;

@Repository
public class ProjectAccessManagerDAO extends DAOConnectionManager implements  IProjectAccessManager 
{
	@Autowired
	private SessionFactory sessionFactory;
	
	public static final Logger logger = LoggerFactory.getLogger(ProjectAccessManagerDAO.class);
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getProjectOwner(String projectId)
	{
		ProjectDTO project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, projectId);
		
		return project.getProjectowner().getUsername();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getNrOfOwnedProjects(String userName)
	{
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT count(p.projectid) FROM ProjectDTO p WHERE p.projectowner.username =:userName");
		query.setParameter("userName", userName);
		return ((Number) query.iterate().next()).intValue();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int nrOfProjectsCollaboratingOn(String userName,String collaboratorRole)
	{
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT count(pc.projectCollaboratorDTOPK.projectid) FROM ProjectCollaboratorDTO pc WHERE pc.projectCollaboratorDTOPK.collaboratoruser =:userName AND pc.projectCollaboratorDTOPK.collaboratorrole =:collaboratorRole");
		query.setParameter("userName", userName);
		query.setParameter("collaboratorRole", collaboratorRole);
		return ((Number) query.iterate().next()).intValue();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isCollaborator(String userName,String collaboratorRole,String projectId)
	{
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT count(pc.projectCollaboratorDTOPK.projectid) FROM ProjectCollaboratorDTO pc WHERE pc.projectCollaboratorDTOPK.collaboratoruser =:userName AND pc.projectCollaboratorDTOPK.collaboratorrole =:collaboratorRole AND pc.projectCollaboratorDTOPK.projectid =:projectId");
		query.setParameter("userName", userName);
		query.setParameter("collaboratorRole", collaboratorRole);
		query.setParameter("projectId",projectId);
		return ((Number) query.iterate().next()).intValue() > 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean chkDuplicateProjUnixName(String unixName,String projectId)
	{
		boolean isDuplicate;
		isDuplicate = false;
		
		Query query = sessionFactory.getCurrentSession().getNamedQuery("ProjectDTO.findByUnixname");
		query.setString("unixname", unixName);
		
		ProjectDTO project = (ProjectDTO) query.uniqueResult();
		
		if(project !=null)
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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean chkProjectOwnerEditorRole(String userName,String projectId)
	{
		boolean isEditor;
		isEditor = false;
		int count;
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT count(pc.projectEditorDTOPK.projectid) FROM ProjectEditorDTO pc WHERE pc.projectEditorDTOPK.projectid =:projectId AND pc.projectEditorDTOPK.editor =:userName");
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
