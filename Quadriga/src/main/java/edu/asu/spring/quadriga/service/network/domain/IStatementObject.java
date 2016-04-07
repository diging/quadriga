package edu.asu.spring.quadriga.service.network.domain;

public interface IStatementObject {

	public abstract String getStatementId();

	public abstract void setStatementId(String statementId);

	public abstract boolean isReference();

	public abstract void setReference(boolean isReference);

}