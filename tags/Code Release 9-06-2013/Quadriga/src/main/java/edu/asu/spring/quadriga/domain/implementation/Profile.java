package edu.asu.spring.quadriga.domain.implementation;

import edu.asu.spring.quadriga.domain.IProfile;

public class Profile implements IProfile {

	String serviceName;
	String uri;
	
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

}
