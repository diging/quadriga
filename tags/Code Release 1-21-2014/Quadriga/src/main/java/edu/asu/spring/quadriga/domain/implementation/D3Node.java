package edu.asu.spring.quadriga.domain.implementation;

import edu.asu.spring.quadriga.domain.ID3Node;

public class D3Node implements ID3Node {

	private String nodeName;
	private String nodeId;
	
	@Override
	public String getNodeId() {
		return nodeId;
	}
	@Override
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.ID3Nodes#getNodeName()
	 */
	@Override
	public String getNodeName() {
		return nodeName;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.ID3Nodes#setNodeName(java.lang.String)
	 */
	@Override
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.ID3Nodes#getGroupId()
	 */
	@Override
	public int getGroupId() {
		return groupId;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.ID3Nodes#setGroupId(int)
	 */
	@Override
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	private int groupId;
	
}
