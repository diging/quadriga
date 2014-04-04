package edu.asu.spring.quadriga.service.network.factory.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.impl.networks.jsonobject.NodeObject;
import edu.asu.spring.quadriga.service.network.domain.INodeObjectWithStatement;
import edu.asu.spring.quadriga.service.network.domain.IStatementObject;
import edu.asu.spring.quadriga.service.network.domain.impl.NodeObjectWithStatement;
import edu.asu.spring.quadriga.service.network.domain.impl.StatementObject;
import edu.asu.spring.quadriga.service.network.factory.INodeObjectWithStatementFactory;

@Service
public class NodeObjectWithStatementFactory implements INodeObjectWithStatementFactory {

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.network.factory.impl.INodeObjectWithStatementFactory#getNodeObjectWithStatementFactory(edu.asu.spring.quadriga.domain.impl.networks.jsonobject.NodeObject, java.lang.String)
	 */
	@Override
	public INodeObjectWithStatement getNodeObjectWithStatementFactory(NodeObject nodeObject,String statementId,boolean isReference) {
		IStatementObject statementObject = new StatementObject(statementId, isReference);
		List<IStatementObject> statementObjectList = new ArrayList<IStatementObject>();
		statementObjectList.add(statementObject);
		INodeObjectWithStatement nodeObjectWithStatement = new NodeObjectWithStatement(nodeObject,statementObjectList);
		return nodeObjectWithStatement;
	}
}
