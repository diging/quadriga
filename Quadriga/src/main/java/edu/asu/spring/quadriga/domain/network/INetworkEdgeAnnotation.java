package edu.asu.spring.quadriga.domain.network;

public interface INetworkEdgeAnnotation extends INetworkAnnotation 
{
	
	public abstract String getSourceId();
	
	public abstract void setSourceId(String sourceId);
	
	public abstract String getSourceName();
	
	public abstract void setSourceName(String sourceName);
	
	public abstract String getTargetId();
	
	public abstract void setTargetId(String targetId);
	
	public abstract String getTargetName();
	
	public abstract void setTargetName(String targetName);
	
	public abstract String getTargetNodeType();
	
	public abstract void setTargetNodeType(String targetNodeType);

}
