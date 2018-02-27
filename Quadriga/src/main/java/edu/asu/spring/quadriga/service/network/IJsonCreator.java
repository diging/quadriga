package edu.asu.spring.quadriga.service.network;

import java.util.List;
import java.util.Map;

import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.transform.Link;
import edu.asu.spring.quadriga.transform.Node;
import edu.asu.spring.quadriga.web.publicwebsite.cytoscapeobjects.CytoscapeLinkObject;
import edu.asu.spring.quadriga.web.publicwebsite.cytoscapeobjects.CytoscapeNodeObject;
import edu.asu.spring.quadriga.web.publicwebsite.cytoscapeobjects.PublicSearchObject;

public interface IJsonCreator {

    public abstract String getJson(Map<String, Node> nodes, List<Link> links);
    public abstract List<CytoscapeNodeObject> getNodes(List<Node> nodeList);
    public abstract List<CytoscapeLinkObject> getLinks(List<Link> links);
    public abstract Integer submitTransformationRequest(String conceptId, IProject project);
    public abstract PublicSearchObject getSearchTransformedNetwork(Integer tokenId);

}