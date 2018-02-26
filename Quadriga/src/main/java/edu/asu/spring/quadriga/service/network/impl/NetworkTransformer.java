package edu.asu.spring.quadriga.service.network.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.network.INetworkNodeInfo;
import edu.asu.spring.quadriga.domain.network.impl.CreationEvent;
import edu.asu.spring.quadriga.domain.network.impl.ElementEventsType;
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
    
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public ITransformedNetwork transformNetwork(List<INetworkNodeInfo> networkNodeInfoList) {
        Map<String, Node> nodes = new HashMap<>();
        List<Link> links = new ArrayList<>();
        ITransformedNetwork transformedNetwork = new TransformedNetwork(nodes, links);

        if (networkNodeInfoList == null || networkNodeInfoList.size() == 0) {
            // return the transformed network
            return transformedNetwork;
        }

        List<ElementEventsType> elementEventsTypeList = networkDownloadService
                .getElementEventTypes(networkNodeInfoList);
        
        Map<String, List<CreationEvent>> eventsById = new HashMap<>();
        for (ElementEventsType type : elementEventsTypeList) {
            List<CreationEvent> events = type.getRelationEventOrAppellationEvent();
            for (CreationEvent event : events) {
                if (eventsById.get(event.getId()) == null) {
                    eventsById.put(event.getId(), new ArrayList<>());
                }
                eventsById.get(event.getId()).add(event);
            }
        }
        for (INetworkNodeInfo networkNodeInfo : networkNodeInfoList) {
            List<CreationEvent> events = eventsById.get(networkNodeInfo.getId());
            // Do not proceed if there are no events
            // null implies there is some exception while retrieving the dataj
            if (events == null || events.size() == 0) {
                continue;
            }
            parser.parseStatement(networkNodeInfo.getId(), events, nodes, links);
        }

        // Instead of sending null
        // send an empty transformed network
        return transformedNetwork;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITransformedNetwork transformNetworkUsingCreationList(Stream<CreationEvent> creationEventStream) {
        Map<String, Node> nodes = new HashMap<>();
        List<Link> links = new ArrayList<>();
        ITransformedNetwork transformedNetwork = new TransformedNetwork(nodes, links);
        parser.parseEvents(creationEventStream, nodes, links);
        return transformedNetwork;
    }
}
