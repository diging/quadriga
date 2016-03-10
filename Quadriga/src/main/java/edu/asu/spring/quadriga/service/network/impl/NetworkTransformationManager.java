package edu.asu.spring.quadriga.service.network.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
