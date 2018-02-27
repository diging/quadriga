package edu.asu.spring.quadriga.web.publicwebsite.cytoscapeobjects;

import java.util.List;

public class PublicSearchObject {

    boolean isNetworkEmpty;
    List<CytoscapeNodeObject> nodes;
    List<CytoscapeLinkObject> links;
    int status;

    public boolean isNetworkEmpty() {
        return isNetworkEmpty;
    }

    public void setNetworkEmpty(boolean isNetworkEmpty) {
        this.isNetworkEmpty = isNetworkEmpty;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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
