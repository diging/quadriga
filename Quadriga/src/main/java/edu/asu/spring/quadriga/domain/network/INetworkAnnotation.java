package edu.asu.spring.quadriga.domain.network;

import java.util.Date;

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
	
	public abstract String getObjectType();
	 
	public abstract void setObjectType(String objectType);
	
	public abstract String getCreatedBy();
	
	public abstract void setCreatedBy(String createdBy);
	
	public abstract Date getCreatedDate();
	
	public abstract void setCreatedDate(Date createdDate);
	
	public abstract String getUpdatedBy();
	
	public abstract void setUpdatedBy(String updatedBy);
	
	public abstract Date getUpdatedDate();
	
	public abstract void setUpdatedDate(Date updatedDate);
}
