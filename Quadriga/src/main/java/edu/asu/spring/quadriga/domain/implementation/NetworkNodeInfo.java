package edu.asu.spring.quadriga.domain.implementation;

import edu.asu.spring.quadriga.domain.INetworkNodeInfo;


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
	private String statementType ;
	private int isTop;
	private int isArchived;

	
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
		return isArchived;
	}
	@Override
	public void setVersion(int isArchived) {
		this.isArchived = isArchived;
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
