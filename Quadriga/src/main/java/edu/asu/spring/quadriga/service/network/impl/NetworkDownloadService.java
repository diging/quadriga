package edu.asu.spring.quadriga.service.network.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.network.INetworkNodeInfo;
import edu.asu.spring.quadriga.domain.network.impl.ElementEventsType;
import edu.asu.spring.quadriga.exceptions.QuadrigaException;

/**
 * Created by Vikranth on 4/15/2016.
 */
@Service
public class NetworkDownloadService {

    @Autowired
    private ElementEventTypeDownloadService elementEventTypeDownloadService;

    private final Logger logger = LoggerFactory.getLogger(NetworkDownloadService.class);

    /**
     * Return ElementEventsType asynchronously
     * 
     * @param networkNodeInfoList
     *            List of networks
     * @return elementEventsTypeList
     */
    public List<ElementEventsType> getElementEventTypes(final List<INetworkNodeInfo> networkNodeInfoList) {
        List<Future<ElementEventsType>> futureEvents = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        networkNodeInfoList.forEach(networkNodeInfo -> {

            // adding future tasks to a list
            // this is asynchronous
            // Future<ElementEventsType> future =
            // elementEventTypeDownloadService
            // .getElementEventTypeAsync(networkNodeInfo.getId());
            // futureEvents.add(future);
            ids.add(networkNodeInfo.getId());
        });
        try {
            futureEvents.add(elementEventTypeDownloadService.getElementEventTypeAsync(ids));
        } catch (QuadrigaException e1) {
            logger.error("An exception occured: " + e1);
        }

        // Get the data from future tasks
        List<ElementEventsType> elementEventsTypeList = new ArrayList<>();
        for (Future<ElementEventsType> future : futureEvents) {
            ElementEventsType elementEventsType = null;
            try {
                elementEventsType = future.get();
                logger.debug("Done with retrieving networks.");
            } catch (InterruptedException | ExecutionException e) {
                logger.error(
                        "Unable to execute a qstore asynchronosly for relation event id: " + ids,
                        e);
            } finally {
                if(elementEventsType != null){
                    elementEventsTypeList.add(elementEventsType);
                }
            }
        }
        return elementEventsTypeList;
    }
}
