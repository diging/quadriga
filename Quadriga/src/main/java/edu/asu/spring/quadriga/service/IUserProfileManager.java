package edu.asu.spring.quadriga.service;

import java.util.List;

import edu.asu.spring.quadriga.domain.IProfile;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.profile.ISearchResult;

public interface IUserProfileManager {
	
	public abstract String addUserProfile(String name,String serviceid, String profileid, String desription ) throws QuadrigaStorageException;
	
	public abstract List<ISearchResult> showUserProfile(String loggedinUser, String serviceid) throws QuadrigaStorageException;

}
