package edu.asu.spring.quadriga.qstore;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import edu.asu.spring.quadriga.domain.network.impl.RelationEventType;
import edu.asu.spring.quadriga.exceptions.AsyncExecutionException;
import edu.asu.spring.quadriga.exceptions.QStoreStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaException;

public interface IQStoreConnector {

    public static String RELATION_EVENT = "relationevent";
    public static String APPELLATION_EVENT = "appellationevent";

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
     * Get all Networks from QStore with popular terms. This will start an async
     * task on QStore and will keep polling QStore until the query is executed
     * 
     * @return
     * @throws AsyncExecutionException
     */
    Future<String> loadNetworkWithPopularTerms() throws AsyncExecutionException;
    
    String loadNetworkWithConceptsBelongingToSameStatements(List<String> conceptUriSearchList) throws AsyncExecutionException;

    String getCreationEvents(List<String> ids) throws QuadrigaException;

    String executeNeo4jQuery(String queryName, Map<String, String> parameters, String clazzType) throws AsyncExecutionException;

}