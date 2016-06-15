package edu.asu.spring.quadriga.dao.impl.workbench.passthroughproject;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.workbench.passthroughproject.IPassThroughProjectDAO;
import edu.asu.spring.quadriga.dto.PassThroughProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * This class is responsible for Querying the MySQL database and fetch the class
 * objects related to Pass Through Project.
 *
 */
@Repository
public class PassThroughProjectDAO extends BaseDAO<PassThroughProjectDTO> implements IPassThroughProjectDAO {

    @Override
    public PassThroughProjectDTO getDTO(String id) {
        return getDTO(PassThroughProjectDTO.class, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public PassThroughProjectDTO getExternalProject(String externalProjectid, String client) throws QuadrigaStorageException {
        return (PassThroughProjectDTO) sessionFactory.getCurrentSession()
                .createCriteria(PassThroughProjectDTO.class)
                .add(Restrictions.eq("externalProjectid", externalProjectid))
                .add(Restrictions.eq("client", client)).uniqueResult();
    }

    @Override
    public String getIdPrefix() {
        return messages.getProperty("project_id.prefix");
    }
}
