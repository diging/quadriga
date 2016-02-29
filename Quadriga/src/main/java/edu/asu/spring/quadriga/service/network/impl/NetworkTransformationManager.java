package edu.asu.spring.quadriga.service.network.impl;

import java.util.*;

import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.service.network.domain.impl.TransformedNetwork;
import edu.asu.spring.quadriga.transform.Link;
import edu.asu.spring.quadriga.transform.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.network.INetworkNodeInfo;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;

@Service
public class NetworkTransformationManager implements INetworkTransformationManager {

    private static final Logger logger = LoggerFactory.getLogger(NetworkTransformationManager.class);

    @Autowired
    private INetworkManager networkManager;

    @Autowired
    private NetworkTransformer transformer;

    @Override
    public ITransformedNetwork getTransformedNetwork(String networkId, String versionID)
            throws QuadrigaStorageException {

        ITransformedNetwork networkJSon = null;
        List<INetworkNodeInfo> oldNetworkTopNodesList = null;

        try {
            oldNetworkTopNodesList = networkManager.getNetworkTopNodesByVersion(networkId, Integer.parseInt(versionID));
        } catch (QuadrigaStorageException e) {
            logger.error("DB Error while getting network top nodes", e);
        }

        networkJSon = transformer.transformNetwork(oldNetworkTopNodesList);
        return networkJSon;
    }

    @Override
    public ITransformedNetwork getTransformedNetwork(String networkId) throws QuadrigaStorageException {

        ITransformedNetwork networkJSon = null;

        List<INetworkNodeInfo> networkTopNodesList = null;

        try {
            networkTopNodesList = networkManager.getNetworkTopNodes(networkId);
        } catch (QuadrigaStorageException e) {
            logger.error("DB Error while getting network top nodes", e);
            return null;
        }

        networkJSon = transformer.transformNetwork(networkTopNodesList);

        return networkJSon;
    }

    @Override
    public ITransformedNetwork getTransformedNetworkOfProject(String projectId)
            throws QuadrigaStorageException {

        List<INetwork> networkList = getNetworkList(projectId);
        if (networkList == null) {
            return null;
        }

        List<INetworkNodeInfo> networkNodeInfoList = new ArrayList<INetworkNodeInfo>();
        for (INetwork network : networkList) {
            try {
                List<INetworkNodeInfo> localNetworkNodeInfoList =
                        networkManager.getNetworkTopNodes(network.getNetworkId());
                if (localNetworkNodeInfoList != null) {
                    networkNodeInfoList.addAll(localNetworkNodeInfoList);
                }
            } catch (QuadrigaStorageException e) {
                throw new QuadrigaStorageException("Database Error while getting network top nodes for a network with ID: " +
                        network.getNetworkId(), e);
            }
        }

        ITransformedNetwork networkJSon = transformer.transformNetwork(networkNodeInfoList);
        return networkJSon;
    }

    @Override
    public ITransformedNetwork searchTransformedNetworkOfProject(String projectId, String conceptId)
            throws QuadrigaStorageException {
        List<INetwork> networkList = getNetworkList(projectId);
        if (networkList == null) {
            return null;
        }

        Map<String, Node> searchNodes = new HashMap<String, Node>();
        List<Link> searchLinks = new ArrayList<Link>();
        for (INetwork network : networkList) {
            try {
                List<INetworkNodeInfo> localNetworkNodeInfoList =
                        networkManager.getNetworkTopNodes(network.getNetworkId());
                // check if this network has the node with given concept id
                if (localNetworkNodeInfoList != null) {
                    ITransformedNetwork transformedNetwork =
                            transformer.transformNetwork(localNetworkNodeInfoList);
                    // search for concept id
                    Node searchNode = null;
                    for (Node node: transformedNetwork.getNodes().values()) {
                        if (node.getConceptId().contains(conceptId)) {
                            searchNode = node;
                            break;
                        }
                    }
                    // if node does not exist continue
                    if (searchNode == null) {
                        continue;
                    }

                    String statementId = searchNode.getStatementIds().get(0);
                    Set<Node> associatedNodes = new HashSet<Node>();
                    for (Map.Entry<String, Node> entry: transformedNetwork.getNodes().entrySet()) {
                        // check if first statementId is in all the nodes
                        Node n = entry.getValue();
                        if (n.getStatementIds().contains(statementId)) {
                            searchNodes.put(entry.getKey(), n);
                            associatedNodes.add(n);
                        }
                    }

                    // find all the links associated with the searched nodes
                    for (Link link: transformedNetwork.getLinks()) {
                        if (associatedNodes.contains(link.getSubject())) {
                            searchLinks.add(link);
                        }
                    }
                }
            } catch (QuadrigaStorageException e) {
                throw new QuadrigaStorageException("Database Error while getting network top nodes for a network with ID: " +
                        network.getNetworkId(), e);
            }
        }

        // transformed network
        return new TransformedNetwork(searchNodes, searchLinks);
    }

    private List<INetwork> getNetworkList(String projectId) throws QuadrigaStorageException {
        List<INetwork> networkList;
        try {
            networkList = networkManager.getNetworksInProject(projectId);
        } catch (QuadrigaStorageException e) {
            throw new QuadrigaStorageException("Database Error while getting networks of a project with ID: "
                    + projectId, e);
        }

        return networkList;
    }
}
