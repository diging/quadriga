package edu.asu.spring.quadriga.service.network.transform.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.conceptpower.impl.ConceptpowerConnector;
import edu.asu.spring.quadriga.domain.impl.ConceptpowerReply;
import edu.asu.spring.quadriga.domain.impl.networks.AppellationEventType;
import edu.asu.spring.quadriga.domain.impl.networks.CreationEvent;
import edu.asu.spring.quadriga.domain.impl.networks.PrintedRepresentationType;
import edu.asu.spring.quadriga.domain.impl.networks.RelationEventType;
import edu.asu.spring.quadriga.domain.impl.networks.RelationType;
import edu.asu.spring.quadriga.domain.impl.networks.TermPartType;
import edu.asu.spring.quadriga.domain.impl.networks.TermType;

/**
 * This class takes Event-based graphs created with Vogon and
 * transforms them into {@Relation Node}s and {@Relation Relation}s that can then be
 * searched.
 * 
 * @author Julia Damerow
 *
 */
public class EventGraphMapper {
    
    private ConceptpowerConnector conceptpower;

	private List<Node> startNodes;

	public EventGraphMapper(ConceptpowerConnector conceptpower) {
		startNodes = new ArrayList<Node>();
		this.conceptpower = conceptpower;
	}
	
	public List<Node> getStartNodes() {
        return startNodes;
    }

    public void setStartNodes(List<Node> startNodes) {
        this.startNodes = startNodes;
    }
    
	/**
	 * Method for transforming event-based graphs into {@Relation Node}/{@Relation Relation}
	 * based graphs.
	 * 
	 * @param events A list of events to be transformed.
	 * @param conceptPath Path to a Conceptpower instance to retrieve concept information from.
	 */
	public void buildGraphs(List<CreationEvent> events) {
		List<CreationEvent> unreferenced = new ArrayList<CreationEvent>();
		Map<String, Node> nodeMap = new HashMap<String, Node>();
		Map<String, Relation> relationMap = new HashMap<String, Relation>();
		
		// find unreferenced events
		MainLoop: for (CreationEvent event : events) {
			if (event instanceof AppellationEventType) {
				Node node = new Node();
				node.setId(event.hashCode() + "");
				node.setEventId(node.getId());
				
				List<TermType> terms = ((AppellationEventType) event).getTerms();
				if (terms != null) {
					
				    for (TermType term : terms) {
    					Term nodeTerm = new Term();
    					
    					fillTermWithTermParts(nodeTerm, term);
    					
    					nodeTerm.setSourceUri(term.getSourceReference());
    					
    					
    					node.addTerm(nodeTerm);
    					String interpretation = term.getTermInterpertation();
    					if (interpretation != null) {
    						node.setConcept(interpretation);
    						ConceptpowerReply reply = conceptpower.getById(interpretation);
    						if (reply.getConceptEntry().size() > 0) {
        						node.setType(reply.getConceptEntry().get(0).getType());
    						}
    					}
				    }
				}
				nodeMap.put(node.getId(), node);
			}
			else {
				
				if (((RelationEventType)event).getRelation() != null &&((RelationEventType)event).getRelation().getSubjectType() != null && ((RelationEventType)event).getRelation().getObjectType() != null && ((RelationEventType)event).getRelation().getPredicateType() != null) {
					Node node = new Node();
					node.setId(((RelationEventType)event).getRelation().hashCode() + "" + ((RelationEventType)event).getRelation().getPredicateType().hashCode() + "");
					node.setEventId(node.getId());
					
					List<TermType> terms = ((RelationEventType) event).getRelation().getPredicateType().getAppellationEvent().getTerms();
					if (terms != null) {
						for (TermType term : terms) {
						
						    Term nodeTerm = new Term();
						
						    fillTermWithTermParts(nodeTerm, term);
						    
						    nodeTerm.setSourceUri(term.getSourceReference());
						
						    node.addTerm(nodeTerm);
						
						    String interpretation = term.getTermInterpertation();
    						if (interpretation != null) {
    							node.setConcept(interpretation);
    							ConceptpowerReply reply = conceptpower.getById(interpretation);
                                if (reply.getConceptEntry().size() > 0) {
                                    node.setType(reply.getConceptEntry().get(0).getType());
                                }
    						}
    					}
					}
					
					nodeMap.put(node.getId(), node);
					
					Relation relNode = new Relation();
					
					RelationType relation = ((RelationEventType)event).getRelation();
					relNode.setId(relation.hashCode() + "" + relation.getPredicateType().hashCode() + "");
					relNode.setEventId(relNode.getId());
					relationMap.put(relNode.getId(), relNode);
				}
				
			}
			
			for (CreationEvent checkAgains : events) {
				if (checkAgains instanceof AppellationEventType)
					continue;
						
				RelationType rel = ((RelationEventType)checkAgains).getRelation();
				if (rel != null) {
					if (rel.getSubjectType() != null && rel.getSubjectType().hashCode() == event.hashCode()) {
						continue MainLoop;
					}
					if (rel.getPredicateType() != null && rel.getPredicateType().hashCode() == event.hashCode())
						continue MainLoop;
					
					if (rel.getObjectType() != null && rel.getObjectType().hashCode() == event.hashCode())
						continue MainLoop;
				}
			}
			
			unreferenced.add(event);
		}

		
		// Relation nodes
		for (CreationEvent event : events) {
			if (event instanceof RelationEventType &&  ((RelationEventType)event).getRelation() != null &&((RelationEventType)event).getRelation().getSubjectType() != null && ((RelationEventType)event).getRelation().getObjectType() != null && ((RelationEventType)event).getRelation().getPredicateType() != null) {
				if (((RelationEventType) event).getRelation() != null  &&((RelationEventType)event).getRelation().getSubjectType() != null && ((RelationEventType)event).getRelation().getObjectType() != null && ((RelationEventType)event).getRelation().getPredicateType() != null) {
					RelationType relation = ((RelationEventType) event).getRelation();
					if (relation.getSubjectType() != null && relation.getObjectType() != null && relation.getPredicateType() != null) {
						
						Relation rel = relationMap.get(relation.hashCode() + "" + relation.getPredicateType().hashCode());
						rel.setPredicate(nodeMap.get(relation.hashCode() + "" + relation.getPredicateType().hashCode()));
						
						String id = "";
						if (relation.getSubjectType().getRelationEvent() != null) {
						    RelationEventType relationET = (RelationEventType)relation.getSubjectType().getRelationEvent();
							id = relationET.getRelation().hashCode() + "" + relationET.getRelation().getPredicateType().hashCode() + "";
						}
						Node subject = relationMap.get(id);
						if (subject == null) {
							subject = nodeMap.get(relation.getSubjectType().getAppellationEvent().hashCode() + "");
						}
						rel.setSubject(subject);
						
						
						String objId = "";
						if (relation.getObjectType().getRelationEvent() != null && (relation.getObjectType().getRelationEvent().getRelation() != null && relation.getObjectType().getRelationEvent().getRelation().getPredicateType() != null)) {
							RelationEventType relationET = relation.getObjectType().getRelationEvent();
						    objId = relationET.getRelation().hashCode() + "" + relationET.getRelation().getPredicateType().hashCode();
						}
						Node object = relationMap.get(objId);
						if (object == null) {
							object = nodeMap.get(relation.getObjectType().getAppellationEvent().hashCode() + "");
						}
						rel.setObject(object);
						
					}
				
				}
			}
		}
		
		for (CreationEvent event : unreferenced) {
			if (event instanceof AppellationEventType) {
				Node start = nodeMap.get(event.hashCode() + "");
				if (start != null)
					startNodes.add(copyGraph(start));
			}
			else {
				if (((RelationEventType) event).getRelation() != null) {
					RelationType relation = ((RelationEventType) event).getRelation();
					if (relation.getSubjectType() != null && relation.getObjectType() != null && relation.getPredicateType() != null) {
						Node start = relationMap.get(relation.hashCode() + "" + relation.getPredicateType().hashCode());
						if (start == null)
							start = nodeMap.get(relation.hashCode() + "" + relation.getPredicateType().hashCode());
						if (start != null && isGraphComplete(start))
							startNodes.add(copyGraph(start));
					}
				}
			}
		}
	}
	
	/**
	 * This method takes term parts from the given term and fill the
	 * {@Relation Term} so that text positions can be used in transformation templates.
	 * @param nodeTerm The term to be filled for transformation templates.
	 * @param term The term from the {@Relation AppellationEventType} which is used to create
	 * the term.
	 */
	protected void fillTermWithTermParts(Term nodeTerm, TermType term) {
	    PrintedRepresentationType printedRep = term.getPrintedRepresentation();
	    if (printedRep == null) {
	        return;
	    }
		List<TermPartType> itermParts = printedRep.getTermPart();
		if (itermParts != null) {

			for (TermPartType part : itermParts) {
				TermPart termPart = new TermPart();
				if (part.getExpression() != null) {
					termPart.setExpression(part.getExpression());
					termPart.setStartPosition(new Integer(part.getPosition()));
					termPart.setEndPosition(new Integer(part.getPosition() + part.getExpression().length()));
					nodeTerm.addTermPart(termPart);
				}
			}
		}
	}
	
	/**
	 * This method checks if the graph that with the given start node is
	 * complete, which means that all subject, predicate, and objects are 
	 * filled. This is a recursive function.
	 * 
	 * @param start The top node that is the entry point to the graph.
	 * @return true if all subject, predicate, and objects are filled. 
	 * otherwise false.
	 */
	protected boolean isGraphComplete(Node start) {
		if (start == null)
			return false;
		
		if (start instanceof Relation) {
			if (!isGraphComplete(((Relation)start).getSubject()))
				return false;
			if (!isGraphComplete(((Relation)start).getPredicate()))
				return false;
			if (!isGraphComplete(((Relation)start).getObject()))
				return false;
		}
		
		return true;
	}
	
	/**
	 * Method for copying transformed graphs to have no nodes that are used
	 * by two different graphs. This is a recursive function.
	 * 
	 * @param start Start node where copy starts.
	 * @return copied Node
	 */
	protected Node copyGraph(Node start) {
		Node copiedNode = new Node();
		if (start instanceof Relation)
			copiedNode = new Relation();
		
		copiedNode.setConcept(start.getConcept());
		copiedNode.setId(start.getId());
		copiedNode.setEventId(start.getEventId());
		copiedNode.setType(start.getType());
		copiedNode.setTerms(start.getTerms());

		if (start instanceof Relation) {
			Node subject = ((Relation) start).getSubject();
			Node predicate = ((Relation) start).getPredicate();
			Node object = ((Relation) start).getObject();
			
			((Relation) copiedNode).setSubject(copyGraph(subject));
			((Relation) copiedNode).setPredicate(copyGraph(predicate));
			((Relation) copiedNode).setObject(copyGraph(object));
		}
		return copiedNode;
	}
}
