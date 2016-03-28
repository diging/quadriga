package edu.asu.spring.quadriga.service;

import java.util.List;

import edu.asu.spring.quadriga.domain.IProfile;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.web.profile.impl.SearchResultBackBean;

public interface IUserProfileManager {
	
	public abstract void addUserProfile(String name, String serviceId, SearchResultBackBean resultBackBean) throws QuadrigaStorageException;
	
	public abstract List<IProfile> getUserProfiles(String loggedinUser) throws QuadrigaStorageException;
	
	public abstract void deleteUserProfile(String profileId,String serviceid, String username) throws QuadrigaStorageException;

	public abstract String retrieveServiceId(String profileid) throws QuadrigaStorageException;
}
