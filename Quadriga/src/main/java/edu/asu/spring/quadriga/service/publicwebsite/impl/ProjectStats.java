package edu.asu.spring.quadriga.service.publicwebsite.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.workspace.IListWsDAO;
import edu.asu.spring.quadriga.domain.IConceptStats;
import edu.asu.spring.quadriga.domain.IUserStats;
import edu.asu.spring.quadriga.domain.impl.ConceptStats;
import edu.asu.spring.quadriga.domain.impl.UserStats;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workbench.IProjectCollaborator;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.network.INetworkTransformationManager;
import edu.asu.spring.quadriga.service.publicwebsite.IProjectStats;
import edu.asu.spring.quadriga.service.workbench.IProjectCollaboratorManager;
import edu.asu.spring.quadriga.transform.Node;

/**
 * This class represents business logic for getting top concepts from the given
 * list of networks.
 *
 * @author Ajay Modi
 *
 */

@Service
public class ProjectStats implements IProjectStats {

    @Autowired
    private INetworkTransformationManager transformationManager;

    @Autowired
    private IListWsDAO listWSDAO;

    @Autowired
    private INetworkManager networkManager;

    @Autowired
    private IProjectCollaboratorManager projectManager;

    @Override
    public List<IConceptStats> getConceptCount(List<INetwork> networks)
            throws QuadrigaStorageException {
        HashMap<String, ConceptStats> mapStats = new HashMap<String, ConceptStats>();

        for (INetwork n : networks) {
            ITransformedNetwork transformedNetwork = transformationManager
                    .getTransformedNetwork(n.getNetworkId());
            if (transformedNetwork != null) {
                Map<String, Node> mapNodes = transformedNetwork.getNodes();
                for (Node node : mapNodes.values()) {
                    String url = node.getConceptId();
                    if (mapStats.containsKey(url)) {
                        mapStats.get(url).incrementCount();
                    } else {
                        ConceptStats conceptStats = new ConceptStats(url,
                                node.getDescription(), node.getLabel(), 1);
                        mapStats.put(url, conceptStats);
                    }
                }
            }
        }

        List<IConceptStats> valuesList = new ArrayList<IConceptStats>(
                mapStats.values());

        return getSortedList(valuesList);

    }

    public List<IConceptStats> getSortedList(List<IConceptStats> csList) {
        Collections.sort(csList, new Comparator<IConceptStats>() {
            public int compare(IConceptStats o1, IConceptStats o2) {
                if (o1.getCount() == o2.getCount())
                    return 0;
                return o1.getCount() < o2.getCount() ? 1 : -1;
            }
        });
        return csList;
    }

    @Override
    public Map<String, IUserStats> getCountofWorkspace(String projectId)
            throws QuadrigaStorageException {

        List<IProjectCollaborator> projectCollaboratorList = projectManager
                .getProjectCollaborators(projectId);

        if (projectCollaboratorList == null)
            return null;

        Map<String, IUserStats> mapUserWorkspace = new HashMap<String, IUserStats>();
        // loop through each collaborator
        for (IProjectCollaborator projectCollaborator : projectCollaboratorList) {
            if (projectCollaborator.getCollaborator() != null) {
                String username = projectCollaborator.getCollaborator()
                        .getUserObj().getUserName();
                List<IWorkSpace> workspaceList = listWSDAO.listWorkspace(
                        projectId, username);
                UserStats userStats;
                if (workspaceList == null) {
                    userStats = new UserStats(username, 0, 0);
                } else {
                    userStats = new UserStats(username, workspaceList.size(), 0);
                }
                mapUserWorkspace.put(username, userStats);
            }
        }

        return mapUserWorkspace;
    }

    @Override
    public Map<String, IUserStats> getCountofNetwork(String projectid,
            Map<String, IUserStats> mapUserNetworks)
            throws QuadrigaStorageException {
        List<INetwork> networksList = networkManager
                .getNetworksInProject(projectid);
        for (INetwork network : networksList) {
            if (network.getCreator() != null) {
                String username = network.getCreator().getUserName();
                IUserStats userStats = mapUserNetworks.get(username);
                if (userStats == null) {
                    new UserStats(username, 0, 1);
                } else {
                    userStats.incrementNetworkCount();
                }
            }
        }
        return mapUserNetworks;
    }
}
