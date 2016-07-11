package edu.asu.spring.quadriga.dao.workbench.impl;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.workbench.IProjectWorkspaceDAO;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

@Service
public class ProjectWorkspaceDAO extends BaseDAO<ProjectWorkspaceDTO> implements
		IProjectWorkspaceDAO {

	@Override
	public ProjectWorkspaceDTO getDTO(String id) {
		return getDTO(ProjectWorkspaceDTO.class, id);
	}

	/**
	 * This method gets the project id for the workspace id.
	 * 
	 * @param workspaceId
	 * @return Project Id - String object
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public String getCorrespondingProjectID(String workspaceId)
			throws QuadrigaStorageException {
		ProjectWorkspaceDTO projectWorkspaceDTO = null;
		try {
			Query query = sessionFactory.getCurrentSession().getNamedQuery(
					"ProjectWorkspaceDTO.findByWorkspaceid");
			query.setParameter("workspaceid", workspaceId);
			projectWorkspaceDTO = (ProjectWorkspaceDTO) query.uniqueResult();
		} catch (HibernateException he) {
			throw new QuadrigaStorageException(he);
		}
		if (projectWorkspaceDTO != null) {
			return projectWorkspaceDTO.getProjectDTO() != null ? projectWorkspaceDTO
					.getProjectDTO().getProjectid() : null;
		}
		return null;
	}
}
