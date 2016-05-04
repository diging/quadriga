package edu.asu.spring.quadriga.dao.impl.workbench.passthroughproject;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

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
    @SuppressWarnings("unchecked")
    @Override
    public List<PassThroughProjectDTO> getExternalProjects(String externalProjectid) throws QuadrigaStorageException {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("PassThroughProjectDTO.findByExternalProjectid");
        query.setParameter("externalProjectid", externalProjectid);

        List<PassThroughProjectDTO> projectDTOs = query.list();
        return projectDTOs;
    }

    @Override
    public String getIdPrefix() {
        return messages.getProperty("project_id.prefix");
    }
}
