package edu.asu.spring.quadriga.service.network.impl;

import java.util.List;

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
    public ITransformedNetwork getTransformedNetwork(String networkId, String jqueryType, String versionID)
            throws QuadrigaStorageException {

        ITransformedNetwork networkJSon = null;

        List<INetworkNodeInfo> oldNetworkTopNodesList = null;

        try {
            oldNetworkTopNodesList = networkManager.getNetworkTopNodesByVersion(networkId, Integer.parseInt(versionID));
        } catch (QuadrigaStorageException e) {
            logger.error("DB Error while getting network top nodes", e);
        }

        if (jqueryType.equals(INetworkManager.D3JQUERY)) {
            networkJSon = transformer.transformNetwork(oldNetworkTopNodesList);

        } 

        return networkJSon;
    }
    
    @Override
    public ITransformedNetwork getTransformedNetwork(String networkId, String jqueryType) throws QuadrigaStorageException {

        ITransformedNetwork networkJSon = null;

        List<INetworkNodeInfo> networkTopNodesList = null;

        try {
            networkTopNodesList = networkManager.getNetworkTopNodes(networkId);
        } catch (QuadrigaStorageException e) {
            logger.error("DB Error while getting network top nodes", e);
            return null;
        }

        if (jqueryType.equals(INetworkManager.D3JQUERY)) {
            networkJSon = transformer.transformNetwork(networkTopNodesList);
        } 

        return networkJSon;
    }
}
