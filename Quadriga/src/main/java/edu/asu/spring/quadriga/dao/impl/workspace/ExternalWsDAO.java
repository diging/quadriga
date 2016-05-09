package edu.asu.spring.quadriga.dao.impl.workspace;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.workspace.IExternalWorkspaceDAO;
import edu.asu.spring.quadriga.dto.ExternalWorkspaceDTO;

/**
 * This class is responsible for Querying the MySQL database and fetch the class
 * objects related to External Workspace.
 *
 */
@Repository
public class ExternalWsDAO extends BaseDAO<ExternalWorkspaceDTO> implements IExternalWorkspaceDAO {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * {@inheritDoc}
     */
    @Override
    public ExternalWorkspaceDTO getExternalWorkspace(String externalWorkspaceid) {
        ExternalWorkspaceDTO externalWorkspace = null;
        Query query = sessionFactory.getCurrentSession()
                .getNamedQuery("ExternalWorkspaceDTO.findByExternalWorkspaceid");
        query.setParameter("externalWorkspaceid", externalWorkspaceid);
        externalWorkspace = (ExternalWorkspaceDTO) query.uniqueResult();
        return externalWorkspace;
    }

    @Override
    public ExternalWorkspaceDTO getDTO(String id) {
        return getDTO(ExternalWorkspaceDTO.class, id);
    }

}
