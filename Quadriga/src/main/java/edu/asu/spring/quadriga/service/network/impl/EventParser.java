package edu.asu.spring.quadriga.service.network.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.conceptpower.IConceptpowerConnector;
import edu.asu.spring.quadriga.domain.impl.ConceptpowerReply;
import edu.asu.spring.quadriga.domain.impl.networks.AppellationEventType;
import edu.asu.spring.quadriga.domain.impl.networks.CreationEvent;
import edu.asu.spring.quadriga.domain.impl.networks.ElementEventsType;
import edu.asu.spring.quadriga.domain.impl.networks.PredicateType;
import edu.asu.spring.quadriga.domain.impl.networks.RelationEventType;
import edu.asu.spring.quadriga.domain.impl.networks.RelationType;
import edu.asu.spring.quadriga.domain.impl.networks.TermType;
import edu.asu.spring.quadriga.transform.Link;
import edu.asu.spring.quadriga.transform.Node;
import edu.asu.spring.quadriga.transform.PredicateNode;

/**
 * Class for parsing Appellation/Relation Event networks into S-O-P networks.
 * 
 * @author jdamerow
 *
 */
@PropertySource(value = "classpath:/settings.properties")
@Service
public class EventParser {

    @Autowired
    private IConceptpowerConnector conceptPowerConnector;

    @Autowired
    @Qualifier("jaxbMarshaller")
    private Jaxb2Marshaller jaxbMarshaller;

    public void parseStatement(String relationEventId, ElementEventsType elementEventType, Map<String, Node> nodes,
            List<Link> links) {
        List<CreationEvent> creationEventList = elementEventType.getRelationEventOrAppellationEvent();
        Iterator<CreationEvent> creationEventIterator = creationEventList.iterator();

        while (creationEventIterator.hasNext()) {
            CreationEvent event = creationEventIterator.next();
            parseSubjectOrObjectEvent(event, relationEventId, nodes, links);
        }

    }

    public void parseEvents(List<CreationEvent> creationEventList, Map<String, Node> nodes, List<Link> links) {
        creationEventList.forEach(event -> parseSubjectOrObjectEvent(event, event.getId(), nodes, links));
    }

    private Node parseSubjectOrObjectEvent(CreationEvent event, String statementId, Map<String, Node> leafNodes,
            List<Link> links) {
        if (event == null) {
            return null;
        }

        if (event instanceof AppellationEventType) {
            TermType term = ((AppellationEventType) event).getTermType();
            if (term != null) {
                String conceptId = term.getTermInterpertation();
                if (leafNodes.containsKey(conceptId)) {
                    leafNodes.get(conceptId).getStatementIds().add(statementId);
                } else {
                    Node node = new Node();
                    parseNode((AppellationEventType) event, node, statementId);
                    leafNodes.put(conceptId, node);
                }
                return leafNodes.get(conceptId);
            }
            return null;
        } else if (event instanceof RelationEventType) {
            RelationType relation = ((RelationEventType) event).getRelation();

            // create node for predicate
            PredicateType pred = relation.getPredicateType();
            PredicateNode predNode = parsePredicateEvent(pred.getAppellationEvent(), statementId);
            leafNodes.put(predNode.getId(), predNode);

            Node subjectNode = parseSubjectOrObjectEvent(relation.getSubjectType().getAppellationEvent(), statementId,
                    leafNodes, links);
            if (subjectNode == null) {
                subjectNode = parseSubjectOrObjectEvent(relation.getSubjectType().getRelationEvent(), statementId,
                        leafNodes, links);
            }

            Node objectNode = parseSubjectOrObjectEvent(relation.getObjectType().getAppellationEvent(), statementId,
                    leafNodes, links);
            if (objectNode == null) {
                objectNode = parseSubjectOrObjectEvent(relation.getObjectType().getRelationEvent(), statementId,
                        leafNodes, links);
            }

            // source reference from relation type
            String sourceReference = relation.getSourceReference();

            if (subjectNode != null) {
                Link link = new Link();
                // add the statement id to the link
                link.setStatementId(statementId);
                link.setSubject(predNode);
                link.setObject(subjectNode);
                link.setLabel("has subject");
                links.add(link);
                // set the source reference to the link
                link.setSourceReference(sourceReference);
            }

            if (objectNode != null) {
                Link link = new Link();
                // add the statement id to the link
                link.setStatementId(statementId);
                link.setSubject(predNode);
                link.setObject(objectNode);
                link.setLabel("has object");
                links.add(link);
                // set the source reference to the link
                link.setSourceReference(sourceReference);
            }

            return predNode;
        }

        return null;
    }

    private PredicateNode parsePredicateEvent(AppellationEventType appellationEvent, String statementId) {
        PredicateNode predNode = new PredicateNode();
        parseNode(appellationEvent, predNode, statementId);
        predNode.setId(UUID.randomUUID().toString());
        return predNode;
    }

    private void parseNode(AppellationEventType event, Node node, String statementId) {
        StringBuffer label = new StringBuffer();
        if (event.getTermType() != null) {
            label.append(event.getTermType().getTermInterpertation());
            label.append(" ");
        }
        node.setId(event.getId());
        node.setConceptId(label.toString().trim());
        String conceptId = node.getConceptId();
        if (conceptId != null) {
            int lastSlash = conceptId.lastIndexOf("/");
            if (lastSlash > -1) {
                node.setConceptIdShort(conceptId.substring(lastSlash + 1));
            }
        }

        // set the source reference
        node.setSourceReference(event.getSourceReference());

        if (node.getConceptId() != null) {
            String id = node.getConceptId();
            ConceptpowerReply re = conceptPowerConnector.getById(id);
            if (re != null && re.getConceptEntry().size() != 0) {
                node.setLabel(getLemma(re));
                node.setDescription(getDescription(re));
            } else {
                node.setLabel(id);
                node.setDescription("");
            }

        }
        node.getStatementIds().add(statementId);
    }

    private String getLemma(ConceptpowerReply re) {
        return re.getConceptEntry().get(0).getLemma();
    }

    private String getDescription(ConceptpowerReply re) {
        return re.getConceptEntry().get(0).getDescription();
    }
}
