package edu.asu.spring.quadriga.domain;

public interface ID3Node {

	public abstract String getNodeName();

	public abstract void setNodeName(String nodeName);

	public abstract int getGroupId();

	public abstract void setGroupId(int groupId);

	public abstract void setNodeId(String nodeId);

	public abstract String getNodeId();

}