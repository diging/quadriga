package edu.asu.spring.quadriga.service.network.impl;

import java.util.*;
import java.util.concurrent.*;

import javax.xml.bind.JAXBException;

import edu.asu.spring.quadriga.domain.impl.networks.ElementEventsType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.network.INetworkNodeInfo;
import edu.asu.spring.quadriga.exceptions.QStoreStorageException;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;
import edu.asu.spring.quadriga.service.network.INetworkManager;
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
    private INetworkManager networkManager;

    @Autowired
    private IConceptCollectionManager conceptCollectionManager;

    @Autowired
    private EventParser parser;

    @Autowired
    private ElementEventTypeDownloadService elementEventTypeDownloadService;

    private static final Logger logger = LoggerFactory
            .getLogger(NetworkTransformer.class);

    private static int NUM_THREADS = 4;

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public ITransformedNetwork transformNetwork(
            List<INetworkNodeInfo> networkTopNodesList) {
        Map<String, Node> nodes = new HashMap<>();
        List<Link> links = new ArrayList<>();
        ITransformedNetwork transformedNetwork = new TransformedNetwork(nodes, links);

        if (networkTopNodesList == null || networkTopNodesList.size() == 0) {
            // return the trasnformed network
            return transformedNetwork;
        }

        // networkTopNode list has size > 0
        // Create executors and tasks
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        // Completion service
        CompletionService<ElementEventsType> completionService = new
                ExecutorCompletionService<>(executorService);

        // submit tasks
        networkTopNodesList.forEach((networkNodeInfo) -> {
            Callable<ElementEventsType> callable = elementEventTypeDownloadService
                    .getElementEventTypeDownloadTask(networkNodeInfo.getId());
            completionService.submit(callable);
        });

        // check for callabale executions
        networkTopNodesList.forEach((networkNodeInfo -> {
            try {
                Future<ElementEventsType> future = completionService.take();
                // take() will block if none are executed
                ElementEventsType elementEventsType = future.get();
                parser.parseStatement(networkNodeInfo.getId(), elementEventsType, nodes, links);
            } catch (InterruptedException | ExecutionException e) {
                logger.error("Issue while retrieving and marshalling object", e);
            }
        }));

        // Instead of sending null
        // send an empty transformed network
        return transformedNetwork;
    }
}
