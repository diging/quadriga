package edu.asu.spring.quadriga.domain;

/**
 * Interface to implement user profile
 *
 */
public interface IProfile {
	
	public abstract String getServiceName();
	
	public abstract void setServiceName(String serviceName);
	
	public abstract String getUri();
	
	public abstract void setUri(String uri);
	
	public abstract IUser getUSerObj();
	
	public abstract void setUserObj(IUser user);

}
