package edu.asu.spring.quadriga.conceptpower.impl;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.stereotype.Component;

import edu.asu.spring.quadriga.conceptpower.IConcept;
import edu.asu.spring.quadriga.conceptpower.IConceptMapper;
import edu.asu.spring.quadriga.conceptpower.IConceptType;
import edu.asu.spring.quadriga.domain.impl.ConceptpowerReply.ConceptEntry;

@Component
public class ConceptMapper implements IConceptMapper {

    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.impl.IConceptMapper#mapConceptpowerReplyToConcept(edu.asu.spring.quadriga.domain.impl.ConceptpowerReply.ConceptEntry)
     */
    @Override
    public IConcept mapConceptpowerReplyToConcept(ConceptEntry reply) {
        IConcept concept = new Concept();
        concept.setAlternativeUris(reply.getAlternativeIdList());
        concept.setConceptList(reply.getConceptList());
        concept.setDescription(reply.getDescription());
        concept.setPos(reply.getPos());
        concept.setTypeId(reply.getTypeUri() != null ? reply.getTypeUri() : "");
        concept.setUri(reply.getId());
        concept.setId(reply.getId());
        concept.setCreatorId(reply.getCreatorId());
        concept.setWord(reply.getLemma());
        if (reply.getWordnetId() != null) {
            concept.setWordnetIds(Arrays.asList(reply.getWordnetId().split(",")));
        } else {
            concept.setWordnetIds(new ArrayList<>());
        }
        if (reply.getEqualTo() != null) {
            concept.setEqualTo(Arrays.asList(reply.getEqualTo().split(",")));
        } else {
            concept.setEqualTo(new ArrayList<>());
        }
        
        IConceptType type = new ConceptType();
        type.setUri(reply.getTypeUri());
        type.setId(reply.getTypeUri());
        type.setDescription("");
        type.setName(reply.getType());
        concept.setType(type);
        return concept;
    }
}
