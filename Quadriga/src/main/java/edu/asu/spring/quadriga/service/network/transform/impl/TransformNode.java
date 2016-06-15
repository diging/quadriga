package edu.asu.spring.quadriga.service.network.transform.impl;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a node in mapping graphs
 * and mapped graphs. It has the following properties:
 * 
 * <ul>
 * 	<li>concept: the URI of a concept a node refers to</li>
 * 	<li>type: the URI of a type a node refers to</li>
 * 	<li>correspondingId: the id of a node in the pattern that a graph was matching</li>
 * 	<li>links: a list of outgoing edges of a node</li>
 *  <li>terms: a list of terms a nodes represenets (these terms point to positions
 *  in a text.</li>
 *  <li>id: a unique identifier for a node</li>
 *  <li>conceptName: the name a concept has in Conceptpower</li>
 * </ul>
 * 
 * @author Julia Damerow
 *
 */

public class TransformNode {
	private String concept;
	private String type;
	private String correspondingId;
	private List<TransformLink> links;
	private List<Term> terms;
	private String id;
	private String conceptName;
		
 	public String getConcept() {
		return concept;
	}
	public void setConcept(String concept) {
		this.concept = concept;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCorrespondingId() {
		return correspondingId;
	}
	public void setCorrespondingId(String id) {
		this.correspondingId = id;
	}
	public List<TransformLink> getLinks() {
		return links;
	}
	public void setLinks(List<TransformLink> links) {
		this.links = links;
	}
	public List<Term> getTerms() {
		return terms;
	}
	public void setTerms(List<Term> terms) {
		this.terms = terms;
	}

	public void addTerm(Term term) {
		if(terms == null) {
			terms = new ArrayList<Term>();
		}
		terms.add(term);
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getConceptName() {
		return conceptName;
	}
	public void setConceptName(String conceptName) {
		this.conceptName = conceptName;
	}
}
