package edu.asu.spring.quadriga.domain.impl;

import edu.asu.spring.quadriga.domain.IProfile;
import edu.asu.spring.quadriga.domain.IUser;

/**
 * This class represents the profile object variables and methods.
 * @author Rohith
 *
 */
public class Profile implements IProfile {

	String serviceName;
	String uri;
	IUser userObj;
	
	@Override
	public String getServiceName() {
		return serviceName;
	}

	@Override
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;

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

}
