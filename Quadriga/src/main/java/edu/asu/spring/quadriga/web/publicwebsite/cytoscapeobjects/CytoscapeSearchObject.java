package edu.asu.spring.quadriga.web.publicwebsite.cytoscapeobjects;

import java.util.List;

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
