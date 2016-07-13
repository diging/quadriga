package edu.asu.spring.quadriga.dao.resolver.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.resolver.IProjectHandleResolverDAO;
import edu.asu.spring.quadriga.dto.ProjectHandleResolverDTO;

/**
 * This DAO is responsible for storing and retrieving {@link ProjectHandleResolverDTO}s in and from
 * the database. 
 * 
 * @author jdamerow
 *
 */
@Repository
@Transactional
public class ProjectHandleResolverDAO extends BaseDAO<ProjectHandleResolverDTO> implements IProjectHandleResolverDAO {

    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.dao.resolver.impl.IProjectHandleResolverDAO#getDTO(java.lang.String)
     */
    @Override
    public ProjectHandleResolverDTO getDTO(String id) {
        return getDTO(ProjectHandleResolverDTO.class, id);
    }
    
    @Override
    public String getIdPrefix() {
        return messages.getProperty("resolver_id.prefix");
    }

    @Override
    public List<ProjectHandleResolverDTO> getProjectResolversForUser(String username) {
        return sessionFactory.getCurrentSession().createCriteria(ProjectHandleResolverDTO.class).add(Restrictions.eq("username", username)).list();
    }
}
