package edu.asu.spring.quadriga.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.IProjectStatsDAO;
import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.workspace.IListWsDAO;
import edu.asu.spring.quadriga.domain.dspace.IBitStream;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.dto.ConceptCollectionDTO;
import edu.asu.spring.quadriga.dto.NetworksDTO;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.dto.WorkspaceConceptcollectionDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.NetworkDTOMapper;
import edu.asu.spring.quadriga.mapper.WorkspaceDTOMapper;
import edu.asu.spring.quadriga.mapper.WorkspaceDspaceDTOMapper;
import edu.asu.spring.quadriga.web.network.INetworkStatus;

@Repository
public class ProjectStatsDAO implements IProjectStatsDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public int getCountofWorkspace(String projectid, String username)
            throws QuadrigaStorageException {
        Integer wsCount = 0;
        try {
            Query query = sessionFactory
                    .getCurrentSession()
                    .createQuery(
                            "Select count(*) from ProjectWorkspaceDTO projWork where projWork.projectDTO.projectid =:projectid and projWork.workspaceDTO.workspaceowner.username =:username");
            query.setParameter("username", username);
            query.setParameter("projectid", projectid);
            wsCount = (Integer) query.uniqueResult();
        } catch (HibernateException e) {
            logger.error("Get Count Workspace method :", e);
            throw new QuadrigaStorageException();
        }
        return wsCount;

    }

    @Override
    public int getCountofNetwork(String workspaceid, String username)
            throws QuadrigaStorageException {
        try {
            Query query = sessionFactory.getCurrentSession().getNamedQuery(
                    "NetworksDTO.findByNetworkowner");
            query.setParameter("networkowner", username);

            @SuppressWarnings("unchecked")
            List<NetworksDTO> listNetworksDTO = query.list();
            return listNetworksDTO.size();

        } catch (HibernateException e) {
            logger.error("Get Count Network method :", e);
            throw new QuadrigaStorageException();
        }

    }

}
