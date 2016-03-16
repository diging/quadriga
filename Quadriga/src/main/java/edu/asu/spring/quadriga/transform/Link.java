package edu.asu.spring.quadriga.transform;

public class Link {

    private Node subject;
    private Node object;
    private String label;
    
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

}
