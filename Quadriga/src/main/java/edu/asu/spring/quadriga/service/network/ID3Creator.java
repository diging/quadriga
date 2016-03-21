package edu.asu.spring.quadriga.service.network;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.asu.spring.quadriga.transform.Link;
import edu.asu.spring.quadriga.transform.Node;
import edu.asu.spring.quadriga.transform.PredicateNode;

public interface ID3Creator {

    public abstract String getD3JSON(Map<String, Node> nodes, List<Link> links);

}