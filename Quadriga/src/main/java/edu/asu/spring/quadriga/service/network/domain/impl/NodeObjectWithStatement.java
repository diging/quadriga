package edu.asu.spring.quadriga.service.network.domain.impl;

import edu.asu.spring.quadriga.domain.impl.networks.jsonobject.NodeObject;
import edu.asu.spring.quadriga.service.network.domain.INodeObjectWithStatement;

/**
 * Inner class to store {@link NodeObject} with statement ID it belongs to.
 * @author Lohith Dwaraka
 *
 */
public class NodeObjectWithStatement implements INodeObjectWithStatement {

	private NodeObject nodeObject;
	private String statementId;
	
	public NodeObjectWithStatement(NodeObject nodeObject,String statementId) {
		this.nodeObject = nodeObject;
		this.statementId = statementId;
	}
	
	@Override
	public INodeObjectWithStatement getNodeObjectWithStatementFactory(NodeObject nodeObject,String statementId) {
		INodeObjectWithStatement nodeObjectWithStatement = new NodeObjectWithStatement(nodeObject,statementId);
		return nodeObjectWithStatement;
	}

	@Override
	public NodeObject getNodeObject() {
		return nodeObject;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.network.domain.impl.INodeObjectWithStatement#setNodeObject(edu.asu.spring.quadriga.domain.impl.networks.jsonobject.NodeObject)
	 */
	@Override
	public void setNodeObject(NodeObject nodeObject) {
		this.nodeObject = nodeObject;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.network.domain.impl.INodeObjectWithStatement#getStatementId()
	 */
	@Override
	public String getStatementId() {
		return statementId;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.network.domain.impl.INodeObjectWithStatement#setStatementId(java.lang.String)
	 */
	@Override
	public void setStatementId(String statementId) {
		this.statementId = statementId;
	}	
}
