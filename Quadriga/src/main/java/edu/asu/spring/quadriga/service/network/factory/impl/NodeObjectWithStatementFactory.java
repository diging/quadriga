package edu.asu.spring.quadriga.service.network.factory.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.impl.networks.jsonobject.NodeObject;
import edu.asu.spring.quadriga.service.network.domain.INodeObjectWithStatement;
import edu.asu.spring.quadriga.service.network.domain.impl.NodeObjectWithStatement;
import edu.asu.spring.quadriga.service.network.factory.INodeObjectWithStatementFactory;

@Service
public class NodeObjectWithStatementFactory implements INodeObjectWithStatementFactory {

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.network.factory.impl.INodeObjectWithStatementFactory#getNodeObjectWithStatementFactory(edu.asu.spring.quadriga.domain.impl.networks.jsonobject.NodeObject, java.lang.String)
	 */
	@Override
	public INodeObjectWithStatement getNodeObjectWithStatementFactory(NodeObject nodeObject,String statementId) {
		INodeObjectWithStatement nodeObjectWithStatement = new NodeObjectWithStatement(nodeObject,statementId);
		return nodeObjectWithStatement;
	}
}
