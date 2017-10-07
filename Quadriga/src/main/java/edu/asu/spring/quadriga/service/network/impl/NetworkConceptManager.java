package edu.asu.spring.quadriga.service.network.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import edu.asu.spring.quadriga.conceptpower.IConcept;
import edu.asu.spring.quadriga.conceptpower.IConceptpowerCache;
import edu.asu.spring.quadriga.domain.network.impl.AppellationEventType;
import edu.asu.spring.quadriga.domain.network.impl.CreationEvent;
import edu.asu.spring.quadriga.domain.network.impl.ElementEventsType;
import edu.asu.spring.quadriga.domain.network.impl.TermType;
import edu.asu.spring.quadriga.exceptions.AsyncExecutionException;
import edu.asu.spring.quadriga.exceptions.NoCacheEntryForKeyException;
import edu.asu.spring.quadriga.qstore.IQStoreConnector;
import edu.asu.spring.quadriga.qstore.impl.MarshallingService;
import edu.asu.spring.quadriga.service.network.INetworkConceptManager;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

@Service
public class NetworkConceptManager implements INetworkConceptManager {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IQStoreConnector qstoreConnector;
    
    @Autowired
    private MarshallingService marshallingService;
    
    @Autowired
    private IConceptpowerCache conceptpowerCache;
    
    @Autowired
    @Qualifier("ehcache")
    private CacheManager cacheManager;
    
    private Cache cache;
    
    @PostConstruct
    public void init() {
        cache = cacheManager.getCache("statementConcepts");
    }
    
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.service.network.impl.INetworkConceptManager#getConceptsOfStatements(java.util.List)
     */
    @Override
    @Async
    public void getConceptsOfStatements(int key, List<String> allNodes, List<String> typeIdList) throws JAXBException {
        // if there exists and unexpired element in the cache
        // we don't want to run this method again
        if (cache.getQuiet(key) != null) {
            Element element = cache.getQuiet(key);
            if (!element.isExpired()) {
                return;
            }
        }
        
        cache.put(new Element(key, null));
        
        // QUAD-261, break up long queries
        List<IConcept> concepts = new ArrayList<>();
        for (List<String> partition : Lists.partition(allNodes, 200)) {
            String idList = "\"" + String.join("\",\"", partition) + "\"";
            Map<String, String> paras = new HashMap<>();
            paras.put("nodes.id.list", idList);
            String result;
            try {
                result = qstoreConnector.executeNeo4jQuery("concepts.of.statements", paras, IQStoreConnector.APPELLATION_EVENT);
            } catch (AsyncExecutionException e) {
                logger.error("Could not execute query.", e);
                continue;
            }
            ElementEventsType eventsType = marshallingService.unMarshalXmlToElementEventsType(result);
            List<CreationEvent> events = eventsType.getRelationEventOrAppellationEvent();
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
        }
        
        List<IConcept> filteredConcepts = concepts.stream().filter(c -> typeIdList.contains(c.getTypeId().trim())).distinct().collect(Collectors.toList());
        
        // let's put the result in the cache
        cache.put(new Element(key, filteredConcepts));
    }
    
    @Override
    public List<IConcept> getQueryResult(int key) throws NoCacheEntryForKeyException {
        Element element = cache.get(key);
        if (element == null) {
            throw new NoCacheEntryForKeyException();
        }
        Object result = element.getObjectValue();
        if (result == null) {
            return null;
        }
        return (List<IConcept>) result;
    }
}
