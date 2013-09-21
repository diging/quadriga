package edu.asu.spring.quadriga.service;

import java.util.List;

import edu.asu.spring.quadriga.domain.IProfile;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IUserProfileManager {
	
	public abstract String addUserProfile(String name,String service,String uri) throws QuadrigaStorageException;
	
	public abstract List<IProfile> showUserProfile(String loggedinUser) throws QuadrigaStorageException;

}
