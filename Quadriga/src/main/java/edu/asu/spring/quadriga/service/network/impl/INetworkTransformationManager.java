package edu.asu.spring.quadriga.service.network.impl;

import java.util.List;

import edu.asu.spring.quadriga.domain.network.INetworkNodeInfo;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;

public interface INetworkTransformationManager {

    public abstract ITransformedNetwork getTransformedNetwork(String networkId, String jqueryType)
            throws QuadrigaStorageException;

    public abstract ITransformedNetwork getTransformedNetwork(String networkId, String jqueryType,
            String versionID) throws QuadrigaStorageException;

}