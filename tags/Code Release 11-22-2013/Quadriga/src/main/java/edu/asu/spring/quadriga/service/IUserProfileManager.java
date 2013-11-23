package edu.asu.spring.quadriga.service;

import java.util.List;

import edu.asu.spring.quadriga.domain.IProfile;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.profile.ISearchResult;
import edu.asu.spring.quadriga.profile.impl.SearchResultBackBean;
import edu.asu.spring.quadriga.profile.impl.ServiceBackBean;

public interface IUserProfileManager {
	
	public abstract String addUserProfile(String name,String serviceid, String profileid) throws QuadrigaStorageException;
	
	public abstract List<SearchResultBackBean> showUserProfile(String loggedinUser) throws QuadrigaStorageException;
	
	public abstract String deleteUserProfile(String id) throws QuadrigaStorageException;

}
