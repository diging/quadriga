package edu.asu.spring.quadriga.service.network.impl;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;

/**
 * Manager class that handles network transformation. It gets networks from QStore
 * and then transforms it into Nodes and Links.
 *  
 * @author Julia Damerow
 *
 */
public interface INetworkTransformationManager {

    public abstract ITransformedNetwork getTransformedNetwork(String networkId)
            throws QuadrigaStorageException;

    public abstract ITransformedNetwork getTransformedNetwork(String networkId,
            String versionID) throws QuadrigaStorageException;

}