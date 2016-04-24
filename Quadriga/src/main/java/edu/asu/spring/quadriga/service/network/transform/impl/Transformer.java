package edu.asu.spring.quadriga.service.network.transform.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import edu.asu.spring.quadriga.conceptpower.IConceptpowerConnector;
import edu.asu.spring.quadriga.domain.impl.ConceptpowerReply;

/**
 * This class transformes graphs that were found by the {@link PatternFinder}
 * into {@link TransformNode}s and {@link TransformLink}s. Transformed nodes and links
 * are enriched with additional information such as time and place.
 * 
 * @author Julia Damerow
 *
 */
public class Transformer {
	
	
	private IConceptpowerConnector conceptpower;
	private Map<String, TransformLink> statementLinkMap;
	
	public Transformer(IConceptpowerConnector conceptpower) {
	    this.conceptpower = conceptpower;
		statementLinkMap = new HashMap<String, TransformLink>();
	}

	public List<TransformNode> transform(List<Node> graphs, TransformNode transformNode) {
		
		List<TransformNode> transformedGraphs = new ArrayList<TransformNode>();
		for (Node gNode : graphs) {
			Map<String, Node> nodeIdMap = new HashMap<String, Node>();
			putIds(gNode, nodeIdMap);
			
			TransformNode mappedGraph = transformNodes(nodeIdMap, transformNode);
			if (mappedGraph != null)
				transformedGraphs.add(mappedGraph);
		}
		return transformedGraphs;
	}
	
	protected void putIds(Node node, Map<String, Node> ids) {
		if (node.getId() != null) {
			ids.put(node.getId(), node);
		}
		
		if (node instanceof Relation) {
			putIds(((Relation) node).getSubject(), ids);
			putIds(((Relation) node).getPredicate(), ids);
			putIds(((Relation) node).getObject(), ids);
		}
	}
	
	protected TransformNode transformNodes(Map<String, Node> nodes, TransformNode mapping) {
		
		
		TransformNode mappedNode = new TransformNode();
		mappedNode.setId(UUID.randomUUID().toString());		
		
		String concept = mapping.getConcept();
		if (concept != null) {
			mappedNode.setConcept(concept);
			ConceptpowerReply reply = conceptpower.getById(concept);
			if (reply != null) {
			    if (reply.getConceptEntry().size() > 0) {
			        mappedNode.setConceptName(reply.getConceptEntry().get(0).getLemma());
			    }
			}
		}
		else {
			String correspondingId = mapping.getCorrespondingId();
			Node corrNode = nodes.get(correspondingId);
			if (corrNode != null) {
				mappedNode.setConcept(corrNode.getConcept());
				mappedNode.setType(corrNode.getType());
				mappedNode.setCorrespondingId(correspondingId);
				
				ConceptpowerReply reply = conceptpower.getById(corrNode.getConcept());
	            if (reply != null) {
	                if (reply.getConceptEntry().size() > 0) {
	                    mappedNode.setConceptName(reply.getConceptEntry().get(0).getLemma());
	                }
	            }
				
				mappedNode.setTerms(corrNode.getTerms());
			}
		}
		
		if (mapping.getLinks() != null) {
			for (TransformLink link : mapping.getLinks()) {
				
				/*
				 *  if a link represents a statement we need to make sure that
				 *  triples attaching to that statement like start and end
				 *  are added to the same statement
				 */	
				TransformLink mappedLink = null;
				if (link.getRepresentedStatement() != null) {
					String stateId = link.getRepresentedStatement();
					Node statementNode = nodes.get(stateId);
					if (statementNode.getEventId() != null) {
						mappedLink = statementLinkMap.get(statementNode.getEventId());
					}
				}
				if (mappedLink == null) {
					mappedLink = new TransformLink();
					mappedLink.setId(UUID.randomUUID().toString());
					mappedLink.setSubject(mappedNode);
					
					if (link.getRepresentedStatement() != null) {
						String stateId = link.getRepresentedStatement();
						Node statementNode = nodes.get(stateId);
						if (statementNode.getEventId() != null) {
							statementLinkMap.put(statementNode.getEventId(), mappedLink);
						}
					}
				}
				
					
				
				// if the link has a specified concept 
				if (link.getConcept() != null) {
					mappedLink.setConcept(link.getConcept());
				
					ConceptpowerReply reply = conceptpower.getById(link.getConcept());
                    if (reply != null) {
                        if (reply.getConceptEntry().size() > 0) {
                            mappedLink.setConceptName(reply.getConceptEntry().get(0).getLemma());
                        }
                    }
				}
				
				/*
				 *  if the link has a specified node to take information from,
				 *  use the information rather than the concept
				 */
				String corrId = link.getCorrespondingId();
				if (corrId != null && !corrId.isEmpty()) {
					Node corrNode = nodes.get(corrId);
					if (corrNode != null) {
						mappedLink.setConcept(corrNode.getConcept());
						mappedLink.setType(corrNode.getType());
						mappedLink.setCorrespondingId(corrId);
						
						ConceptpowerReply reply = conceptpower.getById(corrNode.getConcept());
	                    if (reply != null) {
	                        if (reply.getConceptEntry().size() > 0) {
	                            mappedLink.setConceptName(reply.getConceptEntry().get(0).getLemma());
	                        }
	                    }
					}
				}
					
				// map start time
				String startTimeId = link.getStartTime();
				if (startTimeId != null) {
					Node startNode = nodes.get(startTimeId);
					if (startNode != null) {
						mappedLink.setStartTime(startNode.getConcept());
					}
				}
				
				// map end time
				String endTimeId = link.getEndTime();
				if (endTimeId != null) {
					Node endNode = nodes.get(endTimeId);
					if (endNode != null) {
						mappedLink.setEndTime(endNode.getConcept());
					}
				}
				
				// map occur time
				String occursId = link.getOccurTime();
				if (occursId != null) {
					Node occurNode = nodes.get(occursId);
					if (occurNode != null) {
						mappedLink.setOccurTime(occurNode.getConcept());
					}
				}
				
				// map place
				String placeId = link.getPlace();
				if (placeId != null) {
					Node node = nodes.get(placeId);
					if (node != null) {
						mappedLink.setPlace(node.getConcept());
					}
				}
				
				
				/*
				 * If the link has an object go on recursively to fill
				 * the object node
				 */
				TransformNode linkedNode = link.getObject();
				if (linkedNode != null) {
					TransformNode mappedLinkedNode = transformNodes(nodes, linkedNode);
					mappedLink.setObject(mappedLinkedNode);
				}
				
				// add link to root node
				if (mappedNode.getLinks() == null)
					mappedNode.setLinks(new ArrayList<TransformLink>());
				
				mappedNode.getLinks().add(mappedLink);
			}
		}
		
		return mappedNode;
	}
	
	public String getTurtle(TransformNode node) {
		StringBuffer sb = new StringBuffer();
		
		createTurtle(node, sb);
		return sb.toString();
	}
	
	protected void createTurtle(TransformNode node, StringBuffer output) {
		if (node.getLinks() == null)
			return;
		
		for (TransformLink link : node.getLinks()) {
			output.append("<" + node.getConcept() + "> ");
			output.append("<" + link.getConcept() + ">");
			output.append("<" + link.getObject().getConcept() + ">. \n");
			
			createTurtle(link.getObject(), output);
		}
		
	}
}
