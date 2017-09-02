package edu.asu.spring.quadriga.service.network.transform.impl;

import java.util.HashMap;

/**
 * This class represents a link between two nodes
 * in mapping graphs and mapped graphs.
 * 
 * @author Julia Damerow
 *
 */
public class TransformLink {

	private String concept;
	private String correspondingId;
	private TransformNode object;
	private TransformNode subject;
	private String type;
	private String startTime;
	private String endTime;
	private String occurTime;
	private String place;
	private String conceptName;
	private String sourceUri;
	private String id;
	private HashMap<String, Integer> terms = new HashMap<String, Integer>();
	private String representedStatement;

	public HashMap<String, Integer> getTerms() {
		return terms;
	}

	public void setTerms(HashMap<String, Integer> terms) {
		this.terms = terms;
	}
	
	public void addTerm(String expression, Integer position){
		
		terms.put(expression, position);
		
	}
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getOccurTime() {
		return occurTime;
	}
	public void setOccurTime(String occurTime) {
		this.occurTime = occurTime;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getConcept() {
		return concept;
	}
	public void setConcept(String concept) {
		this.concept = concept;
	}
	public String getCorrespondingId() {
		return correspondingId;
	}
	public void setCorrespondingId(String correspondingId) {
		this.correspondingId = correspondingId;
	}
	public TransformNode getObject() {
		return object;
	}
	public void setObject(TransformNode object) {
		this.object = object;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public TransformNode getSubject() {
		return subject;
	}
	public void setSubject(TransformNode subject) {
		this.subject = subject;
	}

	public String getConceptName() {
		return conceptName;
	}

	public void setConceptName(String conceptName) {
		this.conceptName = conceptName;
	}

	public String getSourceUri() {
        return sourceUri;
    }

    public void setSourceUri(String sourceUri) {
        this.sourceUri = sourceUri;
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRepresentedStatement() {
		return representedStatement;
	}

	public void setRepresentedStatement(String representedStatement) {
		this.representedStatement = representedStatement;
	}
	
}
