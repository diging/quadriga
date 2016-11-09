package edu.asu.spring.quadriga.service.network.transform.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * This class finds graphs that match the given pattern.
 * 
 * @author Julia Damerow
 *
 */
@Service
public class PatternFinder {

	/**
	 * This method finds in the list of graphs, graphs that match
	 * the given pattern. This method only finds graphs that completely
	 * match the given pattern; no matching subgraphs are found.
	 * 
	 * @param graphs List of graphs to search the pattern in.
	 * @param pattern The pattern to be found.
	 * @return List of matching graphs.
	 */
	public List<Node> findPattern(List<Node> graphs, Node pattern) {
		
		List<Node> results = new ArrayList<Node>();
		
		for (Node startNode : graphs) {
			if (equals(startNode, pattern)) {
				results.add(startNode);
			}
				
		}
		
		return results;
	}
	
	/**
	 * This method checks if two nodes and its child nodes are equal.
	 * 
	 * If the nodes are simple nodes and not relations (no subject, predicate, object) then:
	 * <ul>
	 * <li> First if the pattern defines a concept the concept is compared. If it is
	 * not equal false is returned.</li>
	 * 
	 * <li>Second  if there is a type given in the pattern, the types of both nodes
	 * are compared and if there are not equal, false is returned. </li>
	 * 
	 * <li>If there are neither concept nor type given, true is returned.</li>
	 * </ul>
	 * 
	 * If the given node is a relation than it is checked if subject, predicate, and object
	 * are equal.
	 * 
	 * This is a recursive method.
	 * 
	 * @param graph The graph to be checked against the pattern.
	 * @param pattern The pattern to be found.
	 * @return true if graph and pattern are equal in the sense described above, otherwise false.
	 * 
	 */
	public boolean equals(Node graph, Node pattern) {
		if(graph!=null&& pattern!=null){
			
			
			// if the pattern node has a concept this must be equal with graph node
			if (! (graph instanceof Relation) && ! (pattern instanceof Relation)) {
				/*
				 * If the pattern node does not have a concept or type
				 * it should return true.
				 * 
				 * @author: Ashwin Prabhu Verleker
				 */
				if(pattern.getConcept() == null && pattern.getType() == null) {
					graph.setId(pattern.getId());
					return true;
				}
				if (graph.getConcept() != null && pattern.getConcept() != null && graph.getAlternativeIds() != null) {
				    if (graph.getAlternativeIds().contains(pattern.getConcept())) {
				        graph.setId(pattern.getId());
				        return true;
				    }
				    return false;
				}
				else if (graph.getType()!=null && pattern.getType() != null) {
					if (!graph.getType().equals(pattern.getType()))
						return false;
					graph.setId(pattern.getId());
					return true;
				}
				
			}
			
			if (graph instanceof Relation && pattern instanceof Relation) {
				if (!equals(((Relation)graph).getSubject(), ((Relation)pattern).getSubject()))
					return false;
				if (!equals(((Relation)graph).getPredicate(), ((Relation)pattern).getPredicate()))
					return false;
				if (!equals(((Relation)graph).getObject(), ((Relation)pattern).getObject()))
					return false;
				graph.setId(null);
				return true;
			}
		}
	
		// if graph or pattern is not a relation then they are not equal
		return false;
	}
}
