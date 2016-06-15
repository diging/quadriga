package edu.asu.spring.quadriga.service.network.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.impl.networks.ElementEventsType;
import edu.asu.spring.quadriga.domain.network.INetworkNodeInfo;
import edu.asu.spring.quadriga.exceptions.QStoreStorageException;

/**
 * Created by Vikranth on 4/15/2016.
 */
@Service
public class NetworkDownloadService {

    @Autowired
    private ElementEventTypeDownloadService elementEventTypeDownloadService;

    private static final Logger logger = LoggerFactory
            .getLogger(NetworkDownloadService.class);

    /**
     * Return ElementEventsType asynchronously
     * @param networkNodeInfoList List of networks
     * @return elementEventsTypeList
     */
    public List<ElementEventsType> getElementEventTypes(
            final List<INetworkNodeInfo> networkNodeInfoList) {
        List<Future<ElementEventsType>> futureEvents = new ArrayList<>();
        networkNodeInfoList.forEach(networkNodeInfo -> {
            try {
                // adding future tasks to a list
                // this is asynchronous
                Future<ElementEventsType> future = elementEventTypeDownloadService
                        .getElementEventTypeAsync(networkNodeInfo.getId());
                futureEvents.add(future);
            } catch (QStoreStorageException | JAXBException e) {
                logger.error("Error while fetching data for relation event id: " + networkNodeInfo.getId(),
                        e);
                // all the future lists have not been added
                // add an empty future list so that the nodes size
                // is equal to the future list size
                futureEvents.add(new AsyncResult<>(null));
            }
        });

        // Get the data from future tasks
        List<ElementEventsType> elementEventsTypeList = new ArrayList<>();
        int index = 0;
        for (INetworkNodeInfo networkNodeInfo: networkNodeInfoList) {
            Future<ElementEventsType> future = futureEvents.get(index++);
            ElementEventsType elementEventsType = null;
            try {
                elementEventsType = future.get();
            } catch (InterruptedException | ExecutionException e) {
                logger.error("Unable to execute a qstore asynchronosly for relation event id: " +
                        networkNodeInfo.getId(), e);
            } finally {
                // add element event type in the list even if it is null
                elementEventsTypeList.add(elementEventsType);
            }
        }

        return elementEventsTypeList;
    }
}
