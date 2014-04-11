package edu.asu.spring.quadriga.domain.implementation;

import edu.asu.spring.quadriga.domain.network.INetworkNodeInfo;


/**
 * @description : Network class describing the properties 
 *                of a Network object
 * 
 * @author      : Kiran Kumar Batna
 *
 */
public class NetworkNodeInfo implements INetworkNodeInfo 
{
	private String id;
	private String statementType ; // RE or AE
	private int isTop;
	private int version;

	
	@Override
	public int getIsTop() {
		return isTop;
	}
	@Override
	public void setIsTop(int isTop) {
		this.isTop = isTop;
	}
	@Override
	public int getVersion() {
		return version;
	}
	@Override
	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String getStatementType() {
		return statementType;
	}

	@Override
	public void setStatementType(String statementType) {
		this.statementType = statementType;
	}
	
	
}
