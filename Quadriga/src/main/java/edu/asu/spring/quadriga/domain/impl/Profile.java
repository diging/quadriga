package edu.asu.spring.quadriga.domain.impl;

import edu.asu.spring.quadriga.domain.IProfile;
import edu.asu.spring.quadriga.domain.IUser;

/**
 * This class represents the profile object variables and methods.
 * @author Rohith
 *
 */
public class Profile implements IProfile {

	private String serviceId;
	private String uri;
	private IUser userObj;
	private String profilename;
	private String description;
	private String profileId;
	
	@Override
	public String getServiceId() {
		return serviceId;
	}

	@Override
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;

	}

	@Override
	public String getUri() {
		return uri;
	}

	@Override
	public void setUri(String uri) {
		this.uri = uri;
	}

	@Override
	public IUser getUSerObj() {
		return userObj;
	}

	@Override
	public void setUserObj(IUser userObj) {
		
		this.userObj = userObj;		
	}

    @Override
    public String getProfilename() {
        return profilename;
    }

    @Override
    public void setProfilename(String profilename) {
        this.profilename = profilename;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileid) {
        this.profileId = profileid;
    }

}
