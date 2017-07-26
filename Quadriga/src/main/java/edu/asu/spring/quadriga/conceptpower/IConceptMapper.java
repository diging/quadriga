package edu.asu.spring.quadriga.conceptpower;

import edu.asu.spring.quadriga.conceptpower.model.ConceptpowerReply.ConceptEntry;

public interface IConceptMapper {

    IConcept mapConceptpowerReplyToConcept(ConceptEntry reply);

}