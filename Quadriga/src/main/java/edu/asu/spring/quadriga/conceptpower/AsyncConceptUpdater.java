package edu.asu.spring.quadriga.conceptpower;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
        ConceptpowerReply reply = connector.getById(uri);
        if (reply.getConceptEntry() != null && !reply.getConceptEntry().isEmpty()) {
            IConcept concept = conceptMapper.mapConceptpowerReplyToConcept(reply.getConceptEntry().get(0));
            conceptDB.createOrUpdate(concept);
        }
    }
}
