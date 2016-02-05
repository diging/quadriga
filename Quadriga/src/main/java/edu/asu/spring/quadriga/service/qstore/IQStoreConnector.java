package edu.asu.spring.quadriga.service.qstore;

import edu.asu.spring.quadriga.domain.impl.networks.RelationEventType;
import edu.asu.spring.quadriga.exceptions.QStoreStorageException;

public interface IQStoreConnector {

    public abstract String getQStoreAddURL();

    public abstract String getQStoreGetURL();

    public abstract String getQStoreGetPOSTURL();

    public abstract String getCreationEvent(String id)
            throws QStoreStorageException;

    public abstract String store(String xml) throws QStoreStorageException;

    /**
     * 
     * {@inheritDoc} QStore allows us to get network XML for specific
     * {@link RelationEventType} and also List of {@link RelationEventType}
     * embedded in XML.
     * 
     */
    public abstract String getStatements(String xml);

}