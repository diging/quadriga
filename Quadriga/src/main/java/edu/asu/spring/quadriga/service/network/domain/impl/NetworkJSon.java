package edu.asu.spring.quadriga.service.network.domain.impl;

import edu.asu.spring.quadriga.service.network.domain.INetworkJSon;

public class NetworkJSon implements INetworkJSon {
	private String json;
	
	public NetworkJSon(String json) {
		this.json = json;
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
	
}
