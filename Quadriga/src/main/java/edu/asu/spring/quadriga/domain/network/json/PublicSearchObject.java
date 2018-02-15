package edu.asu.spring.quadriga.domain.network.json;

import java.util.List;

public class PublicSearchObject {

    String searchNodeLabel;
    String description;
    String unixName;
    boolean isNetworkEmpty;
    List<CytoscapeNodeObject> nodes;
    List<CytoscapeLinkObject> links;
    int status;

    public String getSearchNodeLabel() {
        return searchNodeLabel;
    }

    public void setSearchNodeLabel(String searchNodeLabel) {
        this.searchNodeLabel = searchNodeLabel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnixName() {
        return unixName;
    }

    public void setUnixName(String unixName) {
        this.unixName = unixName;
    }

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
