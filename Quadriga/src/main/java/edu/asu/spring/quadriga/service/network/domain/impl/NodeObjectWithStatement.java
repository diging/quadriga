package edu.asu.spring.quadriga.service.network.domain.impl;

import java.util.List;

import edu.asu.spring.quadriga.domain.impl.networks.jsonobject.NodeObject;
import edu.asu.spring.quadriga.service.network.domain.INodeObjectWithStatement;
import edu.asu.spring.quadriga.service.network.domain.IStatementObject;

/**
 * Inner class to store {@link NodeObject} with statement ID it belongs to.
 * @author Lohith Dwaraka
 *
 */
public class NodeObjectWithStatement implements INodeObjectWithStatement {

	private NodeObject nodeObject;
	private List<IStatementObject> statementObjectList;
	
	public NodeObjectWithStatement(NodeObject nodeObject,List<IStatementObject> statementObjectList) {
		this.nodeObject = nodeObject;
		this.statementObjectList = statementObjectList;
	}
	
	/**
	 * {@inheritDoc}
	*/
	@Override
	public INodeObjectWithStatement getNodeObjectWithStatementFactory(NodeObject nodeObject,List<IStatementObject> statementObjectList) {
		INodeObjectWithStatement nodeObjectWithStatement = new NodeObjectWithStatement(nodeObject,statementObjectList);
		return nodeObjectWithStatement;
	}

	/**
	 * {@inheritDoc}
	*/
	@Override
	public NodeObject getNodeObject() {
		return nodeObject;
	}

	/**
	 * {@inheritDoc}
	*/
	@Override
	public void setNodeObject(NodeObject nodeObject) {
		this.nodeObject = nodeObject;
	}

	/**
	 * {@inheritDoc}
	*/
	@Override
	public List<IStatementObject> getStatementObjectList() {
		return this.statementObjectList;
	}

	/**
	 * {@inheritDoc}
	*/
	@Override
	public void setStatementId(List<IStatementObject> statementObjectList) {
		this.statementObjectList = statementObjectList;
	}	
}
