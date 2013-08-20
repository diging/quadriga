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
	
	
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.INetworkNodeInfo#getId()
	 */
	@Override
	public String getId() {
		return id;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.INetworkNodeInfo#setId(java.lang.String)
	 */
	@Override
	public void setId(String id) {
		this.id = id;
	}
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.INetworkNodeInfo#getStatementType()
	 */
	@Override
	public String getStatementType() {
		return statementType;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.INetworkNodeInfo#setStatementType(java.lang.String)
	 */
	@Override
	public void setStatementType(String statementType) {
		this.statementType = statementType;
	}
	
	
}
