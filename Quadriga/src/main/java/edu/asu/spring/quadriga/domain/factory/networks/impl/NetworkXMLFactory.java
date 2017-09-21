package edu.asu.spring.quadriga.domain.factory.networks.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factory.networks.INetworkXMLFactory;
import edu.asu.spring.quadriga.domain.network.INetworkXML;
import edu.asu.spring.quadriga.domain.network.impl.NetworkXML;

@Service
public class NetworkXMLFactory implements INetworkXMLFactory {

    @Override
    public INetworkXML createNetworkXMLObject() {
        return new NetworkXML();
    }

}
