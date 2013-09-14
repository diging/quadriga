package edu.asu.spring.quadriga.domain.implementation;

import edu.asu.spring.quadriga.domain.IProfile;
import edu.asu.spring.quadriga.domain.IUser;

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
