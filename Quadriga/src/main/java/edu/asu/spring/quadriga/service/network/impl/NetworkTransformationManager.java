package edu.asu.spring.quadriga.service.network.impl;

import java.util.ArrayList;
import java.util.List;

import edu.asu.spring.quadriga.domain.network.INetwork;
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

		List<INetwork> networkList;
		try {
			networkList = networkManager.getNetworksInProject(projectId);
		} catch (QuadrigaStorageException e) {
			throw new QuadrigaStorageException("Database Error while getting networks a project with ID: " + projectId, e);
		}
		if (networkList == null) {
			return null;
		}

		List<INetworkNodeInfo> networkNodeInfoList = new ArrayList<INetworkNodeInfo>();
		for (INetwork network: networkList) {
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
}
