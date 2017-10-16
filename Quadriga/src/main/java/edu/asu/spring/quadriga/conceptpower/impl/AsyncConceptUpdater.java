package edu.asu.spring.quadriga.conceptpower.impl;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.conceptpower.IAsyncConceptUpdater;
import edu.asu.spring.quadriga.conceptpower.IConcept;
import edu.asu.spring.quadriga.conceptpower.IConceptMapper;
import edu.asu.spring.quadriga.conceptpower.IConceptpowerConnector;
import edu.asu.spring.quadriga.conceptpower.db.IConceptDatabaseConnection;
import edu.asu.spring.quadriga.conceptpower.model.ConceptpowerReply;

@Service
public class AsyncConceptUpdater implements IAsyncConceptUpdater {
    
    @Autowired
    private IConceptDatabaseConnection conceptDB;
    
    @Autowired
    private IConceptpowerConnector connector;
    
    @Autowired
    private IConceptMapper conceptMapper;

    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.IAsyncConceptUpdater#updateConcept(java.lang.String)
     */
    @Override
    @Async
    public void updateConcept(String uri) {
        IConcept storedConcept = conceptDB.getConcept(uri);
        if (storedConcept.getLastUpdated() != null && storedConcept.getLastUpdated().plusDays(2).isAfter(OffsetDateTime.now())) {
            // only update a concept every 2 days
            return;
        }
        ConceptpowerReply reply = connector.getById(uri);
        if (reply.getConceptEntry() != null && !reply.getConceptEntry().isEmpty()) {
            IConcept concept = conceptMapper.mapConceptpowerReplyToConcept(reply.getConceptEntry().get(0));
            conceptDB.createOrUpdate(concept);
        }
    }
}
