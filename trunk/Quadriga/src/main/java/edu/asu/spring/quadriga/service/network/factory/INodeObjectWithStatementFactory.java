package edu.asu.spring.quadriga.service.network.factory;

import edu.asu.spring.quadriga.domain.impl.networks.jsonobject.NodeObject;
import edu.asu.spring.quadriga.service.network.domain.INodeObjectWithStatement;

public interface INodeObjectWithStatementFactory {

	public abstract INodeObjectWithStatement getNodeObjectWithStatementFactory(
			NodeObject nodeObject, String statementId);

}