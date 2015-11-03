package edu.asu.spring.quadriga.dao.impl.workspace;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.workspace.IListExternalWsDAO;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.dto.ExternalWorkspaceDTO;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

@Repository
public class ListExternalWsDAO implements IListExternalWsDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger logger = LoggerFactory.getLogger(ListExternalWsDAO.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isExternalWorkspaceExists(String workspaceId) throws QuadrigaStorageException {
        List externalWorkspaceid = null;
        try {
            Query query = sessionFactory.getCurrentSession().getNamedQuery("ExternalWorkspaceDTO.findByWorkspaceid");
            query.setParameter("externalWorkspaceid", externalWorkspaceid);
            externalWorkspaceid = query.list();
        } catch (HibernateException e) {
            logger.error("Retrieve external workspace details method :", e);
            throw new QuadrigaStorageException(e);
        }
        if (externalWorkspaceid != null) {
            return true;
        }

        return false;
    }


    @Override
    public String getInternalWorkspaceId(String externalWorkspaceid) {
        // TODO Auto-generated method stub

        Query query = sessionFactory.getCurrentSession().getNamedQuery("getWorkspaceIdFromExternalWorkspaceId");
        query.setParameter("externalWorkspaceid", externalWorkspaceid);
        List internalProjectId = query.list();
        return String.valueOf(internalProjectId.get(0));

    }

    @Override
    public void createExternalWorkspace(String externalId, String externalWorkspaceName, String workspaceId,
            String projectId, IUser user) {
        ExternalWorkspaceDTO externalWorkspaceDTO = new ExternalWorkspaceDTO();
        externalWorkspaceDTO.setExternalWorkspaceid(externalId);
        externalWorkspaceDTO.setWorkspaceid(workspaceId);
        externalWorkspaceDTO.setWorkspacename(externalWorkspaceName);
        //Saving projectDetails of the externalWorkspace
        ProjectWorkspaceDTO projectDTO = new ProjectWorkspaceDTO(projectId,workspaceId,user.getName(),new Date(),user.getName(),new Date());
        externalWorkspaceDTO.setProjectWorkspaceDTO(projectDTO);
        sessionFactory.getCurrentSession().save(externalWorkspaceDTO);
        
    }

}
