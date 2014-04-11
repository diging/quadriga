package edu.asu.spring.quadriga.domain.implementation;

import edu.asu.spring.quadriga.domain.network.INetworkEdgeAnnotation;

public class NetworkEdgeAnnotation implements INetworkEdgeAnnotation
{
	String annotationId;
	String annotationText;
	String networkId;
	String sourceId;
	String sourceName;
	String targetId;
	String targetName;
	String targetNodeType;
	String userName;
	String objectType;
	
	@Override
	public String getAnnotationId() 
	{
		return annotationId;
	}

	@Override
	public void setAnnotationId(String annotationId) 
	{
		this.annotationId = annotationId;

	}

	@Override
	public String getAnnotationText() 
	{
		return annotationText;
	}

	@Override
	public void setAnnotationText(String annotationText) 
	{
        this.annotationText = annotationText;		
	}

	@Override
	public String getNetworkId() 
	{
		return networkId;
	}

	@Override
	public void setNetworkId(String networkId) {

		this.networkId = networkId;
	}

	@Override
	public String getSourceId() 
	{
		return sourceId;
	}

	@Override
	public void setSourceId(String sourceId) 
	{
		this.sourceId = sourceId;

	}

	@Override
	public String getSourceName() {
		return sourceName;
	}

	@Override
	public void setSourceName(String sourceName) {

		this.sourceName = sourceName;
	}

	@Override
	public String getTargetId() 
	{
		return targetId;
	}

	@Override
	public void setTargetId(String targetId) {

		this.targetId = targetId;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	@Override
	public String getTargetNodeType() 
	{
		return targetNodeType;
	}

	@Override
	public void setTargetNodeType(String targetNodeType)
	{
		this.targetNodeType = targetNodeType;

	}

	@Override
	public String getUserName() {
		return userName;
	}

	@Override
	public void setUserName(String userName) 
	{
		this.userName = userName;
		
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	
}
