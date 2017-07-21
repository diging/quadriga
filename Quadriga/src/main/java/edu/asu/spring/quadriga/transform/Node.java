package edu.asu.spring.quadriga.transform;

import java.util.ArrayList;
import java.util.List;

/**
 * Leaf nodes represent subject and object nodes. If we'd make a tree, those nodes
 * would be at the leaves.
 * 
 * @author Julia Damerow
 *
 */
public class Node {

    private String id;
    private List<String> statementIds;
    private String label;
    private String description;
    private String conceptId;
    private String conceptIdShort;
    private String sourceReference;
    private List<String> alternativeIds;
    
    public Node() {
        statementIds = new ArrayList<String>();
    }
    
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public List<String> getStatementIds() {
        return statementIds;
    }
    public void setStatementIds(List<String> statementIds) {
        this.statementIds = statementIds;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getConceptId() {
        return conceptId;
    }
    public void setConceptId(String conceptId) {
        this.conceptId = conceptId;
    }


    public String getSourceReference() {
        return sourceReference;
    }

    public void setSourceReference(String sourceReference) {
        this.sourceReference = sourceReference;
    }


    public String getConceptIdShort() {
        return conceptIdShort;
    }


    public void setConceptIdShort(String conceptIdShort) {
        this.conceptIdShort = conceptIdShort;
    }


    public List<String> getAlternativeIds() {
        return alternativeIds;
    }


    public void setAlternativeIds(List<String> alternativeIds) {
        this.alternativeIds = alternativeIds;
    }
}