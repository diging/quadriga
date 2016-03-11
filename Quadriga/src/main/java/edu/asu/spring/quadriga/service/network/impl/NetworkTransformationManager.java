package edu.asu.spring.quadriga.service.network.impl;

import java.util.*;

import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.service.network.domain.impl.TransformedNetwork;
import edu.asu.spring.quadriga.transform.Link;
import edu.asu.spring.quadriga.transform.Node;
import edu.asu.spring.quadriga.transform.PredicateNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
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

        ITransformedNetwork transformedNetwork = transformer.transformNetwork(networkNodeInfoList);

        // combine all the nodes except predicate nodes

        Map<String, Node> nodes = transformedNetwork.getNodes();
        List<Link> links = transformedNetwork.getLinks();

        Map<String, Node> updatedNodes = new HashMap<String, Node>();
        int index = 0;
        for (Node node: nodes.values()) {
            // if the node is predicate node
            // add to the updated nodes map
            if (node instanceof PredicateNode) {
                // the key does not matter
                // as it is never being used
                updatedNodes.put(String.valueOf(++index), node);
                continue;
            }

            // node is subject or object
            // If the concept id is already present in the
            // updated nodes map - dont add it
            if (!updatedNodes.containsKey(node.getConceptId())) {
                updatedNodes.put(node.getConceptId(), node);
            }
        }

        // update the links to point to the new updatedNodes
        for (Link link: links) {
            Node subjectNode = link.getSubject();
            if (!(subjectNode instanceof PredicateNode)) {
                link.setSubject(updatedNodes.get(subjectNode.getConceptId()));
            }

            Node objectNode = link.getObject();
            if (!(objectNode instanceof PredicateNode)) {
                link.setObject(updatedNodes.get(objectNode.getConceptId()));
            }
        }

        // return new network with updated nodes and links
        return new TransformedNetwork(updatedNodes, links);
    }

    @Override
    public ITransformedNetwork getSearchTransformedNetwork(String projectId, String conceptId)
        throws QuadrigaStorageException {
        // get the transformed network and search for the concept id
        List<INetwork> networkList = getNetworkList(projectId);

        if (networkList == null) {
            return null;
        }

        ITransformedNetwork transformedNetwork = getTransformedNetworkOfProject(projectId);
        // separate each statement id and include all the list of nodes in the statement id
        Map<String, Set<Node>> statementIdMap = getStatementIdNodeMap(transformedNetwork.getNodes());

        // Filter the nodes with the concept id
        List<Node> searchNodes = new ArrayList<Node>();
        for (Node node: transformedNetwork.getNodes().values()) {
            if (conceptId.equals(node.getConceptId())) {
                searchNodes.add(node);
            }
        }

        // include only those statements which have the search node
        Iterator<Map.Entry<String, Set<Node>>> it = statementIdMap.entrySet().iterator();
        Set<Node> searchedNodeSet = new HashSet<Node>();
        while (it.hasNext()) {
            boolean isNodeExists = false;
            Set<Node> nodeSet = it.next().getValue();
            // search if the node exists
            for (Node node: searchNodes) {
                if (nodeSet.contains(node)) {
                    // include this statement
                    isNodeExists = true;
                }
            }
            // remove the statement if it is not present
            if (!isNodeExists) {
                it.remove();
            } else {
                searchedNodeSet.addAll(nodeSet);
            }
        }

        // now include the statements
        Map<String, Node> finalNodes = new HashMap<String, Node>();
        int index = 0;
        for (Node n: searchedNodeSet) {
            finalNodes.put(String.valueOf(++index), n);
        }
        // final links
        List<Link> finalLinks = transformedNetwork.getLinks();
        Iterator<Link> linkIterator = finalLinks.iterator();
        while (linkIterator.hasNext()) {
            Link link = linkIterator.next();
            // if the subject or object does not exist in any links
            // remove that link
            if (!searchedNodeSet.contains(link.getSubject()) ||
                    !searchedNodeSet.contains(link.getObject())) {
                linkIterator.remove();
            }
        }

        return new TransformedNetwork(finalNodes, finalLinks);
    }

    private List<INetwork> getNetworkList(String projectId) throws QuadrigaStorageException {
        List<INetwork> networkList;
        try {
            networkList = networkManager.getNetworksInProject(projectId);
        } catch (QuadrigaStorageException e) {
            throw new QuadrigaStorageException("Database error while getting networks of a project" +
                    " with id: " + projectId, e);
        }

        return networkList;
    }

    private Map<String, Set<Node>> getStatementIdNodeMap(Map<String, Node> nodes) {
        Map<String, Set<Node>> statementIdMap = new HashMap<String, Set<Node>>();
        for (Node node: nodes.values()) {
            for (String statementId: node.getStatementIds()) {
                if (!statementIdMap.containsKey(statementId)) {
                    statementIdMap.put(statementId, new HashSet<Node>());
                }
                statementIdMap.get(statementId).add(node);
            }
        }
        return statementIdMap;
    }
}
