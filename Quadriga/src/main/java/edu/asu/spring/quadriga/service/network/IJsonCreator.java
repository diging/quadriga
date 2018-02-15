package edu.asu.spring.quadriga.service.network;

import java.util.List;
import java.util.Map;

import edu.asu.spring.quadriga.domain.network.json.CytoscapeLinkObject;
import edu.asu.spring.quadriga.domain.network.json.CytoscapeNodeObject;
import edu.asu.spring.quadriga.transform.Link;
import edu.asu.spring.quadriga.transform.Node;

public interface IJsonCreator {

    public abstract String getJson(Map<String, Node> nodes, List<Link> links);
    public abstract List<CytoscapeNodeObject> getNodes(Map<String, Node> nodes);
    public abstract List<CytoscapeLinkObject> getLinks(List<Link> links);

}