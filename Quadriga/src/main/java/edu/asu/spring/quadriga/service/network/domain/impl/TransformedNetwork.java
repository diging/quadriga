package edu.asu.spring.quadriga.service.network.domain.impl;

import java.util.List;
import java.util.Map;

import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;
import edu.asu.spring.quadriga.transform.Link;
import edu.asu.spring.quadriga.transform.Node;

public class TransformedNetwork implements ITransformedNetwork {

    private Map<String, Node> nodes;
    private List<Link> links;

    public TransformedNetwork(Map<String, Node> nodes, List<Link> links) {
        super();
        this.nodes = nodes;
        this.links = links;
    }

    @Override
    public Map<String, Node> getNodes() {
        return nodes;
    }

    @Override
    public void setNodes(Map<String, Node> nodes) {
        this.nodes = nodes;
    }

    @Override
    public List<Link> getLinks() {
        return links;
    }

    @Override
    public void setLinks(List<Link> links) {
        this.links = links;
    }

}
