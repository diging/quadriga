package edu.asu.spring.quadriga.service.network.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.impl.networks.ElementEventsType;
import edu.asu.spring.quadriga.domain.network.INetworkNodeInfo;
import edu.asu.spring.quadriga.service.network.INetworkTransformer;
import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;
import edu.asu.spring.quadriga.service.network.domain.impl.TransformedNetwork;
import edu.asu.spring.quadriga.transform.Link;
import edu.asu.spring.quadriga.transform.Node;

/**
 * Class responsible for transforming networks retrieved from QStore into that
 * have Appellation and Relation Events into S-P-O networks that are collapsed
 * for S and O nodes, but have unique P nodes.
 * 
 * @author jdamerow
 *
 */
@Service
public class NetworkTransformer implements INetworkTransformer {

    @Autowired
    private EventParser parser;

    @Autowired
    private NetworkDownloadService networkDownloadService;

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public ITransformedNetwork transformNetwork(
            List<INetworkNodeInfo> networkNodeInfoList) {
        Map<String, Node> nodes = new HashMap<>();
        List<Link> links = new ArrayList<>();
        ITransformedNetwork transformedNetwork = new TransformedNetwork(nodes, links);

        if (networkNodeInfoList == null || networkNodeInfoList.size() == 0) {
            // return the trasnformed network
            return transformedNetwork;
        }

        List<ElementEventsType> elementEventsTypeList = networkDownloadService
                .getElementEventTypes(networkNodeInfoList);

        // loop through all the elementEventsTypeList and parse the staement
        // We made sure that networkNodeInfoList and elementEventsTypeList
        // have same size.

        int index = 0;
        for (INetworkNodeInfo networkNodeInfo: networkNodeInfoList) {
            ElementEventsType elementEventsType = elementEventsTypeList.get(index++);
            // Do not proceed if the elementEventsType is null
            // null implies there is some exception while retrieving the dataj
            if (elementEventsType == null) {
                continue;
            }
            parser.parseStatement(networkNodeInfo.getId(), elementEventsType, nodes, links);
        }

        // Instead of sending null
        // send an empty transformed network
        return transformedNetwork;
    }
}
