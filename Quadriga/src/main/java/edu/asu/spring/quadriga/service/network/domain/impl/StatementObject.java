package edu.asu.spring.quadriga.service.network.domain.impl;

import edu.asu.spring.quadriga.service.network.domain.IStatementObject;

public class StatementObject implements IStatementObject {

	String statementId;
	boolean isReference;
	

	public StatementObject(String statementId, boolean isReference){
		this.statementId = statementId;
		this.isReference = isReference;
	}
	
	/**
	 * {@inheritDoc}
	*/
	@Override
	public String getStatementId() {
		return statementId;
	}
	/**
	 * {@inheritDoc}
	*/
	@Override
	public void setStatementId(String statementId) {
		this.statementId = statementId;
	}
	/**
	 * {@inheritDoc}
	*/
	@Override
	public boolean isReference() {
		return isReference;
	}
	/**
	 * {@inheritDoc}
	*/
	@Override
	public void setReference(boolean isReference) {
		this.isReference = isReference;
	}
	
}
