package edu.asu.spring.quadriga.service.network.transform.impl;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents one node in graphs that should be searched and mapped.
 * 
 * @author Julia Damerow
 *
 */
public class Node {

    private String concept;
    private String type;
    private String id;
    private String eventId;
    private List<Term> terms;
    private List<String> alternativeIds;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Term> getTerms() {
        return terms;
    }

    public void setTerms(List<Term> terms) {
        this.terms = terms;
    }

    public void addTerm(Term term) {
        if (terms == null) {
            terms = new ArrayList<Term>();
        }
        terms.add(term);
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public List<String> getAlternativeIds() {
        return alternativeIds;
    }

    public void setAlternativeIds(List<String> alternativeIds) {
        this.alternativeIds = alternativeIds;
    }

}
