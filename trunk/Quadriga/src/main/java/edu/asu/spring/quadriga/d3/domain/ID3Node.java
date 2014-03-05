package edu.asu.spring.quadriga.d3.domain;

import java.util.List;

/**
 * Interface to represent the node in a network.
 * This class is very specific to the D3 Jquery.
 * 
 * @author Lohith Dwaraka
 */
public interface ID3Node {

	/**
	 * Getter of {@link ID3Node} node name
	 * @return				Node name of type {@link String}
	 */
	public abstract String getNodeName();

	/**
	 * Setter for {@link ID3Node} node name
	 * @param nodeName		Node name of type {@link String}
	 */
	public abstract void setNodeName(String nodeName);

	/**
	 * Getter of {@link ID3Node} group id. 
	 * Please check the group id reference in {@link ID3Constant}
	 * @return				Returns Group ID as per node type in {@link ID3Constant} 
	 */
	public abstract int getGroupId();

	/**
	 * Setter of {@link ID3Node} group id. 
	 * Please check the group id reference in {@link ID3Constant}
	 * @param groupId		Group ID as per node type in {@link ID3Constant} 
	 */
	public abstract void setGroupId(int groupId);

	/**
	 * Setter of node id of {@link ID3Node}
	 * @param nodeId		Node id as per the QStore ids of network
	 */
	public abstract void setNodeId(String nodeId);

	/**
	 * Getter of node id of {@link ID3Node}
	 * @return nodeId		Node id as per the QStore ids of network
	 */
	public abstract String getNodeId();
	

	/**
	 * Getter of statement lists the {@link ID3Node} belongs to
	 * @return				returns a {@link List} of Statement IDs
	 */
	public List<String> getStatementIdList();

	/**
	 * Setter of statement lists the {@link ID3Node} belongs to
	 * @param statementIdList			{@link List} of Statement IDs
	 */
	public void setStatementIdList(List<String> statementIdList);

}