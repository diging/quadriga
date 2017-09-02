package edu.asu.spring.quadriga.service.network.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.conceptpower.IConcept;
import edu.asu.spring.quadriga.conceptpower.IConceptpowerCache;
import edu.asu.spring.quadriga.domain.network.impl.AppellationEventType;
import edu.asu.spring.quadriga.domain.network.impl.CreationEvent;
import edu.asu.spring.quadriga.domain.network.impl.ElementEventsType;
import edu.asu.spring.quadriga.domain.network.impl.TermType;
import edu.asu.spring.quadriga.exceptions.AsyncExecutionException;
import edu.asu.spring.quadriga.qstore.IQStoreConnector;
import edu.asu.spring.quadriga.qstore.impl.MarshallingService;
import edu.asu.spring.quadriga.service.network.INetworkConceptManager;

@Service
public class NetworkConceptManager implements INetworkConceptManager {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IQStoreConnector qstoreConnector;
    
    @Autowired
    private MarshallingService marshallingService;
    
    @Autowired
    private IConceptpowerCache conceptpowerCache;
    
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.service.network.impl.INetworkConceptManager#getConceptsOfStatements(java.util.List)
     */
    @Override
    public List<IConcept> getConceptsOfStatements(List<String> allNodes) throws JAXBException {
        String idList = "\"" + String.join("\",\"", allNodes) + "\"";
        Map<String, String> paras = new HashMap<>();
        paras.put("nodes.id.list", idList);
        String result;
        try {
            result = qstoreConnector.executeNeo4jQuery("concepts.of.statements", paras, IQStoreConnector.APPELLATION_EVENT);
        } catch (AsyncExecutionException e) {
            logger.error("Could not execute query.", e);
            return null;
        }
        ElementEventsType eventsType = marshallingService.unMarshalXmlToElementEventsType(result);
        List<CreationEvent> events = eventsType.getRelationEventOrAppellationEvent();
        List<IConcept> concepts = new ArrayList<>();
        events.forEach(e -> {
            if (e instanceof AppellationEventType) {
               TermType term = ((AppellationEventType) e).getTermType();
               String conceptUri = term.getTermInterpertation();
               IConcept concept = conceptpowerCache.getConceptByUri(conceptUri);
               if (concept != null) {
                   concepts.add(concept);
               }
            }
        });
        
        return concepts;
    }
}
