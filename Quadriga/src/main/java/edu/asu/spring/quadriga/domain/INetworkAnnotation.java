package edu.asu.spring.quadriga.domain;

/**
 * Interface to implement NetworkAnnotations class.
 * 
 * @author        : Sowjanya Ambati
 *
 */
public interface INetworkAnnotation {

	public abstract String getAnnotationId();
	
	public abstract void setAnnotationId(String annotationId);
	
	public abstract String getAnnotationText();
	
	public abstract void setAnnotationText(String annotationText);
	
	public abstract String getNetworkId();
	
	public abstract void setNetworkId(String networkId);
	
	public abstract String getUserName();
	
	public abstract void setUserName(String userName);
}
