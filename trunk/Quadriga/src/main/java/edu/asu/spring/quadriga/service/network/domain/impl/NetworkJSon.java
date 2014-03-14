package edu.asu.spring.quadriga.service.network.domain.impl;

import java.util.List;

import edu.asu.spring.quadriga.d3.domain.ID3Node;
import edu.asu.spring.quadriga.service.network.domain.INetworkJSon;

public class NetworkJSon implements INetworkJSon {
	private String json;
	private List<ID3Node> nodeList;
	
	public NetworkJSon(String json, List<ID3Node> nodeList) {
		this.json = json;
		this.nodeList = nodeList;
	}
	
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.network.domain.INetworkJSon#getJson()
	 */
	@Override
	public String getJson() {
		return json;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.network.domain.INetworkJSon#setJson(java.lang.String)
	 */
	@Override
	public void setJson(String json) {
		this.json = json;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.network.domain.INetworkJSon#getNodeList()
	 */
	@Override
	public List<ID3Node> getNodeList() {
		return nodeList;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.network.domain.INetworkJSon#setNodeList(java.util.List)
	 */
	@Override
	public void setNodeList(List<ID3Node> nodeList) {
		this.nodeList = nodeList;
	}
}
