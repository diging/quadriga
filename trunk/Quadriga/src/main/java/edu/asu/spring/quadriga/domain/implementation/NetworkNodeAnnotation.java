package edu.asu.spring.quadriga.domain.implementation;

import edu.asu.spring.quadriga.domain.network.INetworkNodeAnnotation;

public class NetworkNodeAnnotation implements INetworkNodeAnnotation 
{
	String annotationId;
	String annotationText;
	String networkId;
	String userName;
	String nodeId;
	String nodeName;
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
	public String getAnnotationText() {
		return annotationText;
	}

	@Override
	public void setAnnotationText(String annotationText) {
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
	public String getUserName() 
	{
		return userName;
	}

	@Override
	public void setUserName(String userName) 
	{
       this.userName = userName;
	}

	@Override
	public String getNodeId()
	{
		return nodeId;
	}

	@Override
	public void setNodeId(String nodeId)
	{
         this.nodeId = nodeId;
	}

	@Override
	public String getNodeName() {
		return nodeName;
	}

	@Override
	public void setNodeName(String nodeName) 
	{
        this.nodeName = nodeName;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	

}
