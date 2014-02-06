package edu.asu.spring.quadriga.domain;

/**
 * Interface to extract the node info from a network
 *
 */
public interface INetworkNodeInfo {

	public abstract String getId();

	public abstract void setId(String id);

	public abstract String getStatementType();

	public abstract void setStatementType(String statementType);

	public abstract void setIsArchived(int isArchived);

	public abstract int getIsArchived();

	public abstract void setIsTop(int isTop);

	public abstract int getIsTop();

}