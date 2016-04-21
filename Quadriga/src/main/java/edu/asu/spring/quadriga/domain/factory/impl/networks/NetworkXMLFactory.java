package edu.asu.spring.quadriga.domain.factory.impl.networks;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factory.networks.INetworkXMLFactory;
import edu.asu.spring.quadriga.domain.impl.networks.NetworkXML;
import edu.asu.spring.quadriga.domain.network.INetworkXML;

@Service
public class NetworkXMLFactory implements INetworkXMLFactory {

    @Override
    public INetworkXML createNetworkXMLObject() {
        return new NetworkXML();
    }

}
