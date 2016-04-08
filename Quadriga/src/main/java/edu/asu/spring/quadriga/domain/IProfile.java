package edu.asu.spring.quadriga.domain;

/**
 * Interface to implement user profile
 *
 */
public interface IProfile {
	
	public abstract String getServiceId();
	
	public abstract void setServiceId(String serviceName);
	
	public abstract String getUri();
	
	public abstract void setUri(String uri);
	
	public abstract IUser getUSerObj();
	
	public abstract void setUserObj(IUser user);

    public abstract void setDescription(String description);

    public abstract String getDescription();

    public abstract void setProfilename(String profilename);

    public abstract String getProfilename();

    public abstract String getProfileId();
    
    public abstract void setProfileId(String profileId);

}
