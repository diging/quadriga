package edu.asu.spring.quadriga.service.network.domain;

import java.util.List;

import edu.asu.spring.quadriga.d3.domain.ID3Node;

public interface INetworkJSon {

	public abstract String getJson();

	public abstract void setJson(String json);

	public abstract List<ID3Node> getNodeList();

	public abstract void setNodeList(List<ID3Node> nodeList);

}