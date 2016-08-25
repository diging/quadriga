package edu.asu.spring.quadriga.service.network.impl;

import java.util.concurrent.Future;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.impl.networks.ElementEventsType;
import edu.asu.spring.quadriga.exceptions.QStoreStorageException;
import edu.asu.spring.quadriga.qstore.IMarshallingService;
import edu.asu.spring.quadriga.qstore.IQStoreConnector;

/**
 * Created by Vikranth on 4/12/2016.
 *
 * This class will create a Task to download creation event xml
 * from qstore asynchronously
 */
@Service
public class ElementEventTypeDownloadService {
    
    Logger logger = LoggerFactory.getLogger(ElementEventTypeDownloadService.class);

    @Autowired
    private IMarshallingService marshallingService;

    @Autowired
    private IQStoreConnector qstoreConnector;

    /**
     * Asynchronously fetch the data from qstore and marshall the xml
     * @param relationEventId Relation event id
     * @return future
     * @throws QStoreStorageException
     * @throws JAXBException
     */
    @Async
    public Future<ElementEventsType> getElementEventTypeAsync(String relationEventId) {
        String xml;
        try {
            xml = qstoreConnector.getCreationEvent(relationEventId);
        } catch (QStoreStorageException e) {
            logger.error("Error retrieving element " + relationEventId + " from QStore.", e);
            return new AsyncResult<ElementEventsType>(null);
        }

        if (xml == null) {
           logger.error("Unable to download data from QStore for" +
                    " relation event id: " + relationEventId);
           return new AsyncResult<ElementEventsType>(null);
           
        }
        // convert the xml to ElementEventsType object
        ElementEventsType elementEventsType = null;
        try {
            elementEventsType = marshallingService.unMarshalXmlToElementEventsType(xml);
        } catch (JAXBException e) {
            logger.error("Could not unmarshal XML.", e);
        }
        // asyn task ends here
        return new AsyncResult<>(elementEventsType);
    }
}
