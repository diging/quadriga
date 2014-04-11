package edu.asu.spring.quadriga.domain.implementation;

import edu.asu.spring.quadriga.domain.network.INetworkAnnotation;

/**
 * This class is a Annotation bean class for network nodes.
 * 
 * @author  : Sowjanya Ambati
 *
 */

public class NetworkAnnotation implements INetworkAnnotation {

	private String nodeName;
	private String nodeId;
	private String annotationId;
	private String annotationText;
	private String networkId;
	private String userName;
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getAnnotationId() {
		return annotationId;
	}
	public void setAnnotationId(String annotationId) {
		this.annotationId = annotationId;
	}
	public String getAnnotationText() {
		return annotationText;
	}
	public void setAnnotationText(String annotationText) {
		this.annotationText = annotationText;
	}
	public String getNetworkId() {
		return networkId;
	}
	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userId) {
		this.userName = userId;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
}
