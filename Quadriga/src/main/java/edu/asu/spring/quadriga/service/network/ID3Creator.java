package edu.asu.spring.quadriga.service.network;

import java.util.List;
import java.util.Map;

import edu.asu.spring.quadriga.transform.Link;
import edu.asu.spring.quadriga.transform.Node;

public interface ID3Creator {

    public abstract String getD3JSON(Map<String, Node> nodes, List<Link> links);

}