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
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.network.INetworkNodeInfo;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.network.INetworkTransformationManager;
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
        
        ITransformedNetwork transformedNetwork = getTransformedNetworkusingNetworkList(networkList);
        
        return transformedNetwork;
    }


    /**
	 * This method returns the transformation network in provided project for given conceptId.
	 * @param projectIds
	 * @param conceptId
	 * @return ITransformedNetwork
	 * @throws QuadrigaStorageException
	 * @author suraj nilapwar
	 */
    @Override
    public ITransformedNetwork getSearchTransformedNetwork(String projectId, String conceptId)
        throws QuadrigaStorageException {
        // get the transformed network and search for the concept id
        List<INetwork> networkList = getNetworkList(projectId);

        if (networkList == null) {
            return null;
        }

        // get the transformed network of all the networks in a project
        ITransformedNetwork transformedNetwork = getTransformedNetworkusingNetworkList(networkList);
        // create finalnetwork using conceptId 
        ITransformedNetwork finalTransformedNetwork = getFinalTransformedNetwork(transformedNetwork,conceptId);

        return finalTransformedNetwork;
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
    
    /**
	 * This method returns the transformation network for searched concept under provided project list
	 * @param projectIds : List of projectIds for projects to search under
	 * @param conceptId : Id of concept to search
	 * @return ITransformedNetwork
	 * @throws QuadrigaStorageException
	 * @author suraj nilapwar
	 */
    @Override
    public ITransformedNetwork getSearchTransformedNetworkMultipleProjects(List<String> projectIds, String conceptId)
        throws QuadrigaStorageException {
    	// get the transformed network and search for the concept id
        List<INetwork> networkList = new ArrayList<>();
        
        for(String projectId : projectIds){
        	List<INetwork> networks = getNetworkList(projectId);
        	if(networks != null){
        		networkList.addAll(networks);
        	}
        }
        
        if (networkList.size()==0) {
            return null;
        }

        // get the transformed network of all the networks in projects
        ITransformedNetwork transformedNetwork = getTransformedNetworkusingNetworkList(networkList);
        // create finalnetwork using conceptId 
        ITransformedNetwork finalTransformedNetwork = getFinalTransformedNetwork(transformedNetwork,conceptId);
        
        return finalTransformedNetwork;  
     }

    @Override
    public ITransformedNetwork getTransformedNetworkusingNetworkList(List<INetwork> networkList)
            throws QuadrigaStorageException {

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
        for (Map.Entry<String, Node> entry: nodes.entrySet()) {
            Node node = entry.getValue();
            // if the node is predicate node
            // add to the updated nodes map
            if (node instanceof PredicateNode) {
                // used the original key to store the
                // predicate node
                updatedNodes.put(entry.getKey(), node);
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
    
    private ITransformedNetwork getFinalTransformedNetwork(ITransformedNetwork transformedNetwork, String conceptId)
    {
        // Filter the nodes with the concept id
        // add all the statement ids to a set
        Set<String> statementIdSearchSet = new HashSet<String>();
        List<Node> searchedNodes = new ArrayList<Node>();
        for (Node node: transformedNetwork.getNodes().values()) {
            if (conceptId.equals(node.getConceptId())) {
                searchedNodes.add(node);
                statementIdSearchSet.addAll(node.getStatementIds());
            }
        }

        // include only those links which have statement ids in the search set
        List<Link> finalLinks = new ArrayList<Link>();
        // final nodes
        Map<String, Node> finalNodes = new HashMap<String, Node>();
        // To store already added nodes to the final nodes map
        // this would avoid duplicate nodes
        Set<Node> addedNodes = new HashSet<Node>();
        for (Link link: transformedNetwork.getLinks()) {
            if (statementIdSearchSet.contains(link.getStatementId())) {
                // statement id match
                // add to the final link list
                finalLinks.add(link);

                Node subjectNode = link.getSubject();
                Node objectNode = link.getObject();
                // if node already added then do not add it
                // if nodes are added twice - it would produce many
                // nodes and less links => disconnected graph
                if (!addedNodes.contains(subjectNode)) {
                    finalNodes.put(subjectNode.getId(), subjectNode);
                    addedNodes.add(subjectNode);
                }

                if (!addedNodes.contains(objectNode)) {
                    finalNodes.put(objectNode.getId(), objectNode);
                    addedNodes.add(objectNode);
                }
            }
        }

        // finally add the searched concept nodes if they are not present
        for (Node node: searchedNodes) {
            if (!searchedNodes.contains(node)) {
                finalNodes.put(node.getId(), node);
            }
        }

        return new TransformedNetwork(finalNodes, finalLinks);
    }
}
