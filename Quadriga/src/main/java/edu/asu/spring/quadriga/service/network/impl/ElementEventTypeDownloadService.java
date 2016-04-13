package edu.asu.spring.quadriga.service.network.impl;

import edu.asu.spring.quadriga.domain.impl.networks.ElementEventsType;
import edu.asu.spring.quadriga.exceptions.QStoreStorageException;
import edu.asu.spring.quadriga.qstore.IMarshallingService;
import edu.asu.spring.quadriga.qstore.IQStoreConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;

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
     * This method would create a new Callabale object to download data from qstore
     * and process for a relation event id
     * @param relationEventId Relation event id
     * @return retrieveElementEvetsTypeCallable
     */
    public Callable<ElementEventsType> getElementEventTypeDownloadTask(String relationEventId) {
        return new RetrieveElementEventsTypeCallable(relationEventId);
    }

    /**
     * Callable class to retrieve the xml from qstore and marshall
     */
    private class RetrieveElementEventsTypeCallable implements Callable<ElementEventsType> {

        private String relationEventId;

        /**
         * Set the relation event id
         * @param relationEventId Relation event id
         */
        private RetrieveElementEventsTypeCallable(String relationEventId) {
            this.relationEventId = relationEventId;
        }

        @Override
        public ElementEventsType call() throws Exception {
            // Get the xml from qstore
            String xml = qstoreConnector.getCreationEvent(relationEventId);

            if (xml == null) {
                throw new QStoreStorageException("Unable to download data from QStore for" +
                        " relation event id: " + relationEventId);
            }
            // convert the xml to ElementEventsType object
            return marshallingService.unMarshalXmlToElementEventsType(xml);
        }
    }

}
