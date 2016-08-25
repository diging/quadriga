package edu.asu.spring.quadriga.domain.network;

public interface INetworkNodeAnnotation extends INetworkAnnotation 
{
	 public abstract String getNodeId();
	 
	 public abstract void setNodeId(String nodeId);
	 
	 public abstract String getNodeName();
	 
	 public abstract void setNodeName(String nodeName);
	 
}
