package edu.asu.spring.quadriga.dao.workbench.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.workbench.IProjectAccessDAO;
import edu.asu.spring.quadriga.dto.ProjectDTO;

@Repository
public class ProjectAccessDAO extends BaseDAO<ProjectDTO> implements IProjectAccessDAO {
    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger logger = LoggerFactory.getLogger(ProjectAccessDAO.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public String getProjectOwner(String projectId) {
        ProjectDTO project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, projectId);

        return project.getProjectowner().getUsername();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNrOfOwnedProjects(String userName) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery("SELECT count(p.projectid) FROM ProjectDTO p WHERE p.projectowner.username =:userName");
        query.setParameter("userName", userName);
        return ((Number) query.iterate().next()).intValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNrOfProjectsCollaboratingOn(String userName, String collaboratorRole) {
        Query query = sessionFactory.getCurrentSession().createQuery(
                "SELECT count(pc.collaboratorDTOPK.projectid) FROM ProjectCollaboratorDTO pc WHERE pc.collaboratorDTOPK.collaboratoruser =:userName AND pc.collaboratorDTOPK.collaboratorrole =:collaboratorRole");
        query.setParameter("userName", userName);
        query.setParameter("collaboratorRole", collaboratorRole);
        return ((Number) query.iterate().next()).intValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCollaborator(String userName, String collaboratorRole, String projectId) {
        Query query = sessionFactory.getCurrentSession().createQuery(
                "SELECT count(pc.collaboratorDTOPK.projectid) FROM ProjectCollaboratorDTO pc WHERE pc.collaboratorDTOPK.collaboratoruser =:userName AND pc.collaboratorDTOPK.collaboratorrole =:collaboratorRole AND pc.collaboratorDTOPK.projectid =:projectId");
        query.setParameter("userName", userName);
        query.setParameter("collaboratorRole", collaboratorRole);
        query.setParameter("projectId", projectId);
        return ((Number) query.iterate().next()).intValue() > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getProjectIdByUnixName(String unixName) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("ProjectDTO.findByUnixname");
        query.setString("unixname", unixName);

        ProjectDTO project = (ProjectDTO) query.uniqueResult();

        if (project != null) {
            return project.getProjectid();
        }

        return null;
    }

    @Override
    public ProjectDTO getDTO(String id) {
        return getDTO(ProjectDTO.class, id);
    }

    @Transactional
    @Override
    public List<String> getProjectCollaboratorRoles(String userName, String projectId) {
        // TODO Auto-generated method stub
        Query query = sessionFactory.getCurrentSession().createQuery(
                "SELECT pc.collaboratorDTOPK.collaboratorrole FROM ProjectCollaboratorDTO pc WHERE pc.collaboratorDTOPK.collaboratoruser =:userName AND pc.collaboratorDTOPK.projectid =:projectId");
        query.setParameter("userName", userName);
        query.setParameter("projectId", projectId);

        List<String> userCollaboratorRoles = query.list();
        System.out.println(query.list());
        return userCollaboratorRoles;
    }

}
