package edu.asu.spring.quadriga.qstore;

import edu.asu.spring.quadriga.domain.impl.networks.RelationEventType;
import edu.asu.spring.quadriga.exceptions.QStoreStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaException;

public interface IQStoreConnector {

    public static String RELATION_EVENT = "relationevent";

    public abstract String getQStoreAddURL();

    public abstract String getQStoreGetURL();

    public abstract String getQStoreGetPOSTURL();

    public abstract String getCreationEvent(String id) throws QStoreStorageException;

    public abstract String store(String xml) throws QStoreStorageException;

    /**
     * 
     * {@inheritDoc} QStore allows us to get network XML for specific
     * {@link RelationEventType} and also List of {@link RelationEventType}
     * embedded in XML.
     * 
     */
    public abstract String getStatements(String xml);

    public abstract String searchNodesByConcept(String conceptId) throws Exception;

    String getAppellationEventsByConceptAndText(String conceptUri, String textUri) throws QuadrigaException;

    String getQStoreQueryURL();

    /**
     * Execute the given query in Qstore and return the result
     * 
     * @return
     * @throws QStoreStorageException
     */
    public abstract String executeQuery() throws QStoreStorageException;

}