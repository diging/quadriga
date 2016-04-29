package edu.asu.spring.quadriga.service.network.domain;

import java.util.List;
import java.util.Map;

import edu.asu.spring.quadriga.transform.Link;
import edu.asu.spring.quadriga.transform.Node;


public interface ITransformedNetwork {

    public abstract void setLinks(List<Link> links);

    public abstract List<Link> getLinks();

    public abstract void setNodes(Map<String, Node> nodes);

    public abstract Map<String, Node> getNodes();

}