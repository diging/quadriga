package edu.asu.spring.quadriga.service.network.domain;

import java.util.List;

import edu.asu.spring.quadriga.domain.impl.networks.jsonobject.NodeObject;
import edu.asu.spring.quadriga.service.network.domain.INodeObjectWithStatement;
import edu.asu.spring.quadriga.service.network.domain.IStatementObject;

public interface INodeObjectWithStatement {

	public abstract INodeObjectWithStatement getNodeObjectWithStatementFactory(
			NodeObject nodeObject, List<IStatementObject> statementObjectList);

	public abstract NodeObject getNodeObject();

	public abstract void setNodeObject(NodeObject nodeObject);
	
	public abstract List<IStatementObject> getStatementObjectList();

	public abstract void setStatementId(List<IStatementObject> statementObjectList);

}