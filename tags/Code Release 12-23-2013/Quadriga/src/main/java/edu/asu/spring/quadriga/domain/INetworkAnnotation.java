package edu.asu.spring.quadriga.domain;

/**
 * Interface to implement NetworkAnnotations class.
 * 
 * @author        : Sowjanya Ambati
 *
 */
public interface INetworkAnnotation {

	public abstract String getNodeName();
	
	public abstract void setNodeName(String nodeName);
	
	public abstract String getAnnotationId();
	
	public abstract void setAnnotationId(String annotationId);
	
	public abstract String getAnnotationText();
	
	public abstract void setAnnotationText(String annotationText);
	
	public abstract String getNetworkId();
	
	public abstract void setNetworkId(String networkId);
	
	public abstract String getUserId();
	
	public abstract void setUserId(String userId);
}
