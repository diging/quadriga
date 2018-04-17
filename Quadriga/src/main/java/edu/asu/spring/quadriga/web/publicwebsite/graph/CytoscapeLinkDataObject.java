package edu.asu.spring.quadriga.web.publicwebsite.graph;

import java.util.List;

/**
 * This represents the information associated with the network graph links.
 * 
 * @author Chiraag Subramanian
 *
 */
public class CytoscapeLinkDataObject {

    private String id;
    private String source;
    private String target;
    private String label;
    private String sourceReference;
    private List<String> statementIds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSourceReference() {
        return sourceReference;
    }

    public void setSourceReference(String sourceReference) {
        this.sourceReference = sourceReference;
    }

    public List<String> getStatementIds() {
        return statementIds;
    }

    public void setStatementIds(List<String> statementIds) {
        this.statementIds = statementIds;
    }

}
