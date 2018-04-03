package edu.asu.spring.quadriga.service.network;

import java.util.List;
import java.util.Map;
import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;
import edu.asu.spring.quadriga.transform.Link;
import edu.asu.spring.quadriga.transform.Node;
import edu.asu.spring.quadriga.web.publicwebsite.graph.CytoscapeLinkObject;
import edu.asu.spring.quadriga.web.publicwebsite.graph.CytoscapeNodeObject;
import edu.asu.spring.quadriga.web.publicwebsite.graph.CytoscapeSearchObject;

public interface IJsonCreator {

    public abstract String getJson(Map<String, Node> nodes, List<Link> links);
    public abstract List<CytoscapeNodeObject> getNodes(List<Node> nodeList);
    public abstract List<CytoscapeLinkObject> getLinks(List<Link> links);
    public abstract CytoscapeSearchObject getCytoscapeSearchObject(ITransformedNetwork transformedNetwork, TransformationRequestStatus transformationRequestStatus);
   

}