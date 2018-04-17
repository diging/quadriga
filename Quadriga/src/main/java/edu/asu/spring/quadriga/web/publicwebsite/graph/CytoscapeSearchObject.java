package edu.asu.spring.quadriga.web.publicwebsite.graph;

import java.util.List;
/**
 * This represents the asynchronous network transformation result comprising of:
 * (i) processing status, (ii) graph nodes, (iii) graph links.
 * The format of the nodes and links is as per the requirements of cytoscapejs network graph visualization library. 
 * @author Chiraag Subramanian
 *
 */
public class CytoscapeSearchObject {

    private boolean isNetworkEmpty;
    private List<CytoscapeNodeObject> nodes;
    private List<CytoscapeLinkObject> links;
    private String status;

    public boolean isNetworkEmpty() {
        return isNetworkEmpty;
    }

    public void setNetworkEmpty(boolean isNetworkEmpty) {
        this.isNetworkEmpty = isNetworkEmpty;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CytoscapeNodeObject> getNodes() {
        return nodes;
    }

    public void setNodes(List<CytoscapeNodeObject> nodes) {
        this.nodes = nodes;
    }

    public List<CytoscapeLinkObject> getLinks() {
        return links;
    }

    public void setLinks(List<CytoscapeLinkObject> links) {
        this.links = links;
    }

}
