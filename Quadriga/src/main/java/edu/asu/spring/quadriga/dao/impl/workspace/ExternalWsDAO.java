package edu.asu.spring.quadriga.dao.impl.workspace;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.workspace.IExternalWsDAO;
import edu.asu.spring.quadriga.dto.ExternalWorkspaceDTO;

@Repository
public class ExternalWsDAO extends BaseDAO<ExternalWorkspaceDTO> implements IExternalWsDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public String getInternalWorkspaceId(String externalWorkspaceid) {
        String internalProjectId = null;
        ExternalWorkspaceDTO externalWorkspace = getExternalWorkspace(externalWorkspaceid);
        if (externalWorkspace != null) {
            internalProjectId = externalWorkspace.getWorkspaceid();
        }
        return internalProjectId;

    }

    @SuppressWarnings("unchecked")
    @Override
    public ExternalWorkspaceDTO getExternalWorkspace(String externalWorkspaceid) {
        ExternalWorkspaceDTO externalWorkspace = null;
        Query query = sessionFactory.getCurrentSession()
                .getNamedQuery("ExternalWorkspaceDTO.findByExternalWorkspaceid");
        query.setParameter("externalWorkspaceid", externalWorkspaceid);
        List<ExternalWorkspaceDTO> externalWorkspaceList = query.list();
        if (externalWorkspaceList.size() > 0) {
            externalWorkspace = externalWorkspaceList.get(0);
        }
        return externalWorkspace;
    }

    @Override
    public ExternalWorkspaceDTO getDTO(String id) {
        return getDTO(ExternalWorkspaceDTO.class, id);
    }

}
