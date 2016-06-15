package edu.asu.spring.quadriga.service.network.impl;

import java.util.concurrent.Future;

import javax.xml.bind.JAXBException;

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
    public Future<ElementEventsType> getElementEventTypeAsync(String relationEventId)
            throws QStoreStorageException, JAXBException {
        String xml = qstoreConnector.getCreationEvent(relationEventId);

        if (xml == null) {
            throw new QStoreStorageException("Unable to download data from QStore for" +
                    " relation event id: " + relationEventId);
        }
        // convert the xml to ElementEventsType object
        ElementEventsType elementEventsType = marshallingService.unMarshalXmlToElementEventsType(xml);
        // asyn task ends here
        return new AsyncResult<>(elementEventsType);
    }
}
