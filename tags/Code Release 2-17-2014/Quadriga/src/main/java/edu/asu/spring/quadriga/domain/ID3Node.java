package edu.asu.spring.quadriga.domain;

import java.util.List;

/**
 * Interface to represent the node in a network
 *
 */
public interface ID3Node {

	public abstract String getNodeName();

	public abstract void setNodeName(String nodeName);

	public abstract int getGroupId();

	public abstract void setGroupId(int groupId);

	public abstract void setNodeId(String nodeId);

	public abstract String getNodeId();
	

	public List<String> getStatementIdList();

	public void setStatementIdList(List<String> statementIdList);

}