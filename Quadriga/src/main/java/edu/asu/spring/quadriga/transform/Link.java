package edu.asu.spring.quadriga.transform;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Link {

    private Node subject;
    private Node object;
    private String label;
    private List<String> statementIds;

    public Link() {
        // create a new hash set for statement ids
        statementIds = new ArrayList<String>();
    }
    
    public Node getSubject() {
        return subject;
    }

    public void setSubject(Node subject) {
        this.subject = subject;
    }

    public Node getObject() {
        return object;
    }

    public void setObject(Node object) {
        this.object = object;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<String> getStatementIds() {
        return statementIds;
    }

    public void addStatementId(String statementId) {
        statementIds.add(statementId);
    }
}
