package edu.asu.spring.quadriga.domain.network;

/**
 * Interface to extract the node info from a network
 *
 */
public interface INetworkNodeInfo {

	public abstract String getId();

	public abstract void setId(String id);

	public abstract String getStatementType();

	public abstract void setStatementType(String statementType);

	public abstract void setVersion(int version);

	public abstract int getVersion();

	public abstract void setIsTop(int isTop);

	public abstract int getIsTop();

}