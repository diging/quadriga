package edu.asu.spring.quadriga.conceptpower.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.conceptpower.IAsyncConceptUpdater;
import edu.asu.spring.quadriga.conceptpower.IConcept;
import edu.asu.spring.quadriga.conceptpower.IConceptMapper;
import edu.asu.spring.quadriga.conceptpower.IConceptpowerCache;
import edu.asu.spring.quadriga.conceptpower.IConceptpowerConnector;
import edu.asu.spring.quadriga.conceptpower.db.IConceptDatabaseConnection;
import edu.asu.spring.quadriga.conceptpower.model.ConceptpowerReply;

@Service
public class ConceptpowerCache implements IConceptpowerCache {

    @Autowired
    private IConceptpowerConnector connector;
    
    @Autowired
    private IConceptDatabaseConnection conceptDB;
    
    @Autowired
    private IConceptMapper conceptMapper;
    
    @Autowired
    private IAsyncConceptUpdater conceptUpdater;
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.impl.IConceptpowerCache#getConceptByUri(java.lang.String)
     */
    @Override
    public IConcept getConceptByUri(String uri) {
        IConcept concept = conceptDB.getConcept(uri);
        
        if (concept != null) {
            conceptUpdater.updateConcept(uri);
            return concept;
        }
        
        ConceptpowerReply reply = connector.getById(uri);
        if (reply.getConceptEntry() != null && !reply.getConceptEntry().isEmpty()) {
            concept = conceptMapper.mapConceptpowerReplyToConcept(reply.getConceptEntry().get(0));
            conceptDB.createOrUpdate(concept);
        }
        return concept;
    }
}
