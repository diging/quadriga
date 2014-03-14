package edu.asu.spring.quadriga.service.network.domain;

import edu.asu.spring.quadriga.domain.impl.networks.jsonobject.NodeObject;

public interface INodeObjectWithStatement {

	public abstract NodeObject getNodeObject();

	public abstract void setNodeObject(NodeObject nodeObject);

	public abstract String getStatementId();

	public abstract void setStatementId(String statementId);

	public abstract INodeObjectWithStatement getNodeObjectWithStatementFactory(NodeObject nodeObject,
			String statementId);

}