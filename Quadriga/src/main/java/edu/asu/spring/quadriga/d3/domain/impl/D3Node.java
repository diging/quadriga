package edu.asu.spring.quadriga.d3.domain.impl;

import java.util.List;

import edu.asu.spring.quadriga.d3.domain.ID3Node;
import edu.asu.spring.quadriga.service.network.domain.IStatementObject;
/**
 * Implemenation of {@link ID3Node} domain interface.
 * Used specifically for D3 Jquery JSON building.
 * @author Lohith Dwaraka
 *
 */
public class D3Node implements ID3Node {

	private String nodeName;
	private String nodeId;
	private List<IStatementObject> statementIdList;
	private int groupId;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<IStatementObject> getStatementIdList() {
		return statementIdList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setStatementIdList(List<IStatementObject> statementIdList) {
		this.statementIdList = statementIdList;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getNodeId() {
		return nodeId;
	}
	@Override
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getNodeName() {
		return nodeName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getGroupId() {
		return groupId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	
}
