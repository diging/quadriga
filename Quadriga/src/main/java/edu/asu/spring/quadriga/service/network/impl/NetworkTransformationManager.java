package edu.asu.spring.quadriga.service.network.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Sets;

import edu.asu.spring.quadriga.conceptpower.IConcept;
import edu.asu.spring.quadriga.conceptpower.IConceptpowerCache;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.network.INetworkNodeInfo;
import edu.asu.spring.quadriga.domain.network.impl.CreationEvent;
import edu.asu.spring.quadriga.exceptions.QStoreStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.network.INetworkTransformationManager;
import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;
import edu.asu.spring.quadriga.service.network.domain.impl.TransformedNetwork;
import edu.asu.spring.quadriga.transform.Link;
import edu.asu.spring.quadriga.transform.Node;
import edu.asu.spring.quadriga.transform.PredicateNode;

@Service
public class NetworkTransformationManager implements INetworkTransformationManager {

    @Autowired
    private INetworkManager networkManager;

    @Autowired
    private NetworkTransformer transformer;

    @Autowired
    private IConceptpowerCache cpCache;

    /**
     * This method returns the transformed network based on networkId and
     * versionID.
     * 
     * @param networkId
     * @param versionID
     * @return ITransformedNetwork
     * @throws QuadrigaStorageException
     */

    @Override
    public ITransformedNetwork getTransformedNetwork(String networkId, String versionID)
            throws QuadrigaStorageException {

        List<INetworkNodeInfo> oldNetworkTopNodesList = networkManager.getNetworkTopNodesByVersion(networkId,
                Integer.parseInt(versionID));
        return transformer.transformNetwork(oldNetworkTopNodesList);
    }

    /**
     * This method returns the transformed network based on networkId.
     * 
     * @param networkId
     * @return ITransformedNetwork
     * @throws QuadrigaStorageException
     */
    @Override
    public ITransformedNetwork getTransformedNetwork(String networkId) throws QuadrigaStorageException {

        List<INetworkNodeInfo> networkTopNodesList = networkManager.getNetworkTopNodes(networkId);
        return transformer.transformNetwork(networkTopNodesList);
    }

    /**
     * This method returns the approved transformed network for a list of
     * networks.
     * 
     * @param networkList
     * @return ITransformedNetwork
     * @throws QuadrigaStorageException
     */
    @Override
    public ITransformedNetwork getAllTransformedNetworks(String xml)
            throws QStoreStorageException, JAXBException, QuadrigaStorageException {

        if (xml == null || xml.isEmpty()) {
            return new TransformedNetwork(new HashMap<>(), new ArrayList<>());
        }

        List<INetwork> networkList = networkManager.getApprovedNetworkList();

        List<INetworkNodeInfo> networkTopNodesList = new ArrayList<INetworkNodeInfo>();
        for (INetwork curnetwork : networkList) {
            List<INetworkNodeInfo> curTopNodesList = networkManager.getNetworkTopNodes(curnetwork.getNetworkId());
            networkTopNodesList.addAll(curTopNodesList);
        }

        Stream<String> topNodeIDStream = networkTopNodesList.stream().map(networkNodeInfo -> networkNodeInfo.getId());

        Stream<CreationEvent> creationEventStream = networkManager.getTopElementEvents(xml.trim(), topNodeIDStream)
                .stream();

        return transformer.transformNetworkUsingCreationList(creationEventStream);
    }

    @Override
    public ITransformedNetwork getTransformedApprovedNetworks(List<INetwork> networkList)
            throws QuadrigaStorageException {

        List<INetworkNodeInfo> networkTopNodesList = new ArrayList<INetworkNodeInfo>();
        for (INetwork curnetwork : networkList) {
            List<INetworkNodeInfo> curTopNodesList = networkManager.getNetworkTopNodes(curnetwork.getNetworkId());
            networkTopNodesList.addAll(curTopNodesList);
        }
        return transformer.transformNetwork(networkTopNodesList);
    }

    /**
     * This method returns the transformed network of a project based on
     * projectId and status.
     * 
     * @param projectId
     * @param status
     * @return ITransformedNetwork
     * @throws QuadrigaStorageException
     */
    @Override
    public ITransformedNetwork getTransformedNetworkOfProject(String projectId, String status)
            throws QuadrigaStorageException {

        List<INetwork> networkList = getNetworkList(projectId, status);
        if (networkList == null) {
            return null;
        }
        return getTransformedNetworkusingNetworkList(networkList);
    }

    /**
     * This method returns a network that consists of all the statements in the
     * given projects that contain the provided concept id.
     * 
     * @param projectIds
     * @param conceptId
     * @return ITransformedNetwork
     * @throws QuadrigaStorageException
     * @author suraj nilapwar
     */
    @Override
    public ITransformedNetwork getSearchTransformedNetwork(String projectId, String conceptId, String status)
            throws QuadrigaStorageException {
        // Get the transformed network and search for the concept id.
        List<INetwork> networkList = getNetworkList(projectId, status);
        if (networkList == null) {
            return null;
        }
        List<List<String>> alternativeIdsForConceptsList = new ArrayList<List<String>>();
        List<String> alternativeIdsForConcept = getAlternativeIdsForConcept(conceptId);
        alternativeIdsForConceptsList.add(alternativeIdsForConcept);
        // Get the transformed network of all the networks in a project.
        ITransformedNetwork transformedNetwork = getTransformedNetworkusingNetworkList(networkList);

        // Create final network using alternativeIdsForConcept.
        return getFinalTransformedNetwork(transformedNetwork, alternativeIdsForConceptsList);
    }

    /**
     * This method fetches ConceptPower entries related to the conceptId and
     * return alternative ids for the concept.
     * 
     * @param conceptId
     * @return List<String>
     */
    private List<String> getAlternativeIdsForConcept(String conceptId) {
        List<String> alternativeIdsForConcept = null;
        IConcept reply = cpCache.getConceptByUri(conceptId);
        if (reply != null) {
            alternativeIdsForConcept = reply.getAlternativeUris();
        }
        if (alternativeIdsForConcept == null) {
            alternativeIdsForConcept = new ArrayList<String>();
        }
        return alternativeIdsForConcept;
    }

    private List<INetwork> getNetworkList(String projectId, String status) throws QuadrigaStorageException {
        return networkManager.getNetworksInProject(projectId, status);
    }

    /**
     * This method searches for concept specified by conceptId in the networks
     * of projects specified by project Ids and returns transformed networks of
     * all projects specified by projectIds containing given conceptId.
     * 
     * @param projectIds
     *            : List of projectIds for projects to search under
     * @param conceptId
     *            : Id of concept to search
     * @return ITransformedNetwork
     * @throws QuadrigaStorageException
     * @author suraj nilapwar
     */
    @Override
    public ITransformedNetwork getSearchTransformedNetworkMultipleProjects(List<String> projectIds, List<String> conceptIds,
            String status) throws QuadrigaStorageException {
        // get the transformed network and search for the concept id
        List<INetwork> networkList = new ArrayList<>();
        
        for (String projectId : projectIds) {
            List<INetwork> networks = getNetworkList(projectId, status);
            if (networks != null) {
                networkList.addAll(networks);
            }
        }

        if (networkList.size() == 0) {
            return null;
        }

        // get the transformed network of all the networks in projects
        ITransformedNetwork transformedNetwork = getTransformedNetworkusingNetworkList(networkList);
        List<List<String>> alternativeIdsForConceptsList = new ArrayList<List<String>>();
        List<String> alternativeIdsForConcepts;
        
        for(String conceptId : conceptIds){
            alternativeIdsForConcepts = new ArrayList<String>();
            alternativeIdsForConcepts.addAll(getAlternativeIdsForConcept(conceptId));
            alternativeIdsForConceptsList.add(alternativeIdsForConcepts);
        }
        
        // create final network using alternativeIdsForConcept
        return getFinalTransformedNetwork(transformedNetwork, alternativeIdsForConceptsList);
    }

    @Override
    public ITransformedNetwork getTransformedNetworkusingNetworkList(List<INetwork> networkList)
            throws QuadrigaStorageException {

        List<INetworkNodeInfo> networkNodeInfoList = new ArrayList<INetworkNodeInfo>();
        for (INetwork network : networkList) {
            List<INetworkNodeInfo> localNetworkNodeInfoList = networkManager.getNetworkTopNodes(network.getNetworkId());
            if (localNetworkNodeInfoList != null) {
                networkNodeInfoList.addAll(localNetworkNodeInfoList);
            }
        }

        ITransformedNetwork transformedNetwork = transformer.transformNetwork(networkNodeInfoList);

        // combine all the nodes except predicate nodes

        Map<String, Node> nodes = transformedNetwork.getNodes();
        List<Link> links = transformedNetwork.getLinks();

        Map<String, Node> updatedNodes = new HashMap<String, Node>();
        for (Map.Entry<String, Node> entry : nodes.entrySet()) {
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
        for (Link link : links) {
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

    /**
     * Filter the nodes in the network using the concept id and the alternative
     * ids of the concept.
     * 
     * @param transformedNetwork
     * @param alternativeIdsForConcept
     * @return ITransformedNetwork
     */
    private ITransformedNetwork getFinalTransformedNetwork(ITransformedNetwork transformedNetwork,
            List<List<String>> alternativeIdsForConceptList) {
        // Select the nodes with the concept id that is present in the list of
        // alternative id for a concept.
        // Add all the statement id of the selected node to a set.
        // The alternative id list if not empty, is sure to contain the concept
        // id corresponding to the searched concept.
        List<Set<String>> statementIdSearchSetList = new ArrayList<Set<String>>();
        Set<String> statementIdSet = new HashSet<String>();
        List<Node> searchedNodes = new ArrayList<Node>();
        
        for(int i = 0; i < alternativeIdsForConceptList.size(); i++){
            statementIdSearchSetList.add(new HashSet<String>());
        }
        
        
        for (Node node : transformedNetwork.getNodes().values()) {
            // Check if the node's concept id is present in the alternative id
            // list of the concept.
           
            /*for (String alternativeId : alternativeIdsForConcept) {
                if (alternativeId.equals(node.getConceptId())) {
                    searchedNodes.add(node);
                    statementIdSearchSet.addAll(node.getStatementIds());
                    statementIdSearchSetList.add(statementIdSearchSet);
                    break;
                }
            }*/
            int index = 0;
            for(List<String> alternativeIdsForConcept : alternativeIdsForConceptList){
                    if(alternativeIdsForConcept.contains(node.getConceptId())){
                        searchedNodes.add(node);
                        statementIdSearchSetList.get(index).addAll(node.getStatementIds());
                        break;
                    }
                    index += 1;
            }
        }
        if(statementIdSearchSetList.size() >= 1){
            statementIdSet = statementIdSearchSetList.get(0);
            for(int i = 1; i < statementIdSearchSetList.size() ; i++){
                statementIdSet = Sets.intersection(statementIdSet, statementIdSearchSetList.get(i));
            }
        } 
        // Include only those links which have statement ids in the search set.
        List<Link> finalLinks = new ArrayList<Link>();
        // Final nodes.
        Map<String, Node> finalNodes = new HashMap<String, Node>();
        // To store already added nodes to the final nodes map this would avoid
        // duplicate nodes.
        Set<Node> addedNodes = new HashSet<Node>();
        for (Link link : transformedNetwork.getLinks()) { 
            if (statementIdSet.contains(link.getStatementId())) {
                // Statement id match, add to the final link list.
                finalLinks.add(link);

                Node subjectNode = link.getSubject();
                Node objectNode = link.getObject();
                // If node already added then do not add it.
                // If nodes are added twice - it would produce many nodes and
                // less links => disconnected graph.
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

        // Finally add the searched concept nodes if they are not present.
        for (Node node : searchedNodes) {
            if (!searchedNodes.contains(node)) {
                finalNodes.put(node.getId(), node);
            }
        }
        return new TransformedNetwork(finalNodes, finalLinks);
    }
}
