package edu.asu.spring.quadriga.service.network.impl;

import java.util.List;
import java.util.Map;

import edu.asu.spring.quadriga.transform.Link;
import edu.asu.spring.quadriga.transform.Node;

public interface JsonCreator {

    public abstract String getJson(Map<String, Node> nodes, List<Link> links);

}