package edu.asu.spring.quadriga.service;

import edu.asu.spring.quadriga.domain.IProfile;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IUserProfileManager {
	
	public abstract String addUserProfile(String name,String service,String uri) throws QuadrigaStorageException;
	
	public abstract IProfile showUserProfile();

}
