package edu.asu.spring.quadriga.conceptpower;

import edu.asu.spring.quadriga.domain.impl.ConceptpowerReply.ConceptEntry;

public interface IConceptMapper {

    IConcept mapConceptpowerReplyToConcept(ConceptEntry reply);

}